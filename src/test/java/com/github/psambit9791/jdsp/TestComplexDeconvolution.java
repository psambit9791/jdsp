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
import com.github.psambit9791.jdsp.signal.ComplexDeconvolution;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestComplexDeconvolution {

    @Test
    public void complexDeconvolutionTest1() {
        double[] kernel = {1, 0, 1, 0.5};
        double[][] result = {{-1.6, 2.4}, {2.2, 2.2}, {3.9, -9.1}, {14.5, 1.54}, {5.7, -15.88}};

        double[][] complexOutFull = {{-1.6, 2.4}, {2.2, 2.2}, {2.3, -6.7}, {15.9, 4.94}, {10.7, -23.88}, {16.45, -3.01}, {12.95, -15.11}, {2.85, -7.94}};
        Complex[] output = UtilMethods.matToComplex(complexOutFull);
        ComplexDeconvolution cd1 = new ComplexDeconvolution(output, kernel);
        cd1.deconvolve("full");

        double[][] signal1 = UtilMethods.complexTo2D(cd1.getComplexOutput());
        for (int i=0; i<signal1.length; i++) {
            Assertions.assertArrayEquals(signal1[i], result[i], 0.001);
        }

        double[][] complexOutSame = {{2.2, 2.2}, {2.3, -6.7}, {15.9, 4.94}, {10.7, -23.88}, {16.45, -3.01}};
        output = UtilMethods.matToComplex(complexOutSame);
        ComplexDeconvolution cd2 = new ComplexDeconvolution(output, kernel);
        cd2.deconvolve("same");

        double[][] signal2 = UtilMethods.complexTo2D(cd2.getComplexOutput());
        for (int i=0; i<signal2.length; i++) {
            Assertions.assertArrayEquals(signal2[i], result[i], 0.001);
        }
    }

    @Test
    public void complexDeconvolutionTest2() {
        double[][] krn = {{0, -0.5}, {2.4, 4.1}, {5, -6}, {4.7, 7.1}};
        double[] result = {2, 8, 0, 4, 1, 9, 9, 0};

        double[][] complexOutFull = {{0.0, -1.0}, {4.8, 4.2}, {29.2, 20.8}, {49.4, -35.8}, {47.2, 72.7}, {22.4, -24.4}, {45.4, 54.8}, {71.3, -10.0}, {87.3, 9.9}, {42.3, 63.9}, {0.0, 0.0}};
        Complex[] output = UtilMethods.matToComplex(complexOutFull);
        Complex[] kernel = UtilMethods.matToComplex(krn);
        ComplexDeconvolution cd1 = new ComplexDeconvolution(output, kernel);
        cd1.deconvolve("full");
        double[] signal1 = cd1.getRealOutput();
        Assertions.assertArrayEquals(signal1, result, 0.001);

        double[][] complexOutSame = {{4.8, 4.2}, {29.2, 20.8}, {49.4, -35.8}, {47.2, 72.7}, {22.4, -24.4}, {45.4, 54.8}, {71.3, -10.0}, {87.3, 9.9}};
        output = UtilMethods.matToComplex(complexOutSame);
        ComplexDeconvolution cd2 = new ComplexDeconvolution(output, kernel);
        cd2.deconvolve("same");
        double[] signal2 = cd2.getRealOutput();
        Assertions.assertArrayEquals(signal2, result, 0.001);
    }
}
