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

/**
 * <h1>Finite Impulse Response Filter Class</h1>
 * The FIR Filter class is holds all FIR-based filter methods - firWin1, firWin2, firLS and remez.
 * The user of this interface has control over implementing the low pass, high pass, band pass and band stop filter for a specific filter class.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.2
 */
public class FIRFilter {

    private double attenuation;
    private double beta;
    private int numTaps;
    private double width;

    public FIRFilter(int numTaps, double width) {
        this.numTaps = numTaps;
        this.width = width;
        this.kaiser_attenutation();
        this.kaiser_beta();
    }

    public FIRFilter(double ripple, double width) {
        this.width = width;
        this.kaiser_order(ripple, width);
    }

    /**
     * Compputes the beta parameter for the Kaiser window given the attenuation
     */
    private void kaiser_beta() {
        if (this.attenuation > 50) {
            this.beta = 0.1102 * (this.attenuation - 0.7);
        }
        else if( this.attenuation > 21) {
            this.beta = (0.5842 * Math.pow((this.attenuation - 21), 0.4)) + 0.07866 * (this.attenuation - 21);
        }
        else {
            this.beta = 0.0;
        }
    }

    /**
     * Computes the attenuation factor of a Kaiser FIR filter
     */
    private void kaiser_attenutation() {
        this.attenuation = 2.285 * (this.numTaps - 1) * Math.PI * this.width + 7.95;
    }

    /**
     * This method computes the filter parameters (numTaps and beta) for the Kaiser Window method.
     * @param ripple Upper bound for the deviation (in dB) of the magnitude of the filter's frequency response from that of the desired filter
     * @param width Width of the transition region expressed as a fraction of the Nyquist Frequency
     */
    public void kaiser_order(double ripple, double width) {
        this.attenuation = Math.abs(ripple);
        if (this.attenuation < 8) {
            throw new IllegalArgumentException("Maximum ripple attenuation too small for Kaiser function");
        }
        this.kaiser_beta();
        this.width = width;
        this.numTaps = (int) Math.ceil((((this.attenuation - 7.95) / 2.285) / (Math.PI * this.width)) + 1);
    }

    /**
     * #########This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param cutoff The cutoff frequencies for the filter
     * @param filterType This can be 'lowpass', 'bandpass', 'highpass' or 'bandpass'
     * @return double[] Filtered signal
     */
    public double[] firWin1(double[] cutoff, String filterType) {
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

    /**
     * ##########This method implements a high pass filter with given parameters, filters the signal and returns it.
     * @param cutoff The cutoff frequencies for the filter
     * @param gain The filter gains at the frequency sampling points.
     * @param filterType This can be 'lowpass', 'bandpass', 'highpass' or 'bandpass'
     * @return double[] Filtered signal
     */
    public double[] firWin2(double[] cutoff, double[] gain, String filterType) {
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
