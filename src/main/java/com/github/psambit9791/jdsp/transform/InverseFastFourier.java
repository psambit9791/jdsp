/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 * <h2>Inverse Fast Fourier Transform</h2>
 * The InverseFastFourier class applies the inverse fast fourier transform on the input sequence (real/complex) and
 * provides different representations of the reconstructed signal to be returned (real signal, complex signal, ...).
 * This should be used for signals transformed using FastFourier.
 * This can be considered a wrapper on top of the Apache Math3 FastFourierTransformer [INVERSE] which pre-processes the signal before
 * the operation.
 *  
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class InverseFastFourier implements _InverseFourier {

    private Complex[] sequence;
    private Complex[] signal = null;
    private FastFourierTransformer ft;

    private void checkOutput() {
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning result");
        }
    }

    private Complex[] toFullSequence(Complex[] signal) {
        int size = (signal.length-1)*2;
        Complex[] out = new Complex[size];
        System.arraycopy(signal, 0, out, 0, signal.length);
        int index = signal.length;
        for (int i=signal.length-2; i>0; i--) {
            out[index] = signal[i].conjugate();
            index++;
        }
        return out;
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastFourier.
     * @param fftOutput Sequence to be transformed (complex). [The FFT output is complex]
     *            Dimension 1: Length, Dimension 2: Real part, complex part
     * @param onlyPositive Only the first half of the FFT output is provided. Used for real signals where the last half
     *                     are complex conjugates of the first half.
     */
    public InverseFastFourier(double[][] fftOutput, boolean onlyPositive) {
        Complex[] temp = UtilMethods.matToComplex(fftOutput);
        if (onlyPositive) {
            this.sequence = this.toFullSequence(temp);
        }
        else {
            this.sequence = temp;
        }
        this.ft = new FastFourierTransformer(DftNormalization.STANDARD);
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastFourier.
     * @param fftOutput Sequence to be transformed (array of Complex type). [The FFT output is complex]
     * @param onlyPositive Only the first half of the FFT output is provided. Used for real signals where the last half
     *                     are complex conjugates of the first half.
     */
    public InverseFastFourier(Complex[] fftOutput, boolean onlyPositive) {
        if (onlyPositive) {
            this.sequence = this.toFullSequence(fftOutput);
        }
        else {
            this.sequence = fftOutput;
        }
        this.ft = new FastFourierTransformer(DftNormalization.STANDARD);
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastFourier.
     * @param fftOutput Sequence to be transformed (array of Complex type). [The FFT output is complex]
     * @param onlyPositive Only the first half of the FFT output is provided. Used for real signals where the last half
     *                     are complex conjugates of the first half.
     * @param norm The normalization option to be used the Inverse Fast Fourier transform.
     */
    public InverseFastFourier(Complex[] fftOutput, boolean onlyPositive, DftNormalization norm) {
        if (onlyPositive) {
            this.sequence = this.toFullSequence(fftOutput);
        }
        else {
            this.sequence = fftOutput;
        }
        this.ft = new FastFourierTransformer(norm);
    }

    /**
     * Performs the inverse fourier transformation on the input signal.
     */
    public void transform() {
        this.signal = this.ft.transform(this.sequence, TransformType.INVERSE);
    }

    /**
     * This method returns the complex value of the generated signal as a Complex array.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return Complex[] The generated signal (complex)
     */
    public Complex[] getComplex() throws ExceptionInInitializerError {
        this.checkOutput();
        return this.signal;
    }

    /**
     * This method returns the complex value of the generated signal as a 2D matrix.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[][] The generated signal (complex as 2D array)
     */
    public double[][] getComplex2D() throws ExceptionInInitializerError {
        checkOutput();
        double[][] ret = new double[this.signal.length][2];
        for (int i=0; i<ret.length; i++) {
            ret[i][0] = this.signal[i].getReal();
            ret[i][1] = this.signal[i].getImaginary();
        }
        return ret;
    }

    /**
     * This method returns the real part of the generated signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The signal (real part)
     */
    public double[] getReal() throws ExceptionInInitializerError {
        this.checkOutput();
        double[] real = new double[this.signal.length];
        for (int i=0; i<real.length; i++) {
            real[i] = this.signal[i].getReal();
        }
        return real;
    }

    /**
     * This method returns the imaginary part of the generated signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The signal (imaginary part)
     */
    public double[] getImaginary() throws ExceptionInInitializerError {
        this.checkOutput();
        double[] imag = new double[this.signal.length];
        for (int i=0; i<imag.length; i++) {
            imag[i] = this.signal[i].getImaginary();
        }
        return imag;
    }

    /**
     * This method returns the magnitude value of the IFFT result.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The signal (magnitude)
     */
    public double[] getMagnitude() throws ExceptionInInitializerError {
        checkOutput();
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].abs();
        }
        return ret;
    }

    /**
     * This method returns the phase value of the IFFT result.
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] phase of the signal
     */
    public double[] getPhase() throws ExceptionInInitializerError {
        checkOutput();
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].getArgument();
        }
        return ret;
    }
}
