package com.onyx.jdsp.signal;

import com.onyx.jdsp.UtilMethods;
import com.onyx.jdsp.filter.Chebyshev;

public class Decimate {

    private double[] signal;
    private int samplingFreq;
    private boolean zeroPhase;

    public Decimate(double[] s, int Fs, boolean zeroPhase) {
        this.samplingFreq = Fs;
        this.signal = s;
        this.zeroPhase = zeroPhase;
    }

    public Decimate(double[] s, int Fs) {
        this.samplingFreq = Fs;
        this.signal = s;
        this.zeroPhase = true;
    }

    public double[] decimate(int downSamplingFactor) {
        double[] output = new double[(int)Math.ceil(signal.length/(double)downSamplingFactor)];

        if (!this.zeroPhase) {
            int newSamplingFreq = this.samplingFreq/downSamplingFactor;
            double nyquistFreq = newSamplingFreq/2.0;

            // Refer to this document: http://matlab.izmiran.ru/help/toolbox/signal/decimate.html
            Chebyshev f = new Chebyshev(this.signal, (double)this.samplingFreq);
            double[] lowPassOutput = f.low_pass_filter(8, nyquistFreq*0.8, 0.05);

            int index = 0;
            for (int i=0; i<output.length; i++) {
                output[i] = lowPassOutput[index];
                index = index + downSamplingFactor;
            }
        }
        else {
            // Refer to this answer: https://dsp.stackexchange.com/a/9468
            // Refer to this video: https://www.youtube.com/watch?v=ue4ba_wXV6A
            int newSamplingFreq = this.samplingFreq/downSamplingFactor;
            double nyquistFreq = newSamplingFreq/2.0;

            Chebyshev f1 = new Chebyshev(this.signal, (double)this.samplingFreq);
            double[] lowPassOutput = f1.low_pass_filter(4, nyquistFreq*0.8, 0.05);
            lowPassOutput = UtilMethods.reverse(lowPassOutput);
            Chebyshev f2 = new Chebyshev(lowPassOutput, (double)this.samplingFreq);
            lowPassOutput = f2.low_pass_filter(4, nyquistFreq*0.8, 0.05);
            lowPassOutput = UtilMethods.reverse(lowPassOutput);

            int index = 0;
            for (int i=0; i<output.length; i++) {
                output[i] = lowPassOutput[index];
                index = index + downSamplingFactor;
            }
        }
        return output;
    }
}
