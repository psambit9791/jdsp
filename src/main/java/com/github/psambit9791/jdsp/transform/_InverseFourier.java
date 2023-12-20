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
 * <h2>Interface for Inverse Fourier Transform</h2>
 *
 * The _InverseFourier interface is implemented by all Inverse Fourier Transforms.
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public interface _InverseFourier {

    /**
     * This function performs the inverse fourier transform on the input signal
     */
    void transform();

    /**
     * This method returns the real part of the IDFT result.
     * @return double[] The signal (real part only)
     */
    double[] getReal();

    /**
     * This method returns the imaginary part of the IDFT result.
     * @return double[] The signal (imaginary part only)
     */
    double[] getImaginary();

    /**
     * This method returns the magnitude of the IDFT result.
     * @return double[] The magnitude of the signal
     */
    double[] getMagnitude();

    /**
     * This method returns the phase (in radians) of the IDFT result.
     * @return double[] The phase of the signal (in radians)
     */
    double[] getPhase();

    /**
     * This method returns the IDFT result as a Complex array.
     * @return double[][] The generated signal (complex as 2D)
     */
    double[][] getComplex2D();

    /**
     * This method returns the IDFT result as a Complex array.
     * @return Complex[] The generated signal (complex)
     */
    Complex[] getComplex();
}
