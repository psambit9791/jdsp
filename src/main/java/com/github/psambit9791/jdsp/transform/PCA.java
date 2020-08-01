/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

/**
 * <h1>Principal Component Analysis (PCA)</h1>
 * The PCA class reduces the dimensionality of the input multi-channel input and
 * provide a signal with reduced dimensions by transforming the input to a new set of
 * variables called Principal Components.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class PCA {

    private double[][] signal;
    private int n_components;
    public double[] explained_variance;
    public double[] explained_variance_ratio;

    /**
     * This constructor initialises the prerequisites required to use PCA.
     * @throws java.lang.ExceptionInInitializerError if n_components less than 1 or greater than total channels in signal
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Channels, Dimension 2: Channel Data
     * @param n_components Number of components to keep. Must be greater than 0 and less than the original number of channels in the signal
     */
    public PCA(double[][] signal, int n_components) throws ExceptionInInitializerError {
        if ((n_components > signal.length) || (n_components <= 0)) {
            throw new ExceptionInInitializerError("n_components must be greater than 0 and less than total channels in signal");
        }
        this.signal = signal;
        this.n_components = n_components;
    }
}
