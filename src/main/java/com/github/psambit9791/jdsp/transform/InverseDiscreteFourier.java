/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

import org.apache.commons.math3.complex.Complex;

/**
 * <h1>Inverse Discrete Fourier Transform</h1>
 * The InverseDiscreteFourier class applies the inverse discrete fourier transform on the input sequence (real/complex) and
 * provides different representations of the reconstructed signal to be returned (real signal, complex signal, absolute signal)
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class InverseDiscreteFourier {

    private double[][] complex_sequence;
    private double[] real_sequence;
    private boolean isComplex;
    private Complex[] signal = null;
    private boolean onlyPositive;

    /**
     * This constructor initialises the prerequisites required to use InverseDiscreteFourier.
     * @param seq Sequence to be transformed (complex). [The FFT output is complex]
     *            Dimension 1: Length, Dimension 2: Real part, complex part
     * @param onlyPositive Only the first half of the FFT output is provided. Used for real signals where the last half
     *                     are complex conjugates of the first half.
     */
    public InverseDiscreteFourier(double[][] seq, boolean onlyPositive) {
        this.complex_sequence = seq;
        this.isComplex = true;
        this.onlyPositive = onlyPositive;
    }

    /**
     * This constructor initialises the prerequisites required to use InverseDiscreteFourier.
     * @param seq Sequence to be transformed (real) [The FFT output has only real numbers]
     */
    public InverseDiscreteFourier(double[] seq, boolean onlyPositive) {
        this.real_sequence = seq;
        this.isComplex = false;
        this.onlyPositive = onlyPositive;
    }

    /**
     * This function performs the inverse discrete fourier transform on the input sequence
     */
    public void idft() {
        if (this.isComplex) {
            if (this.onlyPositive) {
                this.idftComplexMirror();
            }
            else {
                this.idftComplexFull();
            }
        }
        else {
            if (this.onlyPositive) {
                this.idftRealMirror();
            }
            else {
                this.idftRealFull();
            }
        }
    }

    /**
     * This method returns the real part of the generated signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing idft() method
     * @return double[] The signal (real part)
     */
    public double[] getRealSignal() throws ExceptionInInitializerError {
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute idft() function before returning result");
        }
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].getReal();
        }
        return ret;
    }

    /**
     * This method returns the absolute value of the generated signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing idft() method
     * @return double[] The signal (absolute)
     */
    public double[] getAbsoluteSignal() throws ExceptionInInitializerError {
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute idft() function before returning result");
        }
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].abs();
        }
        return ret;
    }

    /**
     * This method returns the complex value of the generated signal as a 2D matrix.
     * @throws java.lang.ExceptionInInitializerError if called before executing idft() method
     * @return double[][] The signal (complex)
     */
    public double[][] getComplexSignal() throws ExceptionInInitializerError {
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute idft() function before returning result");
        }
        double[][] ret = new double[this.signal.length][2];
        for (int i=0; i<ret.length; i++) {
            ret[i][0] = this.signal[i].getReal();
            ret[i][1] = this.signal[i].getImaginary();
        }
        return ret;
    }

    /**
     * This method returns the complex value of the generated signal as a Complex array.
     * @throws java.lang.ExceptionInInitializerError if called before executing idft() method
     * @return double[] The signal (complex)
     */
    protected Complex[] getAsComplex() throws ExceptionInInitializerError
    {
        if (this.signal == null) {
            throw new ExceptionInInitializerError("Execute idft() function before returning result");
        }
        return this.signal;
    }


    private void idftRealFull() {
        Complex[] out = new Complex[this.real_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigValR += this.real_sequence[m]*Math.cos(angle);
                sigValI += this.real_sequence[m]*Math.sin(angle);
            }
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }

    private void idftComplexFull() {
        Complex[] out = new Complex[this.complex_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = (2*Math.PI*t*m)/out.length;
                sigValR += (this.complex_sequence[m][0]*Math.cos(angle) - this.complex_sequence[m][1]*Math.sin(angle));
                sigValI += (this.complex_sequence[m][0]*Math.sin(angle) + this.complex_sequence[m][1]*Math.cos(angle));
            }
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }

    private void idftRealMirror() {
        int size = (this.real_sequence.length-1)*2;
        Complex[] out = new Complex[size];
        double[] full_real = new double[size];

        int index = 0;
        for (int i=0; i<this.real_sequence.length; i++) {
            full_real[index] = this.real_sequence[i];
            index++;
        }
        for (int i=this.real_sequence.length-2; i>0; i--) {
            full_real[index] = this.real_sequence[i];
            index++;
        }

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigValR += full_real[m]*Math.cos(angle);
                sigValI += full_real[m]*Math.sin(angle);
            }
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }

    private void idftComplexMirror() {
        int size = (this.complex_sequence.length-1)*2;
        Complex[] out = new Complex[size];
        double[][] full_cplx = new double[size][2];

        int index = 0;
        for (int i=0; i<this.complex_sequence.length; i++) {
            full_cplx[index][0] = this.complex_sequence[i][0];
            full_cplx[index][1] = this.complex_sequence[i][1];
            index++;
        }
        for (int i=this.complex_sequence.length-2; i>0; i--) {
            full_cplx[index][0] = this.complex_sequence[i][0];
            full_cplx[index][1] = -this.complex_sequence[i][1];
            index++;
        }

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = (2*Math.PI*t*m)/out.length;
                sigValR += (full_cplx[m][0]*Math.cos(angle) - full_cplx[m][1]*Math.sin(angle));
                sigValI += (full_cplx[m][0]*Math.sin(angle) + full_cplx[m][1]*Math.cos(angle));
            }
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }
}
