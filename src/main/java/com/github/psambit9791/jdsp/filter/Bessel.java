package com.github.psambit9791.jdsp.filter;

/**
 * <h1>Bessel Filter</h1>
 * The Bessel class implements low-pass, high-pass, band-pass and band-stop filter using the Bessel polynomials.
 * Has the worst roll-off rate amongst all filters but the best phase response.
 * Use: The Bessel filter is ideal for applications that require minimal phase shift. Due to the gentle frequency response of the Bessel filter, it can only be used in applications where there is adequate space between the passband and stopband. <a href="https://blog.bliley.com/filter-typology-face-off-a-closer-look-at-the-top-4-filter-types">[ref]</a>
 * Reference <a href="https://en.wikipedia.org/wiki/Bessel_filter">article</a> for more information on Bessel Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Bessel {
    private double[] signal;
    private double samplingFreq;
    private double[] output;

    /**
     * This constructor initialises the prerequisites
     * required to use Bessel filter.
     * @param s Signal to be filtered
     * @param Fs Sampling frequency of input signal in Hz
     */
    public Bessel(double[] s, double Fs) {
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
        uk.me.berndporr.iirj.Bessel lp = new uk.me.berndporr.iirj.Bessel();
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
        uk.me.berndporr.iirj.Bessel hp = new uk.me.berndporr.iirj.Bessel();
        hp.highPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<this.output.length; i++) {
            this.output[i] = hp.filter(this.signal[i]);
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
    public double[] band_pass_filter(int order, double lowCutoff, double highCutoff) {
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Bessel bp = new uk.me.berndporr.iirj.Bessel();
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
        uk.me.berndporr.iirj.Bessel bs = new uk.me.berndporr.iirj.Bessel();
        bs.bandStop(order, this.samplingFreq, centreFreq, width);
        for (int i=0; i<this.output.length; i++) {
            this.output[i] = bs.filter(this.signal[i]);
        }
        return this.output;
    }
}
