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
import org.apache.commons.math3.ode.events.FilterType;
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
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.LOWPASS, true);
        double[] resCoeffs = {0.00268693, 0.99462614, 0.00268693};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);

    }

    @Test
    public void lowPassTest2() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.LOWPASS, true);
        double[] resCoeffs = {0.5, 0.5};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);

    }

    @Test
    public void lowPassFilter1() {
        double width = 5;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.LOWPASS, true);
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
    public void lowPassFilter2() {
        double width = 5;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.LOWPASS, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {0.1901608 , 0.473582  , 0.76881702, 1.00790566, 1.16770492,
                1.12162921, 1.04850568, 1.03978354, 1.119971  , 1.21912949,
                1.24980136, 1.19397311, 1.10581742, 1.05005066, 1.05320138,
                1.09501044, 1.12248736, 1.08383095, 0.97686872, 0.86340098,
                0.81251667, 0.82668991, 0.8420768 , 0.80245594, 0.7123073 ,
                0.61530519, 0.54669431, 0.51740714, 0.51924806, 0.52436654,
                0.49637518, 0.42671523, 0.35323531, 0.3226435 , 0.3389943 ,
                0.36615496, 0.37373827, 0.35922214, 0.33083775, 0.29755868,
                0.27990049, 0.30106783, 0.35180822, 0.38506415, 0.36486717,
                0.30853642, 0.26497258, 0.26206498, 0.28912136, 0.31655845};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void highPassTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.HIGHPASS, true);
        double[] resCoeffs = {-2.99980692e-04,  9.99400039e-01, -2.99980692e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void highPassTest2() {
        double width = 1;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.HIGHPASS, true);
        double[] resCoeffs = {-0.03832441, -0.08195481,  0.9127392,  -0.08195481, -0.03832441};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void highPassFilter1() {
        double width = 2;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.HIGHPASS, true);
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
    public void highPassFilter2() {
        double width = 5;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.HIGHPASS, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-1.91352519e-01, -4.76549886e-01,  4.90541417e-01,  5.71511829e-01,
                3.05582889e-01,  8.38546192e-02,  2.84155234e-02,  5.29834669e-02,
                7.36393412e-02,  1.97311242e-01,  3.98919512e-01,  3.90635305e-01,
                7.82131995e-02, -1.66617821e-01, -6.62683292e-03,  3.36819813e-01,
                4.11125686e-01,  1.91826505e-01,  2.68725835e-02,  5.06189650e-02,
                7.98902819e-02,  5.30336746e-02,  1.28188376e-01,  2.84428526e-01,
                2.51329715e-01, -1.46185744e-02, -1.66383532e-01,  8.19596259e-03,
                2.35805882e-01,  1.97081192e-01,  1.72375604e-02, -5.90178161e-03,
                8.40827630e-02,  4.58221347e-02, -7.17205403e-02, -1.28376757e-02,
                1.84533730e-01,  2.06389149e-01,  5.79830273e-05, -1.21800107e-01,
                -4.30645242e-03,  1.18245094e-01,  6.71302439e-02,  1.64619208e-02,
                1.17909085e-01,  1.74351807e-01, -1.64901262e-03, -1.84223800e-01,
                -6.21207032e-02,  2.23438396e-01 };

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void bandPassTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1, 0.2};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.BANDPASS, true);
        double[] resCoeffs = {0.00242647, 0.99567599, 0.00242647};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandPassTest2() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1, 0.2};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.BANDPASS, true);
        double[] resCoeffs = {0.5142076, 0.5142076};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandPassFilter1() {
        double width = 5;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.BANDPASS, true);
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
    public void bandPassFilter2() {
        double width = 5;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.BANDPASS, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {0.20772608, 0.52470795, 0.85913907, 1.12963528, 1.3036719 ,
                1.24984228, 1.16746578, 1.1579019 , 1.24769863, 1.35974143,
                1.39602243, 1.33354023, 1.23191993, 1.16722067, 1.17245299,
                1.22273594, 1.25430461, 1.2090291 , 1.08798472, 0.9615976 ,
                0.90523724, 0.92102071, 0.93915518, 0.89660573, 0.79570275,
                0.6848389 , 0.60686002, 0.57607144, 0.58055735, 0.58600703,
                0.55303645, 0.47508014, 0.39399288, 0.35952999, 0.37668792,
                0.40764934, 0.41810815, 0.40213425, 0.36839638, 0.33001944,
                0.31145565, 0.33630347, 0.39246977, 0.42913414, 0.40761446,
                0.34526862, 0.29484904, 0.28978685, 0.32127469, 0.35486742};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void bandStopTest1() {
        double width = 5;
        FIRWin1 fw = new FIRWin1(this.taps, width);

        double[] f = {0.1, 0.2};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.BANDSTOP, true);
        double[] resCoeffs = {-2.70925670e-04,  1.00054185e+00, -2.70925670e-04};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandStopTest2() {
        double width = 1;
        FIRWin1 fw = new FIRWin1(this.ripple, width);

        double[] f = {0.1, 0.2};
        double[] outCoeffs = fw.computeCoefficients(f, FIRWin1.FIRfilterType.BANDSTOP, true);
        double[] resCoeffs = {-0.03301143, -0.10304144,  1.27210575, -0.10304144, -0.03301143};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void bandStopFilter1() {
        double width = 2;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.BANDSTOP, true);
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
    public void bandStopFilter2() {
        double width = 2;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {1.0, 10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.BANDSTOP, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-0.64941196, -1.64038918,  2.2556598 ,  2.66695555,  1.7119277 ,
                0.83225897,  0.58545822,  0.67708416,  0.7925074 ,  1.31568659,
                2.11097988,  2.0543386 ,  0.80404299, -0.17007014,  0.45135882,
                1.80112154,  2.10096363,  1.23323067,  0.54612905,  0.58775772,
                0.67822637,  0.57965147,  0.87725554,  1.46516348,  1.29664144,
                0.22211003, -0.39721985,  0.26625428,  1.14918237,  1.00090616,
                0.2908836 ,  0.17014525,  0.48636292,  0.32421515, -0.12457691,
                0.11563217,  0.8842743 ,  0.96254652,  0.14983885, -0.33741924,
                0.11043382,  0.59505793,  0.41924506,  0.23737243,  0.62175569,
                0.81572467,  0.1140208 , -0.59526222, -0.10998559,  1.00914615};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);

    }

    @Test
    public void multiBandPassTest1() {
        double width = 5;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.MULTIBANDPASS, true);
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
    public void multiBandPassTest2() {
        double width = 5;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.MULTIBANDPASS, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {0.17567404, 0.46569011, 0.78458339, 1.04121531, 1.18679129,
                1.13073856, 1.05358204, 1.04539423, 1.12793204, 1.23386937,
                1.27291764, 1.21559438, 1.11363864, 1.04753493, 1.05736223,
                1.11378453, 1.1451507 , 1.09767336, 0.98268149, 0.86850283,
                0.8185216 , 0.83275863, 0.85208192, 0.81832801, 0.72571801,
                0.61721187, 0.54214885, 0.51976019, 0.53102301, 0.53519461,
                0.5000944 , 0.42858626, 0.35758064, 0.32529424, 0.3376652 ,
                0.36773193, 0.38314252, 0.36928929, 0.33252243, 0.29397486,
                0.28048059, 0.30669318, 0.35639011, 0.38838956, 0.37184366,
                0.31670405, 0.26553906, 0.25556108, 0.28800724, 0.3273342};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }

    @Test
    public void multiBandStopTest() {
        double width = 2;
        double samplingRate = 100;
        double rippleVal = 60.0;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(rippleVal, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.MULTIBANDSTOP, true);
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

    @Test
    public void multiBandStopTest2() {
        double width = 5;
        double samplingRate = 100;
        int taps = 5;
        double[] cutoff = {1.0, 2.5, 7.5, 10.0};

        FIRWin1 fw = new FIRWin1(taps, width, samplingRate);
        double[] outCoeffs = fw.computeCoefficients(cutoff, FIRWin1.FIRfilterType.MULTIBANDSTOP, true);
        double[] filteredX = fw.firfilter(outCoeffs, this.signal);

        double[] resFiltered = {-0.0850478 , -0.22545117,  1.26073302,  1.55378835,  1.34688298,
                1.02610573,  0.89602389,  0.92048054,  1.0120423 ,  1.25073712,
                1.53352077,  1.47761717,  1.00641499,  0.64786714,  0.85485006,
                1.32783504,  1.44496065,  1.1328728 ,  0.83479764,  0.77271667,
                0.76845193,  0.74521505,  0.85348445,  1.02084433,  0.9050031 ,
                0.48573061,  0.23552263,  0.43467442,  0.72700346,  0.68141352,
                0.42846369,  0.34208687,  0.39728336,  0.32331285,  0.18613641,
                0.28346311,  0.54204132,  0.55815439,  0.27112433,  0.08818833,
                0.2241377 ,  0.39812894,  0.37399624,  0.33617835,  0.44946564,
                0.47584717,  0.21532599, -0.02057429,  0.15750748,  0.54487804};

        Assertions.assertArrayEquals(resFiltered, filteredX, 0.0001);
    }
}
