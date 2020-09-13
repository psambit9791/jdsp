package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>Bartlett Window</h1>
 * The Bartlett window is very similar to a triangular window, except that the end points are at zero. It is often
 * used in signal processing for tapering a signal, without generating too much ripple in the frequency domain.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Bartlett extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Bartlett class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Bartlett(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Bartlett class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Bartlett(int len) throws IllegalArgumentException {
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
            if (this.window[i] <= (tempLen-1)/2.0) {
                this.window[i] = 2.0*this.window[i]/(tempLen - 1);
            }
            else {
                this.window[i] = 2.0 - 2.0*this.window[i]/(tempLen - 1);
            }
        }

        this.window = super.truncate(this.window);
        return this.window;
    }
}
