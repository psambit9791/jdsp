package com.onyx.signal;

import org.apache.commons.math3.util.MathArrays;

public class Convolution {
    private double[] signal;
    private double[] kernel;
    private double[] output;

    public Convolution(double[] s, double[] w) {
        this.signal = s;
        this.kernel = w;
        this.output = null;
    }

    public double[] convolve() {
        // Works in "full" mode
        this.output = MathArrays.convolve(this.signal, this.kernel);
        return this.output;
    }

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

    public double[] convolve(double[] sig, double[] w) {
        // Works in "full" mode
        double[] output;
        output = MathArrays.convolve(sig,  w);
        return output;
    }

    public double[] convolve1d(String mode) {

        double[] output = new double[this.signal.length];
        double[] temp;

        if (this.signal.length <= this.kernel.length) {
            throw new IllegalArgumentException("Weight Size should be less than Signal Length");
        }
        else {
            if (mode.equals("reflect") || mode.equals("constant") || mode.equals("nearest") ||
                    mode.equals("mirror") || mode.equals("wrap") || mode.equals("interp")) {
                double[] newSignal = new double[this.signal.length*3];
                newSignal = UtilMethods.padSignalforConvolution(this.signal, mode);
                temp = this.convolve(newSignal, this.kernel);
                // TODO: Process Signal -> split, concatenate
            }
            else  {
                throw new IllegalArgumentException("convolve1d modes can only be reflect, constant, nearest, mirror, " +
                        "wrap or interp (default)");
            }

        }
        return output;
    }
}
