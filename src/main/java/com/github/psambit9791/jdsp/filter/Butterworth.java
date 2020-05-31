package com.github.psambit9791.jdsp.filter;

/**
 * <h1>Butterworth Filter</h1>
 * The Butterworth class implements low-pass, high-pass, band-pass and band-stop filter using the Butterworth polynomials.
 * Has the flattest pass-band but a poor roll-off rate.
 * Reference <a href="https://en.wikipedia.org/wiki/Butterworth_filter">article</a> for more information on Buttterworth Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Butterworth {
    private double[] signal;
    private double samplingFreq;
    private double[] output;

    /**
     * This constructor initialises the prerequisites
     * required to use Butterworth filter.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal
     */
    public Butterworth(double[] s, double Fs) {
        this.signal = s;
        this.samplingFreq = Fs;
    }

    /**
     * This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] low_pass_filter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Butterworth lp = new uk.me.berndporr.iirj.Butterworth();
        lp.lowPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<this.output.length; i++) {
            this.output[i] = lp.filter(this.signal[i]);
        }
        return this.output;
    }

    /**
     * This method implements a high pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] high_pass_filter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Butterworth hp = new uk.me.berndporr.iirj.Butterworth();
        hp.highPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<this.output.length; i++) {
            this.output[i] = hp.filter(this.signal[i]);
        }
        return this.output;
    }

    /**
     * This method implements a band pass filter with given parameters, filters the signal and returns it.
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter
     * @param highCutoff The upper cutoff frequency for the filter
     * @return double[] Filtered signal
     */
    public double[] band_pass_filter(int order, double lowCutoff, double highCutoff) {
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Butterworth bp = new uk.me.berndporr.iirj.Butterworth();
        bp.bandPass(order, this.samplingFreq, centreFreq, width);
        for (int i=0; i<this.output.length; i++) {
            this.output[i] = bp.filter(this.signal[i]);
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
    public double[] band_stop_filter(int order, double lowCutoff, double highCutoff) {
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Butterworth bs = new uk.me.berndporr.iirj.Butterworth();
        bs.bandStop(order, this.samplingFreq, centreFreq, width);
        for (int i=0; i<this.output.length; i++) {
            this.output[i] = bs.filter(this.signal[i]);
        }
        return this.output;
    }
}
