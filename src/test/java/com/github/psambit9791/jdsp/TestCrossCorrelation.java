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

import com.github.psambit9791.jdsp.signal.CrossCorrelation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestCrossCorrelation {

    final double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel1 = {0.5, 1.0, 0.0, 1.0};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};

    @Test
    public void autocorrelationTest() {
        final double[] result1 = {5.0, 14.0, 26.0, 40.0, 55.0, 40.0, 26.0, 14.0, 5.0};
        final double[] result2 = {0, 18, 90, 74, 52, 77, 110, 247, 110, 77, 52, 74, 90, 18, 0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1);
        double[] out = cc1.crossCorrelate();
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2);
        out = cc2.crossCorrelate();
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void fullCrossCorrelationTest() {
        final double[] result1 = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        final double[] result2 = {6, 26, 14, 38, 15, 40, 43, 37, 36,  9,  0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.crossCorrelate("full");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.crossCorrelate("full");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void sameCrossCorrelationTest() {
        final double[] result1 = {2.0, 4.0, 6.5, 9.0, 5.5};
        final double[] result2 = {26, 14, 38, 15, 40, 43, 37, 36};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.crossCorrelate("same");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.crossCorrelate("same");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void validCrossCorrelationTest() {
        final double[] result1 = {6.5, 9.0};
        final double[] result2 = {38, 15, 40, 43, 37};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.crossCorrelate("valid");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.crossCorrelate();
        Assertions.assertArrayEquals(result2, out, 0.001);
    }
}
