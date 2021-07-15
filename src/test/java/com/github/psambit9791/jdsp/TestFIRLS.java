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

import com.github.psambit9791.jdsp.filter.FIRLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFIRLS {

    private final int samplingRate = 10;
    private final double[] signal = {1.10168563, 1.38191166, 1.29029608, 1.05666263, 0.94422545, 0.95798688, 1.04630625,
            1.24103669, 1.44362822, 1.38745155, 1.03788056, 0.77561554, 0.91780505, 1.25377026, 1.34262046, 1.11761007,
            0.88006049, 0.80125178, 0.78213891, 0.7711632 , 0.85015107, 0.95156431, 0.84366613, 0.52683779, 0.33441325,
            0.46087048, 0.66083891, 0.63158023, 0.45030643, 0.36905465, 0.38303655, 0.32286699, 0.23477111, 0.30990337,
            0.48855565, 0.49487231, 0.29017088, 0.15479253, 0.24169921, 0.36706079, 0.36701158, 0.35201891, 0.4227153 ,
            0.42250524, 0.23092439, 0.0692671 , 0.19940213, 0.47231721, 0.51117719, 0.26229594};

    @Test
    public void LSCoeffTest() {
        double[] freqs = {0.0, 1.0, 2.0, 4.0, 4.5, 5.0};
        double[] gains = {0.0, 0.0, 1.0, 1.0, 0.0, 0.0};
        int taps = 7;
        FIRLS fwls = new FIRLS(taps, this.samplingRate);
        double[] outCoeffs = fwls.computeCoefficients(freqs, gains);
        double[] resCoeffs = {0.0919431, -0.25844535, -0.10932055,  0.51617629, -0.10932055, -0.25844535, 0.0919431};
        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);
    }

    @Test
    public void LSFilterTest() {
        double[] freqs = {0.0, 1.0, 2.0, 4.0, 4.5, 5.0};
        double[] gains = {0.0, 0.0, 1.0, 1.0, 0.0, 0.0};
        int taps = 7;
        FIRLS fwls = new FIRLS(taps, this.samplingRate);
        double[] coefficients = fwls.computeCoefficients(freqs, gains);
        double[] out = fwls.firfilter(coefficients, this.signal);
        double[] filteredX = {0.10129239, -0.1576683 , -0.35895171,  0.18127447,  0.26554274,
                -0.04124198, -0.10609781, -0.09557783, -0.06558016, -0.09273025,
                -0.055534  ,  0.07856325,  0.10428176, -0.08165953, -0.23987745,
                -0.13301068,  0.08710159,  0.11412031, -0.04091367, -0.11846021,
                -0.05465986, -0.02509455, -0.07490909, -0.04757479,  0.07320284,
                0.08099257, -0.08295922, -0.17863375, -0.05132701,  0.10124603,
                0.05600998, -0.07467797, -0.06493154,  0.03158094,  0.00996555,
                -0.09381836, -0.06855386,  0.072451  ,  0.0940202 , -0.04773118,
                -0.1223432 , -0.02426985,  0.05748538, -0.00780381, -0.06523476,
                0.01910777,  0.08982935, -0.01955447, -0.15668723, -0.08510961};
        Assertions.assertArrayEquals(filteredX, out, 0.0001);
    }
}
