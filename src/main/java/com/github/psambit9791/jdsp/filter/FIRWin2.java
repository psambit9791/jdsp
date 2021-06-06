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

import com.github.psambit9791.jdsp.windows.Kaiser;

public class FIRWin2 {

    private int nyquistF;
    private int numTaps;
    private boolean antisymmetric;

    public FIRWin2(int numTaps, int samplingFreq, boolean antisymmetric) {
        this.nyquistF = (int)(samplingFreq * 0.5);
        this.numTaps = numTaps;
        this.antisymmetric = antisymmetric;
    }


    /**
     * From the given cutoff frequencies and their corresponding gains, this method constructs an FIR filter with
     * linear phase and (approximately) the given frequency response.
     * @param cutoff The cutoff frequencies for the filter
     * @param gain The filter gains at the frequency sampling points.
     * @param filterType This can be 'lowpass', 'bandpass', 'highpass' or 'bandpass'
     * @return double[] Filtered signal
     */
    public double[] get_coefficients(double[] cutoff, double[] gain, String filterType) {
        if (cutoff.length != gain.length) {
            throw new IllegalArgumentException("Size of cutoff array and gain array must be same.");
        }
        boolean passZero;

        if (filterType.equals("lowpass")) {
            if (cutoff.length < 1) {
                throw new IllegalArgumentException("For lowpass, cutoff must have at least one frequency");
            }
            passZero = true;
        }
        else if (filterType.equals("bandstop")) {
            if (cutoff.length < 2) {
                throw new IllegalArgumentException("For bandstop, cutoff must have at least two frequencies");
            }
            passZero = true;
        }
        else if (filterType.equals("highpass")) {
            if (cutoff.length < 1) {
                throw new IllegalArgumentException("For highpass, cutoff must have at least one frequency");
            }
            passZero = false;
        }
        else if (filterType.equals("bandpass")) {
            if (cutoff.length < 2) {
                throw new IllegalArgumentException("For bandpass, cutoff must have at least two frequencies");
            }
            passZero = false;
        }
        else {
            throw new IllegalArgumentException("filterType must be one of 'lowpass', 'bandpass', 'highpass' or 'bandpass'");
        }
        double[] out = {};
        return out;
    }
}
