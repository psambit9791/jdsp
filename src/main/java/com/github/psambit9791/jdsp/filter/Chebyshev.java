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
 * <h1>Chebyshev Filter</h1>
 * The Chebyshev class implements low-pass, high-pass, band-pass and band-stop filter using the Chebyshev Type I and Type II equations.
 * Has some pass-band ripple but a better (steeper) roll-off rate than Butterworth Filter.
 * Reference <a href="https://en.wikipedia.org/wiki/Chebyshev_filter">article</a> for more information on Chebyshev Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class Chebyshev implements _FrequencyFilter{
    private double[] signal;
    private double samplingFreq;
    private double[] output;
    private int filterType;
    private double rippleFactor;

    /**
     * This constructor initialises the prerequisites
     * required to use Chebyshev filter.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal
     * @param rf The maximum ripple allowed below unity gain in the pass band
     * @param filterType Type of Chebyshev filter. 1: Type I, 2: Type II
     */
    public Chebyshev(double[] s, double Fs, double rf, int filterType) {
        this.signal = s;
        this.samplingFreq = Fs;
        this.rippleFactor = rf;
        this.filterType = filterType;
    }

    /**
     * This constructor initialises the prerequisites
     * required to use Chebyshev filter. Default mode operation is of Type I.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal
     * @param rf The maximum ripple allowed below unity gain in the pass band
     */
    public Chebyshev(double[] s, double Fs, double rf) {
        this.signal = s;
        this.samplingFreq = Fs;
        this.rippleFactor = rf;
        this.filterType = 1;
    }

    /**
     * This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] lowPassFilter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        if (this.filterType == 1) {
            uk.me.berndporr.iirj.ChebyshevI lp = new uk.me.berndporr.iirj.ChebyshevI();
            lp.lowPass(order, this.samplingFreq, cutoffFreq, rippleFactor);
            for (int i =0; i<this.output.length; i++) {
                this.output[i] = lp.filter(this.signal[i]);
            }
        }
        else if (this.filterType == 2) {
            uk.me.berndporr.iirj.ChebyshevII lp = new uk.me.berndporr.iirj.ChebyshevII();
            lp.lowPass(order, this.samplingFreq, cutoffFreq, rippleFactor);
            for (int i =0; i<this.output.length; i++) {
                this.output[i] = lp.filter(this.signal[i]);
            }
        }
        else {
            throw new ExceptionInInitializerError("Chebyshev filter can only be of Type 1 and 2.");
        }
        return this.output;
    }

    /**
     * This method implements a high pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] highPassFilter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        if (this.filterType == 1) {
            uk.me.berndporr.iirj.ChebyshevI hp = new uk.me.berndporr.iirj.ChebyshevI();
            hp.highPass(order, this.samplingFreq, cutoffFreq, rippleFactor);
            for (int i =0; i<this.output.length; i++) {
                this.output[i] = hp.filter(this.signal[i]);
            }
        }
        else if (this.filterType == 2) {
            uk.me.berndporr.iirj.ChebyshevII hp = new uk.me.berndporr.iirj.ChebyshevII();
            hp.highPass(order, this.samplingFreq, cutoffFreq, rippleFactor);
            for (int i =0; i<this.output.length; i++) {
                this.output[i] = hp.filter(this.signal[i]);
            }
        }
        else {
            throw new ExceptionInInitializerError("Chebyshev filter can only be of Type 1 and 2.");
        }
        return this.output;
    }

    /**
     * This method implements a band pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @throws java.lang.IllegalArgumentException The lower cutoff frequency is greater than the higher cutoff frequency
     * @return double[] Filtered signal
     */
    public double[] bandPassFilter(int order, double lowCutoff, double highCutoff) throws IllegalArgumentException {
        if (lowCutoff >= highCutoff) {
            throw new IllegalArgumentException("Lower Cutoff Frequency cannot be more than the Higher Cutoff Frequency");
        }
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        this.output = new double[this.signal.length];
        if (this.filterType == 1) {
            uk.me.berndporr.iirj.ChebyshevI bp = new uk.me.berndporr.iirj.ChebyshevI();
            bp.bandPass(order, this.samplingFreq, centreFreq, width, rippleFactor);
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = bp.filter(this.signal[i]);
            }
        }
        else if (this.filterType == 2) {
            uk.me.berndporr.iirj.ChebyshevII bp = new uk.me.berndporr.iirj.ChebyshevII();
            bp.bandPass(order, this.samplingFreq, centreFreq, width, rippleFactor);
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = bp.filter(this.signal[i]);
            }
        }
        else {
            throw new ExceptionInInitializerError("Chebyshev filter can only be of Type 1 and 2.");
        }
        return this.output;
    }

    /**
     * This method implements a band stop filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @throws java.lang.IllegalArgumentException The lower cutoff frequency is greater than the higher cutoff frequency
     * @return double[] Filtered signal
     */
    public double[] bandStopFilter(int order, double lowCutoff, double highCutoff) throws IllegalArgumentException {
        if (lowCutoff >= highCutoff) {
            throw new IllegalArgumentException("Lower Cutoff Frequency cannot be more than the Higher Cutoff Frequency");
        }
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        this.output = new double[this.signal.length];
        if (this.filterType == 1) {
            uk.me.berndporr.iirj.ChebyshevI bs = new uk.me.berndporr.iirj.ChebyshevI();
            bs.bandStop(order, this.samplingFreq, centreFreq, width, rippleFactor);
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = bs.filter(this.signal[i]);
            }
        }
        else if (this.filterType == 2) {
            uk.me.berndporr.iirj.ChebyshevII bs = new uk.me.berndporr.iirj.ChebyshevII();
            bs.bandStop(order, this.samplingFreq, centreFreq, width, rippleFactor);
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = bs.filter(this.signal[i]);
            }
        }
        else {
            throw new IllegalArgumentException("Chebyshev filter can only be of Type 1 and 2.");
        }
        return this.output;
    }
}
