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

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import java.util.Arrays;

// Using FFT method
public class Deconvolution {

    private double[] signal;
    private double[] kernel;
    private int shape;
    private double[] output;

    public Deconvolution(double[] signal, double[] window) {
        this.shape = Math.max(signal.length, window.length);
        double[] padding = new double[this.shape - Math.min(signal.length, window.length)];
        Arrays.fill(padding, 0.0);
        if (signal.length < this.shape) {
            signal = UtilMethods.concatenateArray(signal, padding);
        }
        else if (window.length < this.shape){
            window = UtilMethods.concatenateArray(window, padding);
        }
        this.signal = signal;
        this.kernel = window;
    }
}
