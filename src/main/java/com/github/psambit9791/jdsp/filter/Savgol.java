/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.filter;

import com.github.psambit9791.jdsp.misc.Polynomial;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.Convolution;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;
import java.util.Arrays;

/**
 * <h2>Savitzky–Golay Filter</h2>
 * The Savgol class implements the Savitzky–Golay filter in 5 modes of operation: 'nearest', 'constant', 'mirror', 'wrap' and 'interp'.
 * Reference <a href="https://en.wikipedia.org/wiki/Savitzky%E2%80%93Golay_filter">article</a> for more information on Savitzky–Golay Filters.
 *  
 *
 * @author  Sambit Paul
 * @version 3.0
 */
public class Savgol implements _KernelFilter{

    private int windowSize;
    private int polyOrder;
    private double[] output;
    private int deriv;
    private double delta;

    private double[] coeffs;

    /**
     * This constructor initialises the prerequisites required to use Savgol filter.
     * deriv is set to 0 and delta is set to 1
     * @param windowSize Size of the filter window/kernel
     * @param polynomialOrder The order of the polynomial used to fit the samples
     */
    public Savgol(int windowSize, int polynomialOrder) {
        if (polynomialOrder >= windowSize) {
            throw new IllegalArgumentException("polynomialOrder must be less that windowSize");
        }
        this.windowSize = windowSize;
        this.polyOrder = polynomialOrder;
        this.deriv = 0;
        this.delta = 1;
    }

    /**
     * This constructor initialises the prerequisites required to use Savgol filter.
     * @param windowSize Size of the filter window/kernel
     * @param polynomialOrder The order of the polynomial used to fit the samples
     * @param deriv The order of the derivative to compute
     * @param delta The spacing of the samples to which the filter will be applied. Used only if deriv greater than 0
     */
    public Savgol(int windowSize, int polynomialOrder, int deriv, double delta) {
        if (polynomialOrder >= windowSize) {
            throw new IllegalArgumentException("polynomialOrder must be less that windowSize");
        }
        this.windowSize = windowSize;
        this.polyOrder = polynomialOrder;
        this.deriv = deriv;
        this.delta = delta;
    }

    private void fitEdge(double[] signal, double[] output, int windowStart, int windowStop, int interpStart, int interpStop) {
        double[] xEdge = UtilMethods.splitByIndex(signal, windowStart, windowStop);
        double[] polyCoeffs = Polynomial.polyfit(UtilMethods.arange(0.0, (double)(windowStop - windowStart), 1.0), xEdge, this.polyOrder);

        if (this.deriv > 0) {
            polyCoeffs = Polynomial.polyder(polyCoeffs, this.deriv);
        }

        double[] i = UtilMethods.arange((double)(interpStart - windowStart), (double)(interpStop - windowStart), 1.0);
        double[] values = Polynomial.polyval(polyCoeffs, i);
        double divisor = Math.pow(this.delta, this.deriv);
        for (int k=0; k<values.length; k++) {
            values[k] = values[k] / divisor;
        }
        System.arraycopy(values, 0, this.output, interpStart, values.length);
    }

    private void fitEdgePolyfit(double[] signal, int windowLength) {
        int halflen = windowLength/2;
        int n = signal.length;
        this.fitEdge(signal, this.output, 0, windowLength, 0, halflen);
        this.fitEdge(signal, this.output, n-windowLength, n, n-halflen, n);
    }

    /**
     * Compute the coefficients for a 1-d Savitzky-Golay FIR filter based on the parameters provided.
     * @throws java.lang.IllegalArgumentException if window size is even
     * @return the coefficients for a 1-d Savitzky-Golay FIR filter
     */
    public double[] savgolCoeffs() throws IllegalArgumentException {
        int halflen = this.windowSize/2;
        int rem = this.windowSize%2;

        if (rem == 0) {
            throw new IllegalArgumentException("windowSize must be odd");
        }
        double pos = halflen;

        double[] x = UtilMethods.arange(-pos, this.windowSize-pos, 1);
        x = UtilMethods.reverse(x);

        int[] order = UtilMethods.arange(0, polyOrder+1, 1);

        double[][] A = new double[order.length][x.length];
        for (int i = 0; i<order.length; i++) {
            for (int j = 0; j<x.length; j++) {
                A[i][j] = Math.pow(x[j], order[i]);
            }
        }

        double[] y = new double[order.length];
        Arrays.fill(y, 0);

        y[this.deriv] = CombinatoricsUtils.factorial(this.deriv)/(Math.pow(this.delta, this.deriv));
        A = UtilMethods.pseudoInverse(A);
        this.coeffs = MatrixUtils.createRealMatrix(A).operate(y);

        return this.coeffs;
    }

    /**
     * Convolves the 1-d Savitzky-Golay coefficients with the signals in "interp" mode
     * @param signal Signal to be filtered
     * @return double[] Filtered signal
     */
    public double[] filter(double[] signal) {
        this.savgolCoeffs();
        Convolution c = new Convolution(signal, this.coeffs);
        if (this.windowSize > signal.length) {
            throw new IllegalArgumentException("For interp mode, window size should be less than signal size");
        }
        this.output = c.convolve1d("constant");
        fitEdgePolyfit(signal, this.windowSize);
        return this.output;
    }

    /**
     * Convolves the 1-d Savitzky-Golay coefficients with the signals
     * Operates in 4 modes of convolution for filtering: "nearest", "constant", "mirror", "wrap"
     * @param signal Signal to be filtered
     * @param mode Mode of Filter operation
     * @throws java.lang.IllegalArgumentException if mode is not nearest, constant, mirror, wrap or interp
     * @throws java.lang.IllegalArgumentException if mode is interp and windowSize is greater than signal length
     * @return double[] Filtered signal
     */
    public double[] filter(double[] signal, String mode) throws IllegalArgumentException {
        if (!mode.equals("nearest") && !mode.equals("constant") && !mode.equals("mirror") && !mode.equals("wrap") && !mode.equals("interp")) {
            throw new IllegalArgumentException("mode must be mirror, constant, nearest, wrap or interp");
        }
        this.savgolCoeffs();
        Convolution c = new Convolution(signal, this.coeffs);
        if (mode.equals("interp")) {
            if (this.windowSize > signal.length) {
                throw new IllegalArgumentException("For interp mode, window size should be less than signal size");
            }
            this.output = c.convolve1d("constant");
            fitEdgePolyfit(signal, this.windowSize);
        }
        else {
            this.output = c.convolve1d(mode);
        }
        return this.output;
    }
}
