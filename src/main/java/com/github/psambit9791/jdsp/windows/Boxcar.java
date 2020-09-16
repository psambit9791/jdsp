package com.github.psambit9791.jdsp.windows;

import java.util.Arrays;

/**
 * <h1>Boxcar Window</h1>
 * Also known as a rectangular window or Dirichlet window, this is equivalent to no window at all.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Boxcar extends _Window {

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Boxcar class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Boxcar(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Boxcar class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Boxcar(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Boxcar Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 1);
        this.window = super.truncate(this.window);
        return this.window;
    }
}
