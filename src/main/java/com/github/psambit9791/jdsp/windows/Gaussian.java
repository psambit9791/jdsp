/*
 *
 *  * Copyright (c) 2023 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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
