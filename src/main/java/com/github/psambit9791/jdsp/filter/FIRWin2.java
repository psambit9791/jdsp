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
import com.github.psambit9791.jdsp.windows.Hamming;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Finite Impulse Response Windowed Filter 1 Class</h1>
 * The FIRWin2 Filter class is used to compute the coefficients of the linear phase FIR filter given the cutoff frequencies,
 * their corresponding gains and the number of filter coefficients.
 * This class extends to the abstract class _FIRFilter which also allows for computing the filter output for an input signal.
 * This class can create 4 types of filters:
 * - Type 1 filters
 * - Type 2 filters (which have zero response at the Nyquist frequency)
 * - Type 3 filters (which have zero response at the zero and Nyquist frequencies)
 * - Type 4 filters (which have zero response at the zero frequency)
 *
 * The possible combinations to achieve each of these filters is:
 * - Type 1: 'numTaps' is odd and 'antisymmetric' is false
 * - Type 2: 'numTaps' is even and 'antisymmetric' is false
 * - Type 3: 'numTaps' is odd and 'antisymmetric' is true
 * - Type 4: 'numTaps' is even and 'antisymmetric' is true
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FIRWin2 {

    private int nyquistF;
    private int numTaps;
    private boolean antisymmetric;
    private int ftype;
    private int nfreqs;

    public FIRWin2(int numTaps, int samplingFreq, boolean antisymmetric) {
        this.nyquistF = (int)(samplingFreq * 0.5);
        this.numTaps = numTaps;
        this.antisymmetric = antisymmetric;

        if (!this.antisymmetric) {
            if (this.numTaps%2 == 0) {
                this.ftype = 2;
            }
            else {
                this.ftype = 1;
            }
        }
        else {
            if (this.numTaps%2 == 0) {
                this.ftype = 4;
            }
            else {
                this.ftype = 3;
            }
        }
    }

    public FIRWin2(int numTaps, int samplingFreq) {
        this.nyquistF = (int)(samplingFreq * 0.5);
        this.numTaps = numTaps;
        this.antisymmetric = false;

        if (this.numTaps%2 == 0) {
            this.ftype = 2;
        }
        else {
            this.ftype = 1;
        }
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

        int base = 2;
        int log_val = (int)(Math.ceil(UtilMethods.log(numTaps, base)));
        this.nfreqs = (int)(UtilMethods.antilog(log_val, base) + 1);


        double[] x = UtilMethods.linspace(0.0, (double)(this.nyquistF), this.nfreqs, true);
        double[] fx = UtilMethods.interpolate(x, cutoff, gain);

        double[] outfull = {};

        //// FILL IN CODE FOR SHIFTING COEFFICIENTS
        //// FILL IN CODE FOR IFFT

        Hamming w = new Hamming(this.numTaps);
        double[] window = w.getWindow();

        double[] out = UtilMethods.splitByIndex(outfull, 0, this.numTaps);
        out = MathArrays.ebeMultiply(out, window);

        if (this.ftype == 3) {
            out[out.length/2] = 0.0;
        }
        return out;
    }
}
