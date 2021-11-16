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

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;


/**
 * <h1>Discrete Fourier Transform</h1>
 * The DiscreteFourier class applies the discrete fourier transform on the input signal and
 * provides different representations of the output to be returned (absolute values or complex values)
 * and if the output should be mirrored or not-mirrored
 * Reference <a href="https://mathworld.wolfram.com/DiscreteFourierTransform.html">article</a> for more information on discrete fourier transform.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.2
 */
public class DiscreteFourier implements _Fourier {

    private double[] signal;
    private Complex[] output = null;

    /**
     * This constructor initialises the prerequisites required to use DiscreteFourier.
     * @param s Signal to be transformed
     */
    public DiscreteFourier(double[] s) {
        this.signal = s;
    }

    /**
     * Return the length of the modified signal (padded length)
     */
    public int getSignalLength() {
        return this.signal.length;
    }

    /**
     * This function performs the fourier transform on the input signal
     */
    public void transform() {
        Complex[] out = new Complex[this.signal.length];

        for (int k=0; k<out.length; k++) {
            double real = 0;
            double imag = 0;
            for (int t=0; t<out.length; t++) {
                double angle = (2*Math.PI*t*k)/out.length;
                real += this.signal[t]*Math.cos(angle);
                imag += -this.signal[t]*Math.sin(angle);
            }
            out[k] = new Complex(real, imag);
        }
        this.output = out;
    }

    // Refer to this post to know the relevance of only positive: https://dsp.stackexchange.com/a/4827
    // About plotting, please refer here: https://stackoverflow.com/a/25735274

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
     * Returns the complex value of the discrete fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @throws java.lang.ExceptionInInitializerError if called before executing dft() method
     * @return Complex[] The complex DFT output
     */
    public Complex[] getComplex(boolean onlyPositive) throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute dft() function before returning result");
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
