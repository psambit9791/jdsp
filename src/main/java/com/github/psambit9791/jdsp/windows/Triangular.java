package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

import java.util.Arrays;

/**
 * <h1>Triangular Window</h1>
 * Generates a triangular window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Triangular extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Triangular class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Triangular(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Triangular class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Triangular(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Triangular Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);

        int halfPoint = (tempLen+1)/2 + 1;
        double[] n = UtilMethods.arange(1.0, (double)halfPoint, 1.0);

        if (tempLen%2 == 0) {
            for (int i=0; i<n.length; i++) {
                n[i]  = (2 * n[i] - 1.0)/tempLen;
            }
            double[] nRev = UtilMethods.reverse(n);
            this.window = UtilMethods.concatenateArray(n, nRev);
        }
        else {
            for (int i=0; i<n.length; i++) {
                n[i]  = (2 * n[i])/(tempLen+1);
            }
            double[] nRev = UtilMethods.splitByIndex(n,0, n.length-1);
            nRev = UtilMethods.reverse(nRev);
            this.window = UtilMethods.concatenateArray(n, nRev);
        }
        this.window = super.truncate(this.window);
        return this.window;
    }
}
