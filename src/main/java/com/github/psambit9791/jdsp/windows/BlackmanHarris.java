package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Blackman-Harris Window</h1>
 * The Blackman-Harris window is a taper formed by using the first three terms of a summation of cosines. It was designed
 * to have close to the minimal leakage possible.  It is close to optimal, only slightly worse than a Kaiser window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class BlackmanHarris extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the BlackmanHarris class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public BlackmanHarris(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the BlackmanHarris class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public BlackmanHarris(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the BlackmanHarris Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.35875, 0.48829, 0.14128, 0.01168};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
