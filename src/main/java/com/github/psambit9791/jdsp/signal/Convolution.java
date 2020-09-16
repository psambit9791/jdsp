/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.util.MathArrays;

/**
 * <h1>Convolution</h1>
 * The Convolution class implements different variations of convolution as provided in numpy
 * <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.convolve.html">convolve()</a> function
 * and scipy.ndimage <a href="https://docs.scipy.org/doc/scipy/reference/generated/scipy.ndimage.convolve1d.html">convolve1d()</a> function
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */

public class Convolution {
    private double[] signal;
    private double[] kernel;
    private double[] output;

    /**
     * This constructor initialises the prerequisites required to perform convolution.
     * @param s Signal to be convolved
     * @param w Kernel for convolution
     * @throws java.lang.IllegalArgumentException if kernel size is greater than or equal to signal length
     */
    public Convolution(double[] s, double[] w) {
        if (s.length <= w.length) {
            throw new IllegalArgumentException("Weight Size should be less than Signal Length");
        }
        this.signal = s;
        this.kernel = w;
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

        double[] output = new double[this.signal.length];
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
