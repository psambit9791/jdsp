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

import com.github.psambit9791.jdsp.signal.Detrend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestDetrend {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] polySignal = {1.0, 4.0, 9.0, 16.0, 25.0};

    @Test
    public void linearDetrendTest() {
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(this.signal, "linear");
        double[] out = d1.detrendSignal();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void constantDetrendTest() {
        final double[] result = {-2.0, -1.0, 0.0, 1.0, 2.0};

        Detrend d1 = new Detrend(this.signal, "constant");
        double[] out = d1.detrendSignal();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void polyDetrendTest() {
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(this.polySignal, 2);
        double[] out = d1.detrendSignal();
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
