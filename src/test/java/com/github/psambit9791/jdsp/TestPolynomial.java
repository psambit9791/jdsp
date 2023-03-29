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

import com.github.psambit9791.jdsp.misc.Polynomial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPolynomial {

    @Test
    public void polyfitTest1() {
        double[] x = {0.0, 1.0, 2.0, 3.0,  4.0,  5.0};
        double[] y = {0.0, 0.8, 0.9, 0.1, -0.8, -1.0};
        double[] out = Polynomial.polyfit(x, y, 3);
        double[] result = {0.08703704, -0.81349206,  1.69312169, -0.03968254};
        Assertions.assertArrayEquals(result, out, 0.00001);
    }

    @Test
    public void polyfitTest2() {
        double[] x = {0.0, 1.0, 2.0, 3.0,  4.0,  5.0};
        double[] y = {0.0, 100.4, 209.11, 320.1, 499.8, -700.0};
        double[] out = Polynomial.polyfit(x, y, 2);
        double[] result = {-111.01857143,  492.49828571, -142.00714286};
        Assertions.assertArrayEquals(result, out, 0.00001);
    }

    @Test
    public void polyvalTest1() {
        double[] coeffs = {3.0, 0.0, 1.0};
        double[] x = {5.0, 3.2, 1.2};
        double[] out = Polynomial.polyval(coeffs, x);
        double[] result = {76.0  , 31.72,  5.32};
        Assertions.assertArrayEquals(result, out, 0.00001);
    }

    @Test
    public void polyvalTest2() {
        double[] coeffs = {3.0, 0.0, 1.0};
        double x = 5.0;
        double out = Polynomial.polyval(coeffs, x);
        double result = 76;
        Assertions.assertEquals(result, out, 0.00001);
    }

    @Test
    public void polyderTest1() {
        double[] coeffs = {6, 3, 1, 1};

        int m1 = 1;
        double[] out1 = Polynomial.polyder(coeffs, m1);
        double[] result1 = {18, 6, 1};
        Assertions.assertArrayEquals(result1, out1, 0.00001);

        int m2 = 2;
        double[] out2 = Polynomial.polyder(coeffs, m2);
        double[] result2 = {36, 6};
        Assertions.assertArrayEquals(result2, out2, 0.00001);

        int m3 = 3;
        double[] out3 = Polynomial.polyder(coeffs, m3);
        double[] result3 = {36};
        Assertions.assertArrayEquals(result3, out3, 0.00001);
    }

    @Test
    public void polyderTest2() {
        double[] coeffs = {1.4, 3.9, 0.8, 1.1};

        int m1 = 1;
        double[] out1 = Polynomial.polyder(coeffs, m1);
        double[] result1 = {4.2, 7.8, 0.8};
        Assertions.assertArrayEquals(result1, out1, 0.00001);

        int m2 = 2;
        double[] out2 = Polynomial.polyder(coeffs, m2);
        double[] result2 = {8.4, 7.8};
        Assertions.assertArrayEquals(result2, out2, 0.00001);

        int m3 = 3;
        double[] out3 = Polynomial.polyder(coeffs, m3);
        double[] result3 = {8.4};
        Assertions.assertArrayEquals(result3, out3, 0.00001);
    }
}
