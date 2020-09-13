package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Hamming Window</h1>
 * The Hamming window is a taper formed by using a raised cosine with non-zero endpoints,
 * optimized to minimize the nearest side lobe.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Hamming extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Hamming class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Hamming(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Hamming class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Hamming(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Hamming Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.54, 0.46};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
