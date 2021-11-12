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

public class FastFourier {

    private double[] signal;
    private Complex[] output;
    private FastFourierTransformer ft;

    private void extendSignal() {
        double power = Math.log(this.signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power));
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length);
        }
    }

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

    public void fft() {
        this.output = this.ft.transform(this.signal, TransformType.FORWARD);
    }

    /**
     * Returns the magnitude of the discrete fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return double[] The magnitude of the DFT output
     */
    public double[] getMagnitude(boolean onlyPositive) throws ExceptionInInitializerError{
        Complex[] dftout = getComplex(onlyPositive);
        return Arrays.stream(dftout).mapToDouble(Complex::abs).toArray();
    }

    /**
     * Returns the phase of the discrete fourier transformed sequence in radians
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return double[] The phase of the DFT output (in radians)
     */
    public double[] getPhaseRad(boolean onlyPositive) throws ExceptionInInitializerError{
        Complex[] dftout = getComplex(onlyPositive);
        return Arrays.stream(dftout).mapToDouble(Complex::getArgument).toArray();
    }

    /**
     * Returns the phase of the discrete fourier transformed sequence in degrees
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return double[] The phase of the DFT output (in degrees)
     */
    public double[] getPhaseDeg(boolean onlyPositive) throws ExceptionInInitializerError{
        double[] dftout = getPhaseRad(onlyPositive);
        return Arrays.stream(dftout).map(Math::toDegrees).toArray();
    }

    /**
     * Returns the magnitude and phase (in radians) of the fourier transformed sequence. The first column
     * of the output contains the magnitude, the second one the phase.
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The magnitude and phase (in radians) of the DFT output in respectively the first and second column
     * @throws ExceptionInInitializerError if called before executing dft() method
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
     * @return double[][] The magnitude and phase (in degrees) of the DFT output in respectively the first and second column
     * @throws ExceptionInInitializerError if called before executing dft() method
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
     * Returns the complex value of the discrete fourier transformed sequence as a 2D matrix
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return double[][] The complex DFT output; first array column = real part; second array column = imaginary part
     */
    public double[][] getComplex2D(boolean onlyPositive) throws ExceptionInInitializerError {
        Complex[] dftout = getComplex(onlyPositive);
        return UtilMethods.complexTo2D(dftout);
    }

    /**
     * Returns the complex value of the fast fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return Complex[] The complex DFT output
     */
    public Complex[] getComplex(boolean onlyPositive) throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute fft() function before returning result");
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
