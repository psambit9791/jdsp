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


import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.InverseDiscreteFourier;
import org.apache.commons.math3.util.MathArrays;

/**
 * <h1>Resample</h1>
 * The Resample class samples the signal again with a new number of samples. The new spacing equals the number of previous
 * samples divided by the new number of samples and the previous spacing. This method uses Fourier Transform and expects
 * the signal to be periodic.
 * Resampling works only for real signals.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Resample {

    private double[] signal;
    private int num;
    private boolean isComplex;

    /**
     * This constructor initialises the prerequisites required to use Resample.
     * @param signal Signal to be resampled
     * @param num The number of samples required after resampling
     */
    public Resample(double[] signal, int num) {
        this.signal = signal;
        this.num = num;
        this.isComplex = false;
    }

    /**
     * This method using the Fourier method to change the number of samples to the given number of samples.
      * @return double[] The resampled signal with 'num' samples
     */
    public double[] resampleSignal() {
        int Nx = this.signal.length;

        DiscreteFourier df = new DiscreteFourier(this.signal);
        df.dft();
        double[][] X = df.returnFull(true);
        double[][] Y = new double[this.num/2+1][2];

        int N = Math.min(this.num, Nx);
        int nyquist_idx = N/2+1;
        for (int i=0; i<nyquist_idx; i++) {
            Y[i][0] = X[i][0];
            Y[i][1] = X[i][1];
        }

        if (N%2 == 0) {
            // Downsampling
            if (this.num < Nx) {
                Y[N/2][0] = Y[N/2][0]*2;
                Y[N/2][1] = Y[N/2][1]*2;
            }
            // Upsampling
            else {
                Y[N/2][0] = Y[N/2][0]*0.5;
                Y[N/2][1] = Y[N/2][1]*0.5;
            }
        }

        InverseDiscreteFourier idf = new InverseDiscreteFourier(Y, true);
        idf.idft();
        double[] y = idf.getRealSignal();
        y = MathArrays.scale((float)this.num/(float)Nx, y);
        return y;
    }
}
