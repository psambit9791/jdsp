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

/**
 * <h1>Nuttall Window</h1>
 * Generates a minimum 4-term Blackman-Harris window according to Nuttall.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Nuttall extends _Window{
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Nuttall class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Nuttall(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.sym = sym;
        this.len = len;
        generateWindow();
    }

    /**
     * This constructor initialises the Nuttall class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Nuttall(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        double[] w = {0.3635819, 0.4891775, 0.1365995, 0.0106411};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
    }

    /**
     * Generates and returns the Nuttall Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
