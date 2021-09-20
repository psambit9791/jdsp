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
import com.github.psambit9791.jdsp.signal.Resample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class TestResamplePoly {

    private double[] input_signal_1 = {1.   ,  0.999,  0.986,  0.931,  0.786,  0.505,  0.071, -0.454, -0.889, -0.973, -0.519,
            0.323,  0.96 ,  0.726, -0.308, -0.999, -0.323,  0.865,  0.595, -0.786};

    private double[] input_signal_2 = {1.   ,  1.129,  1.388,  1.697,  1.957,  2.042,  1.843,  1.335, 0.648,  0.039, -0.288,
            -0.434, -0.764, -1.338, -1.407, -0.246, 1.092,  0.56 , -0.862};


    @Test
    public void MeanTest5() {
        double[] result_1 = {1.0005,  1.0978,  1.1103,  1.0642,  0.9995,  0.9517,  0.9387,
                0.9566,  0.9865,  1.0056,  1.0011,  0.973 ,  0.9315,  0.8883,
                0.8515,  0.82  ,  0.7864,  0.7419,  0.6799,  0.5997,  0.5052,
                0.4035,  0.2975,  0.1873,  0.0709, -0.0529, -0.1841, -0.3197,
                -0.4544, -0.5837, -0.702 , -0.8052, -0.8897, -0.9526, -0.99  ,
                -0.9983, -0.9737, -0.9127, -0.8151, -0.6828, -0.5194, -0.3305,
                -0.1213,  0.1001,  0.3231,  0.5344,  0.7206,  0.8667,  0.9605,
                0.9937,  0.9648,  0.8747,  0.7264,  0.5233,  0.2734, -0.011 ,
                -0.3083, -0.5884, -0.8153, -0.9583, -0.9997, -0.9397, -0.7927,
                -0.5807, -0.3233, -0.0336,  0.2771,  0.5893,  0.8654,  1.0503,
                1.0841,  0.9296,  0.5953,  0.1471, -0.3072, -0.6477, -0.7866,
                -0.7008, -0.4424, -0.1132};

        Resample rmean5_1 = new Resample(80, 20, "mean");
        double[] out_1 = rmean5_1.resampleSignal(this.input_signal_1);
        System.out.println(Arrays.toString(UtilMethods.round(out_1, 4)));
        System.out.println(Arrays.toString(result_1));
        Assertions.assertArrayEquals(result_1, out_1, 0.001);

//        double[] result_2 = {};
    }
}
