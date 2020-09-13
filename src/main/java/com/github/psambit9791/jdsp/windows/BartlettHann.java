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

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the BartlettHann class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public BartlettHann(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the BartlettHann class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public BartlettHann(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Bartlett Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];

        this.window = UtilMethods.arange(0.0, (double)tempLen, 1.0);
        for (int i=0; i<this.window.length; i++) {
            double fac = Math.abs(this.window[i]/(tempLen - 1) - 0.5);
            this.window[i] = 0.62 - 0.48 * fac + 0.38 * Math.cos(2 * Math.PI * fac);
        }

        this.window = super.truncate(this.window);
        return this.window;
    }
}
