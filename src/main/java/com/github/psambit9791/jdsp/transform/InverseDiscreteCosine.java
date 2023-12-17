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

/**
 * <h1>Inverse Discrete Cosine Transform</h1>
 * The InverseDiscreteCosine class applies the inverse cosine transform on the input sequence and returns the output signal.
 * This should be used for signals transformed using DiscreteCosine with the specific transform type.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class InverseDiscreteCosine implements _InverseSineCosine {

    private double[] signal;
    private double[] output = null;
    private Normalization norm;

    private int inferType(int t) {
        int inf = 0;
        if (t == 1 || t == 4) {
            inf = t;
        }
        else if (t == 2) {
            inf = 3;
        }
        else if (t == 3) {
            inf = 2;
        }
        return inf;
    }

    /**
     * This constructor initialises the prerequisites required to use InverseDiscreteCosine
     * @param signal The signal to be transformed
     * @param norm The normalization option (STANDARD or ORTHOGONAL).
     */
    public InverseDiscreteCosine(double[] signal, Normalization norm) {
        this.signal = signal;
        this.norm = norm;
    }

    /**
     * This constructor initialises the prerequisites required to use DiscreteCosine, normalization is set to STANDARD
     * @param signal The signal to be transformed
     */
    public InverseDiscreteCosine(double[] signal) {
        this.signal = signal;
        this.norm = Normalization.STANDARD;
    }

    /**
     * This function performs the inverse discrete cosine transform on the input signal
     * @param type Type of transform that was applied during the forward transform
     * @throws java.lang.IllegalArgumentException If type is not between 1 and 4
     */
    public void transform(int type) throws IllegalArgumentException{
        if ((type <= 0) || (type > 4)) {
            throw new IllegalArgumentException("Type must be between 1 and 4");
        }
        type = this.inferType(type);
        DiscreteCosine dct;
        if (this.norm == Normalization.STANDARD) {
            dct = new DiscreteCosine(this.signal, _SineCosine.Normalization.STANDARD);
        }
        else {
            dct = new DiscreteCosine(this.signal, _SineCosine.Normalization.ORTHOGONAL);
        }
        dct.transform(type);
        this.output = dct.getOutput();
    }

    /**
     * This function performs the inverse discrete cosine transform on the input signal. Original transform type is set to 2.
     * @throws java.lang.IllegalArgumentException If type is not between 1 and 4
     */
    public void transform() {
        int type = this.inferType(2);
        DiscreteCosine dct;
        if (this.norm == Normalization.STANDARD) {
            dct = new DiscreteCosine(this.signal, _SineCosine.Normalization.STANDARD);
        }
        else {
            dct = new DiscreteCosine(this.signal, _SineCosine.Normalization.ORTHOGONAL);
        }
        dct.transform(type);
        this.output = dct.getOutput();
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
     * Gets the length of the input signal.
     *
     * @return int The updated length of the input signal.
     */
    public int getSignalLength() {
        return this.signal.length;
    }
}
