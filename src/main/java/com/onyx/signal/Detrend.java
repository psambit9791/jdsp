package com.onyx.signal;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class Detrend {

    private String mode;
    private int power;

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

    public Detrend(double[] signal, int power) {
        this.originalSignal = signal;
        this.mode = "poly";
        this.power = power;
        this.trendLine = new double[signal.length];
    }

    public double[] detrendSignal() {
        if (this.mode.equals("constant")) {
            this.detrendedSignal = this.constantDetrend(this.originalSignal);
            return this.detrendedSignal;
        }
        else if(this.mode.equals("poly")) {
            this.detrendedSignal = this.polyDetrend(this.originalSignal, this.power);
            return this.detrendedSignal;
        }
        else if(this.mode.equals("linear")) {
            this.detrendedSignal = this.linearDetrend(this.originalSignal);
            return this.detrendedSignal;
        }
        else {
            throw new IllegalArgumentException("Mode can only be linear or constant.");
        }
    }

    public double[] getOutput() {
        return this.detrendedSignal;
    }

    private double[] linearDetrend(double[] y) {
        double[] out = new double[y.length];
        double[] x = this.generateX(y);
        SimpleRegression sr = new SimpleRegression();

        for (int i=0; i<y.length; i++) {
            sr.addData(x[i], y[i]);
        }

        double slope = sr.getSlope();
        double intercept = sr.getIntercept();

        for (int i=0; i<y.length; i++) {
            this.trendLine[i] = (x[i]*slope)+intercept;
            out[i] = y[i] - this.trendLine[i];
        }
        return out;
    }

    private double[] polyDetrend(double[] y, int power) {
        double[] out = new double[y.length];
        double[][] x = this.generateX(y, power);
        OLSMultipleLinearRegression sr = new OLSMultipleLinearRegression();
        sr.setNoIntercept(true);
        sr.newSampleData(y, x);

//        for (int i=0; i<x.length; i++) {
//            for (int j=0; j<x[i].length; j++) {
//                System.out.print(x[i][j]+" ");
//            }
//            System.out.println();
//        }

        double[] params = sr.estimateRegressionParameters();
//        for (int i=0; i<params.length; i++) {
//            System.out.println(params[i]);
//        }

        for (int i=0; i<y.length; i++) {
            for (int j=0; j<=power; j++) {
                this.trendLine[i] += (x[i][j]*params[j]);
            }
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

    private double[][] generateX(double[] y, int power) {
        double[][] x = new double[y.length][power+1];
        double len_y = y.length;
        for (int i=0; i<x.length; i++){
            for (int j=0; j<=power; j++) {
                if (j > 1) {
                    x[i][j] = Math.pow(x[i][1], (j));
                }
                else if ( j == 1) {
                    x[i][j] = ((double)(i+1))/len_y;
                }
                else {
                    x[i][j] = 1.0;
                }
            }
        }
        return x;
    }
}
