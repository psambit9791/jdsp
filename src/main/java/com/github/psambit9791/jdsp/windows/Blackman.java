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
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Blackman class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Blackman(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Blackman class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     */
    public Blackman(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        double[] w = {0.42, 0.50, 0.08};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
    }

    /**
     * Generates and returns the Blackman Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
