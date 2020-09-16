/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>Cross-Correlation</h1>
 * The Cross-Correlation class implements
 * correlation as provided in numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.correlate.html">correlate()</a>
 * function.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */

public class CrossCorrelation {

    private double[] signal;
    private double[] kernel;
    private double[] output;

    /**
     * This constructor initialises the prerequisites required to perform cross-correlation.
     * @param s Signal to be convolved
     * @param w Kernel for convolution
     * @throws java.lang.IllegalArgumentException if kernel size is greater than or equal to signal length
     */
    public CrossCorrelation(double[] s, double[] w) {
        if (s.length <= w.length) {
            throw new IllegalArgumentException("Weight Size should be less than Signal Length");
        }
        this.signal = s;
        this.kernel = w;
    }

    /**
     * This is the default cross-correlation procedure which works in "valid" mode.
     * @return double[] The result of correlation.
     */
    public double[] crossCorrelate() {
        //Works in "valid" mode
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve("valid");
        return this.output;
    }

    /**
     * This is the discrete linear convolution procedure which works in the specified mode.
     * @param mode Mode in which correlation will work. Can be 'full', 'same' or 'valid'
     * @return double[] Result of cross-correlation.
     */
    public double[] crossCorrelate(String mode) {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve(mode);
        return this.output;
    }
}
