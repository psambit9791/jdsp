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

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.Convolution;

/**
 * <h1>Finite Impulse Response Filter Class (Abstract)</h1>
 * The FIR Filter class is a super class for all FIR filters - firWin1, firWin2 and firLS.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.2
 */
public abstract class _FIRFilter {

    /**
     * FIR Filters follow the formula y_n = sum(b_i * x_(n-i)) for all coefficients i = 0 to M.
     * For FIR filters we can set a to 1.0.
     * Similar to lfilter (from scipy) but modified for FIR application
     * @param b The numerator coefficient vector
     * @param x The signal to be filtered
     * @param zi Initial conditions for the filter delays
     * @return double[] The filtered signal
     */
    public double[] firfilter(double[] b, double[] x, double[] zi) {
        if (x.length != zi.length) {
            throw new IllegalArgumentException("Shape of x and Shape of init must to be same");
        }

        double[] fout = new double[x.length];

        Convolution c1 = new Convolution(x, b);
        double[] conv_out = c1.convolve();

        for (int i=0; i<zi.length; i++) {
            fout[i] = conv_out[i] + zi[i];
        }

        return fout;
    }

    /**
     * FIR Filters follow the formula y_n = sum(b_i * x_(n-i)) for all coefficients i = 0 to M.
     * For FIR filters we can set a to 1.0. Initial conditions are set to 0.
     * @param b The numerator coefficient vector
     * @param x The signal to be filtered
     * @return double[] The filtered signal
     */
    public double[] firfilter(double[] b, double[] x) {
        Convolution c1 = new Convolution(x, b);
        double[] conv_out = c1.convolve();

        double[] fout = UtilMethods.splitByIndex(conv_out, 0, x.length);
        return fout;
    }

}
