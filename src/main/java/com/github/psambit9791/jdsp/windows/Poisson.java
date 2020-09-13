package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>Poisson Window</h1>
 * Generates an Poisson (or exponential) window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Poisson extends _Window {

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Poisson class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Poisson(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Poisson class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Poisson(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Poisson Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        double centre = ((double)tempLen-1.0)/2.0;
        this.window = UtilMethods.arange(0.0, (double)tempLen, 1.0);
        for (int i=0; i<this.window.length; i++) {
            this.window[i] = Math.exp(-Math.abs(this.window[i] - centre));
        }
        this.window = super.truncate(this.window);
        return this.window;
    }
}
