package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

import java.util.Arrays;

/**
 * <h1>General Cosine Window</h1>
 * Generic weighted sum of cosine terms window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class GeneralCosine extends _Window {
    private double[] window;
    private final double[] weights;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the General Cosine class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param weights Sequence of weighting coefficients
     * @param sym Whether the window is symmetric
     */
    public GeneralCosine(int len, double[] weights, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.weights = weights;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Rectangular class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     * @param weights Sequence of weighting coefficients
     */
    public GeneralCosine(int len, double[] weights) throws IllegalArgumentException {
        this(len, weights, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        double[] tempArr = UtilMethods.linspace(-Math.PI, Math.PI, tempLen, true);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 0);
        for (int i=0; i<this.weights.length; i++) {
            for (int j=0; j<tempArr.length; j++) {
                this.window[j] = this.window[j] + this.weights[i]*Math.cos(i*tempArr[j]);
            }
        }
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the General Cosine Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
