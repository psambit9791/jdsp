package com.onyx.signal;

import uk.me.berndporr.iirj.*;

public class Chebyshev {
    private double[] signal;
    private double samplingFreq;
    private double[] output;
    private int filterType;

    public Chebyshev(double[] s, double Fs, int filterType) {
        this.signal = s;
        this.samplingFreq = Fs;
        this.filterType = filterType;
    }

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
