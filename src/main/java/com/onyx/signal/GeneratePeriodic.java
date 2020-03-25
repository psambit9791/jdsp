package com.onyx.signal;

public class GeneratePeriodic {

    private int Fs;
    private double[] time;

    public GeneratePeriodic(int samples, int samplingFreq) {
        this.Fs = samplingFreq;
        this.time = UtilMethods.linspace(0, 1, samplingFreq, samples);
    }

    public double[] generateSineWave(int waveFreq) {
        double[] sine = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            double temp = 2*Math.PI*waveFreq*this.time[i];
            sine[i] = Math.sin(temp);
        }
        return sine;
    }

    public double[] generateCosineWave(int waveFreq) {
        double[] cosine = new double[this.time.length];
        for (int i=0; i<this.time.length; i++) {
            double temp = 2*Math.PI*waveFreq*this.time[i];
            cosine[i] = Math.cos(temp);
        }
        return cosine;
    }
}
