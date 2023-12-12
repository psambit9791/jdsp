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
 * <h1>Inverse Fast Sine Transform</h1>
 * The InverseFastSine class applies the inverse fast sine transform on the input sequence and returns the output signal.
 * This should be used for signals transformed using FastSine (Type 1).
 * This can be considered a wrapper on top of the Apache Math3 FastSine [INVERSE] which pre-processes the signal before
 * the operation.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class InverseFastSine implements _SineCosine {

    private double[] signal;
    private double[] output;
    private FastSineTransformer fst;


    private void extendSignal() {
        double power = Math.log(this.signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power));
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length-this.signal.length);
        }
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastSine.
     *
     * @param signal The input signal to be transformed.
     */
    public InverseFastSine(double[] signal) {
        this.signal = signal;
        this.extendSignal();
        this.fst = new FastSineTransformer(DstNormalization.STANDARD_DST_I);
    }

    /**
     * This constructor initialises the prerequisites required to use InverseFastSine.
     *
     * @param signal The input signal to be transformed.
     * @param norm   The normalization option (STANDARD or ORTHOGONAL).
     */
    public InverseFastSine(double[] signal, FastSine.Normalization norm) {
        this.signal = signal;
        this.extendSignal();
        if (norm == FastSine.Normalization.ORTHOGONAL) {
            this.fst = new FastSineTransformer(DstNormalization.ORTHOGONAL_DST_I);
        }
        else {
            this.fst = new FastSineTransformer(DstNormalization.STANDARD_DST_I);
        }
    }

    /**
     * Performs the inverse sine transformation on the input signal.
     */
    public void transform() {
        this.output = this.fst.transform(this.signal, TransformType.INVERSE);
    }

    /**
     * Returns the output of the transformation.
     *
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The transformed signal.
     */
    public double[] getMagnitude() throws ExceptionInInitializerError {
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
