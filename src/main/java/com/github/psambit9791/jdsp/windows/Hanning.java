package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Hanning Window</h1>
 * The Hanning window is a taper formed by using a raised cosine or sine-squared with ends that touch zero.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Hanning extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Hanning class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Hanning(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Hanning class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Hanning(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Hanning Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.5, 0.5};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
