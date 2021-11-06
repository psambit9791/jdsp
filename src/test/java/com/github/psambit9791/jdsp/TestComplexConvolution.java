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

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.ComplexConvolution;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestComplexConvolution {

    public double[][] complexSignal1 = {{1, 2}, {2, 2}, {3, -3}, {4, 1}, {5, -4}};
    public double[] doubleSignal1 = {1, 2, 3, 4, 5};
    public double[][] complexKernel1 = {{1, 2}, {2, 4}, {5, -2}, {4, 7}};
    public double[] doubleKernel1 = {1, 3, 1, 3};

    public double[][] complexSignal2 = {{-1.6, 2.4}, {2.2, 2.2}, {3.9, -9.1}, {14.5, 1.54}, {5.7, -15.88}};
    public double[] doubleSignal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    public double[][] complexKernel2 = {{0, -0.5}, {2.4, 4.1}, {5, -6}, {4.7, 7.1}};
    public double[] doubleKernel2 = {1, 0, 1, 0.5};

    @Test
    public void testComplexDouble1() {
        Complex[] sg = UtilMethods.matToComplex(this.complexSignal1);
        ComplexConvolution c1 = new ComplexConvolution(sg, this.doubleKernel1);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{5.0, 8.0}, {10.0, 5.0}, {18.0, 0.0}, {26.0, 2.0}, {28.0, -20.0}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{1.0, 2.0}, {5.0, 8.0}, {10.0, 5.0}, {18.0, 0.0}, {26.0, 2.0}, {28.0, -20.0}, {17.0, -1.0}, {15.0, -12.0}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{18.0, 0.0}, {26.0, 2.0}};

        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }

    @Test
    public void testDoubleComplex1() {
        Complex[] krn = UtilMethods.matToComplex(this.complexKernel1);
        ComplexConvolution c1 = new ComplexConvolution(this.doubleSignal1, krn);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{4.0, 8.0}, {12.0, 12.0}, {24.0, 23.0}, {36.0, 34.0}, {42.0, 33.0}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{1.0, 2.0}, {4.0, 8.0}, {12.0, 12.0}, {24.0, 23.0}, {36.0, 34.0}, {42.0, 33.0}, {41.0, 18.0}, {20.0, 35.0}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{24.0, 23.0}, {36.0, 34.0}};
        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }

    @Test
    public void testComplexComplex1() {
        Complex[] sg = UtilMethods.matToComplex(this.complexSignal1);
        Complex[] krn = UtilMethods.matToComplex(this.complexKernel1);
        ComplexConvolution c1 = new ComplexConvolution(sg, krn);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{-8.0, 14.0}, {14.0, 23.0}, {24.0, 36.0}, {20.0, 25.0}, {81.0, 18.0}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{-3.0, 4.0}, {-8.0, 14.0}, {14.0, 23.0}, {24.0, 36.0}, {20.0, 25.0}, {81.0, 18.0}, {26.0, 2.0}, {48.0, 19.0}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{24.0, 36.0}, {20.0, 25.0}};
        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }

    @Test
    public void testComplexDouble2() {
        Complex[] sg = UtilMethods.matToComplex(this.complexSignal2);
        ComplexConvolution c1 = new ComplexConvolution(sg, this.doubleKernel2);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{2.2, 2.2}, {2.3, -6.7}, {15.9, 4.94}, {10.7, -23.88}, {16.45, -3.01}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{-1.6, 2.4}, {2.2, 2.2}, {2.3, -6.7}, {15.9, 4.94}, {10.7, -23.88}, {16.45, -3.01}, {12.95, -15.11}, {2.85, -7.94}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{15.9, 4.94}, {10.7, -23.88}};

        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }

    @Test
    public void testDoubleComplex2() {
        Complex[] krn = UtilMethods.matToComplex(this.complexKernel2);
        ComplexConvolution c1 = new ComplexConvolution(this.doubleSignal2, krn);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{4.8, 4.2}, {29.2, 20.8}, {49.4, -35.8}, {47.2, 72.7}, {22.4, -24.4}, {45.4, 54.8}, {71.3, -10.0}, {87.3, 9.9}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{0.0, -1.0}, {4.8, 4.2}, {29.2, 20.8}, {49.4, -35.8}, {47.2, 72.7}, {22.4, -24.4}, {45.4, 54.8}, {71.3, -10.0}, {87.3, 9.9}, {42.3, 63.9}, {0.0, 0.0}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{49.4, -35.8}, {47.2, 72.7}, {22.4, -24.4}, {45.4, 54.8}, {71.3, -10.0}};
        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }

    @Test
    public void testComplexComplex2() {
        Complex[] sg = UtilMethods.matToComplex(this.complexSignal2);
        Complex[] krn = UtilMethods.matToComplex(this.complexKernel2);
        ComplexConvolution c1 = new ComplexConvolution(sg, krn);
        double[][] output_same = UtilMethods.complexTo2D(c1.convolve("same"));
        double[][] result_same = {{-12.58, -1.9}, {-1.89, 33.95}, {47.08, -15.38}, {-19.834, 17.356}, {243.468, -109.122}};
        double[][] output_full = UtilMethods.complexTo2D(c1.convolve("full"));
        double[][] result_full = {{1.2, 0.8}, {-12.58, -1.9}, {-1.89, 33.95}, {47.08, -15.38}, {-19.834, 17.356}, {243.468, -109.122}, {-9.564, -3.412}, {139.538, -34.166}};
        double[][] output_valid = UtilMethods.complexTo2D(c1.convolve("valid"));
        double[][] result_valid = {{47.08, -15.38}, {-19.834, 17.356}};
        for (int i=0; i<result_same.length; i++) {
            Assertions.assertArrayEquals(result_same[i], output_same[i], 0.001);
        }
        for (int i=0; i<result_full.length; i++) {
            Assertions.assertArrayEquals(result_full[i], output_full[i], 0.001);
        }
        for (int i=0; i<result_valid.length; i++) {
            Assertions.assertArrayEquals(result_valid[i], output_valid[i], 0.001);
        }
    }
}
