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

import com.github.psambit9791.jdsp.filter.Wiener;
import com.github.psambit9791.jdsp.filter._KernelFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWiener {

    @Test
    public void wienerTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wsize = 5;
        double[] result = {1.8, 2.0, 3.0, 3.6, 3.336, 3.883, 3.685, 3.367, 2.385};

        Wiener wf = new Wiener(wsize);
        double[] out = wf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void wienerTest2() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {2.333, 3.0, 2.333, 2.667, 4.164, 4.679, 2.09, 3.164, 3.328};

        Wiener wf = new Wiener();
        double[] out = wf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void wienerInterfaceTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {2.333, 3.0, 2.333, 2.667, 4.164, 4.679, 2.09, 3.164, 3.328};

        _KernelFilter wf = new Wiener();
        double[] out = wf.filter(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
