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

import com.github.psambit9791.jdsp.filter.Median;
import com.github.psambit9791.jdsp.filter._KernelFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMedian {

    @Test
    public void medianTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wSize = 5;
        double[] result = {2.0, 2.0, 3.0, 4.0, 2.0, 4.0, 5.0, 4.0, 0.0};

        Median mf = new Median(wSize);
        double[] out = mf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void medianTest2() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {3.0, 3.0, 2.0, 2.0, 5.0, 5.0, 4.0, 4.0, 4.0};

        Median mf = new Median();
        double[] out = mf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void medianInterfaceTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {3.0, 3.0, 2.0, 2.0, 5.0, 5.0, 4.0, 4.0, 4.0};

        _KernelFilter mf = new Median();
        double[] out = mf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
