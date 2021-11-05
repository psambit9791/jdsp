package com.github.psambit9791.jdsp.windows;

import java.util.Arrays;

/**
 * <h1>Rectangular Window</h1>
 * Also known as a Boxcar window or Dirichlet window, this is equivalent to no window at all.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Rectangular extends _Window {
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Rectangular class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Rectangular(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Rectangular class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Rectangular(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 1);
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the Rectangular Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
