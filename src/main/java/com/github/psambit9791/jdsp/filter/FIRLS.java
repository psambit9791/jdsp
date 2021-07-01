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

    public FIRLS(int numTaps, double samplingFreq) {
        if (numTaps % 2 == 1 || numTaps < 1) {
            throw new IllegalArgumentException("numTaps must be odd and greater than 0");
        }
        this.numTaps = numTaps;
        this.nyquistF = samplingFreq * 0.5;
    }

    public FIRLS(int numTaps) {
        if (numTaps % 2 == 1 || numTaps < 1) {
            throw new IllegalArgumentException("numTaps must be odd and greater than 0");
        }
        this.numTaps = numTaps;
        this.nyquistF = 1.0;
    }

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

        // Constraints set

        double[] out = new double[22];
        return out;
    }

    public double[] computeCoefficients(double[] cutoff, double[] gains) {
        double[] weights = new double[cutoff.length/2];
        Arrays.fill(weights, 1.0);
        return this.computeCoefficients(cutoff, gains, weights);
    }
}
