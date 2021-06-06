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
import com.github.psambit9791.jdsp.windows.Kaiser;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Finite Impulse Response Windowed Filter 1 Class</h1>
 * The FIRWin1 Filter class is used to compute the coefficients of the FIR filter given the properties of the filter.
 * This class extends to the abstract class _FIRFilter which also allows for computing the filter output for an input signal.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FIRWin1 extends _FIRFilter {

    private double attenuation;
    private double beta;
    private int numTaps;
    private double width;
    private int nyquistF;

    /**
     * FIRWin1 constructor for generating a filter using the number of coefficients in the filter
     * @param numTaps Number of coefficients in the filter
     * @param width Width of the Kaiser window to be used
     * @param samplingFreq Sampling frequency of the signal
     */
    public FIRWin1(int numTaps, double width, int samplingFreq) {
        this.nyquistF = (int)(samplingFreq * 0.5);
        this.numTaps = numTaps;
        this.width = width;
        this.kaiser_attenutation();
        this.kaiser_beta();
    }

    /**
     * FIRWin1 constructor for generating a filter using the ripple factor of the filter
     * @param ripple Upper bound for the deviation (in dB) of the magnitude of the filter's frequency response from that
     *               of the desired filter
     * @param width Width of the Kaiser window to be used
     * @param samplingFreq Sampling frequency of the signal
     */
    public FIRWin1(double ripple, double width, int samplingFreq) {
        this.nyquistF = (int)(samplingFreq * 0.5);
        this.width = width;
        this.kaiser_order(ripple, width);
    }

    /**
     * Computes the beta parameter for the Kaiser window given the attenuation
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
        this.attenuation = 2.285 * (this.numTaps - 1) * Math.PI * (float)(this.width/this.nyquistF) + 7.95;
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
     * This method computes the coefficients of a finite impulse response filter using a Kaiser window. The filter will
     * have linear phase; it will be Type I if `numtaps` is odd and Type II if `numtaps` is even.
     * @param cutoff The cutoff frequencies for the filter
     * @param filterType This can be 'lowpass', 'bandpass', 'highpass' or 'bandpass'
     * @param scale Scale the coefficients so that the frequency response is exactly unity at either 0, the Nyquist
     *              Frequency or the centre of the first passband.
     * @return double[] Filtered signal
     */
    public double[] compute_coefficients(double[] cutoff, String filterType, boolean scale) {

        for (int i=0; i<cutoff.length; i++) {
            cutoff[i] = cutoff[i]/this.nyquistF;
            if (cutoff[i] >= 1 || cutoff[i] <= 0) {
                throw new IllegalArgumentException("Invalid cutoff frequency: must be greater than 0 and less than Nyquist frequency");
            }
        }

        double[] difference = UtilMethods.diff(cutoff);
        if (StatUtils.min(difference) <= 0) {
            throw new IllegalArgumentException("Invalid cutoff frequency: must be strictly increasing");
        }

        Kaiser w = new Kaiser(this.numTaps);
        double[] window = w.getWindow(this.beta);

        boolean passZero;
        if (filterType.equals("lowpass")) {
            if (cutoff.length < 1) {
                throw new IllegalArgumentException("For lowpass, cutoff must have only one frequency");
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
                throw new IllegalArgumentException("For highpass, cutoff must have only one frequency");
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

        boolean passNyquist = ((cutoff.length & 1) == 1) | passZero;
        if (passNyquist && this.numTaps%2 == 0) {
            throw new ArithmeticException("A filter with an even number of coefficients must have zero response at the Nyquist frequency");
        }

        if (passZero) {
            cutoff = UtilMethods.concatenateArray(new double[]{0.0}, cutoff);
        }
        if (passNyquist) {
            cutoff = UtilMethods.concatenateArray(cutoff, new double[]{1.0});
        }

        double alpha = 0.5 * (this.numTaps - 1);
        double[] m = UtilMethods.arange(0.0, (double)numTaps, 1.0);
        m = UtilMethods.scalarArithmetic(m, alpha, "sub");
        double[] h = new double[this.numTaps];
        Arrays.fill(h, 0.0);

        double[] temp_right, temp_left;

        for (int i=0; i<cutoff.length; i=i+2) {
            double left = cutoff[i];
            double right = cutoff[i+1];

            temp_right = UtilMethods.sinc(UtilMethods.scalarArithmetic(m, right, "mul"));
            temp_right = UtilMethods.scalarArithmetic(temp_right, right, "mul");

            temp_left = UtilMethods.sinc(UtilMethods.scalarArithmetic(m, left, "mul"));
            temp_left = UtilMethods.scalarArithmetic(temp_left, left, "mul");

            h = MathArrays.ebeAdd(h, temp_right);
            h = MathArrays.ebeSubtract(h, temp_left);
        }

        h = MathArrays.ebeMultiply(h, window);

        if (scale) {
            double left = cutoff[0];
            double right = cutoff[1];
            double scaleFreq;

            if (left == 0.0) {
                scaleFreq = 0.0;
            }
            else if (right == 1.0) {
                scaleFreq = 1.0;
            }
            else {
                scaleFreq = 0.5 * (left + right);
            }

            scaleFreq = Math.PI * scaleFreq;
            double[] c = UtilMethods.scalarArithmetic(m, scaleFreq, "mul");
            c = UtilMethods.trigonometricArithmetic(c, "cos");
            double s = Arrays.stream(MathArrays.ebeMultiply(h, c)).sum();
            h = UtilMethods.scalarArithmetic(h, s, "div");
        }
        return h;
    }
}
