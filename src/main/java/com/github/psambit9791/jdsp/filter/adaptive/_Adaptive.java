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

package com.github.psambit9791.jdsp.filter.adaptive;

/**
 * <h1>Adaptive Filter Class (Interface)</h1>
 * The Adaptive Filter class is a super class for all Adaptive filters - AP, GNGD, LMS. NLMS, NSSLMS, SSLMS and RLS.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public interface _Adaptive {

    /**
     * Dictates how the filter weights initialization should be done.
     *      RANDOM: filter weights get an initial random value ranging from 0 to 1
     *      ZEROS: filter weights get initial value 0
     */
    public enum WeightsFillMethod {
        RANDOM,
        ZEROS
    }

    /**
     * Run the adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
     * match the desired signal.
     * @param desired desired signal that you want after filtering of x
     * @param x input signal that you want to filter with the LMS adaptive filter to achieve the desired signal
     */
    public void filter(double[] desired, double[] x);
}
