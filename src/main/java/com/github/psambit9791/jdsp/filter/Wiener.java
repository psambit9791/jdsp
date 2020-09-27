/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.filter;

import com.github.psambit9791.jdsp.signal.Convolution;
import com.github.psambit9791.jdsp.signal.CrossCorrelation;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Wiener Filter</h1>
 * The Wiener class implements the Wiener filter which is usually used as a sharpening filter.
 * Reference <a href="http://www.owlnet.rice.edu/~elec539/Projects99/BACH/proj2/wiener.html">article</a> for more information on Wiener Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Wiener implements _KernelFilter {

    private double[] signal;
    private int windowSize;

    /**
     * This constructor initialises the prerequisites required to use Wiener filter. Default window size set to 3.
     * @param s Signal to be filtered
     * @throws java.lang.IllegalArgumentException if wsize (3) is greater than or equal to signal length
     */
    public Wiener(double[] s) throws IllegalArgumentException{
        if (3 >= s.length) {
            throw new IllegalArgumentException("Signal Length has to be greater than 3.");
        }
        this.signal = s;
        this.windowSize = 3;
    }

    /**
     * This constructor initialises the prerequisites required to use Wiener filter.
     * @param s Signal to be filtered
     * @throws java.lang.IllegalArgumentException if wsize is greater than or equal to signal length
     * @param wsize Window size for the filter
     */
    public Wiener(double[] s, int wsize) throws IllegalArgumentException{
        if (wsize >= s.length) {
            throw new IllegalArgumentException("Window size cannot be greater than or equal to signal length");
        }
        this.signal = s;
        this.windowSize = wsize;
    }

    /**
     * This method implements a Wiener filter with given parameters, applies it on the signal and returns it.
     * @return double[] Filtered signal
     */
    public double[] filter() {
        double[] cons = new double[this.windowSize];
        Arrays.fill(cons, 1);

        double[] localMean;
        double[] localVariance;

        // Estimating the local mean
        Convolution c1 = new Convolution(this.signal, cons);
        localMean = c1.convolve("same");
        localMean = MathArrays.scale((1.0/this.windowSize), localMean);

        // Estimating the local variance
        double[] signalSquare = MathArrays.ebeMultiply(this.signal, this.signal);
        double[] meanSquare = MathArrays.ebeMultiply(localMean, localMean);
        CrossCorrelation c2 = new CrossCorrelation(signalSquare, cons);
        localVariance = c2.crossCorrelate("same");
        localVariance = MathArrays.scale((1.0/this.windowSize), localVariance);
        localVariance = MathArrays.ebeSubtract(localVariance, meanSquare);

        // Estimating the noise as the Average of the Local Variance of the Signal
        double noiseMean = StatUtils.mean(localVariance);

        double[] res = MathArrays.ebeSubtract(this.signal, localMean);
        double[] temp = new double[localVariance.length];
        for (int i=0; i<temp.length; i++) {
            temp[i] = (1.0 - noiseMean/localVariance[i]);
        }
        res = MathArrays.ebeMultiply(res, temp);
        res = MathArrays.ebeAdd(res, localMean);

        double[] out = new double[localVariance.length];
        for (int i=0; i<out.length; i++) {
            if (localVariance[i] < noiseMean) {
                out[i] = localMean[i];
            }
            else {
                out[i] = res[i];
            }
        }
        return out;
    }
}
