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
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Generate Periodic Signal</h1>
 * The GeneratePeriodic class implements methods to generate various waves and wavelets based on different characteristic
 * properties.
 * Provides generate functions for the following waves: Sine, Cosine, Square, Gaussian Pulse, Unit Impulse, Sawtooth, Chirp
 * Provides generate functions for the following wavelets: Ricker, Morlet, Paul and Morlet-CWT
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
     * required to generate wavelets which do not need any arguments.
     */
    public Generate() {}

    /**
     * This constructor initialises the prerequisites
     * required to generate different signals.
     * @param start The starting timepoint
     * @param stop The ending timepoint
     * @param samplingFreq Sampling Frequency
     */
    public Generate(int start, int stop, int samplingFreq) {
        this.Fs = samplingFreq;
        this.time = UtilMethods.linspace(start, stop, (stop - start) * samplingFreq, true);
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
        return new double[][]{gauss_pulse, gauss_env};
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

    /**
     * Returns a Ricker wavelet, also known as the "Mexican hat wavelet"
     * @param points Number of points in the wavelet
     * @param width Width parameter
     * @return double[] Array of points in the shape of a ricker curve
     */
    public double[] generateRicker(int points, double width) {
        double A = 2.0 / ((Math.sqrt(3*width)) * (Math.pow(Math.PI, 0.25)));

        double wsq = Math.pow(width, 2.0);
        double[] vec = UtilMethods.arange(0, points, 1.0);
        vec = UtilMethods.scalarArithmetic(vec, (points - 1)/2.0, "sub");
        vec = UtilMethods.scalarArithmetic(vec, 2, "pow");

        double[] mod = UtilMethods.scalarArithmetic(vec, wsq, "div");
        mod = UtilMethods.scalarArithmetic(mod, 1.0, "reverse_sub");

        double[] gauss = UtilMethods.scalarArithmetic(vec, 2*wsq, "div");

        for (int i=0; i<gauss.length; i++) {
            gauss[i] = Math.exp(0 - gauss[i]);
        }

        double[] out = MathArrays.ebeMultiply(mod, gauss);
        return UtilMethods.scalarArithmetic(out, A, "mul");
    }

    /**
     * Returns a Complex Morlet Wavelet
     * @param points Length of the wavelet
     * @param omega0 Central frequency
     * @param scale Scaling factor
     * @return Complex[] The
     */
    public Complex[] generateMorletComplex(int points, double omega0, double scale) {
        double[] x = UtilMethods.linspace(-scale*2*Math.PI, 2*scale*Math.PI, points, true);
        Complex[] output = new Complex[points];
        Complex temp1;
        Complex temp2;
        for (int i=0; i<points; i++) {
            temp1 = new Complex(0, omega0*x[i]);
            temp1 = temp1.exp();

            temp2 = new Complex(-0.5*Math.pow(omega0, 2), 0);
            temp2 = temp2.exp();

            temp1 = temp1.subtract(temp2);
            temp1 = temp1.multiply(Math.exp(-0.5 * Math.pow(x[i], 2)) * Math.pow(Math.PI, -0.25));

            output[i] = temp1;
        }
        return output;
    }

    /**
     * Returns a Complex Morlet Wavelet in 2D matrix format
     * @param points Length of the wavelet
     * @param omega0 Central frequency
     * @param scale Scaling Factor
     * @return double[][] Complex array as a 2D vector. Dimension 1: Length, Dimension 2: Real part, Complex part
     */
    public double[][] generateMorlet(int points, double omega0, double scale) {
        Complex[] temp = this.generateMorletComplex(points, omega0, scale);
        return UtilMethods.complexTo2D(temp);
    }

    /**
     * Returns a Complex Morlet Wavelet compatible with CWT
     * @param points Length of the wavelet
     * @param omega0 Central frequency
     * @param width Width parameter of wavelet
     * @return Complex[] The generated wavelet
     */
    public Complex[] generateMorletCWTComplex(int points, double omega0, double width) {
        double[] x = UtilMethods.arange(0, points, 1.0);
        double sub_const = (points - 1)/2.0;
        double pi_root_root = Math.pow(Math.PI, -0.25);
        double width_sqrt = Math.pow(1/width, 0.5);

        for (int i=0; i<x.length; i++) {
            x[i] = (x[i] - sub_const)/width;
        }
        Complex[] output = new Complex[points];
        Complex temp1;
        Complex temp2;
        for (int i=0; i<points; i++) {
            temp1 = new Complex(0, omega0*x[i]);
            temp1 = temp1.exp();

            temp2 = new Complex(-0.5*Math.pow(x[i], 2), 0);
            temp2 = temp2.exp();

            temp1 = temp1.multiply(temp2);
            temp1 = temp1.multiply(pi_root_root);

            output[i] = temp1.multiply(width_sqrt);
        }

        return output;
    }

    /**
     * Returns a Complex Morlet Wavelet (CWT compatible) in 2D matrix format
     * @param points Length of the wavelet
     * @param omega0 Central frequency
     * @param width Width parameter of wavelet
     * @return double[][] Complex array as a 2D vector. Dimension 1: Length, Dimension 2: Real part, Complex part
     */
    public double[][] generateMorletCWT(int points, double omega0, double width) {
        Complex[] temp = this.generateMorletCWTComplex(points, omega0, width);
        return UtilMethods.complexTo2D(temp);
    }


    /**
     * Returns a Complex Paul Wavelet compatible with CWT
     * @param order Order of the wavelet function
     * @param width Width parameter of wavelet
     * @return Complex[] The generated wavelet
     */
    public Complex[] generatePaulComplex(int order, double width) {
        double M = width * 10;
        double[] x = UtilMethods.arange((-M + 1)/2.0, (M + 1)/2.0, 1.0);
        Complex mul_const = new Complex(0, 1);
        mul_const = mul_const.pow(order).multiply(CombinatoricsUtils.factorial(order));
        mul_const = mul_const.multiply(Math.pow(2, order));
        mul_const = mul_const.divide(Math.pow(Math.PI * CombinatoricsUtils.factorial(2 * order), 0.5));

        Complex[] output = new Complex[x.length];
        for (int i=0; i<output.length; i++) {
            Complex func_form = new Complex(1, -1 * x[i]);
            func_form = func_form.pow(-(order + 1));
            output[i] = mul_const.multiply(func_form);
        }

        return output;
    }

    /**
     * Returns a Complex Paul Wavelet compatible with CWT
     * @param order Order of the wavelet function
     * @param width Width parameter of wavelet
     * @param scale Scaling factor
     * @return Complex[] The generated wavelet
     */
    public Complex[] generatePaulComplex(int order, double width, double scale) {
        double M = width * 10;
        double[] x = UtilMethods.arange((-M + 1)/2.0, (M + 1)/2.0, 1.0);
        for (int i=0; i<x.length; i++) {
            x[i] = x[i] / scale;
        }
        Complex mul_const = new Complex(0, 1);
        mul_const = mul_const.pow(order).multiply(CombinatoricsUtils.factorial(order));
        mul_const = mul_const.multiply(Math.pow(2, order));
        mul_const = mul_const.divide(Math.pow(Math.PI * CombinatoricsUtils.factorial(2 * order), 0.5));

        Complex[] output = new Complex[x.length];
        for (int i=0; i<output.length; i++) {
            Complex func_form = new Complex(1, -1 * x[i]);
            func_form = func_form.pow(-(order + 1));
            output[i] = mul_const.multiply(func_form);
        }

        return output;
    }

    /**
     * Returns a Complex Paul Wavelet in 2D matrix format
     * @param order Order of the wavelet function
     * @param width Width parameter of wavelet
     * @return double[][] Complex array as a 2D vector. Dimension 1: Length, Dimension 2: Real part, Complex part
     */
    public double[][] generatePaul(int order, double width) {
        Complex[] temp = this.generatePaulComplex(order, width);
        return UtilMethods.complexTo2D(temp);
    }

    /**
     * Return a sine wave chirp signal ranging from startFreq to endFreq and with initial phase phi0.
     * @param startFreq starting frequency of the chirp signal
     * @param endFreq end frequency of the chirp signal
     * @param phi0 initial phase of the chirp signal (in radians)
     * @return double[] chirp signal over time
     */
    public double[] generateChirp(double startFreq, double endFreq, double phi0) {
        double[] out = new double[this.time.length];
        double T = this.time[this.time.length-1] - this.time[0];   // Period of the signal

        double c = (endFreq - startFreq)/T;
        for (int i = 0; i < out.length; i++) {
            out[i] = Math.sin(phi0 + 2*Math.PI * (c/2 * Math.pow(this.time[i], 2) + startFreq*this.time[i]));
        }

        return out;
    }

    /**
     * Return a sine wave chirp signal ranging from startFreq to endFreq, with initial phase 0
     * @param startFreq starting frequency of the chirp signal
     * @param endFreq end frequency of the chirp signal
     * @return double[] chirp signal over time
     */
    public double[] generateChirp(double startFreq, double endFreq) {
        return generateChirp(startFreq, endFreq, 0);
    }
}
