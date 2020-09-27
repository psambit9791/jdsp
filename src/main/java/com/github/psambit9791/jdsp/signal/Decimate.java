/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
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
            Chebyshev f = new Chebyshev(this.signal, (double)this.samplingFreq, 0.05);
            double[] lowPassOutput = f.lowPassFilter(8, nyquistFreq*0.8);

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

            Chebyshev f1 = new Chebyshev(this.signal, (double)this.samplingFreq, 0.05);
            double[] lowPassOutput = f1.lowPassFilter(4, nyquistFreq*0.8);
            lowPassOutput = UtilMethods.reverse(lowPassOutput);
            Chebyshev f2 = new Chebyshev(lowPassOutput, (double)this.samplingFreq, 0.05);
            lowPassOutput = f2.lowPassFilter(4, nyquistFreq*0.8);
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
