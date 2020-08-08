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
        double[][] result = {{-0.1531, -0.163 }, { 0.3476,  0.023 }, {-0.3033,  0.2527}, { 0.2745,  0.0569}, {-0.1657, -0.1695}};

        PCA p1 = new PCA(this.signal, 2);
        p1.fit();

        Assertions.assertArrayEquals(explained_variance, p1.explained_variance_, 0.0001);
        Assertions.assertArrayEquals(explained_variance_ratio, p1.explained_variance_ratio_, 0.0001);
        Assertions.assertArrayEquals(singular_values, p1.singular_values_, 0.0001);

        double[][][] usv = p1.getUSV();

        final double[][] U = {{-0.2629, -0.465 }, { 0.597 ,  0.0655}, {-0.5209,  0.7207}, { 0.4714,  0.1623}, {-0.2845, -0.4835}};
        final double[][] S = {{0.5823, 0}, {0, 0.3506}};
        final double[][] V = {{ 0.9522,  0.3053}, {-0.3053,  0.9522}};

        for (int i=0; i<U.length; i++) {
            Assertions.assertArrayEquals(U[i], usv[0][i], 0.001);
        }
        for (int i=0; i<S.length; i++) {
            Assertions.assertArrayEquals(S[i], usv[1][i], 0.001);
        }
        for (int i=0; i<V.length; i++) {
            Assertions.assertArrayEquals(V[i], usv[2][i], 0.001);
        }

        double[][] newSignal = p1.transform();
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], newSignal[i], 0.001);
        }
    }

    @Test
    public void PCATest2() {
        final double[] explained_variance = {0.0848};
        final double[] explained_variance_ratio = {0.7339};
        final double[] singular_values = {0.5823};
        double[][] result1 = {{-0.1531}, { 0.3476}, {-0.3033}, { 0.2745}, {-0.1657}};
        double[][] result2 = {{-0.4617}, {-0.4375}, { 0.0507}, { 0.2745}, {-0.1315}};

        PCA p1 = new PCA(this.signal, 1);
        p1.fit();

        Assertions.assertArrayEquals(explained_variance, p1.explained_variance_, 0.0001);
        Assertions.assertArrayEquals(explained_variance_ratio, p1.explained_variance_ratio_, 0.0001);
        Assertions.assertArrayEquals(singular_values, p1.singular_values_, 0.0001);

        double[][] newSignal = p1.transform();
        for (int i=0; i<result1.length; i++) {
            Assertions.assertArrayEquals(result1[i], newSignal[i], 0.001);
        }

        double[][] signal2 = {{0.12, 0.33}, {0.20, 0.16}, {0.61, 0.48}, {0.72, 0.87}, {0.47, 0.32}};
        double[][] newSignal2 = p1.transform(signal2);
        for (int i=0; i<result2.length; i++) {
            Assertions.assertArrayEquals(result2[i], newSignal2[i], 0.001);
        }
    }
}
