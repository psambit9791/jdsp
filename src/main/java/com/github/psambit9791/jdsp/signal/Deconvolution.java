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

import java.util.Arrays;

// Using FFT method
public class Deconvolution {

    private double[] signal;
    private double[] kernel;
    private int sig_len;
    private int ker_len;
    private double[] output;

    public Deconvolution(double[] signal, double[] window) {
        this.sig_len = signal.length;
        this.ker_len = window.length;
        int shape = Math.max(signal.length, window.length);
        double[] padding = new double[shape - Math.min(signal.length, window.length)];
        Arrays.fill(padding, 0.0);
        if (signal.length < shape) {
            signal = UtilMethods.concatenateArray(signal, padding);
        }
        else if (window.length < shape){
            window = UtilMethods.concatenateArray(window, padding);
        }
        this.signal = signal;
        this.kernel = window;
    }

    private int[] computeLength(String mode) {
        int start = 0;
        int stop = 0;
        if (mode.equals("full")) {
            start = 0;
            stop = this.sig_len - this.ker_len + 1;
        }

        return new int[] {start, stop};
    }

    public double[] deconvolve(String mode) {
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

        double[] true_signal = idf.getRealSignal();
        true_signal = UtilMethods.round(true_signal, 3);
        int[] limits = this.computeLength(mode);

        return Arrays.copyOfRange(true_signal, limits[0], limits[1]);
    }
}
