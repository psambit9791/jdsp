/*
 *
 *  * Copyright (c) 2020 Sambit Paul
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
 * <h1>Kaiser Window</h1>
 * The Kaiser window is a taper formed by using a Bessel function.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Kaiser extends _Window{
    private double[] window;
    private final boolean sym;
    private final int len;
    private double beta;

    /**
     * This constructor initialises the Kaiser class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param beta Shape parameter. As beta increases, the window gets narrower.
     * @param sym Whether the window is symmetric
     */
    public Kaiser(int len, double beta, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.beta = beta;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Kaiser class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     * @param beta Shape parameter. As beta increases, the window gets narrower.
     */
    public Kaiser(int len, double beta) throws IllegalArgumentException {
        this(len, beta, true);
    }

    /**
     * Set the shape parameter of the Kaiser window. A larger beta narrows the window shape.
     * @param beta Shape parameter. As beta increases, the window gets narrower.
     */
    public void setBeta(double beta) {
        this.beta = beta;
        generateWindow();
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        double alpha = (tempLen - 1) / 2.0;
        this.window = new double[tempLen];
        int[] temp = UtilMethods.arange(0, tempLen, 1);
        for (int i = 0; i<temp.length; i++) {
            this.window[i] = this.beta * Math.sqrt(1 - Math.pow(((double) temp[i] - alpha)/alpha, 2));
        }
        this.window = UtilMethods.i0(this.window);
        this.window = UtilMethods.scalarArithmetic(this.window, UtilMethods.i0(this.beta), "div");
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the Kaiser Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
