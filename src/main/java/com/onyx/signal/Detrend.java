package com.onyx.signal;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Detrend {

    private double slope;
    private double intercept;
    private String mode;

    private double[] originalSignal;
    private double[] detrendedSignal;
    private double[] trendLine;

    public Detrend(double[] signal, String mode_of_op) {
        this.originalSignal = signal;
        this.mode = mode_of_op;
        this.trendLine = new double[signal.length];
    }

    public Detrend(double[] signal) {
        this.originalSignal = signal;
        this.mode = "linear";
        this.trendLine = new double[signal.length];
    }

    public void detrendSignal() {
        if (this.mode.equals("constant")) {
            this.detrendedSignal = this.constantDetrend(this.originalSignal);
        }
        else if(this.mode.equals("linear")) {
            this.detrendedSignal = this.linearDetrend(this.originalSignal);
        }
        else {
            throw new IllegalArgumentException("Mode can only be linear or constant.");
        }
    }

    public double[] getOutput() {
        return this.detrendedSignal;
    }

    public double getSlope() {
        return this.slope;
    }

    public double getIntercept() {
        return this.intercept;
    }

    public double[] getTrendLine() {
        return this.trendLine;
    }

    private double[] linearDetrend(double[] y) {
        double[] out = new double[y.length];
        double[] x = this.generateX(y);
        SimpleRegression sr = new SimpleRegression();

        for (int i=0; i<y.length; i++) {
            sr.addData(x[i], y[i]);
        }

        this.slope = sr.getSlope();
        this.intercept = sr.getIntercept();

        for (int i=0; i<y.length; i++) {
            this.trendLine[i] = (x[i]*this.slope)+this.intercept;
            out[i] = y[i] - this.trendLine[i];
        }
        return out;
    }

    private double[] constantDetrend(double[] y) {
        double[] out = new double[y.length];
        double mean = this.findMean(y);

        for (int i=0; i<y.length; i++) {
            out[i] = y[i] - mean;
        }
        return out;
    }

    private double findMean(double[] arr) {
        double mean = 0.0;
        for (int i=0; i<arr.length; i++) {
            mean = mean + arr[i];
        }
        mean = mean/arr.length;
        return mean;
    }

    private double[] generateX(double[] y) {
        double[] x = new double[y.length];
        double len_y = y.length;
        for (int i=0; i<x.length; i++){
            x[i] = ((double)(i+1))/len_y;
        }
        return x;
    }
}
