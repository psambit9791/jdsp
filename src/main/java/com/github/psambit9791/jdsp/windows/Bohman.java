package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

import java.util.Arrays;

/**
 * <h1>Bohman Window</h1>
 * Generates a Bohman window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Bohman extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Bohman class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Bohman(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Bohman class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Bohman(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Bohman Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = UtilMethods.linspace(-1, 1, tempLen, true);
        for (int i=0; i<this.window.length; i++) {
            double fac = Math.abs(this.window[i]);
            this.window[i] = (1 - fac) * Math.cos(Math.PI * fac) + 1.0/Math.PI * Math.sin(Math.PI * fac);
        }
        this.window[0] = 0;
        this.window[tempLen-1] = 0;
        this.window = super.truncate(this.window);
        return this.window;
    }
}
