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

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.Decimate;
import com.github.psambit9791.jdsp.signal.Generate;
import org.apache.commons.math3.util.MathArrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDecimate {

    private int Fs = 100;
    private double[] signal;
    private Generate gp = new Generate(0, 1, this.Fs);

    private double[] generateSignal() {
        double[] sin1 = this.gp.generateSineWave(10);
        double[] sin2 = this.gp.generateSineWave(35);
        return MathArrays.ebeAdd(sin1, sin2);
    }


    @Test
    public void DecimateWithoutZeroPhaseTest() {
        this.signal = this.generateSignal();
        Decimate d = new Decimate(this.signal, this.Fs, false);

        // TEST 1
        double[] result1 = {0.   ,  0.003,  0.111,  0.481, -0.015, -0.452,  0.616, -0.435, 0.016,  0.446, -0.758,  0.785,
                -0.509,  0.027,  0.486, -0.841, 0.904, -0.645,  0.151,  0.406, -0.828,  0.964, -0.761,  0.29 , 0.287};
        int factor = 4;

        double[] out = d.decimate(factor);

        Assertions.assertArrayEquals(result1, out, 0.05);


        // TEST 2
        double[] result2 = {0.   ,  0.002,  0.101,  0.374, -0.114, -0.088,  0.175, -0.182, 0.146, -0.097,  0.049,
                -0.014, -0.004,  0.009, -0.003, -0.008, 0.021, -0.032,  0.037, -0.038};
        factor = 5;

        out = d.decimate(factor);

        Assertions.assertArrayEquals(result2, out, 0.05);

        // TEST 3
        double[] result3 = {0.   ,  0.004,  0.128,  0.588,  0.335, -0.843,  0.077,  0.929, -0.776, -0.379,  1.017,
                -0.307, -0.786,  0.79 ,  0.292, -0.995, 0.363,  0.763, -0.872, -0.181,  0.98 , -0.454, -0.685,  0.901,
                0.1  , -0.97 ,  0.539,  0.616, -0.941,  0.   ,  0.939, -0.613, -0.539,  0.967};
        factor = 3;

        out = d.decimate(factor);

        Assertions.assertArrayEquals(result3, out, 0.1);
    }

    // For this use-case, testing is done on all samples except the extremes, because the first and last
    // points is inconsistent between Scipy and jDSP due to difference in implementation of filters
    @Test
    public void DecimateWithZeroPhaseTest() {
        this.signal = this.generateSignal();
        Decimate d = new Decimate(this.signal, this.Fs, true);

        // TEST 1
        double[] result1 = {0.43 ,  0.451, -0.863,  0.923, -0.639,  0.116,  0.455, -0.869, 0.979, -0.744,  0.247,  0.337,
                -0.802,  0.984, -0.82 ,  0.366, 0.217, -0.724,  0.977, -0.891,  0.502,  0.036, -0.508,  0.671, -0.05};
        int factor = 4;

        double[] out = d.decimate(factor);
        double[] outTemp = UtilMethods.splitByIndex(out, 1, out.length-1);
        double[] resultTemp = UtilMethods.splitByIndex(result1, 1, result1.length-1);

        Assertions.assertArrayEquals(resultTemp, outTemp, 0.05);


        // TEST 2
        double[] result2 = {0.59 , -0.186,  0.111, -0.086,  0.08 , -0.086,  0.097, -0.111, 0.125, -0.14 ,  0.155, -0.17,
                0.184, -0.196,  0.206, -0.209, 0.2  , -0.168,  0.078,  0.207};
        factor = 5;

        out = d.decimate(factor);
        outTemp = UtilMethods.splitByIndex(out, 1, out.length-1);
        resultTemp = UtilMethods.splitByIndex(result2, 1, result2.length-1);

        Assertions.assertArrayEquals(resultTemp, outTemp, 0.05);


        // TEST 2
        double[] result3 = {0.302,  0.888, -0.59 , -0.551,  0.97 , -0.099, -0.897,  0.681, 0.454, -0.979,  0.187,
                0.856, -0.747, -0.368,  0.988, -0.279, -0.806,  0.806,  0.279, -0.988,  0.367,  0.747, -0.856, -0.187,
                0.98 , -0.456, -0.677,  0.887,  0.12 , -1.012,  0.629,  0.428, -0.494, -0.016};
        factor = 3;

        out = d.decimate(factor);
        outTemp = UtilMethods.splitByIndex(out, 1, out.length-1);
        resultTemp = UtilMethods.splitByIndex(result3, 1, result3.length-1);

        Assertions.assertArrayEquals(resultTemp, outTemp, 0.05);
    }
}
