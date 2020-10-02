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
 * <h1>Frequency Filter Interface</h1>
 * The Frequency Filter interface is implemented by all frequency-based filter classes - Bessel, Butterworth and Chebyshev.
 * The user of this interface has control over implementing the low pass, high pass, band pass and band stop filter for a specific filter class.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public interface _FrequencyFilter {

    /**
     * This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] lowPassFilter(int order, double cutoffFreq);

    /**
     * This method implements a high pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] highPassFilter(int order, double cutoffFreq);

    /**
     * This method implements a band pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] bandPassFilter(int order, double lowCutoff, double highCutoff);

    /**
     * This method implements a band stop filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] bandStopFilter(int order, double lowCutoff, double highCutoff);

}
