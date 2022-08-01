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
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Arrays;

/**
 * <h1>Fast Fourier Transform</h1>
 * The FastFourier class performs discrete fourier transform on the input signal using the FFT algorithm and
 * provides different representations of the output to be returned and if the output should be mirrored or not-mirrored.
 * This can be considered a wrapper on top of the Apache Math3 FastFourierTransformer which pre-processes the signal before
 * the operation.
 * Reference <a href="https://mathworld.wolfram.com/FastFourierTransform.html">article</a> for more information on fast fourier transform.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.2
 */
public class FastFourier implements _Fourier {

    private double[] signal;
    private Complex[] output;
    private FastFourierTransformer ft;

    /**
     * This extends the signal such that length is in the nearest power of 2
     */
    private void extendSignal() {
        double power = Math.log(this.signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power));
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length-this.signal.length);
        }
    }

    /**
     * Return the length of the modified signal (padded length)
     */
    public int getSignalLength() {
        return this.signal.length;
    }

    /**
     * Returns the frequencies of the FFT bins based on the signal length and sampling frequency
     * @param Fs Sampling frequency of the signal
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] Array of frequency bins
     */
    public double[] getFFTFreq(int Fs, boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning FFT bins");
        }
        double[] results;
        double val = (double)Fs /this.signal.length;

        if (onlyPositive) {
            int N = (this.signal.length)/2 + 1;
            int[] p1 = UtilMethods.arange(0, N, 1);
            results = Arrays.stream(p1).asDoubleStream().toArray();
        }
        else {
            int N = (this.signal.length - 1)/2 + 1;
            int[] p1 = UtilMethods.arange(0, N, 1);
            int[] p2 = UtilMethods.arange(-(this.signal.length/2), 0, 1);
            results = Arrays.stream(UtilMethods.concatenateArray(p1, p2)).asDoubleStream().toArray();
        }
        results = UtilMethods.scalarArithmetic(results, val, "mul");
        return results;
    }

    /**
     * This constructor initialises the prerequisites required to use FastFourier.
     * @param signal Signal to be transformed
     */
    public FastFourier(double[] signal) {
        this.signal = signal;
        this.extendSignal();
        this.ft = new FastFourierTransformer(DftNormalization.STANDARD);
    }

    public FastFourier(double[] signal, DftNormalization norm) {
        this.signal = signal;
        this.extendSignal();
        this.ft = new FastFourierTransformer(norm);
    }

    public void transform() {
        this.output = this.ft.transform(this.signal, TransformType.FORWARD);
    }

    /**
     * Returns the magnitude of the discrete fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The magnitude of the FFT output
     */
    public double[] getMagnitude(boolean onlyPositive) throws ExceptionInInitializerError{
        Complex[] dftout = getComplex(onlyPositive);
        return Arrays.stream(dftout).mapToDouble(Complex::abs).toArray();
    }

    /**
     * Returns the phase of the discrete fourier transformed sequence in radians
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The phase of the FFT output (in radians)
     */
    public double[] getPhaseRad(boolean onlyPositive) throws ExceptionInInitializerError{
        Complex[] dftout = getComplex(onlyPositive);
        return Arrays.stream(dftout).mapToDouble(Complex::getArgument).toArray();
    }

    /**
     * Returns the phase of the discrete fourier transformed sequence in degrees
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The phase of the FFT output (in degrees)
     */
    public double[] getPhaseDeg(boolean onlyPositive) throws ExceptionInInitializerError{
        double[] dftout = getPhaseRad(onlyPositive);
        return Arrays.stream(dftout).map(Math::toDegrees).toArray();
    }

    /**
     * Returns the magnitude and phase (in radians) of the fourier transformed sequence. The first column
     * of the output contains the magnitude, the second one the phase.
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The magnitude and phase (in radians) of the FFT output in respectively the first and second column
     * @throws ExceptionInInitializerError if called before executing transform() method
     */
    public double[][] getMagPhaseRad(boolean onlyPositive) throws ExceptionInInitializerError {
        double[] dftMag = getMagnitude(onlyPositive);
        double[] dftPhase = getPhaseRad(onlyPositive);
        double[][] out = new double[dftMag.length][2];

        for (int i = 0; i < out.length; i++) {
            out[i][0] = dftMag[i];
            out[i][1] = dftPhase[i];
        }
        return out;
    }

    /**
     * Returns the magnitude and phase (in degrees) of the fourier transformed sequence. The first column
     * of the output contains the magnitude, the second one the phase.
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The magnitude and phase (in degrees) of the FFT output in respectively the first and second column
     * @throws ExceptionInInitializerError if called before executing transform() method
     */
    public double[][] getMagPhaseDeg(boolean onlyPositive) throws ExceptionInInitializerError {
        double[] dftMag = getMagnitude(onlyPositive);
        double[] dftPhase = getPhaseDeg(onlyPositive);
        double[][] out = new double[dftMag.length][2];

        for (int i = 0; i < out.length; i++) {
            out[i][0] = dftMag[i];
            out[i][1] = dftPhase[i];
        }
        return out;
    }

    /**
     * Returns the complex value of the fast fourier transformed sequence as a 2D matrix
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[][] The complex FFT output; first array column = real part; second array column = imaginary part
     */
    public double[][] getComplex2D(boolean onlyPositive) throws ExceptionInInitializerError {
        Complex[] dftout = getComplex(onlyPositive);
        return UtilMethods.complexTo2D(dftout);
    }

    /**
     * Returns the complex value of the fast fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return Complex[] The complex FFT output
     */
    public Complex[] getComplex(boolean onlyPositive) throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning result");
        }
        Complex[] dftout;

        if (onlyPositive) {
            int numBins = this.output.length/2+1;
            dftout = new Complex[numBins];
        }
        else{
            dftout = new Complex[this.output.length];
        }
        System.arraycopy(this.output, 0, dftout, 0, dftout.length);
        return dftout;
    }
}
