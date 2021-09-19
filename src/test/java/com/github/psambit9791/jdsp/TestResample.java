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

import com.github.psambit9791.jdsp.signal.Resample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestResample {

    private double[] input_signal_1 = {1.   ,  0.999,  0.986,  0.931,  0.786,  0.505,  0.071, -0.454, -0.889, -0.973, -0.519,
            0.323,  0.96 ,  0.726, -0.308, -0.999, -0.323,  0.865,  0.595, -0.786};

    private double[] input_signal_2 = {1.   ,  1.129,  1.388,  1.697,  1.957,  2.042,  1.843,  1.335, 0.648,  0.039, -0.288,
            -0.434, -0.764, -1.338, -1.407, -0.246, 1.092,  0.56 , -0.862};

    @Test
    public void ResampleTest1() {
        double[] result = {1.   ,  1.233,  1.317,  1.273,  1.147,  0.999,  0.881,  0.825,
                0.838,  0.902,  0.986,  1.055,  1.084,  1.065,  1.007,  0.931,
                0.858,  0.806,  0.783,  0.781,  0.786,  0.78 ,  0.75 ,  0.691,
                0.606,  0.505,  0.401,  0.305,  0.221,  0.145,  0.071, -0.011,
                -0.105, -0.214, -0.332, -0.454, -0.569, -0.672, -0.76 , -0.831,
                -0.889, -0.935, -0.97 , -0.99 , -0.992, -0.973, -0.928, -0.859,
                -0.765, -0.651, -0.519, -0.372, -0.212, -0.041,  0.14 ,  0.323,
                0.502,  0.664,  0.799,  0.9  ,  0.96 ,  0.981,  0.964,  0.915,
                0.836,  0.726,  0.583,  0.404,  0.189, -0.053, -0.308, -0.553,
                -0.762, -0.914, -0.994, -0.999, -0.936, -0.823, -0.675, -0.507,
                -0.323, -0.12 ,  0.106,  0.356,  0.618,  0.865,  1.059,  1.153,
                1.112,  0.92 ,  0.595,  0.185, -0.235, -0.582, -0.781, -0.786,
                -0.594, -0.244,  0.195,  0.636};
        Resample r1 = new Resample(100);
        double[] out = r1.resampleSignal(this.input_signal_1);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void ResampleTest2() {
        double[] result = {1.   ,  1.275,  1.359,  1.296,  1.168,  1.063,  1.042,  1.122,
                1.276,  1.451,  1.596,  1.68 ,  1.704,  1.697,  1.698,  1.736,
                1.818,  1.928,  2.032,  2.099,  2.113,  2.077,  2.013,  1.948,
                1.899,  1.868,  1.839,  1.789,  1.696,  1.555,  1.376,  1.182,
                0.998,  0.841,  0.71 ,  0.593,  0.47 ,  0.331,  0.174,  0.014,
                -0.124, -0.223, -0.275, -0.288, -0.286, -0.294, -0.329, -0.395,
                -0.481, -0.57 , -0.648, -0.713, -0.779, -0.865, -0.986, -1.145,
                -1.32 , -1.476, -1.572, -1.575, -1.476, -1.288, -1.037, -0.756,
                -0.464, -0.166,  0.143,  0.467,  0.793,  1.079,  1.266,  1.289,
                1.109,  0.736,  0.232, -0.289, -0.699, -0.889, -0.807, -0.474,
                0.022,  0.555};
        Resample r1 = new Resample(82);
        double[] out = r1.resampleSignal(this.input_signal_2);
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
