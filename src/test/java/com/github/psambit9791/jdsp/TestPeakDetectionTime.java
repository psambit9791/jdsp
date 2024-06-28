/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.io.WAV;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.CrossCorrelation;
import com.github.psambit9791.jdsp.signal.peaks.FindPeak;
import com.github.psambit9791.jdsp.signal.peaks.Peak;
import com.github.psambit9791.wavfile.WavFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;

public class TestPeakDetectionTime {

    @Test
    public void executionTime() throws WavFileException, IOException {

        String root = "test_inputs/peak_det_exec_time/";
        WAV s1 = new WAV();
        s1.readWAV(root + "signal.wav");
        double[] signal = UtilMethods.flattenMatrix(s1.getData("int"));

        WAV s2 = new WAV();
        s2.readWAV(root + "reference.wav");
        double[] reference = UtilMethods.flattenMatrix(s2.getData("int"));

        CrossCorrelation cc1 = new CrossCorrelation(signal, reference);
        double[] zout = cc1.crossCorrelate("valid");

        long start = System.currentTimeMillis();
        FindPeak fp = new FindPeak(zout);
        Peak out = fp.detectPeaks();
        int[] peaks = out.getPeaks();
        long exec_time = (long) (System.currentTimeMillis() - start);
        Assertions.assertTrue(exec_time < 10000);
    }
}