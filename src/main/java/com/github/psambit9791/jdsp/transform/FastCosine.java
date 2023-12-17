/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.transform.*;

/**
 * <h1>Fast Cosine Transform</h1>
 * The FastCosine class decomposes a finite sequence of data points in terms of a sum of cosine functions of different frequencies.
 * This class implements only Type 1 transform.
 * This can be considered a wrapper on top of the Apache Math3 FastCosineTransformer [FORWARD] which pre-processes the signal before
 * the operation.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FastCosine implements _SineCosine {
    // Only DCT-I

    private double[] signal;
    private double[] output;
    private FastCosineTransformer fct;

    /**
     * Extends the length of the signal to the nearest power of 2.
     */
    private void extendSignal() {
        double power = Math.log(this.signal.length - 1)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power)) + 1;
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length-this.signal.length);
        }
    }

    /**
     * This constructor initialises the prerequisites required to use FastCosine.
     *
     * @param signal The input signal to be transformed.
     */
    public FastCosine(double[] signal) {
        this.signal = signal;
        this.extendSignal();
        this.fct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
    }

    /**
     * This constructor initialises the prerequisites required to use FastCosine.
     *
     * @param signal The input signal to be transformed.
     * @param norm   The normalization option (STANDARD or ORTHOGONAL).
     */
    public FastCosine(double[] signal, Normalization norm) {
        this.signal = signal;
        this.extendSignal();
        if (norm == Normalization.ORTHOGONAL) {
            this.fct = new FastCosineTransformer(DctNormalization.ORTHOGONAL_DCT_I);
        }
        else {
            this.fct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
        }
    }

    /**
     * Performs the cosine transformation on the input signal.
     * @param type Only accepts the value 1.
     * @throws java.lang.IllegalArgumentException If type is not 1
     */
    public void transform(int type) throws IllegalArgumentException {
        if (type != 1) {
            throw new IllegalArgumentException("FastCosine only has type 1 implementation");
        }
        else {
            this.output = this.fct.transform(this.signal, TransformType.FORWARD);
        }
    }

    /**
     * Performs the cosine transformation on the input signal.
     */
    public void transform() {
        this.output = this.fct.transform(this.signal, TransformType.FORWARD);
    }

    /**
     * Returns the output of the transformation.
     *
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The transformed signal.
     */
    public double[] getOutput() throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning result");
        }
        return this.output;
    }

    /**
     * Gets the length of the input signal after preprocessing for FastSine (padded to nearest power of 2).
     *
     * @return int The updated length of the input signal.
     */
    public int getSignalLength() {
        return this.signal.length;
    }
}
