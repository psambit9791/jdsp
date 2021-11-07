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

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.ComplexDeconvolution;
import com.github.psambit9791.jdsp.signal.Deconvolution;
import com.github.psambit9791.jdsp.signal.Generate;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.StatUtils;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import java.util.Arrays;

public class InverseWavelet {

    private Complex[][] transformed;
    private int[] widths;

    public enum waveletType {
        MORLET,
        RICKER,
        PAUL
    }

    public InverseWavelet(Complex[][] transformed, int[] widths) {
        if (widths.length != transformed.length) {
            throw new IllegalArgumentException("Number of widths should be same as number of wavelet transformations");
        }
        if (transformed.length == 0) {
            throw new IllegalArgumentException("transformed matrix cannot be empty.");
        }
        else if (transformed.length <= 3) {
            this.transformed = transformed;
            this.widths = widths;
        }
        else {
            this.transformed = new Complex[][] {transformed[0], transformed[widths.length/2], transformed[widths.length-1]};
            this.widths = new int[] {widths[0], widths[widths.length/2], widths[widths.length-1]};
        }

    }

    private double[][] icwt_paul(int args) {
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();

        for (int i=0; i<out.length; i++) {
            double norm = Math.sqrt(1.0/this.widths[i]);
            Complex[] wavelet = gp.generatePaulComplex(args, this.widths[i], (double)this.widths[i]);
            for (int w=0; w<wavelet.length; w++) {
                wavelet[w] = wavelet[w].multiply(norm); //Normalization
            }
            ComplexDeconvolution cdc = new ComplexDeconvolution(this.transformed[i], wavelet);
            cdc.deconvolve("same");
            out[i] = cdc.getRealOutput();
        }
        return out;
    }

    private double[][] icwt_morlet(double args) {
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();
        for (int i=0; i<out.length; i++) {
            int N = Math.min(10*this.widths[i], this.transformed[0].length);
            Complex[] wavelet = gp.generateMorletCWTComplex(N, args, this.widths[i]);
            wavelet = UtilMethods.reverse(wavelet);
            ComplexDeconvolution cdc = new ComplexDeconvolution(this.transformed[i], wavelet);
            cdc.deconvolve("same");
            out[i] = cdc.getRealOutput();
        }
        return out;
    }

    private double[][] icwt_ricker() {
//        System.out.println(Arrays.toString(this.widths));
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();
        for (int i=0; i<out.length; i++) {
            int N = Math.min(10*this.widths[i], this.transformed[0].length);
            double[] data = new double[this.transformed[0].length];
            for (int j=0; j<data.length; j++) {
                data[j] = this.transformed[i][j].getReal();
            }
            double[] wavelet = gp.generateRicker(N, this.widths[i]);
            wavelet = UtilMethods.reverse(wavelet);
//            System.out.println(Arrays.toString(data));
//            System.out.println(Arrays.toString(wavelet));
            Deconvolution cdc = new Deconvolution(data, wavelet);
            out[i] = cdc.deconvolve("same");
//            System.out.println(Arrays.toString(out[i]));
        }
        return out;
    }

    public double[] icwt(waveletType wavelet_type, double wavelet_args) throws IllegalArgumentException{
        double[][] out;
        switch (wavelet_type) {
            case RICKER:
                out = this.icwt_ricker();
                break;
            case MORLET:
                out = this.icwt_morlet(wavelet_args);
                break;
            case PAUL:
                out = this.icwt_paul((int)wavelet_args);
                break;
            default:
                throw new ArithmeticException("wavelet_type must be RICKER, MORLET or PAUL");
        }
        out = UtilMethods.transpose(out);
        double[] signal = new double[this.transformed[0].length];
        for (int i=0; i<signal.length; i++) {
            signal[i] = StatUtils.mean(out[i]);
        }
        return signal;
    }
}
