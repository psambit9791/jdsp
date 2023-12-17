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
import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 * <h2>Inverse Fast Cosine Transform</h2>
 * The InverseFastCosine class applies the inverse fast cosine transform on the input sequence and returns the output signal.
 * This should be used for signals transformed using FastCosine (Type 1).
 * This can be considered a wrapper on top of the Apache Math3 FastCosine [INVERSE] which pre-processes the signal before
 * the operation.
 *  
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class InverseFastCosine implements _InverseSineCosine {

    private double[] signal;
    private double[] output;
    private FastCosineTransformer fct;

    private void extendSignal() {
        double power = Math.log(this.signal.length - 1)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power)) + 1;
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length-this.signal.length);
        }
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastCosine.
     *
     * @param signal The input signal to be transformed.
     */
    public InverseFastCosine(double[] signal) {
        this.signal = signal;
        this.extendSignal();
        this.fct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastCosine.
     *
     * @param signal The input signal to be transformed.
     * @param norm   The normalization option (STANDARD or ORTHOGONAL).
     */
    public InverseFastCosine(double[] signal, Normalization norm) {
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
            throw new IllegalArgumentException("InverseFastCosine only has type 1 implementation");
        }
        else {
            this.output = this.fct.transform(this.signal, TransformType.INVERSE);
        }
    }

    /**
     * Performs the inverse cosine transformation on the input signal.
     */
    public void transform() {
        this.output = this.fct.transform(this.signal, TransformType.INVERSE);
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
     * Gets the length of the input signal after preprocessing for InverseFastSine (padded to nearest power of 2).
     *
     * @return int The updated length of the input signal.
     */
    public int getSignalLength() {
        return this.signal.length;
    }
}
