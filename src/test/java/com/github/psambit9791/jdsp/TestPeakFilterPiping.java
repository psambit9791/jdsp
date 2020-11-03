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
import com.github.psambit9791.jdsp.signal.peaks.FindPeak;
import com.github.psambit9791.jdsp.signal.peaks.Peak;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;

public class TestPeakFilterPiping {

    private double[] ecg;

    public TestPeakFilterPiping() throws IOException {
        this.ecg = UtilMethods.electrocardiogram();
    }

    @Test
    public void PeakPipelineTest1() {
        double[] ecgPart = UtilMethods.splitByIndex(this.ecg, 17000, 18000);
        FindPeak fp = new FindPeak(ecgPart);
        Peak out = fp.detectPeaks();

        int[] peaks = out.getPeaks();
        int[] results = {49, 691};
        int[] filteredPeaks = out.filterByProminence(peaks, 1.0, null);
        filteredPeaks = out.filterByWidth(filteredPeaks, 20.0, null);

        Assertions.assertArrayEquals(results, filteredPeaks);
    }

    @Test
    public void PeakPipelineTest2() {
        double[] ecgPart = UtilMethods.splitByIndex(this.ecg, 2000, 6000);
        FindPeak fp = new FindPeak(ecgPart);
        Peak out = fp.detectPeaks();

        int[] peaks = out.getPeaks();
        int[] results = {956, 3856};
        int[] filteredPeaks = out.filterByHeight(peaks, 0.5, null);
        filteredPeaks = out.filterByPeakDistance(filteredPeaks, 150);
        filteredPeaks = out.filterByWidth(filteredPeaks, 20.0 ,null);

        Assertions.assertArrayEquals(results, filteredPeaks);
    }
}
