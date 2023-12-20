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

import org.apache.commons.math3.complex.Complex;

/**
 * <h2>Interface for Forward Fourier Transform</h2>
 *
 * The _Fourier interface is implemented by all Forward Fourier Transforms.
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public interface _Fourier {

    /**
     * This function performs the fourier transform on the input signal
     */
    void transform();

    /**
     * Returns the magnitude of the fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] The magnitude of the DFT output
     */
    double[] getMagnitude(boolean onlyPositive);

    /**
     * Returns the phase of the fourier transformed sequence in radians
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] The phase of the output in radians
     */
    double[] getPhaseRad(boolean onlyPositive);

    /**
     * Returns the phase of the fourier transformed sequence in degrees
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] The phase of the output in degrees
     */
    double[] getPhaseDeg(boolean onlyPositive);

    /**
     * Returns the magnitude and phase (radians) of the fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The magnitude and phase of the output
     */
    double[][] getMagPhaseRad(boolean onlyPositive);

    /**
     * Returns the magnitude and phase (degrees) of the fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The magnitude and phase of the output
     */
    double[][] getMagPhaseDeg(boolean onlyPositive);

    /**
     * Returns the complex value of the fourier transformed sequence as a 2D matrix
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The complex FFT output
     */
    double[][] getComplex2D(boolean onlyPositive);

    /**
     * Returns the complex value of the fourier transformed sequence as a Complex array
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return Complex[] The complex FFT output
     */
    Complex[] getComplex(boolean onlyPositive);

    /**
     * Returns the length of the modified signal (padded length)
     * @return int The modified length of the input signal
     */
    int getSignalLength();

    /**
     * Returns the frequencies of the FFT bins based on the signal length and sampling frequency
     * @param Fs Sampling frequency of the signal
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] Array of frequency bins
     */
    double[] getFFTFreq(int Fs, boolean onlyPositive);
}
