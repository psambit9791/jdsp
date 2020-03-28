package com.onyx.signal;

import org.apache.commons.math3.util.MathArrays;

/**
 * <h1>Convolution</h1>
 * The Convolution class implements different variations
 * convolution as provided in numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.convolve.html">convolve()</a> function
 * and scipy.ndimage <a href="https://docs.scipy.org/doc/scipy/reference/generated/scipy.ndimage.convolve1d.html">convolve1d()</a> function
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */

public class Convolution {
    private double[] signal;
    private double[] kernel;
    private double[] output;

    /**
     * This constructor initialises the prerequisites
     * required to perform convolution.
     * @param s Signal to be convolved
     * @param w Kernel for convolution
     */
    public Convolution(double[] s, double[] w) {
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
     * @return double[] Result of convolution.
     */
    public double[] convolve(String mode) {
        double[] temp = MathArrays.convolve(this.signal, this.kernel);
        if (mode.equals("full")) {
            this.output = temp;
        }
        else if (mode.equals("same")) {
            this.output = new double[this.signal.length];
            int iterator = 1;
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
     * @return double[] Result of convolution with same length as input signal
     */
    public double[] convolve1d(String mode) {

        double[] output = new double[this.signal.length];
        double[] temp;

        if (this.signal.length <= this.kernel.length) {
            throw new IllegalArgumentException("Weight Size should be less than Signal Length");
        }
        else {
            if (mode.equals("reflect") || mode.equals("constant") || mode.equals("nearest") ||
                    mode.equals("mirror") || mode.equals("wrap") || mode.equals("interp")) {
                int startVal = this.signal.length + this.kernel.length/2;
                double[] newSignal = UtilMethods.padSignal(this.signal, mode);
                temp = this.convolve(newSignal, this.kernel);
                output = UtilMethods.splitByIndex(temp, startVal, startVal+this.signal.length);
            }
            else  {
                throw new IllegalArgumentException("convolve1d modes can only be reflect, constant, nearest, mirror, " +
                        "wrap or interp (default)");
            }
        }
        return output;
    }

    /**
     * This method perform default convolution using padding in 'reflect' modes.
     * @return double[] Result of convolution with same length as input signal
     */
    public double[] convolve1d() {

        double[] output = new double[this.signal.length];
        double[] temp;

        if (this.signal.length <= this.kernel.length) {
            throw new IllegalArgumentException("Weight Size should be less than Signal Length");
        }
        else {
            int startVal = this.signal.length + this.kernel.length/2;
            double[] newSignal = UtilMethods.padSignal(this.signal, "reflect");
            temp = this.convolve(newSignal, this.kernel);
            output = UtilMethods.splitByIndex(temp, startVal, startVal+this.signal.length);
        }
        return output;
    }
}
