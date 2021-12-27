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
import org.apache.commons.math3.util.MathArrays;

/**
 * <h1>Convolution for Complex Numbers</h1>
 * The ComplexConvolution class implements different modes of convolution for a list of complex numbers.
 * The window can be a list of Complex or real (double) numbers.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class ComplexConvolution {
    private double[] signal;
    private Complex[] complexSignal;
    private double[] kernel;
    private Complex[] complexKernel;
    private Complex[] output;

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * @param signal Signal to be convolved
     * @param window Kernel for convolution
     */
    public ComplexConvolution(Complex[] signal, double[] window) {
        this.signal = null;
        this.complexSignal = signal;
        this.kernel = window;
        this.complexKernel = null;
        this.output = null;
    }

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * @param signal Signal to be convolved
     * @param window Kernel for convolution
     */
    public ComplexConvolution(double[] signal, Complex[] window) {
        this.signal = signal;
        this.complexSignal = null;
        this.kernel = null;
        this.complexKernel = window;
        this.output = null;
    }

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * @param signal Signal to be convolved
     * @param window Kernel for convolution
     */
    public ComplexConvolution(Complex[] signal, Complex[] window) {
        this.signal = null;
        this.complexSignal = signal;
        this.kernel = null;
        this.complexKernel = window;
        this.output = null;
    }

    private void convolveDoubleComplex(String mode) {
        double[][] kernel2D = UtilMethods.transpose(UtilMethods.complexTo2D(this.complexKernel));

        Convolution c_real = new Convolution(this.signal, kernel2D[0]);
        Convolution c_imag = new Convolution(this.signal, kernel2D[1]);

        double[][] temp = {c_real.convolve(mode), c_imag.convolve(mode)};
        temp = UtilMethods.transpose(temp);

        this.output = UtilMethods.matToComplex(temp);
    }

    private void convolveComplexDouble(String mode) {
        double[][] signal2D = UtilMethods.transpose(UtilMethods.complexTo2D(this.complexSignal));

        Convolution c_real = new Convolution(signal2D[0], this.kernel);
        Convolution c_imag = new Convolution(signal2D[1], this.kernel);

        double[][] temp = {c_real.convolve(mode), c_imag.convolve(mode)};
        temp = UtilMethods.transpose(temp);

        this.output = UtilMethods.matToComplex(temp);
    }

    private void convolveComplexComplex(String mode) {
        double[][] signal2D = UtilMethods.transpose(UtilMethods.complexTo2D(this.complexSignal));
        double[][] kernel2D = UtilMethods.transpose(UtilMethods.complexTo2D(this.complexKernel));

        Convolution c_real_real = new Convolution(signal2D[0], kernel2D[0]);
        Convolution c_imag_imag = new Convolution(signal2D[1], kernel2D[1]);
        Convolution c_real_imag = new Convolution(signal2D[0], kernel2D[1]);
        Convolution c_imag_real = new Convolution(signal2D[1], kernel2D[0]);

        double[] real = MathArrays.ebeAdd(c_real_real.convolve(mode), MathArrays.scale(-1, c_imag_imag.convolve(mode)));
        double[] imag = MathArrays.ebeAdd(c_real_imag.convolve(mode), c_imag_real.convolve(mode));

        double[][] temp = {real, imag};
        temp = UtilMethods.transpose(temp);

        this.output = UtilMethods.matToComplex(temp);
    }

    public Complex[] convolve(String mode) throws ExceptionInInitializerError {
        if (this.complexSignal != null && this.kernel != null) {
            this.convolveComplexDouble(mode);
        }
        else if (this.signal != null && this.complexKernel != null) {
            this.convolveDoubleComplex(mode);
        }
        else if (this.complexSignal != null && this.complexKernel != null) {
            this.convolveComplexComplex(mode);
        }
        else {
            throw new ExceptionInInitializerError("This is a complex convolution method. Please use normal convolution for non-complex signals.");
        }
        return this.output;
    }

    public Complex[] convolve() throws ExceptionInInitializerError {
        if (this.signal == null && this.complexKernel == null) {
            this.convolveComplexDouble("full");
        }
        else if (this.complexSignal == null && this.kernel == null) {
            this.convolveDoubleComplex("full");
        }
        else if (this.signal == null && this.kernel == null) {
            this.convolveComplexComplex("full");
        }
        else {
            throw new ExceptionInInitializerError("This is a complex convolution method. Please use normal convolution for non-complex signals.");
        }
        return this.output;
    }
}
