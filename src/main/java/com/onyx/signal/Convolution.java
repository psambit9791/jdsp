package com.onyx.signal;

import org.apache.commons.math3.util.MathArrays;

public class Convolution {
    private double[] signal;
    private double[] kernel;
    private double[] output;
    private String mode;

    public Convolution(double[] s, double[] w, String mode) {
        this.signal = s;
        this.kernel = w;
        this.mode = mode;
    }

    public void convolve() {
        double[] temp = MathArrays.convolve(this.signal, this.kernel);
        if (this.mode.equals("full")) {
            this.output = temp;
        }
        else if (this.mode.equals("same")) {
            this.output = new double[this.signal.length];
            int iterator = 1;
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = temp[iterator];
                iterator++;
            }
        }
        else if (this.mode.equals("valid")) {
            this.output = new double[this.signal.length - this.kernel.length + 1];
            int iterator = this.kernel.length-1;;
            for (int i=0; i<this.output.length; i++) {
                this.output[i] = temp[iterator];
                iterator++;
            }
        }
        else {
            throw new IllegalArgumentException("Mode can only be full, same or valid.");
        }
    }

    public double[] getOutput() {
        return this.output;
    }
}
