package com.onyx.signal;

import java.util.Arrays;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

public class Smooth {

    private double[] signal;
    private double[] smoothing_kernel;
    private double[] output;
    private String mode;

    public Smooth(double[] s, int wsize, String mode) {
        this.signal = s;
        this.smoothing_kernel = new double[wsize];
        this.mode = mode;
        if (wsize > s.length) {
            throw new ArithmeticException("Kernel cannot be greater than signal.");
        }
        this.setKernel(this.mode);
    }

    private void setKernel(String type) {
        if (type.equals("rectangular")) {
            double value = 1.0/this.smoothing_kernel.length;
            Arrays.fill(this.smoothing_kernel, value);
        }
        else if (type.equals("triangular")) {
            int iterator = 0;
            for (int i=1; i<=this.smoothing_kernel.length/2; i++) {
                this.smoothing_kernel[iterator] = (double)i;
                iterator++;
            }
            if (this.smoothing_kernel.length%2 != 0) {
                this.smoothing_kernel[iterator] = (double)Math.ceil(smoothing_kernel.length/2.0);
                iterator++;
            }
            for (int i=this.smoothing_kernel.length/2; i>=1; i--) {
                this.smoothing_kernel[iterator] = (double)i;
                iterator++;
            }
        }
        double scaling_factor = 1.0/StatUtils.sum(this.smoothing_kernel);
        MathArrays.scaleInPlace(scaling_factor, this.smoothing_kernel);
    }

    public double[] smoothSignal() {
        if (!this.mode.equals("rectangular") && !this.mode.equals("triangular")) {
            throw new IllegalArgumentException("Mode can only be rectangular or triangular.");
        }
        else {
            CrossCorrelation c = new CrossCorrelation(this.signal, this.smoothing_kernel, "valid");
            this.output = c.crossCorrelate();
        }
        return this.output;
    }

    public double[] getKernel() {
        return this.smoothing_kernel;
    }
}
