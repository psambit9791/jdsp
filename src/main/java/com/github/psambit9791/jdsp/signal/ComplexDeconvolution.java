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
import org.apache.commons.math3.complex.Complex;

import java.security.InvalidParameterException;

/**
 * <h1>Complex Deconvolution</h1>
 * The ComplexDeconvolution class implements methods to recover signals which are convolved with a kernel. This function only
 * works if the output of the convolution is a complex signal and if the original signal has been convolved in 'same' mode
 * or 'full' mode. Given the convolved signal, the convolutional kernel used and the mode of convolution; the process
 * can recover the original signal.
 * The recovered signals can be complex or real.
 * For 'full' mode, FFT-based deconvolution is used.
 * For 'same' mode, the overlap-and-add based deconvolution is used.
 * NOTE: Outputs may vary from the *scipy.signal.deconvolve* implementation which uses inverse filtering.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class ComplexDeconvolution {

    public double[] signal;
    public Complex[] complexSignal;
    public double[] kernel;
    public Complex[] complexKernel;
    public Complex[] convolutionOutput;

    public boolean complex;


    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * Geenerates a complex signal
     * @param signal The output of the complex convolution
     * @param window Kernel for convolution
     */
    public ComplexDeconvolution(Complex[] signal, double[] window) {
        this.convolutionOutput = signal;
        this.kernel = window;
        this.complex = true;
    }

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * Generates a real signal.
     * @param signal The output of the complex convolution
     * @param window Kernel for convolution
     */
    public ComplexDeconvolution(Complex[] signal, Complex[] window) {
        this.convolutionOutput = signal;
        this.complexKernel = window;
        this.complex = false;
    }

    /**
     * Perform deconvolution when the output is complex and the kernel is real.
     * @param mode Mode in which convolution was performed. Can be either 'full' or 'same'.
     */
    private void deconvolve2Complex(String mode) {
        double[][] out = UtilMethods.transpose(UtilMethods.complexTo2D(this.convolutionOutput));
        double[] window = this.kernel;

        Deconvolution d1 = new Deconvolution(out[0], window);
        Deconvolution d2 = new Deconvolution(out[1], window);
        double[][] sig = {d1.deconvolve(mode), d2.deconvolve(mode)};
        sig = UtilMethods.transpose(sig);
        this.complexSignal = UtilMethods.matToComplex(sig);
    }

    /**
     * Perform deconvolution when the output and the kernel are both complex.
     * @param mode Mode in which convolution was performed. Can be either 'full' or 'same'.
     */
    private void deconvolve2Double(String mode) {
        double[][] out = UtilMethods.transpose(UtilMethods.complexTo2D(this.convolutionOutput));
        double[][] window = UtilMethods.transpose(UtilMethods.complexTo2D(this.complexKernel));

        Deconvolution d1 = new Deconvolution(out[0], window[0]);
        this.signal = d1.deconvolve(mode);
    }

    /**
     * This function is a hyper-function which determines which type of deconvolution to perform depending on the convolved
     * signal and the window.
     * @param mode String to state which mode of convolution was performed on the convolved signal.
     *             Can  either be 'full' or 'same'. 'valid' mode is not supported.
     */
    public void deconvolve(String mode) {
        if (this.complexKernel != null) {
            this.deconvolve2Double(mode);
        }
        else if (this.kernel != null) {
            this.deconvolve2Complex(mode);
        }
        else {
            throw new ExceptionInInitializerError("Either signal is complex & kernel is real, or signal is real & kernel is complex.");
        }
    }

    /**
     * If the convolved signal is Complex and the window is Complex, the signal generated is real. This function returns
     * the recovered real signal.
     * @return double[]
     */
    public double[] getRealOutput() {
        if (this.complex) {
            throw new InvalidParameterException("The signal generated is complex. Please use getComplexOutput().");
        }
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute deconvolve() function before returning result");
        }
        return this.signal;
    }

    /**
     * If the convolved signal is Complex and the window is real, the signal generated is Complex. This function returns
     * the recovered Complex signal.
     * @return Complex[]
     */
    public Complex[] getComplexOutput() {
        if (!this.complex) {
            throw new InvalidParameterException("The signal generated is real. Please use getRealOutput().");
        }
        if (this.complexSignal == null) {
            throw new ExceptionInInitializerError("Execute deconvolve() function before returning result");
        }
        return this.complexSignal;
    }
}
