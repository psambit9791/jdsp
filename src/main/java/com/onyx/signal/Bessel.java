package com.onyx.signal;

import uk.me.berndporr.iirj.*;

public class Bessel {
    private double[] signal;
    private double samplingFreq;
    private double[] output;

    public Bessel(double[] s, double Fs) {
        this.signal = s;
        this.samplingFreq = Fs;
    }

    public double[] low_pass_filter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Bessel lp = new uk.me.berndporr.iirj.Bessel();
        lp.lowPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<this.output.length; i++) {
            this.output[i] = lp.filter(this.signal[i]);
        }
        return this.output;
    }

    public double[] high_pass_filter(int order, double cutoffFreq) {
        this.output = new double[this.signal.length];
        uk.me.berndporr.iirj.Bessel hp = new uk.me.berndporr.iirj.Bessel();
        hp.highPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<this.output.length; i++) {
            this.output[i] = hp.filter(this.signal[i]);
        }
        return this.output;
    }

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
