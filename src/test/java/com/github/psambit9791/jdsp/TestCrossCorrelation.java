/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.CrossCorrelation;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.FastFourier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestCrossCorrelation {

    final double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel1 = {0.5, 1.0, 0.0, 1.0};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};

    public double[] signal = new double[0];

    private void buildSignal(int length) throws IOException {
        double[] temp = UtilMethods.electrocardiogram();
        int counts = length/temp.length;
        int rems = length%temp.length;
        for (int i=0; i<counts; i++) {
            this.signal = UtilMethods.concatenateArray(this.signal, temp);
        }
        double[] extra = UtilMethods.splitByIndex(temp, 0, rems);
        this.signal = UtilMethods.concatenateArray(this.signal, extra);
    }

    private void extendSignal() {
        double power = Math.log(this.signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power));
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length - this.signal.length);
        }
    }

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
    public void crossCorrelationTimeTest() throws IOException {
        this.buildSignal(10000);
        this.extendSignal();
        CrossCorrelation xcorr = new CrossCorrelation(this.signal);
        CrossCorrelation xcorrFast = new CrossCorrelation(this.signal);

        long start = System.nanoTime();
        double[] correlationResult = xcorr.crossCorrelate();
        long stop = System.nanoTime();
        long xcorr_dur = stop - start;

        start = System.nanoTime();
        double[] fastCorrelationResult = xcorrFast.fastCrossCorrelate();
        stop = System.nanoTime();
        long fast_xcorr_dur = stop - start;

        Assertions.assertTrue(fast_xcorr_dur < xcorr_dur);
        Assertions.assertArrayEquals(correlationResult, fastCorrelationResult, 0.001);
    }

    @Test
    public void fastAutocorrelationTest() {
        final double[] result1 = {5.0, 14.0, 26.0, 40.0, 55.0, 40.0, 26.0, 14.0, 5.0};
        final double[] result2 = {0, 18, 90, 74, 52, 77, 110, 247, 110, 77, 52, 74, 90, 18, 0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1);
        double[] out = cc1.fastCrossCorrelate();
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2);
        out = cc2.fastCrossCorrelate();
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
    public void fullFastCrossCorrelationTest() {
        final double[] result1 = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        final double[] result2 = {6, 26, 14, 38, 15, 40, 43, 37, 36,  9,  0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.fastCrossCorrelate("full");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.fastCrossCorrelate("full");
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
    public void sameFastCrossCorrelationTest() {
        final double[] result1 = {2.0, 4.0, 6.5, 9.0, 5.5};
        final double[] result2 = {26, 14, 38, 15, 40, 43, 37, 36};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.fastCrossCorrelate("same");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.fastCrossCorrelate("same");
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

    @Test
    public void validFastCrossCorrelationTest() {
        final double[] result1 = {6.5, 9.0};
        final double[] result2 = {38, 15, 40, 43, 37};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.fastCrossCorrelate("valid");
        Assertions.assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.fastCrossCorrelate("valid");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }
}
