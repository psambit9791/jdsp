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
    private double[] window;
    private final double std;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the FlatTop class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param std The standard deviation
     * @param sym Whether the window is symmetric
     */
    public Gaussian(int len, double std, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.std = std;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the FlatTop class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     * @param std The standard deviation
     */
    public Gaussian(int len, double std) throws IllegalArgumentException {
        this(len, std, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = UtilMethods.arange(0.0, tempLen, 1.0);
        for (int i=0; i<this.window.length; i++) {
            this.window[i] = this.window[i] - ((double)tempLen - 1.0)/2.0;
            double sig2 = 2*this.std*this.std;
            this.window[i] = Math.exp(-(this.window[i]*this.window[i])/sig2);
        }
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the FlatTop Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
