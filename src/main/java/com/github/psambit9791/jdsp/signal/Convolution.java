/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.FastFourier;
import com.github.psambit9791.jdsp.transform.InverseFastFourier;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h2>Convolution</h2>
 * The Convolution class implements different variations of convolution as provided in numpy
 * <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.convolve.html">convolve()</a> function
 * and scipy.ndimage <a href="https://docs.scipy.org/doc/scipy/reference/generated/scipy.ndimage.convolve1d.html">convolve1d()</a> function
 *  
 *
 * @author  Sambit Paul
 * @version 1.2
 */

public class Convolution {
    private double[] signal;
    private double[] kernel;
    private double[] output;

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * @param signal Signal to be convolved
     * @param window Kernel for convolution
     */
    public Convolution(double[] signal, double[] window) {
        this.signal = signal;
        this.kernel = window;
        this.output = null;
    }

    /**
     * This is the default discrete linear convolution procedure which works in full mode.
     * @return double[] The result of convolution.
     */
    public double[] convolve() {
        // Works in "full" mode
        this.output = MathArrays.convolve(this.signal, this.kernel);
        return this.output;
    }

    /**
     * This is the discrete linear convolution procedure which works in the specified mode.
     * @param mode Mode in which convolution will work. Can be 'full', 'same' or 'valid'
     * @throws java.lang.IllegalArgumentException if mode is not full, same or valid
     * @return double[] Result of convolution.
     */
    public double[] convolve(String mode) {
        double[] temp = MathArrays.convolve(this.signal, this.kernel);
        if (mode.equals("full")) {
            this.output = temp;
        }
        else if (mode.equals("same")) {
            this.output = new double[this.signal.length];
            int iterator = Math.abs(temp.length - this.signal.length)/2;
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = temp[iterator];
                iterator++;
            }
        }
        else if (mode.equals("valid")) {
            this.output = new double[this.signal.length - this.kernel.length + 1];
            int iterator = this.kernel.length-1;;
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = temp[iterator];
                iterator++;
            }
        }
        else {
            throw new IllegalArgumentException("convolve modes can only be full, same or valid");
        }
        return this.output;
    }

    /**
     * Performs fast convolution of the signal and kernel using the FFT method with the default mode 'full'.
     *
     * @return double[] Result of convolution.
     */
    public double[] fastConvolve() {
        return fastConvolve("full");
    }

    /**
     * Performs fast convolution of the signal and kernel using the FFT method in the specified mode.
     *
     * @param mode Mode in which convolution will work. Can be 'full', 'same' or 'valid'.
     * @throws IllegalArgumentException if mode is not 'full', 'same', or 'valid'.
     * @return double[] Result of convolution.
     */
    public double[] fastConvolve(String mode) {
        int maxLength = Math.max(this.signal.length, this.kernel.length);
        int newSize = (int) UtilMethods.nextPowerOfTwo(2 * maxLength - 1);

        // Pad signals to the new size
        double[] xPadded = new double[newSize];
        double[] yPadded = new double[newSize];
        System.arraycopy(this.signal, 0, xPadded, 0, this.signal.length);
        System.arraycopy(this.kernel, 0, yPadded, 0, this.kernel.length);

        FastFourier fft1 = new FastFourier(xPadded);
        fft1.transform();
        Complex[] fftX = fft1.getComplex(false);

        FastFourier fft2 = new FastFourier(yPadded);
        fft2.transform();
        Complex[] fftY = fft2.getComplex(false);

        Complex[] convolutionBuffer = new Complex[newSize];
        for (int i = 0; i < newSize; i++) {
            convolutionBuffer[i] = fftX[i].multiply(fftY[i]);
        }

        // Extract the relevant part of the convolution
        InverseFastFourier ifft1 = new InverseFastFourier(convolutionBuffer, false);
        ifft1.transform();
        convolutionBuffer = ifft1.getComplex();

        int convolutionLength = this.signal.length + this.kernel.length - 1;
        Complex[] convolution = new Complex[convolutionLength];
        System.arraycopy(convolutionBuffer, 0, convolution, 0, convolutionLength);

        double[] result = Arrays.stream(convolution)
                .map(Complex::getReal)
                .mapToDouble(Double::doubleValue).toArray();

        // Adjust result based on the mode
        double[] output;
        if ("full".equalsIgnoreCase(mode)) {
            output = result;
        } else if ("same".equalsIgnoreCase(mode)) {
            int start = Math.abs(result.length - this.signal.length) / 2;
            output = Arrays.copyOfRange(result, start, start + this.signal.length);
        } else if ("valid".equalsIgnoreCase(mode)) {
            output = Arrays.copyOfRange(result, this.kernel.length - 1, this.signal.length);
        } else {
            throw new IllegalArgumentException("Convolve modes can only be 'full', 'same' or 'valid'.");
        }

        return output;
    }

    private double[] convolve(double[] sig, double[] w) {
        // Works in "full" mode
        double[] output;
        output = MathArrays.convolve(sig,  w);
        return output;
    }

    /**
     * This method perform convolution using padding in different modes.
     * @param mode Mode in which convolution will work. Can be 'reflect', 'constant' or 'nearest', 'mirror' or 'wrap'
     * @throws java.lang.IllegalArgumentException if kernel size is greater than or equal to signal length
     * @return double[] Result of convolution with same length as input signal
     */
    public double[] convolve1d(String mode) {

        double[] output;
        double[] temp;

        if (mode.equals("reflect") || mode.equals("constant") || mode.equals("nearest") ||
                mode.equals("mirror") || mode.equals("wrap")) {
            int startVal = this.signal.length + this.kernel.length/2;
            double[] newSignal = UtilMethods.padSignal(this.signal, mode);
            temp = this.convolve(newSignal, this.kernel);
            output = UtilMethods.splitByIndex(temp, startVal, startVal+this.signal.length);
        }
        else  {
            throw new IllegalArgumentException("convolve1d modes can only be reflect, constant, nearest mirror, " +
                    "or wrap");
        }
        return output;
    }

    /**
     * This method perform default convolution using padding in 'reflect' modes.
     * @return double[] Result of convolution with same length as input signal
     */
    public double[] convolve1d() throws IllegalArgumentException {
        // Works in "reflect" mode
        double[] output;
        double[] temp;

        int startVal = this.signal.length + this.kernel.length/2;
        double[] newSignal = UtilMethods.padSignal(this.signal, "reflect");
        temp = this.convolve(newSignal, this.kernel);
        output = UtilMethods.splitByIndex(temp, startVal, startVal+this.signal.length);
        return output;
    }
}
