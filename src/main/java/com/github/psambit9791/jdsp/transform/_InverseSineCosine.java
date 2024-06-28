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
 * <h2>Interface for Inverse Sine and Cosine Transform</h2>
 *
 * The _InverseSineCosine interface is implemented by all Inverse Sine and Cosine Transforms.
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public interface _InverseSineCosine {

    /**
     * Dictates the normalization mode used in the transform.
     *      STANDARD: No normalization is applied
     *      ORTHOGONAL: The forward and inverse transforms are scaled by the same overall factor
     */
    enum Normalization {
        STANDARD,
        ORTHOGONAL
    }

    /**
     * This method implements the transform corresponding to Type 1 forward transform
     */
    void transform();

    /**
     * This method implements the transform corresponding to forward transform of the provided type
     * @param type The type of transform to be applied
     */
    void transform(int type);

    /**
     * Returns the output of the transformation.
     *
     * @return double[] The transformed signal.
     */
    double[] getOutput();

    /**
     * Returns the length of the input signal.
     *
     * @return int The updated length of the input signal.
     */
    int getSignalLength();
}
