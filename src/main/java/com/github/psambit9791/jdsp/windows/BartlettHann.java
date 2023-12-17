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

/**
 * <h2>Bartlett-Hann Window</h2>
 * Generates a modified Bartlett-Hann window
 *  
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
