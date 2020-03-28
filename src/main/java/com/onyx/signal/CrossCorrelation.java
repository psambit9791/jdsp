package com.onyx.signal;

/**
 * <h1>Cross-Correlation</h1>
 * The Cross-Correlation class implements
 * correlation as provided in numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.correlate.html">correlate()</a>
 * function.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */

public class CrossCorrelation {

    private double[] signal;
    private double[] kernel;
    private double[] output;

    /**
     * This constructor initialises the prerequisites
     * required to perform cross-correlation.
     * @param s Signal to be convolved
     * @param w Kernel for convolution
     */
    public CrossCorrelation(double[] s, double[] w) {
        this.signal = s;
        this.kernel = w;
    }

    /**
     * This is the default cross-correlation procedure which works in "valid" mode.
     * @return double[] The result of correlation.
     */
    public double[] crossCorrelate() {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve("valid");
        return this.output;
    }

    /**
     * This is the discrete linear convolution procedure which works in the specified mode.
     * @param mode Mode in which correlation will work. Can be 'full', 'same' or 'valid'
     * @return double[] Result of cross-correlation.
     */
    public double[] crossCorrelate(String mode) {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve(mode);
        return this.output;
    }
}
