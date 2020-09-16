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

import com.github.psambit9791.jdsp.signal.Smooth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSmooth {
    private double[] signal = {0.425, 0.445, 0.445, 0.405, 0.4, 0.43, 0.44, 0.43, 0.435, 0.455, 0.465, 0.455, 0.44,
            0.435, 0.43, 0.405, 0.375, 0.345, 0.32, 0.33, 0.335, 0.325, 0.29, 0.265, 0.22, 0.2, 0.175, 0.115, 0.005,
            -0.07, -0.01, 0.11, 0.18, 0.23, 0.24, 0.245, 0.245, 0.245, 0.235, 0.23, 0.21, 0.21, 0.235, 0.26, 0.265,
            0.255, 0.25, 0.26, 0.3, 0.31};

    @Test
    public void rectangularSmoothTest() {
        final double[] result1 = {0.427, 0.428, 0.426, 0.428, 0.436, 0.444, 0.446, 0.445, 0.445, 0.441, 0.429, 0.412,
                0.393, 0.377, 0.363, 0.348, 0.331, 0.316, 0.298, 0.281, 0.259, 0.227, 0.181, 0.13, 0.091, 0.075, 0.072,
                0.08, 0.098, 0.132, 0.177, 0.214, 0.231, 0.239, 0.236, 0.231, 0.23, 0.232, 0.235, 0.238, 0.241, 0.248,
                0.261, 0.271};
        final double[] kernel1 = {0.14285714285714288, 0.14285714285714288, 0.14285714285714288, 0.14285714285714288,
                0.14285714285714288, 0.14285714285714288, 0.14285714285714288};

        Smooth s1 = new Smooth(this.signal, 7, "rectangular");
        double[] out = s1.smoothSignal();
        Assertions.assertArrayEquals(result1, out, 0.001);
        Assertions.assertArrayEquals(kernel1, s1.getKernel(), 0.001);

        final double[] result2 = {0.424, 0.425, 0.424, 0.421, 0.427, 0.438, 0.445, 0.448, 0.45 , 0.45 , 0.445, 0.433,
                0.417, 0.398, 0.375, 0.355, 0.341, 0.331, 0.32 , 0.309, 0.287, 0.26 , 0.23 , 0.195, 0.143, 0.085, 0.043,
                0.03 , 0.043, 0.088, 0.15 , 0.201, 0.228, 0.241, 0.242, 0.24 , 0.233, 0.226, 0.224, 0.229, 0.236, 0.245,
                0.253, 0.258, 0.266, 0.275};
        final double[] kernel2 = {0.2, 0.2, 0.2, 0.2, 0.2};

        s1 = new Smooth(this.signal, 5, "rectangular");
        out = s1.smoothSignal();
        Assertions.assertArrayEquals(result2, out, 0.001);
        Assertions.assertArrayEquals(kernel2, s1.getKernel(), 0.001);

        final double[] result3 = {0.438,  0.432,  0.417,  0.412,  0.423,  0.433,  0.435,  0.44 , 0.452,  0.458,  0.453,
                0.443,  0.435,  0.423,  0.403,  0.375, 0.347,  0.332,  0.328,  0.33 ,  0.317,  0.293,  0.258,  0.228,
                0.198,  0.163,  0.098,  0.017, -0.025,  0.01 ,  0.093,  0.173, 0.217,  0.238,  0.243,  0.245,  0.242,
                0.237,  0.225,  0.217, 0.218,  0.235,  0.253,  0.26 ,  0.257,  0.255,  0.27 ,  0.29};
        final double[] kernel3 = {0.3333333333333333, 0.3333333333333333, 0.3333333333333333};

        s1 = new Smooth(this.signal, 3, "rectangular");
        out = s1.smoothSignal();
        Assertions.assertArrayEquals(result3, out, 0.001);
        Assertions.assertArrayEquals(kernel3, s1.getKernel(), 0.001);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {Smooth s0 = new Smooth(this.signal, this.signal.length+2, "rectangular");});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Smooth s0 = new Smooth(this.signal, 2, "rect");});
    }

    @Test
    public void triangularSmoothTest() {
        final double[] result1 = {0.423, 0.422, 0.424, 0.429, 0.436, 0.443, 0.448, 0.45, 0.449, 0.442, 0.432, 0.417,
                0.397, 0.376, 0.356, 0.341, 0.331, 0.321, 0.307, 0.286, 0.259, 0.228, 0.19, 0.143, 0.092, 0.05, 0.032,
                0.05, 0.095, 0.148, 0.195, 0.224, 0.237, 0.241, 0.239, 0.233, 0.228, 0.225, 0.228, 0.237, 0.246, 0.253,
                0.259, 0.265};
        final double[] kernel1 = {0.0625, 0.125, 0.1875, 0.25, 0.1875, 0.125, 0.0625};

        Smooth s1 = new Smooth(this.signal, 7, "triangular");
        double[] out = s1.smoothSignal();
        Assertions.assertArrayEquals(result1, out, 0.001);
        Assertions.assertArrayEquals(kernel1, s1.getKernel(), 0.001);


        final double[] result2 = {0.429, 0.42 , 0.417, 0.423, 0.431, 0.436, 0.442, 0.45 , 0.454, 0.452, 0.444, 0.434,
                0.421, 0.401, 0.375, 0.351, 0.336, 0.33 , 0.325, 0.313, 0.289, 0.26 , 0.228, 0.197, 0.153, 0.093, 0.03 ,
                0.001, 0.026, 0.092, 0.161, 0.209, 0.233, 0.242, 0.243, 0.241, 0.234, 0.226, 0.22 , 0.223, 0.236, 0.249,
                0.257, 0.257, 0.261, 0.272};
        final double[] kernel2 = {0.1111111111111111, 0.2222222222222222, 0.3333333333333333, 0.2222222222222222, 0.1111111111111111};

        s1 = new Smooth(this.signal, 5, "triangular");
        out = s1.smoothSignal();
        Assertions.assertArrayEquals(result2, out, 0.001);
        Assertions.assertArrayEquals(kernel2, s1.getKernel(), 0.001);


        final double[] result3 = {0.44 ,  0.435,  0.414,  0.409,  0.425,  0.435,  0.434,  0.439, 0.452,  0.46 ,  0.454,
                0.442,  0.435,  0.425,  0.404,  0.375, 0.346,  0.329,  0.329,  0.331,  0.319,  0.292,  0.26 ,  0.226,
                0.199,  0.166,  0.103,  0.014, -0.036,  0.005,  0.098,  0.175, 0.22 ,  0.239,  0.244,  0.245,  0.242,
                0.236,  0.226,  0.215, 0.216,  0.235,  0.255,  0.261,  0.256,  0.254,  0.268,  0.292};
        final double[] kernel3 = {0.25, 0.5, 0.25};

        s1 = new Smooth(this.signal, 3, "triangular");
        out = s1.smoothSignal();
        Assertions.assertArrayEquals(result3, out, 0.001);
        Assertions.assertArrayEquals(kernel3, s1.getKernel(), 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Smooth s0 = new Smooth(this.signal, 3, "triangular"); double[] temp = s0.smoothSignal("abc");});

    }
}
