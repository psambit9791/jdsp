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
import com.github.psambit9791.jdsp.signal.ComplexConvolution;
import com.github.psambit9791.jdsp.signal.Convolution;
import com.github.psambit9791.jdsp.signal.Generate;
import org.apache.commons.math3.complex.Complex;

/**
 * <h1>Continuous Wavelet Transform</h1>
 * The ContinuousWavelet class applies the wavelet transform on the input signal using different wavelet functions. This class
 * works with 3 wavelets - Ricker, Morlet and Paul.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class ContinuousWavelet {

    private double[] signal;
    private int[] widths;

    public enum waveletType {
        MORLET,
        RICKER,
        PAUL
    }

    /**
     * This constructor initialises the prerequisites required to use Wavelet.
     * @param signal The signal to be transformed
     * @param widths The width of the wavelets to be used.
     */
    public ContinuousWavelet(double[] signal, int[] widths) {
        this.signal = signal;
        this.widths = widths;
    }

    /**
     * Perform wavelet transform with the Ricker wavelet
     * @param data Signal to be transformed
     * @param wavelet Wavelet to be used for transforming
     * @return Complex[] Transformed signal
     */
    private Complex[] ricker_cwt(double[] data, double[] wavelet) {
        Convolution c = new Convolution(data, wavelet);
        return UtilMethods.matToComplex(c.convolve("same"));
    }

    /**
     * Perform wavelet transform with the Morlet wavelet
     * @param data Signal to be transformed
     * @param wavelet Wavelet to be used for transforming
     * @return Complex[] Transformed signal
     */
    private Complex[] morlet_cwt(double[] data, Complex[] wavelet) {
        Complex[] wavelet_conjugate = new Complex[wavelet.length];
        for (int i=0; i<wavelet.length; i++) {
            wavelet_conjugate[i] = wavelet[i].conjugate();
        }
        ComplexConvolution c1 = new ComplexConvolution(data, wavelet_conjugate);
        return c1.convolve("same");
    }

    /**
     * Perform wavelet transform with the Paul wavelet
     * @param data Signal to be transformed
     * @param wavelet Wavelet to be used for transforming
     * @return Complex[] Transformed signal
     */
    private Complex[] paul_cwt(double[] data, Complex[] wavelet) {
        ComplexConvolution c1 = new ComplexConvolution(data, wavelet);
        return c1.convolve("same");
    }

    /**
     * A function is a hyper-function which accepts the wavelet type and the property of the wavelet to apply the wavelet
     * transform on the signal and returns the output.
     * @param wavelet_type The type of wavelet to be used
     * @param args Wavelet property to be used.
     *             Ignored for Ricker, omega0 for Morlet and wavelet order for Paul
     * @return Complex[][] Transformed wavelet for the different widths
     * @throws IllegalArgumentException If filterType is not one of MORLET, RICKER or PAUL
     */
    public Complex[][] transform(waveletType wavelet_type, double args) throws IllegalArgumentException{
        Complex[][] output = new Complex[this.widths.length][this.signal.length];
        switch (wavelet_type) {
            case RICKER:
                for (int i=0; i<this.widths.length; i++) {
                    int N = Math.min(10*this.widths[i], this.signal.length);
                    Generate gp = new Generate();
                    double[] wavelet = gp.generateRicker(N, this.widths[i]);
                    wavelet = UtilMethods.reverse(wavelet);
                    output[i] = this.ricker_cwt(this.signal, wavelet);
                }
                break;
            case MORLET:
                for (int i=0; i<this.widths.length; i++) {
                    int N = Math.min(10*this.widths[i], this.signal.length);
                    Generate gp = new Generate();
                    Complex[] wavelet = gp.generateMorletCWTComplex(N, args, this.widths[i]);
                    wavelet = UtilMethods.reverse(wavelet);
                    output[i] = this.morlet_cwt(this.signal, wavelet);
                }
                break;
            case PAUL:
                for (int i=0; i<this.widths.length; i++) {
                    Generate gp = new Generate();
                    double norm = Math.sqrt(1.0/this.widths[i]);
                    Complex[] wavelet = gp.generatePaulComplex((int)args, this.widths[i], (double)this.widths[i]);
                    for (int w=0; w<wavelet.length; w++) {
                        wavelet[w] = wavelet[w].multiply(norm); //Normalization
                    }
                    output[i] = this.paul_cwt(this.signal, wavelet);
                }
                break;
            default:
                throw new ArithmeticException("wavelet_type must be RICKER, MORLET or PAUL");
        }
        return output;
    }
}
