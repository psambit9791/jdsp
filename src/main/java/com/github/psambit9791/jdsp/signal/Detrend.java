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

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * <h1>Detrend</h1>
 * The Detrend class implements different methods to remove trends in a signal and is based on
 * numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.convolve.html">detrend()</a> function
 * but extends to additionally provide polynomial detrending.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Detrend {

    private String mode;
    private int power;

    private double[] originalSignal;
    private double[] detrendedSignal;
    private double[] trendLine;

    /**
     * This constructor initialises the prerequisites required to perform detrending.
     * For polynomial detrending, the default polynomial power is 2.
     * @param signal Signal to be detrended
     * @param mode_of_op Method of detrending to be used. Can be "constant", "linear", "poly".
     */
    public Detrend(double[] signal, String mode_of_op) {
        this.originalSignal = signal;
        this.mode = mode_of_op;
        if (this.mode.equals("poly")) {
            this.power = 2;
        }
        this.trendLine = new double[signal.length];
    }

    /**
     * This constructor initialises the prerequisites required to perform detrending.
     * For deafult detrending mode is "linear"
     * @param signal Signal to be detrended
     */
    public Detrend(double[] signal) {
        this.originalSignal = signal;
        this.mode = "linear";
        this.trendLine = new double[signal.length];
    }

    /**
     * This constructor initialises the prerequisites required to perform detrending in polynomial mode.
     * @param signal Signal to be detrended
     * @param power Highest polynomial power in the trend of the signal
     */
    public Detrend(double[] signal, int power) {
        this.originalSignal = signal;
        this.mode = "poly";
        this.power = power;
        this.trendLine = new double[signal.length];
    }

    /**
     * This method detrends the signal and returns it.
     * @throws java.lang.IllegalArgumentException if node is not linear, constant or poly
     * @return double[] Detrended signal
     */
    public double[] detrendSignal() throws IllegalArgumentException{
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
            throw new IllegalArgumentException("Mode can only be linear, constant or poly.");
        }
    }

    /**
     * This getter method to get the trend line fo the signal.
     * @return double[] Calculated trend line
     */
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
        double[] params = sr.estimateRegressionParameters();

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
