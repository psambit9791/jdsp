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

import com.github.psambit9791.jdsp.transform.DiscreteCosine;
import com.github.psambit9791.jdsp.transform.InverseDiscreteCosine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInverseDiscreteCosine {

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

    @Test
    public void testInverseCosineType1Standard() {
        double[] result1 = {0.   ,  13.079,   0.   ,  14.069,   0.   ,  16.654,  -0.   ,
                23.362,  -0.   ,  57.295,   0.   , -42.178,   0.   ,  -5.571,
                0.   ,  17.662,  -0.   , -29.959,  -0.   , -11.76 ,   0.   ,
                -7.549,  -0.   ,  -5.538,   0.   ,  -4.349,  -0.   ,  -3.558,
                0.   ,  -2.987,   0.   ,  -2.574,  -0.   ,  -2.248,  -0.   ,
                -1.991,  -0.   ,  -1.785,   0.   ,  -1.61 ,  -0.   ,  -1.472,
                0.   ,  -1.355,   0.   ,  -1.258,   0.   ,  -1.186,  -0.   ,
                -1.106,   0.   ,  -1.049,  -0.   ,  -0.985,   0.   ,  -0.953,
                -0.   ,  -0.907,   0.   ,  -0.863,   0.   ,  -0.847,   0.   ,
                -0.817,   0.   ,  -0.803,  -0.   ,  -0.782,  -0.   ,  -0.765,
                -0.   ,  -0.746,   0.   ,  -0.742,  -0.   ,  -0.734,   0.   ,
                -0.732,   0.   ,  -0.72};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 1);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {-0.   ,  27.75 ,   0.   ,  58.406,   0.   , -43.758,   0.   ,
                -11.228,   0.   ,  -5.194,   0.   ,  -2.808,   0.   ,  -1.559,
                0.   ,  -0.793,   0.   ,  -0.287,   0.   ,   0.115,  -0.   ,
                0.44 ,   0.   ,   0.747,  -0.   ,   1.055,  -0.   ,   1.39 ,
                0.   ,   1.807,   0.   ,   2.369,  -0.   ,   3.192,  -0.   ,
                4.66 ,   0.   ,   8.039,  -0.   ,  24.815,   0.   , -25.464,
                0.   ,  -8.686,  -0.   ,  -5.335,   0.   ,  -3.903,   0.   ,
                -3.106,   0.   ,  -2.598,  -0.   ,  -2.241,   0.   ,  -1.992,
                0.   ,  -1.812,  -0.   ,  -1.658,   0.   ,  -1.537,  -0.   ,
                -1.448,   0.   ,  -1.375,   0.   ,  -1.313,  -0.   ,  -1.274,
                -0.   ,  -1.234,  -0.   ,  -1.218,   0.   ,  -1.192,   0.   ,
                -1.189,   0.   ,  -1.164};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 1);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType2Standard() {
        double[] result1 = {6.369,   7.   ,   6.172,   8.232,   6.469,  10.674,   7.7  ,
                16.793,  12.823,  60.324, -38.573, -20.565,  -3.089,  -2.186,
                1.373,  23.035, -18.069, -19.768,  -2.498,  -9.285,  -1.218,
                -6.335,  -0.701,  -4.842,  -0.428,  -3.927,  -0.265,  -3.3  ,
                -0.16 ,  -2.832,  -0.095,  -2.484,  -0.051,  -2.201,  -0.024,
                -1.97 ,  -0.009,  -1.778,  -0.001,  -1.61 ,  -0.   ,  -1.472,
                -0.003,  -1.351,  -0.01 ,  -1.247,  -0.019,  -1.164,  -0.032,
                -1.071,  -0.045,  -1.   ,  -0.062,  -0.921,  -0.075,  -0.871,
                -0.096,  -0.807,  -0.115,  -0.745,  -0.129,  -0.709,  -0.153,
                -0.658,  -0.172,  -0.621,  -0.198,  -0.575,  -0.221,  -0.535,
                -0.245,  -0.494,  -0.265,  -0.465,  -0.292,  -0.431,  -0.317,
                -0.401,  -0.346,  -0.366};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 2);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {12.971,  15.921,  18.737,  56.945, -43.861, -17.521,  -5.609,
                -6.057,  -2.151,  -3.128,  -1.015,  -1.818,  -0.5  ,  -1.061,
                -0.234,  -0.549,  -0.092,  -0.184,  -0.002,   0.135,   0.045,
                0.414,   0.073,   0.698,   0.084,   0.997,   0.085,   1.334,
                0.082,   1.76 ,   0.073,   2.338,   0.059,   3.183,   0.045,
                4.681,   0.029,   8.108,   0.011,  24.971,   0.388, -25.922,
                -0.032,  -8.769,  -0.051,  -5.343,  -0.073,  -3.868,  -0.096,
                -3.035,  -0.121,  -2.495,  -0.146,  -2.109,  -0.168,  -1.832,
                -0.193,  -1.622,  -0.223,  -1.438,  -0.25 ,  -1.288,  -0.276,
                -1.17 ,  -0.305,  -1.065,  -0.336,  -0.972,  -0.364,  -0.899,
                -0.401,  -0.824,  -0.433,  -0.767,  -0.476,  -0.702,  -0.512,
                -0.653,  -0.562,  -0.591};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 2);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType3Standard() {
        double[] result1 = {0.   ,  13.074,   0.   ,  14.014,   0.   ,  16.455,   0.   ,
                22.663,   0.   ,  51.36 ,   0.   , -49.141,   0.   ,  -6.717,
                0.   ,  12.746,   0.   , -34.909,   0.   , -11.744,   0.   ,
                -7.256,   0.   ,  -5.183,   0.   ,  -3.971,   0.   ,  -3.168,
                0.   ,  -2.591,   0.   ,  -2.172,   0.   ,  -1.84 ,   0.   ,
                -1.577,   0.   ,  -1.364,   0.   ,  -1.185,   0.   ,  -1.042,
                0.   ,  -0.92 ,   0.   ,  -0.817,   0.   ,  -0.733,   0.   ,
                -0.642,   0.   ,  -0.575,   0.   ,  -0.503,   0.   ,  -0.46 ,
                0.   ,  -0.398,   0.   ,  -0.346,   0.   ,  -0.318,   0.   ,
                -0.27 ,   0.   ,  -0.238,   0.   ,  -0.195,   0.   ,  -0.16 ,
                0.   ,  -0.125,   0.   ,  -0.101,   0.   ,  -0.069,   0.   ,
                -0.042,   0.   ,  -0.007};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 3);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {0.   ,  27.7  ,   0.   ,  56.529,   0.   , -46.845,   0.   ,
                -11.586,   0.   ,  -5.309,   0.   ,  -2.856,   0.   ,  -1.584,
                0.   ,  -0.815,   0.   ,  -0.318,   0.   ,   0.068,   0.   ,
                0.366,   0.   ,   0.635,   0.   ,   0.888,   0.   ,   1.147,
                0.   ,   1.454,   0.   ,   1.843,   0.   ,   2.378,   0.   ,
                3.287,   0.   ,   5.17 ,   0.   ,  11.945,   0.   , -35.972,
                0.   ,  -6.986,   0.   ,  -3.794,   0.   ,  -2.559,   0.   ,
                -1.893,   0.   ,  -1.472,   0.   ,  -1.179,   0.   ,  -0.976,
                0.   ,  -0.819,   0.   ,  -0.677,   0.   ,  -0.568,   0.   ,
                -0.483,   0.   ,  -0.405,   0.   ,  -0.335,   0.   ,  -0.281,
                0.   ,  -0.219,   0.   ,  -0.174,   0.   ,  -0.114,   0.   ,
                -0.072,   0.   ,  -0.011};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 3);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType4Standard() {
        double[] result1 = {6.432,   6.802,   6.499,   7.72 ,   7.125,   9.687,   8.906,
                14.784,  15.638,  51.643, -49.828, -17.159,  -4.248,  -1.782,
                2.022,  18.373, -28.697, -15.463,  -4.311,  -7.136,  -2.305,
                -4.792,  -1.471,  -3.612,  -1.011,  -2.894,  -0.714,  -2.406,
                -0.506,  -2.046,  -0.362,  -1.781,  -0.246,  -1.569,  -0.158,
                -1.398,  -0.088,  -1.259,  -0.03 ,  -1.138,   0.012,  -1.041,
                0.049,  -0.958,   0.079,  -0.887,   0.104,  -0.832,   0.137,
                -0.771,   0.156,  -0.726,   0.18 ,  -0.675,   0.188,  -0.646,
                0.213,  -0.606,   0.228,  -0.568,   0.233,  -0.549,   0.253,
                -0.52 ,   0.263,  -0.5  ,   0.282,  -0.474,   0.294,  -0.452,
                0.307,  -0.429,   0.314,  -0.415,   0.329,  -0.397,   0.341,
                -0.383,   0.357,  -0.362};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 4);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {13.1  ,  15.472,  19.729,  53.404, -48.313, -15.9  ,  -6.487,
                -5.332,  -2.623,  -2.678,  -1.311,  -1.517,  -0.688,  -0.865,
                -0.345,  -0.438,  -0.147,  -0.144,  -0.004,   0.104,   0.084,
                0.314,   0.153,   0.521,   0.199,   0.735,   0.23 ,   0.973,
                0.259,   1.271,   0.278,   1.676,   0.283,   2.269,   0.296,
                3.323,   0.294,   5.74 ,   0.256,  17.658, -27.927, -18.338,
                0.456,  -6.215,   0.412,  -3.8  ,   0.405,  -2.765,   0.41 ,
                -2.184,   0.417,  -1.81 ,   0.424,  -1.545,   0.423,  -1.358,
                0.428,  -1.218,   0.445,  -1.096,   0.451,  -0.999,   0.456,
                -0.924,   0.466,  -0.859,   0.477,  -0.801,   0.485,  -0.76 ,
                0.503,  -0.715,   0.513,  -0.685,   0.538,  -0.647,   0.551,
                -0.624,   0.58 ,  -0.585};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 4);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType1Ortho() {
        double[] result1 = {0.   ,  1.041,  0.   ,  1.119,  0.   ,  1.325, -0.   ,  1.859,
                -0.   ,  4.558,  0.   , -3.356,  0.   , -0.443,  0.   ,  1.405,
                -0.   , -2.383, -0.   , -0.936,  0.   , -0.601, -0.   , -0.441,
                0.   , -0.346, -0.   , -0.283,  0.   , -0.238,  0.   , -0.205,
                -0.   , -0.179, -0.   , -0.158, -0.   , -0.142,  0.   , -0.128,
                -0.   , -0.117,  0.   , -0.108,  0.   , -0.1  ,  0.   , -0.094,
                -0.   , -0.088,  0.   , -0.083, -0.   , -0.078,  0.   , -0.076,
                -0.   , -0.072,  0.   , -0.069,  0.   , -0.067,  0.   , -0.065,
                0.   , -0.064, -0.   , -0.062, -0.   , -0.061, -0.   , -0.059,
                0.   , -0.059, -0.   , -0.058,  0.   , -0.058,  0.   , -0.041};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 1, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {-0.   ,  2.208,  0.   ,  4.647,  0.   , -3.481,  0.   , -0.893,
                0.   , -0.413,  0.   , -0.223,  0.   , -0.124,  0.   , -0.063,
                0.   , -0.023,  0.   ,  0.009, -0.   ,  0.035,  0.   ,  0.059,
                -0.   ,  0.084, -0.   ,  0.111,  0.   ,  0.144,  0.   ,  0.188,
                -0.   ,  0.254, -0.   ,  0.371,  0.   ,  0.64 , -0.   ,  1.974,
                0.   , -2.026,  0.   , -0.691, -0.   , -0.424,  0.   , -0.311,
                0.   , -0.247,  0.   , -0.207, -0.   , -0.178,  0.   , -0.158,
                0.   , -0.144, -0.   , -0.132,  0.   , -0.122, -0.   , -0.115,
                0.   , -0.109,  0.   , -0.104, -0.   , -0.101, -0.   , -0.098,
                -0.   , -0.097,  0.   , -0.095,  0.   , -0.095,  0.   , -0.065};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 1, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType2Ortho() {
        double[] result1 = {0.503,  0.553,  0.488,  0.651,  0.511,  0.844,  0.609,  1.328,
                1.014,  4.769, -3.049, -1.626, -0.244, -0.173,  0.109,  1.821,
                -1.428, -1.563, -0.197, -0.734, -0.096, -0.501, -0.055, -0.383,
                -0.034, -0.31 , -0.021, -0.261, -0.013, -0.224, -0.008, -0.196,
                -0.004, -0.174, -0.002, -0.156, -0.001, -0.141, -0.   , -0.127,
                -0.   , -0.116, -0.   , -0.107, -0.001, -0.099, -0.001, -0.092,
                -0.003, -0.085, -0.004, -0.079, -0.005, -0.073, -0.006, -0.069,
                -0.008, -0.064, -0.009, -0.059, -0.01 , -0.056, -0.012, -0.052,
                -0.014, -0.049, -0.016, -0.045, -0.017, -0.042, -0.019, -0.039,
                -0.021, -0.037, -0.023, -0.034, -0.025, -0.032, -0.027, -0.029};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 2, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {1.025,  1.259,  1.481,  4.502, -3.468, -1.385, -0.443, -0.479,
                -0.17 , -0.247, -0.08 , -0.144, -0.04 , -0.084, -0.018, -0.043,
                -0.007, -0.015, -0.   ,  0.011,  0.004,  0.033,  0.006,  0.055,
                0.007,  0.079,  0.007,  0.105,  0.006,  0.139,  0.006,  0.185,
                0.005,  0.252,  0.004,  0.37 ,  0.002,  0.641,  0.001,  1.974,
                0.031, -2.049, -0.003, -0.693, -0.004, -0.422, -0.006, -0.306,
                -0.008, -0.24 , -0.01 , -0.197, -0.012, -0.167, -0.013, -0.145,
                -0.015, -0.128, -0.018, -0.114, -0.02 , -0.102, -0.022, -0.092,
                -0.024, -0.084, -0.027, -0.077, -0.029, -0.071, -0.032, -0.065,
                -0.034, -0.061, -0.038, -0.055, -0.04 , -0.052, -0.044, -0.047};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 2, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType3Ortho() {
        double[] result1 = {0.   ,  1.034,  0.   ,  1.108,  0.   ,  1.301,  0.   ,  1.792,
                0.   ,  4.06 ,  0.   , -3.885,  0.   , -0.531,  0.   ,  1.008,
                0.   , -2.76 ,  0.   , -0.928,  0.   , -0.574,  0.   , -0.41 ,
                0.   , -0.314,  0.   , -0.25 ,  0.   , -0.205,  0.   , -0.172,
                0.   , -0.145,  0.   , -0.125,  0.   , -0.108,  0.   , -0.094,
                0.   , -0.082,  0.   , -0.073,  0.   , -0.065,  0.   , -0.058,
                0.   , -0.051,  0.   , -0.045,  0.   , -0.04 ,  0.   , -0.036,
                0.   , -0.031,  0.   , -0.027,  0.   , -0.025,  0.   , -0.021,
                0.   , -0.019,  0.   , -0.015,  0.   , -0.013,  0.   , -0.01 ,
                0.   , -0.008,  0.   , -0.005,  0.   , -0.003,  0.   , -0.001};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 3, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {0.   ,  2.19 ,  0.   ,  4.469,  0.   , -3.703,  0.   , -0.916,
                0.   , -0.42 ,  0.   , -0.226,  0.   , -0.125,  0.   , -0.064,
                0.   , -0.025,  0.   ,  0.005,  0.   ,  0.029,  0.   ,  0.05 ,
                0.   ,  0.07 ,  0.   ,  0.091,  0.   ,  0.115,  0.   ,  0.146,
                0.   ,  0.188,  0.   ,  0.26 ,  0.   ,  0.409,  0.   ,  0.944,
                0.   , -2.844,  0.   , -0.552,  0.   , -0.3  ,  0.   , -0.202,
                0.   , -0.15 ,  0.   , -0.116,  0.   , -0.093,  0.   , -0.077,
                0.   , -0.065,  0.   , -0.054,  0.   , -0.045,  0.   , -0.038,
                0.   , -0.032,  0.   , -0.027,  0.   , -0.022,  0.   , -0.017,
                0.   , -0.014,  0.   , -0.009,  0.   , -0.006,  0.   , -0.001};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 3, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }

    @Test
    public void testInverseCosineType4Ortho() {
        double[] result1 = {0.508,  0.538,  0.514,  0.61 ,  0.563,  0.766,  0.704,  1.169,
                1.236,  4.083, -3.939, -1.357, -0.336, -0.141,  0.16 ,  1.453,
                -2.269, -1.222, -0.341, -0.564, -0.182, -0.379, -0.116, -0.286,
                -0.08 , -0.229, -0.056, -0.19 , -0.04 , -0.162, -0.029, -0.141,
                -0.019, -0.124, -0.012, -0.111, -0.007, -0.1  , -0.002, -0.09 ,
                0.001, -0.082,  0.004, -0.076,  0.006, -0.07 ,  0.008, -0.066,
                0.011, -0.061,  0.012, -0.057,  0.014, -0.053,  0.015, -0.051,
                0.017, -0.048,  0.018, -0.045,  0.018, -0.043,  0.02 , -0.041,
                0.021, -0.04 ,  0.022, -0.038,  0.023, -0.036,  0.024, -0.034,
                0.025, -0.033,  0.026, -0.031,  0.027, -0.03 ,  0.028, -0.029};
        InverseDiscreteCosine idct1 = new InverseDiscreteCosine(this.signal1, 4, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct1.transform();
        double[] output1 = idct1.getMagnitude();
        Assertions.assertArrayEquals(result1, output1, 0.001);

        double[] result2 = {1.036,  1.223,  1.56 ,  4.222, -3.819, -1.257, -0.513, -0.422,
                -0.207, -0.212, -0.104, -0.12 , -0.054, -0.068, -0.027, -0.035,
                -0.012, -0.011, -0.   ,  0.008,  0.007,  0.025,  0.012,  0.041,
                0.016,  0.058,  0.018,  0.077,  0.02 ,  0.101,  0.022,  0.133,
                0.022,  0.179,  0.023,  0.263,  0.023,  0.454,  0.02 ,  1.396,
                -2.208, -1.45 ,  0.036, -0.491,  0.033, -0.3  ,  0.032, -0.219,
                0.032, -0.173,  0.033, -0.143,  0.034, -0.122,  0.033, -0.107,
                0.034, -0.096,  0.035, -0.087,  0.036, -0.079,  0.036, -0.073,
                0.037, -0.068,  0.038, -0.063,  0.038, -0.06 ,  0.04 , -0.057,
                0.041, -0.054,  0.042, -0.051,  0.044, -0.049,  0.046, -0.046};
        InverseDiscreteCosine idct2 = new InverseDiscreteCosine(this.signal2, 4, InverseDiscreteCosine.Normalization.ORTHOGONAL);
        idct2.transform();
        double[] output2 = idct2.getMagnitude();
        Assertions.assertArrayEquals(result2, output2, 0.001);
    }
}
