package com.onyx.jdsp;

public class GeneratePeriodic {

    private int Fs;
    private double[] time;

    // Use for multiple samples concatenated one after the other
//    public GeneratePeriodic(int samplingFreq, int samples) {
//        this.Fs = samplingFreq;
//        this.time = UtilMethods.linspace(0, 1, samplingFreq, samples);
//    }

    // Use for single sample
    public GeneratePeriodic(int samplingFreq) {
        this.Fs = samplingFreq;
        this.time = UtilMethods.linspace(0, 1, samplingFreq, true);
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
