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


import com.github.psambit9791.jdsp.filter.FIRWin1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFirWin1 {

    private final int taps = 3;
    private final double ripple = 30;
    private final double[] signal = {1.10168563, 1.38191166, 1.29029608, 1.05666263, 0.94422545, 0.95798688, 1.04630625,
            1.24103669, 1.44362822, 1.38745155, 1.03788056, 0.77561554, 0.91780505, 1.25377026, 1.34262046, 1.11761007,
            0.88006049, 0.80125178, 0.78213891, 0.7711632 , 0.85015107, 0.95156431, 0.84366613, 0.52683779, 0.33441325,
            0.46087048, 0.66083891, 0.63158023, 0.45030643, 0.36905465, 0.38303655, 0.32286699, 0.23477111, 0.30990337,
            0.48855565, 0.49487231, 0.29017088, 0.15479253, 0.24169921, 0.36706079, 0.36701158, 0.35201891, 0.4227153 ,
            0.42250524, 0.23092439, 0.0692671 , 0.19940213, 0.47231721, 0.51117719, 0.26229594};

    @Test
    public void lowPassTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1};
        String filterType = "lowpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {0.00268693, 0.99462614, 0.00268693};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);

    }

    @Test
    public void lowPassTest2() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1};
        String filterType = "lowpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {0.5, 0.5};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);

    }

    @Test
    public void lowPassFilter() {
        double width = 5;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "lowpass", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-1.58405013e-04, -2.90811822e-04, -1.69777617e-04,  3.73634468e-04, 1.28292071e-03,
                2.21598827e-03,  2.61368587e-03,  1.92263396e-03, -4.50468189e-05, -2.80243176e-03, -5.20347304e-03,
                -5.87320857e-03, -3.85600208e-03,  8.12846288e-04,  6.83117616e-03,  1.18176968e-02, 1.30778923e-02,
                8.81574532e-03, -7.06979478e-04, -1.26963378e-02, -2.24153609e-02, -2.47868428e-02, -1.65761755e-02,
                1.60778671e-03, 2.44561383e-02,  4.29981258e-02,  4.75490797e-02,  3.16950796e-02, -4.08205718e-03,
                -5.05287955e-02, -9.04651683e-02, -1.02554290e-01, -6.71446375e-02,  2.76101016e-02,  1.81419688e-01,
                3.80809267e-01, 6.01790210e-01,  8.15567238e-01,  9.95649818e-01,  1.12419752e+00, 1.19563986e+00,
                1.21661221e+00,  1.20254944e+00,  1.17226871e+00, 1.14231727e+00,  1.12280609e+00,  1.11588808e+00,
                1.11702453e+00, 1.11813354e+00,  1.11119415e+0 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void highPassTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1};
        String filterType = "highpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {-2.99980692e-04,  9.99400039e-01, -2.99980692e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void highPassTest2() {
        double width = 1;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1};
        String filterType = "highpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {-0.03832441, -0.08195481,  0.9127392,  -0.08195481, -0.03832441};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void highPassFilter() {
        double width = 2;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "highpass", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-4.61780200e-05, -5.79239141e-05,  1.10122479e-05,  1.60153990e-04, 3.32483420e-04,
                4.44342344e-04,  4.21698688e-04,  2.34302187e-04, -7.09188796e-05, -3.67550506e-04, -5.07560307e-04,
                -4.05391061e-04, -7.91177539e-05,  3.68279600e-04,  7.84969860e-04,  1.00158057e-03, 8.83158281e-04,
                4.05693371e-04, -2.97932389e-04, -9.71602148e-04, -1.33118648e-03, -1.17215543e-03, -4.63841168e-04,
                5.92911221e-04, 1.59760792e-03,  2.10153676e-03,  1.81549427e-03,  7.54967817e-04, -7.44801484e-04,
                -2.13103730e-03, -2.81866534e-03, -2.42352373e-03, -9.63315491e-04,  1.09056053e-03,  2.96545108e-03,
                3.87884846e-03, 3.34492957e-03,  1.39589776e-03, -1.35714717e-03, -3.88668445e-03, -5.12462404e-03,
                -4.41132007e-03, -1.81181471e-03,  1.83969500e-03, 5.18708364e-03,  6.82783690e-03,  5.88245784e-03,
                2.43747900e-03, -2.37184540e-03, -6.72878837e-03 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void bandPassTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1, 0.2};
        String filterType = "bandpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {0.00242647, 0.99567599, 0.00242647};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandPassTest2() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1, 0.2};
        String filterType = "bandpass";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {0.5142076, 0.5142076};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandPassFilter() {
        double width = 5;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "bandpass", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-3.05136193e-04, -7.10252287e-04, -9.88189075e-04, -9.82162773e-04, -7.87521123e-04,
                -7.94736573e-04, -1.61567648e-03, -3.87021193e-03, -7.81329865e-03, -1.29979183e-02, -1.83005292e-02,
                -2.23973029e-02, -2.44259326e-02, -2.45120051e-02, -2.40050679e-02, -2.53095731e-02, -3.11570707e-02,
                -4.33934418e-02, -6.17956188e-02, -8.35976702e-02, -1.04093440e-01, -1.18224474e-01, -1.22735926e-01,
                -1.18189035e-01, -1.09856523e-01, -1.06687397e-01, -1.18319227e-01, -1.51075257e-01, -2.04370101e-01,
                -2.68859982e-01, -3.27270805e-01, -3.58146614e-01, -3.41707752e-01, -2.65990778e-01, -1.31160657e-01,
                4.94696295e-02, 2.52094378e-01,  4.48081674e-01,  6.11078935e-01,  7.23384833e-01, 7.79572549e-01,
                7.86399036e-01,  7.59393419e-01,  7.17467057e-01, 6.77268165e-01,  6.48981754e-01,  6.34789019e-01,
                6.30166211e-01, 6.27067012e-01,  6.17508117e-01 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void bandStopTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1, 0.2};
        String filterType = "bandstop";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {-2.70925670e-04,  1.00054185e+00, -2.70925670e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandStopTest2() {
        double width = 1;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1, 0.2};
        String filterType = "bandstop";
        double[] outCoeffs = fw.compute_coefficients(f, filterType, true);
        double[] resCoeffs = {-0.03301143, -0.10304144,  1.27210575, -0.10304144, -0.03301143};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandStopFilter() {
        double width = 2;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "bandstop", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-8.84091500e-05, -1.66189536e-04, -1.78407554e-04, -1.21999377e-04, -5.88643835e-05,
                -7.88433018e-05, -2.61481587e-04, -6.44618880e-04, -1.18645483e-03, -1.75582728e-03, -2.19586882e-03,
                -2.42315445e-03, -2.47036261e-03, -2.45118470e-03, -2.51364743e-03, -2.81818525e-03, -3.49836653e-03,
                -4.58216936e-03, -5.93754579e-03, -7.30700695e-03, -8.40754012e-03, -9.03360678e-03, -9.14459551e-03,
                -8.92869043e-03, -8.78323937e-03, -9.16126424e-03, -1.03490850e-02, -1.23164054e-02, -1.47123095e-02,
                -1.69747868e-02, -1.85091056e-02, -1.89152117e-02, -1.81954217e-02, -1.68123033e-02, -1.55286476e-02,
                -1.51070109e-02, -1.60087739e-02, -1.81839386e-02, -2.10082694e-02, -2.34351527e-02, -2.43711345e-02,
                -2.31343620e-02, -1.97721217e-02, -1.50968197e-02, -1.04381969e-02, -7.17870111e-03, -6.18714531e-03,
                -7.36259940e-03, -9.54181341e-03, -1.08753727e-02 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void multiBandPassTest() {
        double width = 5;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "multibandpass", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-4.05441421e-04, -1.01842704e-03, -1.72754421e-03, -2.66866946e-03, -4.45006204e-03,
                -8.10885681e-03, -1.48152805e-02, -2.53355881e-02, -3.93314999e-02, -5.48840885e-02, -6.87688800e-02,
                -7.76068704e-02, -7.94282846e-02, -7.49886331e-02, -6.83303341e-02, -6.61952040e-02, -7.60382930e-02,
                -1.02959487e-01, -1.46679176e-01, -2.00001580e-01, -2.49710027e-01, -2.79952822e-01, -2.77342690e-01,
                -2.36223639e-01, -1.62019524e-01, -7.08717618e-02,  1.48450791e-02,  7.37893101e-02, 9.32405060e-02,
                7.43776674e-02,  3.31721592e-02, -3.92891439e-03, -8.45567798e-03,  4.03811454e-02,  1.48254104e-01,
                3.03178460e-01, 4.78745987e-01,  6.42160156e-01,  7.64490643e-01,  8.29611688e-01, 8.38648787e-01,
                8.08531908e-01,  7.65509459e-01,  7.36085271e-01, 7.38435469e-01,  7.77102351e-01,  8.42649434e-01,
                9.16081133e-01, 9.75973663e-01,  1.00549326e+00 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void multiBandStopTest() {
        double width = 2;
        int samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.compute_coefficients(cutoff, "multibandstop", true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-2.36078255e-04, -5.39401619e-04, -7.95397069e-04, -9.43887084e-04, -1.01541209e-03,
                -1.08256081e-03, -1.22458962e-03, -1.51085890e-03, -1.94560821e-03, -2.42240840e-03, -2.79375772e-03,
                -3.01080046e-03, -3.16191580e-03, -3.36980454e-03, -3.69827948e-03, -4.16568738e-03, -4.77081555e-03,
                -5.43913418e-03, -5.97125476e-03, -6.10576555e-03, -5.64860100e-03, -4.55511501e-03, -2.96818343e-03,
                -1.26386613e-03, -3.12837800e-05,  1.27664935e-04, -1.09666899e-03, -3.54174225e-03, -6.62409607e-03,
                -9.52288869e-03, -1.14020081e-02, -1.16871938e-02, -1.03558328e-02, -8.04919904e-03, -5.87885252e-03,
                -5.04094609e-03, -6.44501741e-03, -1.04369941e-02, -1.66277725e-02, -2.39299652e-02, -3.09003467e-02,
                -3.62631461e-02, -3.93282064e-02, -4.01388373e-02, -3.93809891e-02, -3.81011082e-02, -3.72437296e-02,
                -3.71581789e-02, -3.73825700e-02, -3.68815449e-02 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }
}
