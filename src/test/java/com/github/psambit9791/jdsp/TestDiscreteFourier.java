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
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDiscreteFourier {

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
    public void testFourierMagnitudePositive1(){
        double[] result = {0.0,  0.266,  0.59,  1.113,  2.397, 39.963,  2.12 ,  0.154, 18.472,  3.176,  1.859,  1.4,
                1.148,  0.988,  0.877,  0.789, 0.726,  0.673,  0.628,  0.592,  0.557,  0.53 ,  0.506,  0.485, 0.473,
                0.455,  0.444,  0.426,  0.422,  0.411,  0.397,  0.396, 0.387,  0.386,  0.38 ,  0.375,  0.369,  0.368,
                0.366,  0.366, 0.36};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[] out = fft1.getMagnitude(true);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierMagnitudePositive2(){
        double[] result = {0.    ,  0.6718, 39.7421,  1.1819,  0.6037,  0.3815,  0.2566,
                0.1646,  0.0836,  0.0221,  0.0468,  0.1134,  0.1919,  0.2839,
                0.3939,  0.543 ,  0.7596,  1.0899,  1.7161,  3.3335, 17.6649,
                6.3761,  2.885 ,  1.9382,  1.4996,  1.2456,  1.0793,  0.958 ,
                0.873 ,  0.8136,  0.7597,  0.7162,  0.685 ,  0.6591,  0.6362,
                0.6235,  0.6083,  0.6041,  0.5938,  0.5941, 0.5820};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[] out = fft1.getMagnitude(true);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierMagnitude1(){
        double[] result = {0.0,  0.266,  0.59 ,  1.113,  2.397, 39.963,  2.12,  0.154, 18.472,  3.176,  1.859,  1.4,
                1.148,  0.988,  0.877,  0.789, 0.726,  0.673,  0.628,  0.592,  0.557,  0.53 ,  0.506,  0.485, 0.473,
                0.455,  0.444,  0.426,  0.422,  0.411,  0.397,  0.396, 0.387,  0.386,  0.38 ,  0.375,  0.369,  0.368,
                0.366,  0.366, 0.36 ,  0.366,  0.366,  0.368,  0.369,  0.375,  0.38 ,  0.386, 0.387,  0.396,  0.397,
                0.411,  0.422,  0.426,  0.444,  0.455, 0.473,  0.485,  0.506,  0.53 ,  0.557,  0.592,  0.628,  0.673,
                0.726,  0.789,  0.877,  0.988,  1.148,  1.4,  1.859,  3.176, 18.472,  0.154,  2.12 , 39.963,  2.397,
                1.113,  0.59 ,  0.266};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[] out = fft1.getMagnitude(false);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierMagnitude2(){
        double[] result = {0.    ,  0.6718, 39.7421,  1.1819,  0.6037,  0.3815,  0.2566,
                0.1646,  0.0836,  0.0221,  0.0468,  0.1134,  0.1919,  0.2839,
                0.3939,  0.543 ,  0.7596,  1.0899,  1.7161,  3.3335, 17.6649,
                6.3761,  2.885 ,  1.9382,  1.4996,  1.2456,  1.0793,  0.958 ,
                0.873 ,  0.8136,  0.7597,  0.7162,  0.685 ,  0.6591,  0.6362,
                0.6235,  0.6083,  0.6041,  0.5938,  0.5941,  0.582 ,  0.5941,
                0.5938,  0.6041,  0.6083,  0.6235,  0.6362,  0.6591,  0.685 ,
                0.7162,  0.7597,  0.8136,  0.873 ,  0.958 ,  1.0793,  1.2456,
                1.4996,  1.9382,  2.885 ,  6.3761, 17.6649,  3.3335,  1.7161,
                1.0899,  0.7596,  0.543 ,  0.3939,  0.2839,  0.1919,  0.1134,
                0.0468,  0.0221,  0.0836,  0.1646,  0.2566,  0.3815,  0.6037,
                1.1819, 39.7421,  0.6718};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[] out = fft1.getMagnitude(false);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierPhaseRadPositive1() {
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] result = {0,   -1.5315,    -1.4923,    -1.453,     -1.4137,
                -1.3744,    1.8064,     -1.2959,    -1.2566,    1.9242,     1.9635,
                2.0028,     2.042,      2.0813,     2.1206,     2.1598,     2.1991,
                2.2384,     2.2777,     2.3169,     2.3562,     2.3955,     2.4347,
                2.474,      2.5133,     2.5525,     2.5918,     2.6311,     2.6704,
                2.7096,     2.7489,     2.7882,     2.8274,     2.8667,     2.906,
                2.9452,     2.9845,     3.0238,     3.0631,     3.1023,     -3.1414};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[] out = fft1.getPhaseRad(true);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierPhaseRadPositive2() {
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] result = {3.1415,  -1.5315,    -1.4923,    1.6886,     1.7279,
                1.7671,     1.8064,     1.8457,     1.885,      1.9242,     -1.1781,
                -1.1388,    -1.0996,    -1.0603,    -1.021,     -0.98175,   -0.94248,
                -0.90321,   -0.86394,   -0.82467,   -0.7854,    2.3955,     2.4347,
                2.474,      2.5133,     2.5525,     2.5918,     2.6311,     2.6704,
                2.7096,     2.7489,     2.7882,     2.8274,     2.8667,     2.906,
                2.9452,     2.9845,     3.0238,     3.0631,     3.1023,     3.1416};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[] out = fft1.getPhaseRad(true);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierPhaseDeg1() {
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] result = {0,   -87.75,     -85.5,      -83.25,     -81,
                -78.75,     103.5,      -74.25,     -72,    110.25,     112.5,
                114.75,     117,        119.25,     121.5,  123.75,     126,
                128.25,     130.5,      132.75,     135,    137.25,     139.5,
                141.75,     144,        146.25,     148.5,  150.75,     153,
                155.25,     157.5,      159.75,     162,    164.25,     166.5,
                168.75,     171,        173.25,     175.5,  177.75,     -180,
                -177.75,    -175.5,     -173.25,    -171,   -168.75,    -166.5,
                -164.25,    -162,       -159.75,    -157.5, -155.25,    -153,
                -150.75,    -148.5,     -146.25,    -144,   -141.75,    -139.5,
                -137.25,    -135,       -132.75,    -130.5, -128.25,    -126,
                -123.75,    -121.5,     -119.25,    -117,   -114.75,    -112.5,
                -110.25,    72,         74.25,      -103.5, 78.75,      81,
                83.25,      85.5,       87.75};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[] out = fft1.getPhaseDeg(false);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierPhaseRad1(){
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] result = {0,   -1.5315,    -1.4923,    -1.453,     -1.4137,
                -1.3744,    1.8064,     -1.2959,    -1.2566,    1.9242,     1.9635,
                2.0028,     2.042,      2.0813,     2.1206,     2.1598,     2.1991,
                2.2384,     2.2777,     2.3169,     2.3562,     2.3955,     2.4347,
                2.474,      2.5133,     2.5525,     2.5918,     2.6311,     2.6704,
                2.7096,     2.7489,     2.7882,     2.8274,     2.8667,     2.906,
                2.9452,     2.9845,     3.0238,     3.0631,     3.1023,     -3.1414,
                -3.1023,    -3.0631,    -3.0238,    -2.9845,    -2.9452,    -2.906,
                -2.8667,    -2.8274,    -2.7882,    -2.7489,    -2.7096,    -2.6704,
                -2.6311,    -2.5918,    -2.5525,    -2.5133,    -2.474,     -2.4347,
                -2.3955,    -2.3562,    -2.3169,    -2.2777,    -2.2384,    -2.1991,
                -2.1598,    -2.1206,    -2.0813,    -2.042,     -2.0028,    -1.9635,
                -1.9242,    1.2566,     1.2959,     -1.8064,    1.3744,     1.4137,
                1.453,      1.4923,     1.5315};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[] out = fft1.getPhaseRad(false);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierPhaseRad2(){
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] result = {3.1415,  -1.5315,    -1.4923,    1.6886,     1.7279,
                1.7671,     1.8064,     1.8457,     1.885,      1.9242,     -1.1781,
                -1.1388,    -1.0996,    -1.0603,    -1.021,     -0.98175,   -0.94248,
                -0.90321,   -0.86394,   -0.82467,   -0.7854,    2.3955,     2.4347,
                2.474,      2.5133,     2.5525,     2.5918,     2.6311,     2.6704,
                2.7096,     2.7489,     2.7882,     2.8274,     2.8667,     2.906,
                2.9452,     2.9845,     3.0238,     3.0631,     3.1023,     3.1416,
                -3.1023,    -3.0631,    -3.0238,    -2.9845,    -2.9452,    -2.906,
                -2.8667,    -2.8274,    -2.7882,    -2.7489,    -2.7096,    -2.6704,
                -2.6311,    -2.5918,    -2.5525,    -2.5133,    -2.474,     -2.4347,
                -2.3955,    0.7854,     0.82467,    0.86394,    0.90321,    0.94248,
                0.98175,    1.021,      1.0603,     1.0996,     1.1388,     1.1781,
                -1.9242,    -1.885,     -1.8457,    -1.8064,    -1.7671,    -1.7279,
                -1.6886,    1.4923,     1.5315};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[] out = fft1.getPhaseRad(false);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void testFourierMagPhaseDeg1() {
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] resultMag = {0.0,  0.266,  0.59 ,  1.113,  2.397, 39.963,  2.12,  0.154, 18.472,  3.176,  1.859,  1.4,
                1.148,  0.988,  0.877,  0.789, 0.726,  0.673,  0.628,  0.592,  0.557,  0.53 ,  0.506,  0.485, 0.473,
                0.455,  0.444,  0.426,  0.422,  0.411,  0.397,  0.396, 0.387,  0.386,  0.38 ,  0.375,  0.369,  0.368,
                0.366,  0.366, 0.36 ,  0.366,  0.366,  0.368,  0.369,  0.375,  0.38 ,  0.386, 0.387,  0.396,  0.397,
                0.411,  0.422,  0.426,  0.444,  0.455, 0.473,  0.485,  0.506,  0.53 ,  0.557,  0.592,  0.628,  0.673,
                0.726,  0.789,  0.877,  0.988,  1.148,  1.4,  1.859,  3.176, 18.472,  0.154,  2.12 , 39.963,  2.397,
                1.113,  0.59 ,  0.266};
        double[] resultPhase = {0,   -87.75,     -85.5,      -83.25,     -81,
                -78.75,     103.5,      -74.25,     -72,    110.25,     112.5,
                114.75,     117,        119.25,     121.5,  123.75,     126,
                128.25,     130.5,      132.75,     135,    137.25,     139.5,
                141.75,     144,        146.25,     148.5,  150.75,     153,
                155.25,     157.5,      159.75,     162,    164.25,     166.5,
                168.75,     171,        173.25,     175.5,  177.75,     -180,
                -177.75,    -175.5,     -173.25,    -171,   -168.75,    -166.5,
                -164.25,    -162,       -159.75,    -157.5, -155.25,    -153,
                -150.75,    -148.5,     -146.25,    -144,   -141.75,    -139.5,
                -137.25,    -135,       -132.75,    -130.5, -128.25,    -126,
                -123.75,    -121.5,     -119.25,    -117,   -114.75,    -112.5,
                -110.25,    72,         74.25,      -103.5, 78.75,      81,
                83.25,      85.5,       87.75};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[][] out = fft1.getMagPhaseDeg(false);
        Assertions.assertArrayEquals(resultMag, UtilMethods.getColumn(out, 0), 0.001);
        Assertions.assertArrayEquals(resultPhase, UtilMethods.getColumn(out, 1), 0.001);
    }

    @Test
    public void testFourierMagPhaseRadPositive2() {
        // Results calculated with MATLAB R2020b Update 4 9.9.0.1570001
        double[] resultMag = {0.    ,  0.6718, 39.7421,  1.1819,  0.6037,  0.3815,  0.2566,
                0.1646,  0.0836,  0.0221,  0.0468,  0.1134,  0.1919,  0.2839,
                0.3939,  0.543 ,  0.7596,  1.0899,  1.7161,  3.3335, 17.6649,
                6.3761,  2.885 ,  1.9382,  1.4996,  1.2456,  1.0793,  0.958 ,
                0.873 ,  0.8136,  0.7597,  0.7162,  0.685 ,  0.6591,  0.6362,
                0.6235,  0.6083,  0.6041,  0.5938,  0.5941, 0.5820};
        double[] resultPhase =  {3.1415,  -1.5315,    -1.4923,    1.6886,     1.7279,
                1.7671,     1.8064,     1.8457,     1.885,      1.9242,     -1.1781,
                -1.1388,    -1.0996,    -1.0603,    -1.021,     -0.98175,   -0.94248,
                -0.90321,   -0.86394,   -0.82467,   -0.7854,    2.3955,     2.4347,
                2.474,      2.5133,     2.5525,     2.5918,     2.6311,     2.6704,
                2.7096,     2.7489,     2.7882,     2.8274,     2.8667,     2.906,
                2.9452,     2.9845,     3.0238,     3.0631,     3.1023,     3.1416};
        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[][] out = fft1.getMagPhaseRad(true);
        Assertions.assertArrayEquals(resultMag, UtilMethods.getColumn(out, 0), 0.001);
        Assertions.assertArrayEquals(resultPhase, UtilMethods.getColumn(out, 1), 0.001);
    }

    @Test
    public void testFourierFullPositive1(){
        double[] resultReal = {0.0, 0.01, 0.046, 0.131, 0.375, 7.796, -0.495, 0.042, 5.708, -1.099, -0.712, -0.586,
                -0.521, -0.483, -0.458, -0.438, -0.427, -0.416, -0.408, -0.402, -0.394, -0.389, -0.384, -0.381, -0.383,
                -0.378, -0.378, -0.372, -0.376, -0.373, -0.366, -0.371, -0.368, -0.371, -0.369, -0.368, -0.364, -0.366,
                -0.365, -0.366, -0.360};

        double[] resultIm = {-0.0, -0.266, -0.588, -1.105, -2.367, -39.196, 2.061, -0.148, -17.567, 2.98, 1.718, 1.271,
                1.023, 0.862, 0.748, 0.656, 0.587, 0.528, 0.478, 0.435, 0.394, 0.36, 0.328, 0.3, 0.278, 0.253, 0.232,
                0.208, 0.192, 0.172, 0.152, 0.137, 0.12, 0.105, 0.089, 0.073, 0.058, 0.043, 0.029, 0.014, 0.0};

        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[][] out = fft1.getFull(true);

        double[] outReal = new double[out.length];
        double[] outIm = new double[out.length];

        for (int i=0; i<out.length; i++) {
            outReal[i] = out[i][0];
            outIm[i] = out[i][1];
        }
        Assertions.assertArrayEquals(resultReal, outReal, 0.001);
        Assertions.assertArrayEquals(resultIm, outIm, 0.001);
    }

    @Test
    public void testFourierFullPositive2(){
        double[] resultReal = {0.    ,  0.0264,  3.1181, -0.1389, -0.0944, -0.0744, -0.0599,
                -0.0447, -0.0258, -0.0077,  0.0179,  0.0475,  0.0871,  0.1387,
                0.2058,  0.3016,  0.4465,  0.6747,  1.1145,  2.2628, 12.491 ,
                -4.6821, -2.1938, -1.5221, -1.2132, -1.0357, -0.9202, -0.8359,
                -0.7779, -0.7389, -0.7019, -0.672 , -0.6515, -0.6344, -0.6186,
                -0.6115, -0.6008, -0.5999, -0.592 , -0.593, -0.582};

        double[] resultIm = {-0.    ,  -0.6712, -39.6196,   1.1738,   0.5962,   0.3742,
                0.2495,   0.1584,   0.0795,   0.0207,  -0.0433,  -0.103 ,
                -0.1709,  -0.2477,  -0.3359,  -0.4515,  -0.6145,  -0.8559,
                -1.305 ,  -2.4478, -12.491 ,   4.3281,   1.8737,   1.1999,
                0.8814,   0.692 ,   0.5639,   0.4681,   0.3963,   0.3406,
                0.2907,   0.2479,   0.2117,   0.1789,   0.1485,   0.1216,
                0.0952,   0.071 ,   0.0466,   0.0233, 0.0};

        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[][] out = fft1.getFull(true);

        double[] outReal = new double[out.length];
        double[] outIm = new double[out.length];

        for (int i=0; i<out.length; i++) {
            outReal[i] = out[i][0];
            outIm[i] = out[i][1];
        }
        Assertions.assertArrayEquals(resultReal, outReal, 0.001);
        Assertions.assertArrayEquals(resultIm, outIm, 0.001);
    }

    @Test
    public void testFourierFull1(){
        double[] resultReal = {0.0, 0.01, 0.046, 0.131, 0.375, 7.796, -0.495, 0.042, 5.708, -1.099, -0.712, -0.586,
                -0.521, -0.483, -0.458, -0.438, -0.427, -0.416, -0.408, -0.402, -0.394, -0.389, -0.384, -0.381, -0.383,
                -0.378, -0.378, -0.372, -0.376, -0.373, -0.366, -0.371, -0.368, -0.371, -0.369, -0.368, -0.364, -0.366,
                -0.365, -0.366, -0.36, -0.366, -0.365, -0.366, -0.364, -0.368, -0.369, -0.371, -0.368, -0.371, -0.366,
                -0.373, -0.376, -0.372, -0.378, -0.378, -0.383, -0.381, -0.384, -0.389, -0.394, -0.402, -0.408, -0.416,
                -0.427, -0.438, -0.458, -0.483, -0.521, -0.586, -0.712, -1.099, 5.708, 0.042, -0.495, 7.796, 0.375,
                0.131, 0.046, 0.01};

        double[] resultIm = {-0.0, -0.266, -0.588, -1.105, -2.367, -39.196, 2.061, -0.148, -17.567, 2.98, 1.718, 1.271,
                1.023, 0.862, 0.748, 0.656, 0.587, 0.528, 0.478, 0.435, 0.394, 0.36, 0.328, 0.3, 0.278, 0.253, 0.232,
                0.208, 0.192, 0.172, 0.152, 0.137, 0.12, 0.105, 0.089, 0.073, 0.058, 0.043, 0.029, 0.014, -0.0, -0.014,
                -0.029, -0.043, -0.058, -0.073, -0.089, -0.105, -0.12, -0.137, -0.152, -0.172, -0.192, -0.208, -0.232,
                -0.253, -0.278, -0.3, -0.328, -0.36, -0.394, -0.435, -0.478, -0.528, -0.587, -0.656, -0.748, -0.862,
                -1.023, -1.271, -1.718, -2.98, 17.567, 0.148, -2.061, 39.196, 2.367, 1.105, 0.588, 0.266};

        DiscreteFourier fft1 = new DiscreteFourier(this.signal1);
        fft1.dft();
        double[][] out = fft1.getFull(false);

        double[] outReal = new double[out.length];
        double[] outIm = new double[out.length];

        for (int i=0; i<out.length; i++) {
            outReal[i] = out[i][0];
            outIm[i] = out[i][1];
        }
        Assertions.assertArrayEquals(resultReal, outReal, 0.001);
        Assertions.assertArrayEquals(resultIm, outIm, 0.001);
    }

    @Test
    public void testFourierFull2(){
        double[] resultReal = {0.    ,  0.0264,  3.1181, -0.1389, -0.0944, -0.0744, -0.0599,
                -0.0447, -0.0258, -0.0077,  0.0179,  0.0475,  0.0871,  0.1387,
                0.2058,  0.3016,  0.4465,  0.6747,  1.1145,  2.2628, 12.491 ,
                -4.6821, -2.1938, -1.5221, -1.2132, -1.0357, -0.9202, -0.8359,
                -0.7779, -0.7389, -0.7019, -0.672 , -0.6515, -0.6344, -0.6186,
                -0.6115, -0.6008, -0.5999, -0.592 , -0.5937, -0.582 , -0.5937,
                -0.592 , -0.5999, -0.6008, -0.6115, -0.6186, -0.6344, -0.6515,
                -0.672 , -0.7019, -0.7389, -0.7779, -0.8359, -0.9202, -1.0357,
                -1.2132, -1.5221, -2.1938, -4.6821, 12.491 ,  2.2628,  1.1145,
                0.6747,  0.4465,  0.3016,  0.2058,  0.1387,  0.0871,  0.0475,
                0.0179, -0.0077, -0.0258, -0.0447, -0.0599, -0.0744, -0.0944,
                -0.1389,  3.1181,  0.0264};

        double[] resultIm = {-0.    ,  -0.6712, -39.6196,   1.1738,   0.5962,   0.3742,
                0.2495,   0.1584,   0.0795,   0.0207,  -0.0433,  -0.103 ,
                -0.1709,  -0.2477,  -0.3359,  -0.4515,  -0.6145,  -0.8559,
                -1.305 ,  -2.4478, -12.491 ,   4.3281,   1.8737,   1.1999,
                0.8814,   0.692 ,   0.5639,   0.4681,   0.3963,   0.3406,
                0.2907,   0.2479,   0.2117,   0.1789,   0.1485,   0.1216,
                0.0952,   0.071 ,   0.0466,   0.0233,  -0.    ,  -0.0233,
                -0.0466,  -0.071 ,  -0.0952,  -0.1216,  -0.1485,  -0.1789,
                -0.2117,  -0.2479,  -0.2907,  -0.3406,  -0.3963,  -0.4681,
                -0.5639,  -0.692 ,  -0.8814,  -1.1999,  -1.8737,  -4.3281,
                12.491 ,   2.4478,   1.305 ,   0.8559,   0.6145,   0.4515,
                0.3359,   0.2477,   0.1709,   0.103 ,   0.0433,  -0.0207,
                -0.0795,  -0.1584,  -0.2495,  -0.3742,  -0.5962,  -1.1738,
                39.6196,   0.6712};

        DiscreteFourier fft1 = new DiscreteFourier(this.signal2);
        fft1.dft();
        double[][] out = fft1.getFull(false);

        double[] outReal = new double[out.length];
        double[] outIm = new double[out.length];

        for (int i=0; i<out.length; i++) {
            outReal[i] = out[i][0];
            outIm[i] = out[i][1];
        }
        Assertions.assertArrayEquals(resultReal, outReal, 0.001);
        Assertions.assertArrayEquals(resultIm, outIm, 0.001);
    }
}
