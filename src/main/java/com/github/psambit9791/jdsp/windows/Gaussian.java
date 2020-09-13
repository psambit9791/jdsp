package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>Gaussian Window</h1>
 * Generates a Gaussian window.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Gaussian extends _Window {

    double[] window;
    double std;
    boolean sym;
    int len;

    /**
     * This constructor initialises the FlatTop class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Gaussian(int len, double std, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.std = std;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the FlatTop class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Gaussian(int len, double std) throws IllegalArgumentException {
        this.len = len;
        this.std = std;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the FlatTop Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = UtilMethods.arange(0.0, (double)tempLen, 1.0);
        for (int i=0; i<this.window.length; i++) {
            this.window[i] = this.window[i] - ((double)tempLen - 1.0)/2.0;
            double sig2 = 2*this.std*this.std;
            this.window[i] = Math.exp(-(this.window[i]*this.window[i])/sig2);
        }
        this.window = super.truncate(this.window);
        return this.window;
    }
}
