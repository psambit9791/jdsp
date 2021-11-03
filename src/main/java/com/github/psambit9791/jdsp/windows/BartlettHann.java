package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>Bartlett-Hann Window</h1>
 * Generates a modified Bartlett-Hann window
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class BartlettHann extends _Window {
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the BartlettHann class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public BartlettHann(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the BartlettHann class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public BartlettHann(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);

        this.window = UtilMethods.arange(0.0, tempLen, 1.0);
        for (int i=0; i<this.window.length; i++) {
            double fac = Math.abs(this.window[i]/(tempLen - 1) - 0.5);
            this.window[i] = 0.62 - 0.48 * fac + 0.38 * Math.cos(2 * Math.PI * fac);
        }

        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the Bartlett Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
