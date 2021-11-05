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

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.InverseDiscreteFourier;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.*;

import java.util.Arrays;


// Using FFT method for 'full' and OLA method for 'same'
// DOES NOT WORK FOR VALID MODE
public class Deconvolution {

    private double[] signal;
    private double[] kernel;
    private int sig_len;
    private int ker_len;

    public Deconvolution(double[] signal, double[] window) {
        this.sig_len = signal.length;
        this.ker_len = window.length;
        this.kernel = window;
        this.signal = signal;
    }

    private void preprocess_fft() {
        int shape = Math.max(this.signal.length, this.kernel.length);
        double[] padding = new double[shape - Math.min(this.signal.length, this.kernel.length)];
        Arrays.fill(padding, 0.0);
        if (this.signal.length < shape) {
            this.signal = UtilMethods.concatenateArray(this.signal, padding);
        }
        else if (this.kernel.length < shape){
            this.kernel = UtilMethods.concatenateArray(this.kernel, padding);
        }
    }

    private double[] deconvolve_fft() {
        this.preprocess_fft();
        DiscreteFourier ffts = new DiscreteFourier(this.signal);
        ffts.dft();
        DiscreteFourier fftk = new DiscreteFourier(this.kernel);
        fftk.dft();

        Complex[] s = ffts.getComplex(false);
        Complex[] w = fftk.getComplex(false);

        Complex[] s_w = new Complex[s.length];

        for (int i=0; i<s_w.length; i++) {
            s_w[i] = s[i].divide(w[i].add(Float.MIN_NORMAL));
        }

        InverseDiscreteFourier idf = new InverseDiscreteFourier(UtilMethods.complexTo2D(s_w), false);
        idf.idft();

        return Arrays.copyOfRange(UtilMethods.round(idf.getRealSignal(), 3), 0, this.sig_len - this.ker_len + 1);
    }

    private double[] deconvolve_ola() {
        double[][] matA = new double[this.sig_len][this.sig_len];
        double[] krn_temp = UtilMethods.padSignal(UtilMethods.reverse(this.kernel), "constant", this.sig_len - 1);
        int index = 0;
        for (int i = krn_temp.length - this.sig_len - 1; i >= krn_temp.length - this.sig_len - this.sig_len; i--) {
            matA[index] = UtilMethods.splitByIndex(krn_temp, i, i + this.sig_len);
            index++;
        }
        DecompositionSolver solver = new LUDecomposition(MatrixUtils.createRealMatrix(matA)).getSolver();
        RealVector soln = solver.solve(new ArrayRealVector(this.signal, false));
        return UtilMethods.round(soln.toArray(), 3);
    }

    public double[] deconvolve(String mode) {
        double[] out;
        if (mode.equals("full")) {
            out = this.deconvolve_fft();
        }
        else if (mode.equals("same")) {
            out = this.deconvolve_ola();
        }
        else {
            throw new IllegalArgumentException("mode has to be one of 'full' or 'same'.");
        }
        return out;
    }
}
