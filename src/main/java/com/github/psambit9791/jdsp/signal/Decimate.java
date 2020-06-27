package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.UtilMethods;
import com.github.psambit9791.jdsp.filter.Chebyshev;


/**
 * <h1>Decimate</h1>
 * Downsample the signal after applying an anti-aliasing filter.
 * Reference <a href="https://dspguru.com/dsp/faqs/multirate/decimation/">article</a> for more information on decimation and its difference from down-sampling.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Decimate {

    private double[] signal;
    private int samplingFreq;
    private boolean zeroPhase;

    /**
     * This constructor initialises the prerequisites required to use Decimate.
     * @param s Signal to be filtered
     * @param Fs Sampling Frequency
     * @param zeroPhase If phase shift should be prevented. True implies no phase shift happens.
     */
    public Decimate(double[] s, int Fs, boolean zeroPhase) {
        this.samplingFreq = Fs;
        this.signal = s;
        this.zeroPhase = zeroPhase;
    }

    /**
     * This constructor initialises the prerequisites required to use Decimate. zeroPhase is set to True by default.
     * @param s Signal to be filtered
     * @param Fs Sampling Frequency
     */
    public Decimate(double[] s, int Fs) {
        this.samplingFreq = Fs;
        this.signal = s;
        this.zeroPhase = true;
    }

    /**
     * This method performs default convolution using padding in 'reflect' modes.
     * @param downSamplingFactor Factor by which the signal needs to be downsamples
     * @return double[] The decimated signal
     */
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
