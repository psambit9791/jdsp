package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.UtilMethods;

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
