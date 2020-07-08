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

    // Use for multiple samples concatenated one after the other
//    public GeneratePeriodic(int samplingFreq, int samples) {
//        this.Fs = samplingFreq;
//        this.time = UtilMethods.linspace(0, 1, samplingFreq, samples);
//    }

    // Use for single sample
    /**
     * This constructor initialises the prerequisites
     * required to generate different signals.
     * @param samplingFreq Sampling Frequency
     */
    public Generate(int samplingFreq) {
        this.Fs = samplingFreq;
        this.time = UtilMethods.linspace(0, 1, samplingFreq, true);
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
     * @return double[] Smoothed signal
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
     * @return double[] Smoothed signal
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
     * @return double[] Smoothed signal
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
}
