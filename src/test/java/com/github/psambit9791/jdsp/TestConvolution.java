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

import com.github.psambit9791.jdsp.signal.Convolution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConvolution {

    final double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel1 = {1.0, 0.0, 1.0, 0.5};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};

    final double[] signal3 = {1.01, 2.33, 4.56, 7.89, 11.09, 12.43, 13.96};
    final double[] kernel3 = {1, 2, 3, 4, 5, 2, 1};

    @Test
    public void fullConvolutionTest() {
        final double[] result1 = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        final double[] result2 = {2, 14, 26, 18, 37, 16, 49, 39, 36, 27,  0};
        final double[] result3 = {1.01,   4.35,  12.25,  28.04,  54.92,  90.19, 132.12, 160.47, 167.39, 148.06, 105.75,  40.35,  13.96};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("full");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve();
        Assertions.assertArrayEquals(result2, out, 0.001);

        Convolution con3 = new Convolution(this.signal3, this.kernel3);
        out = con3.convolve();
        Assertions.assertArrayEquals(result3, out, 0.001);
    }

    @Test
    public void sameConvolutionTest() {
        final double[] result1 = {2.0, 4.0, 6.5, 9.0, 5.5};
        final double[] result2 = {14, 26, 18, 37, 16, 49, 39, 36};
        final double[] result3 = {28.04,  54.92,  90.19, 132.12, 160.47, 167.39, 148.06};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("same");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve("same");
        Assertions.assertArrayEquals(result2, out, 0.001);

        Convolution con3 = new Convolution(this.signal3, this.kernel3);
        out = con3.convolve("same");
        Assertions.assertArrayEquals(result3, out, 0.001);
    }

    @Test
    public void validConvolutionTest() {
        final double[] result1 = {6.5, 9.0};
        final double[] result2 = {18, 37, 16, 49, 39};
        final double[] result3 = {132.12};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("valid");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve("valid");
        Assertions.assertArrayEquals(result2, out, 0.001);

        Convolution con3 = new Convolution(this.signal3, this.kernel3);
        out = con3.convolve("valid");
        Assertions.assertArrayEquals(result3, out, 0.001);
    }

    @Test
    public void convolve1DReflectTest() {
        final double[] result1 = {4.5,  6.5,  9. , 10.5, 11};
        final double[] result2 = {32, 18, 37, 16, 49, 39, 36, 36};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("reflect");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d();
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DConstantTest() {
        final double[] result1 = {4. , 6.5, 9. , 5.5, 7.};
        final double[] result2 = {26, 18, 37, 16, 49, 39, 36, 27};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("constant");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("constant");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DNearestTest() {
        final double[] result1 = {4.5,  6.5,  9. , 10.5, 12.};
        final double[] result2 = {32, 18, 37, 16, 49, 39, 36, 27};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("nearest");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("nearest");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DMirrorTest() {
        final double[] result1 = {5. ,  6.5,  9. ,  9.5, 10.};
        final double[] result2 = {50, 18, 37, 16, 49, 39, 45, 63};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("mirror");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("mirror");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DWrapTest() {
        final double[] result1 = {6.5, 6.5, 9. , 6.5, 9.};
        final double[] result2 = {26, 18, 37, 16, 49, 39, 38, 41};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("wrap");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("wrap");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }
}
