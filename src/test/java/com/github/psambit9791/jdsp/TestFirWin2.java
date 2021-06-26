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
import com.github.psambit9791.jdsp.filter.FIRWin1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFirWin2 {

    private final int taps = 10;
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
        double[] gains = {1.0, 1.0, 0.0};
        FIRWin2 fw2 = new FIRWin2(this.taps, this.samplingRate);

        double[] outCoeffs = fw2.compute_coefficients(freqs, gains);
        double[] resCoeffs = {0.00060439,  0.00228306, -0.01076332, -0.04939297,  0.55770673, 0.55770673, -0.04939297,
                -0.01076332,  0.00228306,  0.00060439};

        Assertions.assertArrayEquals(resCoeffs, outCoeffs, 0.0001);

    }
}