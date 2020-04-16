package com.onyx.jdsp.signal;

import java.util.Arrays;

import com.onyx.jdsp.signal.CrossCorrelation;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

/**
 * <h1>Smooth</h1>
 * The Smooth class implements moving average method of smoothing.
 * Reference <a href="http://www.reproducibility.org/RSF/book/gee/ajt/paper_html/node15.html">article</a> for more information on smoothing.
 * The triangular smooth is like the rectangular smooth except that it implements a weighted smoothing function.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */

public class Smooth {

    private double[] signal;
    private double[] smoothing_kernel;
    private double[] output;
    private String mode;

    /**
     * This constructor initialises the prerequisites
     * required to perform smoothing.
     * @param s Signal to be smoother
     * @param wsize Size of the window for smoothing
     * @param mode Method of smoothing to be used. Can be "rectangular" or "triangular"
     */
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

    /**
     * This method smooths the signal and returns it.
     * @return double[] Smoothed signal
     */
    public double[] smoothSignal() {
        if (!this.mode.equals("rectangular") && !this.mode.equals("triangular")) {
            throw new IllegalArgumentException("Mode can only be rectangular or triangular.");
        }
        else {
            CrossCorrelation c = new CrossCorrelation(this.signal, this.smoothing_kernel);
            this.output = c.cross_correlate("valid");
        }
        return this.output;
    }

    /**
     * This getter method to get the kernel used for smoothing.
     * @return double[] Smoothing kernel
     */
    public double[] getKernel() {
        return this.smoothing_kernel;
    }
}
