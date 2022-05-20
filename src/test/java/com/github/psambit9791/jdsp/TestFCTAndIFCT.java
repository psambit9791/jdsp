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
import com.github.psambit9791.jdsp.transform.FastCosine;
import com.github.psambit9791.jdsp.transform.InverseFastCosine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFCTAndIFCT {

    // 50Hz Sine + 80Hz Sine sampled @ 100Hz with Nyquist of 50Hz
    private double[] signal1 = {0.0,  0.684,  1.192,  1.401,  1.281,  0.894,  0.373, -0.133, -0.504, -0.688, -0.702,
            -0.615, -0.51, -0.44, -0.409, -0.372, -0.263, -0.033,  0.319,  0.726,  1.074,  1.236,  1.121,  0.714,
            0.093, -0.593, -1.163, -1.464, -1.42 , -1.053, -0.476,  0.147, 0.658,  0.948,  0.991,  0.839,  0.588,
            0.338,  0.154,  0.041, -0.041, -0.154, -0.338, -0.588, -0.839, -0.991, -0.948, -0.658, -0.147,  0.476,
            1.053,  1.42 ,  1.464,  1.163,  0.593, -0.093, -0.714, -1.121, -1.236, -1.074, -0.726, -0.319,  0.033,
            0.263, 0.372,  0.409,  0.44 ,  0.51 ,  0.615,  0.702,  0.688,  0.504, 0.133, -0.373, -0.894, -1.281, -1.401,
            -1.192, -0.684,  0.};

    // 20Hz Sine + 200Hz Sine sampled @ 100Hz with Nyquist of 50Hz
    private double[] signal2 = {0.   ,  0.658,  0.293, -0.04 ,  0.634,  1.212,  0.756,  0.402, 1.035,  1.482,  0.901,
            0.496,  1.062,  1.362,  0.655,  0.208, 0.718,  0.895,  0.1  , -0.346,  0.154,  0.26 , -0.562, -0.943,
            -0.396, -0.302, -1.085, -1.344, -0.703, -0.576, -1.279, -1.384, -0.632, -0.463, -1.08 , -1.04 , -0.2  ,
            -0.017, -0.579, -0.437, 0.437,  0.579,  0.017,  0.2  ,  1.04 ,  1.08 ,  0.463,  0.632, 1.384,  1.279,
            0.576,  0.703,  1.344,  1.085,  0.302,  0.396, 0.943, 0.562, -0.26 , -0.154,  0.346, -0.1  , -0.895, -0.718,
            -0.208, -0.655, -1.362, -1.062, -0.496, -0.901, -1.482, -1.035, -0.402, -0.756, -1.212, -0.634,  0.04 ,
            -0.293, -0.658,  0.};

    private double[] extendSignal(double[] signal) {
        double power = Math.log(signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power)) + 1;
        if (new_length != signal.length) {
            signal = UtilMethods.zeroPadSignal(signal, new_length-signal.length);
        }
        return signal;
    }

    @Test
    public void testFCTWithIFCT1() {
        FastCosine f1 = new FastCosine(this.signal1);
        f1.transform();
        double[] out = f1.getMagnitude();

        InverseFastCosine f2 = new InverseFastCosine(out);
        f2.transform();
        double[] reconstructed = f2.getMagnitude();

        Assertions.assertArrayEquals(this.extendSignal(this.signal1), reconstructed, 0.001);

    }

    @Test
    public void testFCTWithIFCT2() {
        FastCosine f1 = new FastCosine(this.signal2);
        f1.transform();
        double[] out = f1.getMagnitude();

        InverseFastCosine f2 = new InverseFastCosine(out);
        f2.transform();
        double[] reconstructed = f2.getMagnitude();

        Assertions.assertArrayEquals(this.extendSignal(this.signal2), reconstructed, 0.001);
    }

}
