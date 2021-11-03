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

import com.github.psambit9791.jdsp.signal.Deconvolution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestDeconvolution {

    private double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    private double[] signal2 = {2.0, 8.0, 0.0, 4.0, 1.0, 9.0, 9.0, 0.0};

    @Test
    public void fullDeconvolutionTest1() {
        double[] output = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        double[] kernel = {1.0, 0.0, 1.0, 0.5};

        Deconvolution d1 = new Deconvolution(output, kernel);
        System.out.println(Arrays.toString(d1.deconvolve("full")));
        Assertions.assertArrayEquals(d1.deconvolve("full"), this.signal1, 0.001);
    }

    @Test
    public void fullDeconvolutionTest2() {
        double[] output = {2.0, 14.0, 26.0, 18.0, 37.0, 16.0, 49.0, 39.0, 36.0, 27.0, 0.0};;
        double[] kernel = {1.0, 3.0, 1.0, 3.0};

        Deconvolution d2 = new Deconvolution(output, kernel);
        System.out.println(Arrays.toString(d2.deconvolve("full")));
//        Assertions.assertArrayEquals(d2.deconvolve("full"), this.signal2, 0.001);
    }
}
