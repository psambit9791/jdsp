package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Blackman Window</h1>
 * The Blackman window is a taper formed by using the first three terms of a summation of cosines. It was designed
 * to have close to the minimal leakage possible.  It is close to optimal, only slightly worse than a Kaiser window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Blackman extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Blackman class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Blackman(int len, boolean sym) throws IllegalArgumentException {
        this.sym = sym;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Blackman class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1. Symmetricity is set to True.
     * @param len Length of the window
     */
    public Blackman(int len) throws IllegalArgumentException {
        this.sym = true;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Blackman Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.42, 0.50, 0.08};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
