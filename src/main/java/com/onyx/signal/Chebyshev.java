package com.onyx.signal;

import uk.me.berndporr.iirj.*;

/**
 * <h1>Chebyshec</h1>
 * The Chebyshev class implements low-pass, high-pass, band-pass and band-stop filter using the Chebyshev Type I and Type II equations.
 * Reference <a href="https://en.wikipedia.org/wiki/Chebyshev_filter">article</a> for more information on Chebyshec Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Chebyshev {
    private double[] signal;
    private double samplingFreq;
    private double[] output;
    private int filterType;

    /**
     * This constructor initialises the prerequisites
     * required to use Chebyshev filter.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal
     * @param filterType Type of Chebyshev filter. 1: Type I, 2: Type II
     */
    public Chebyshev(double[] s, double Fs, int filterType) {
        this.signal = s;
        this.samplingFreq = Fs;
        this.filterType = filterType;
    }

    /**
     * This constructor initialises the prerequisites
     * required to use Chebyshev filter. Default mode operation is of Type I.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal
     */
    public Chebyshev(double[] s, double Fs) {
        this.signal = s;
        this.samplingFreq = Fs;
        this.filterType = 1;
    }

    /**
     * This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] low_pass_filter(int order, double cutoffFreq, double rippleFactor) {
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
    public double[] high_pass_filter(int order, double cutoffFreq, double rippleFactor) {
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
     * This method implements a pand pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] band_pass_filter(int order, double lowCutoff, double highCutoff, double rippleFactor) {
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
     * @return double[] Filtered signal
     */
    public double[] band_stop_filter(int order, double lowCutoff, double highCutoff, double rippleFactor) {
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
