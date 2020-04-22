package com.onyx.jdsp.signal;

import com.onyx.jdsp.filter.Chebyshev;

public class Decimate {

    private double[] signal;
    private double samplingFreq;

    public Decimate(double[] s, double Fs) {
        this.samplingFreq = Fs;
        this.signal = s;
    }

    public double[] decimate(int downSamplingFactor) {
        double newSamplingFreq = this.samplingFreq/downSamplingFactor;
        double nyquistFreq = newSamplingFreq/2.0;

        Chebyshev f = new Chebyshev(this.signal, this.samplingFreq);
        // Refer to this document: http://matlab.izmiran.ru/help/toolbox/signal/decimate.html
        double[] lowPassOutput = f.low_pass_filter(8, nyquistFreq*0.8, 0.05);

        double[] output = new double[signal.length/downSamplingFactor];

        int index = 0;
        for (int i=0; i<output.length; i++) {
            output[i] = lowPassOutput[index];
            index = index + downSamplingFactor;
        }
        return output;
    }
}
