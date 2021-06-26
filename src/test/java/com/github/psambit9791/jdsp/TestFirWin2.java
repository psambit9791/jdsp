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

import com.github.psambit9791.jdsp.filter.FIRWin2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestFirWin2 {

    private final int samplingRate = 100;
    private final double[] signal = {1.10168563, 1.38191166, 1.29029608, 1.05666263, 0.94422545, 0.95798688, 1.04630625,
            1.24103669, 1.44362822, 1.38745155, 1.03788056, 0.77561554, 0.91780505, 1.25377026, 1.34262046, 1.11761007,
            0.88006049, 0.80125178, 0.78213891, 0.7711632 , 0.85015107, 0.95156431, 0.84366613, 0.52683779, 0.33441325,
            0.46087048, 0.66083891, 0.63158023, 0.45030643, 0.36905465, 0.38303655, 0.32286699, 0.23477111, 0.30990337,
            0.48855565, 0.49487231, 0.29017088, 0.15479253, 0.24169921, 0.36706079, 0.36701158, 0.35201891, 0.4227153 ,
            0.42250524, 0.23092439, 0.0692671 , 0.19940213, 0.47231721, 0.51117719, 0.26229594};


    @Test
    public void fType1Test1() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 1.0};
        int taps = 15;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate);
        Assertions.assertEquals(1, fw2.getFilterType());

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);

        double[] resCoeffs = {-3.88242319e-04, -1.58896291e-03, -2.22541746e-03,  7.63400590e-17,
                -1.48888123e-02, -8.48578140e-02, -1.94033715e-01,  7.50000000e-01,
                -1.94033715e-01, -8.48578140e-02, -1.48888123e-02, -6.37562342e-17,
                -2.22541746e-03, -1.58896291e-03, -3.88242319e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void fType1Filter() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 1.0};
        int taps = 15;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate);

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] filteredX = fw2.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-4.27720985e-04, -2.28705420e-03, -5.14846435e-03, -5.53580409e-03,
                -2.13198240e-02, -1.18285444e-01, -3.54270857e-01,  4.28626331e-01,
                4.63723260e-01,  3.01087234e-01,  1.22564505e-01,  7.48896664e-02,
                9.02270891e-02,  1.10529916e-01,  2.09055364e-01,  3.59739302e-01,
                3.49333661e-01,  1.12932584e-01, -7.11221124e-02,  4.71614111e-02,
                3.03631222e-01,  3.61497182e-01,  1.98431389e-01,  6.96935725e-02,
                7.89688315e-02,  9.74407630e-02,  8.01792320e-02,  1.38181969e-01,
                2.51341293e-01,  2.21146952e-01,  1.90955811e-02, -9.69042323e-02,
                3.00963126e-02,  1.98748784e-01,  1.72053762e-01,  3.87824906e-02,
                1.68723687e-02,  7.74855270e-02,  4.73629615e-02, -3.70302491e-02,
                9.13705716e-03,  1.55287184e-01,  1.70373998e-01,  1.64347286e-02,
                -7.58723884e-02,  9.11886432e-03,  1.01192045e-01,  6.81046597e-02,
                3.37284212e-02,  1.06553872e-01};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }


    @Test
    public void fType2Test1() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {1.0, 1.0, 0.0};
        int taps = 10;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate);
        Assertions.assertEquals(2, fw2.getFilterType());

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] resCoeffs = {0.00060439,  0.00228306, -0.01076332, -0.04939297,  0.55770673, 0.55770673, -0.04939297,
                -0.01076332,  0.00228306,  0.00060439};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void fType2Filter() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {1.0, 1.0, 0.0};
        int taps = 10;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate);

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] filteredX = fw2.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {6.65853283e-04,  3.35043465e-03, -7.92295787e-03, -6.57050137e-02,
                5.35256021e-01,  1.31274891e+00,  1.37635755e+00,  1.17498981e+00,
                9.84944806e-01,  9.37714512e-01,  9.90535739e-01,  1.13797768e+00,
                1.36068288e+00,  1.45235084e+00,  1.22641440e+00,  8.75709070e-01,
                8.08985442e-01,  1.08980964e+00,  1.33517897e+00,  1.25283797e+00,
                9.91147592e-01,  8.27026824e-01,  7.86834614e-01,  7.70612604e-01,
                8.04969994e-01,  9.14381898e-01,  9.25020418e-01,  6.90833869e-01,
                4.02656891e-01,  3.72931105e-01,  5.70929623e-01,  6.70995821e-01,
                5.45383657e-01,  3.98237933e-01,  3.74722278e-01,  3.58935878e-01,
                2.70256437e-01,  2.56111612e-01,  4.04265429e-01,  5.16374243e-01,
                4.01900902e-01,  2.04468457e-01,  1.81748631e-01,  3.09469554e-01,
                3.75921455e-01,  3.55947574e-01,  3.87565629e-01,  4.39620893e-01,
                3.36633554e-01,  1.29418526e-01};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void fType3Test1() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 0.0};
        int taps = 15;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate, true);
        Assertions.assertEquals(3, fw2.getFilterType());

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] resCoeffs = {-7.76484638e-04, -4.63432037e-18,  4.45083492e-03,  1.98410484e-17,
                -2.97776245e-02,  1.91947410e-17,  3.88067431e-01,  0.00000000e+00,
                -3.88067431e-01,  2.77537464e-17,  2.97776245e-02,  2.00794486e-17,
                -4.45083492e-03, -5.85915682e-18,  7.76484638e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void fType3Filter() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 0.0};
        int taps = 15;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate, true);

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] filteredX = fw2.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-0.00085544, -0.00107303,  0.00390153,  0.00533018, -0.02779586,
                -0.03719088,  0.39249651,  0.5081102 ,  0.04861276, -0.15029881,
                -0.12703022, -0.02852486,  0.03405172,  0.09632024,  0.14955391,
                0.06333195, -0.15153926, -0.23690402, -0.0442037 ,  0.19177916,
                0.16676466, -0.05612095, -0.17583434, -0.10694608, -0.02311935,
                -0.00972301,  0.02318921,  0.07573927,  0.01053276, -0.15575669,
                -0.19307819, -0.01748966,  0.13673446,  0.06850523, -0.085578  ,
                -0.09837913, -0.01230212, -0.00790493, -0.06025375, -0.01082657,
                0.10055051,  0.07699348, -0.07724287, -0.13358765, -0.01423636,
                0.08732632,  0.04356358, -0.01582952,  0.02151609,  0.03781488};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void fType4Test1() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 1.0};
        int taps = 10;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate, true);
        Assertions.assertEquals(4, fw2.getFilterType());

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] resCoeffs = {0.00060439, -0.00228306, -0.01076332,  0.04939297,  0.55770673,
                -0.55770673, -0.04939297,  0.01076332,  0.00228306, -0.00060439};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void fType4Filter() {
        double[] freqs = {0.0, 25, 50};
        double[] gains = {0.0, 1.0, 1.0};
        int taps = 10;
        FIRWin2 fw2 = new FIRWin2(taps, this.samplingRate, true);

        double[] outCoeffs = fw2.computeCoefficients(freqs, gains);
        double[] filteredX = fw2.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {6.65853283e-04, -1.67999368e-03, -1.42329317e-02,  3.72343865e-02,
                6.66944609e-01,  2.07065575e-01, -6.50362039e-02, -1.52009593e-01,
                -7.49541090e-02,  7.72531782e-03,  5.93219196e-02,  1.27550967e-01,
                1.29277941e-01, -3.81722343e-02, -2.24715361e-01, -1.67609525e-01,
                9.09721563e-02,  2.15288114e-01,  5.84715373e-02, -1.42870044e-01,
                -1.53766645e-01, -5.34293986e-02, -1.25144477e-02, -4.34318863e-03,
                5.22970368e-02,  6.37003348e-02, -7.07954537e-02, -2.02829393e-01,
                -1.22789620e-01,  8.02811407e-02,  1.27765382e-01, -1.71988731e-02,
                -1.14822120e-01, -5.34518878e-02,  6.59211022e-03, -3.84522052e-02,
                -5.43382496e-02,  4.88833836e-02,  1.13278792e-01,  3.58468485e-03,
                -1.30190154e-01, -8.67582211e-02,  5.40223713e-02,  7.98562580e-02,
                2.07617506e-03, -7.86596979e-03,  4.40547603e-02, -2.37149118e-03,
                -1.22814435e-01, -1.02137622e-01};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }
}