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
import java.util.Arrays;

/**
 * <h1>Generate Periodic Signal</h1>
 * The GeneratePeriodic class implements methods to generate sin(), cos(), and square() wave based on sampling frequency and wave frequency
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Generate {

    private int Fs;
    private double[] time;

    /**
     * This constructor initialises the prerequisites
     * required to generate different signals.
     * @param start The starting timepoint
     * @param stop The ending timepoint
     * @param samplingFreq Sampling Frequency
     */
    public Generate(int start, int stop, int samplingFreq) {
        this.Fs = samplingFreq;
        this.time = UtilMethods.linspace(start, stop, samplingFreq, true);
    }

    /**
     * Returns the array of time instants based on the sampling frequency
     * @return double[] Time points generated
     */
    public double[] getTimeArray() {
        return this.time;
    }

    /**
     * Generates a sine wave based on the provided parameters
     * @param waveFreq Frequency of the wave to be generated
     * @return double[] Sine wave
     */
    public double[] generateSineWave(int waveFreq) {
        double[] sine = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            double temp = 2*Math.PI*waveFreq*this.time[i];
            sine[i] = Math.sin(temp);
        }
        return sine;
    }

    /**
     * Generates a cosine wave based on the provided parameters
     * @param waveFreq Frequency of the wave to be generated
     * @return double[] Cosine wave
     */
    public double[] generateCosineWave(int waveFreq) {
        double[] cosine = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            double temp = 2*Math.PI*waveFreq*this.time[i];
            cosine[i] = Math.cos(temp);
        }
        return cosine;
    }

    /**
     * Generates a square wave based on the provided parameters
     * @param waveFreq Frequency of the wave to be generated
     * @return double[] Square wave
     */
    public double[] generateSquareWave(int waveFreq) {
        double[] square = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            double temp = 2*Math.PI*waveFreq*this.time[i];
            square[i] = Math.signum(Math.sin(temp));
            if ((Math.abs(Math.sin(temp))-0) < 0.000001) {
                square[i] = 1;
            }
        }
        return square;
    }

    /**
     * Generates a gaussian pulse signal based on the provided parameters
     * @param centralFreq Central Frequency of the wave to be generated
     * @return double[][] The generated gaussian pulse signal and its envelope.
     */
    public double[][] generateGaussianPulse(int centralFreq) {
        double bw = 0.5; // Fractional bandwidth in frequency domain of pulse
        double bwr = -6; // Reference level at which fractional bandwidth is calculated

        double ref = Math.pow(10.0, bwr/20);
        double a = (Math.pow((Math.PI * centralFreq * bw), 2) * -1)/(4.0 * Math.log(ref));

        double[] gauss_env = new double[this.time.length];
        double[] gauss_pulse = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            gauss_env[i] = Math.exp(-a * this.time[i] * this.time[i]);
            gauss_pulse[i] = gauss_env[i] * Math.cos(2 * Math.PI * centralFreq * this.time[i]);
        }
        double[][] out = {gauss_pulse, gauss_env};
        return out;
    }

    /**
     * Generates a unit impulse signal wat the given time point
     * @param time Time point at which impulse happens
     * @throws java.lang.IllegalArgumentException If provided time point is more than time length
     * @return double[] Impulse signal
     */
    public double[] generateUnitImpulse(double time) {
        int index = (int)(time*this.Fs);
        if (index >= this.time.length) {
            throw new IllegalArgumentException("Time must not be more than time length");
        }

        double[] imp = new double[this.time.length];
        Arrays.fill(imp, 0);
        imp[index] = 1;

        return imp;
    }

    /**
     * Generates a sawtooth wave based on the provided parameters
     * @param waveFreq Frequency of the wave to be generated
     * @param width Determines the shape of the sawtooth
     * @throws java.lang.IllegalArgumentException If width value is less than 0 or greater than 1
     * @return double[] Sawtooth signal
     */
    public double[] generateSawtooth(int waveFreq, double width) {
        if (width<0 || width>1) {
            throw new IllegalArgumentException("Width must be between 0 and 1");
        }

        double[] sawtooth = new double[this.time.length];
        double[] t = new double[this.time.length];
        double[] tmod = new double[this.time.length];

        for (int i=0; i<this.time.length; i++) {
            t[i] = 2 * Math.PI * waveFreq * this.time[i];
            tmod[i] = UtilMethods.modulo(t[i], 2*Math.PI);
        }

        double[] w = new double[this.time.length];
        Arrays.fill(w, width);

        boolean[] mask2 = new boolean[this.time.length];
        double threshold = width*2*Math.PI;
        for (int i=0; i<mask2.length ; i++) {
            mask2[i] = tmod[i] < threshold;
        }

        for (int i=0; i<sawtooth.length; i++) {
            if (mask2[i]) {
                sawtooth[i] = (tmod[i]/(Math.PI*w[i])) - 1;
            }
        }

        boolean[] mask3 = new boolean[this.time.length];
        for (int i=0; i< mask3.length; i++) {
            mask3[i] = !mask2[i];
        }

        for (int i=0; i<sawtooth.length; i++) {
            if (mask3[i]) {
                sawtooth[i] = (Math.PI * (w[i]+1) - tmod[i]) / (Math.PI * (1 - w[i])) ;
            }
        }

        return sawtooth;
    }
}
