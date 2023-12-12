/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Triangular class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Triangular(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Triangular class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Triangular(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);

        int halfPoint = (tempLen+1)/2 + 1;
        double[] n = UtilMethods.arange(1.0, halfPoint, 1.0);

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
    }

    /**
     * Generates and returns the Triangular Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
