/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp.transform;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.MathArrays;

public class InverseWavelet {

    private Complex[][] transformed;
    private int[] widths;
    private double dj;

    public InverseWavelet(Complex[][] transformed, int[] widths) {
        this.transformed = transformed;
        this.widths = widths;
        this.dj = 0.125;
    }

//    private double findS0(String wavelet, double args) {
//        double s0 = 0.0;
//        if (wavelet.equals("ricker")) {
//
//        }
//    }

    // Values precomputed for quicker operations during ICWT
    private double getWaveletPsi0(String wavelet, double args) {
        double psi0 = 0.0;
        if (wavelet.equals("morlet")) {

            psi0 = 0.7511255444649425;
        }
        else if(wavelet.equals("ricker")) {
            psi0 = 0.8673250705840775;
        }
        else if(wavelet.equals("paul")) {
            int order = (int)args;
            Complex mul_const = new Complex(0, 1);
            mul_const = mul_const.pow(order).multiply(CombinatoricsUtils.factorial(order));
            mul_const = mul_const.multiply(Math.pow(2, order));
            mul_const = mul_const.divide(Math.pow(Math.PI * CombinatoricsUtils.factorial(2 * order), 0.5));
            psi0 = mul_const.getReal();
        }
        else {
            throw new IllegalArgumentException("wavelet can be one of 'morlet', 'ricker' or 'paul'.");
        }
        return psi0;
    }

    // All values precomputed for quicker operations during ICWT
    private double getWaveletCDelta(String wavelet) {
        double cdelta = 0.0;
        if (wavelet.equals("morlet")) {
            cdelta = 0.776;
        }
        else if(wavelet.equals("ricker")) {
            cdelta = 3.541;
        }
        else if(wavelet.equals("paul")) {
            cdelta = 1.132;
        }
        else {
            throw new IllegalArgumentException("wavelet can be one of 'morlet', 'ricker' or 'paul'.");
        }
        return cdelta;
    }


    public double[] icwt(String wavelet_type, double wavelet_args, double signal_mean) throws IllegalArgumentException{

        if (!wavelet_type.equals("ricker") && !wavelet_type.equals("morlet") && !wavelet_type.equals("paul")) {
            throw new ArithmeticException("wavelet_type must be 'ricker', 'morlet' or 'paul'");
        }

        double[] output = new double[this.transformed[0].length];
        double C_d = this.getWaveletCDelta(wavelet_type);
        double psi0 = this.getWaveletPsi0(wavelet_type, wavelet_args);

        for (int i=0; i<this.transformed[0].length; i++) {
            double temp = 0;
            for (int j = 0; j<this.transformed.length; j++) {
                temp = temp + this.transformed[j][i].getReal()/Math.sqrt(widths[j]);
            }
            output[i] = temp;
        }

        MathArrays.scaleInPlace(this.dj * (1.0 / (C_d*psi0)), output);
        if (signal_mean != 0) {
            for (int i=0; i<output.length; i++) {
                output[i] = output[i] + signal_mean;
            }
        }
        return output;

    }

    public double[] icwt(String wavelet_type, double wavelet_args) throws IllegalArgumentException{

        if (!wavelet_type.equals("ricker") && !wavelet_type.equals("morlet") && !wavelet_type.equals("paul")) {
            throw new ArithmeticException("wavelet_type must be 'ricker', 'morlet' or 'paul'");
        }

        double[] output = this.icwt(wavelet_type, wavelet_args, 0.0);
        return output;

    }
}
