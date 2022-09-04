/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp.filter;


import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.linear.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Finite Impulse Response Filter Class using Least Squares Minimization</h1>
 * The FIRLS Filter class is used to compute the best coefficients of the linear phase FIR filter using least squares minimization
 * for the specified band and their desired gains.
 * This class extends to the abstract class _FIRFilter which also allows for computing the filter output for an input signal.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FIRLS extends _FIRFilter {

    private double nyquistF;
    private int numTaps;

    /**
     * FIRLS constructor for generating a filter using the number of coefficients in the filter
     * @param numTaps Number of coefficients in the filter
     * @param samplingFreq Sampling frequency of the signal
     */
    public FIRLS(int numTaps, double samplingFreq) {
        if (numTaps % 2 == 0 || numTaps < 1) {
            throw new IllegalArgumentException("numTaps must be odd and greater than 0");
        }
        this.numTaps = numTaps;
        this.nyquistF = samplingFreq * 0.5;
    }

    /**
     * FIRLS constructor for generating a filter using the number of coefficients in the filter with the sampling frequency set to 1.0
     * @param numTaps Number of coefficients in the filter
     */
    public FIRLS(int numTaps) {
        if (numTaps % 2 == 0 || numTaps < 1) {
            throw new IllegalArgumentException("numTaps must be odd and greater than 0");
        }
        this.numTaps = numTaps;
        this.nyquistF = 1.0;
    }

    /**
     * This method computes the coefficients of a finite impulse response filter which has the best approximation of the desired gains
     * computed using the least squares solution.
     * @param cutoff The cutoff frequencies for the filter. Must be non-decreasing and less than or equal to the Nyquist Frequency.
     * @param gains Desired gains at the start and stop of each cutoff band. Must be same length as cutoff.
     * @param weights A relative weighting to give to each band region when solving the least squares problem. Must be half the length of gains.
     * @return double[] Filtered signal
     */
    public double[] computeCoefficients(double[] cutoff, double[] gains, double[] weights) {

        // Constraint testing for arrays
        if (cutoff.length % 2 == 1) {
            throw new IllegalArgumentException("Cutoff must contain frequency pairs");
        }
        if (cutoff.length != gains.length) {
            throw new IllegalArgumentException("Cutoff length and gains length should be same");
        }
        if (weights.length != gains.length/2) {
            throw new IllegalArgumentException("Weights length must be exactly half of the cutoff length");
        }

        for (double v : cutoff) {
            if (v < 0) {
                throw new IllegalArgumentException("Cutoff frequencies cannot be negative");
            }
        }

        for (double v : weights) {
            if (v < 0) {
                throw new IllegalArgumentException("Weights must be non-negative");
            }
        }

        if (!UtilMethods.isSorted(cutoff, false)) {
            throw new IllegalArgumentException("Cutoff frequencies must be non-decreasing");
        }

        Double[] arr = new Double[cutoff.length];
        for (int i=0; i<cutoff.length; i++) {
            arr[i] = cutoff[i];
        }
        Set<Double> targetSet = new HashSet<Double>(Arrays.asList(arr));

        if (arr.length != targetSet.size()) {
            throw new IllegalArgumentException("Cutoff array cannot have any duplicates");
        }

        if (arr[0] != 0 || arr[arr.length - 1] != this.nyquistF) {
            throw new IllegalArgumentException("Cutoff must start with 0 and end with the Nyquist frequency");
        }

        int semi_cutoff = cutoff.length/2;

        // Reshape vectors
        RealMatrix bands = MatrixUtils.createRealMatrix(semi_cutoff, 2);
        RealMatrix desired = MatrixUtils.createRealMatrix(semi_cutoff, 2);
        RealMatrix w = MatrixUtils.createRealMatrix(semi_cutoff, 1);

        int index_cutoff = 0;
        int index_gains = 0;
        for (int i=0; i<semi_cutoff; i++) {
            bands.addToEntry(i, 0, cutoff[index_cutoff++]);
            bands.addToEntry(i, 1, cutoff[index_cutoff++]);

            desired.addToEntry(i, 0, gains[index_gains++]);
            desired.addToEntry(i, 1, gains[index_gains++]);

            w.addToEntry(i, 0, weights[i]);
        }

        bands = bands.scalarMultiply(1/this.nyquistF);

        // Generate the Q matrix
        double[] n = UtilMethods.arange(0.0, (double)this.numTaps, 1.0);
        RealMatrix[] holder = new RealMatrix[this.numTaps];
        RealMatrix q = MatrixUtils.createRealMatrix(this.numTaps, semi_cutoff);
        RealMatrix qout = MatrixUtils.createRealMatrix(this.numTaps, 1);

        for (int i=0; i<this.numTaps; i++) {
            holder[i] = bands.scalarMultiply(n[i]);
            holder[i].walkInOptimizedOrder(new DefaultRealMatrixChangingVisitor() {
                @Override
                public double visit(int row, int column, double value) {
                    return UtilMethods.sinc(value);
                }
            });

            holder[i] = UtilMethods.ebeMultiply(holder[i], bands);

            for (int row=0; row<semi_cutoff; row++) {
                q.setEntry(i, row, holder[i].getEntry(row, 1) - holder[i].getEntry(row, 0));
            }
        }

        qout = q.multiply(w);
        double[] q_double = new double[qout.getRowDimension()];
        for (int i=0; i<q_double.length; i++) {
            q_double[i] = qout.getEntry(i, 0);
        }

        int M = (this.numTaps - 1)/2;

        double[] temp1 = UtilMethods.splitByIndex(q_double, 0, M+1);
        double[] temp2 = UtilMethods.splitByIndex(q_double, M, q_double.length);

        RealMatrix Q1 = MatrixUtils.createRealMatrix(UtilMethods.toeplitz(temp1));
        RealMatrix Q2 = MatrixUtils.createRealMatrix(UtilMethods.hankel(temp1, temp2));

        RealMatrix Q = Q1.add(Q2);

        // Calculate b vector
        RealMatrix bands_diff = MatrixUtils.createRealMatrix(semi_cutoff, 1);
        for (int i=0; i<semi_cutoff; i++) {
            bands_diff.setEntry(i, 0, bands.getEntry(i, 1) - bands.getEntry(i, 0));
        }
        RealMatrix desired_diff = MatrixUtils.createRealMatrix(semi_cutoff, 1);
        for (int i=0; i<semi_cutoff; i++) {
            desired_diff.setEntry(i, 0, desired.getEntry(i, 1) - desired.getEntry(i, 0));
        }

        n = UtilMethods.splitByIndex(n,0, M+1);
        int short_n = n.length;

        RealMatrix m = UtilMethods.ebeDivide(desired_diff, bands_diff);
        RealMatrix band0 = bands.getSubMatrix(0, bands.getRowDimension()-1, 0, 0);
        RealMatrix desired0 = desired.getSubMatrix(0, desired.getRowDimension()-1, 0, 0);

        RealMatrix c = desired0.subtract(UtilMethods.ebeMultiply(band0, m));
        RealMatrix mbc;

        holder = new RealMatrix[short_n];
        RealMatrix b = MatrixUtils.createRealMatrix(short_n, semi_cutoff);

        for (int i=0; i<short_n; i++) {
            holder[i] = bands.scalarMultiply(n[i]);
            holder[i].walkInOptimizedOrder(new DefaultRealMatrixChangingVisitor() {
                @Override
                public double visit(int row, int column, double value) {
                    return UtilMethods.sinc(value);
                }
            });

            holder[i] = UtilMethods.ebeMultiply(holder[i], bands);
            mbc = UtilMethods.ebeMultiply(bands, m, "column");
            mbc = UtilMethods.ebeAdd(mbc, c, "column");
            holder[i] = UtilMethods.ebeMultiply(holder[i], mbc);

            if (i == 0) {
                RealMatrix temp = UtilMethods.ebeMultiply(bands, bands);
                temp = UtilMethods.ebeMultiply(temp, m, "column");
                temp = temp.scalarMultiply(0.5);
                holder[i] = UtilMethods.ebeSubtract(holder[i], temp);
            }
            else {
                RealMatrix temp = bands.scalarMultiply(Math.PI*n[i]);
                temp = UtilMethods.ebeMultiply(temp, m, "column");
                temp = temp.scalarMultiply(Math.pow(n[i] * Math.PI, 2));
                holder[i] = UtilMethods.ebeSubtract(holder[i], temp);
            }
            for (int row=0; row<semi_cutoff; row++) {
                b.setEntry(i, row, holder[i].getEntry(row, 1) - holder[i].getEntry(row, 0));
            }
        }

        b = b.multiply(w);
        RealVector b_final = b.getColumnVector(0);

        // Solve for Q and b to get a

        DecompositionSolver solver = new SingularValueDecomposition(Q).getSolver();
        RealVector solution = solver.solve(b_final);
        double[] a = solution.toArray();

        // Process a and return coefficients
        double[] out = new double[(a.length-1)*2 + 1];
        int index = 0;
        for (int i = a.length-1; i>=1; i--) {
            out[index++] = a[i];
        }
        out[index++] = a[0] * 2;
        for (int i = 1; i<a.length; i++) {
            out[index++] = a[i];
        }
        return out;
    }

    /**
     * This method computes the coefficients of a finite impulse response filter which has the best approximation of the desired gains
     * computed using the least squares solution. The weights are set toa default value of 1.0.
     * @param cutoff The cutoff frequencies for the filter. Must be non-decreasing and less than or equal to the Nyquist Frequency.
     * @param gains Desired gains at the start and stop of each cutoff band. Must be same length as cutoff.
     * @return double[] Filtered signal
     */
    public double[] computeCoefficients(double[] cutoff, double[] gains) {
        double[] weights = new double[cutoff.length/2];
        Arrays.fill(weights, 1.0);
        return this.computeCoefficients(cutoff, gains, weights);
    }
}
