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

import java.util.Arrays;

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
 * @version 1.1
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
     * @throws java.lang.IllegalArgumentException if wsize is greater than signal
     * @throws java.lang.IllegalArgumentException if mode is not rectangular or triangular
     */
    public Smooth(double[] s, int wsize, String mode) throws IllegalArgumentException {
        this.signal = s;
        this.smoothing_kernel = new double[wsize];
        this.mode = mode;
        if (!mode.equals("rectangular") && !mode.equals("triangular")) {
            throw new IllegalArgumentException("Mode can only be rectangular or triangular.");
        }
        if (wsize > s.length) {
            throw new IllegalArgumentException("Kernel cannot be greater than signal.");
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
     * This method smooths the signal using "valid" correlation mode and returns it.
     * @return double[] Smoothed signal
     */
    public double[] smoothSignal() throws IllegalArgumentException{
        CrossCorrelation c = new CrossCorrelation(this.signal, this.smoothing_kernel);
        this.output = c.crossCorrelate("valid");
        return this.output;
    }

    /**
     * This method smooths the signal in a specific correlation mode and returns it.
     * @param correlation_mode The mode in which cross-correlation is performed
     * @throws java.lang.IllegalArgumentException if correlation_mode is not same, valid or full
     * @return double[] Smoothed signal
     */
    public double[] smoothSignal(String correlation_mode) throws IllegalArgumentException{
        if (!correlation_mode.equals("same") && !correlation_mode.equals("valid") && !correlation_mode.equals("full")) {
            throw new IllegalArgumentException("Mode can only be same, valid or full.");
        }
        else {
            CrossCorrelation c = new CrossCorrelation(this.signal, this.smoothing_kernel);
            this.output = c.crossCorrelate(correlation_mode);
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
