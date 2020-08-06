/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.transform.PCA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestPCA {

    final double[][] signal = {{0.38, 0.53}, {0.80, 0.86}, {0.11, 0.88}, {0.72, 0.87}, {0.37, 0.52}};

    @Test
    public void PCATest1() {
        final double[] explained_variance = {0.0848, 0.0307};
        final double[] explained_variance_ratio = {0.7339, 0.2661};
        final double[] singular_values = {0.5823, 0.3506};

        PCA p1 = new PCA(this.signal, 2);
        p1.fit();

        Assertions.assertArrayEquals(explained_variance, p1.explained_variance_, 0.0001);
        Assertions.assertArrayEquals(explained_variance_ratio, p1.explained_variance_ratio_, 0.0001);
        Assertions.assertArrayEquals(singular_values, p1.singular_values_, 0.0001);
    }
}
