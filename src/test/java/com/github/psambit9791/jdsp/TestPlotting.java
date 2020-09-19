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

import com.github.psambit9791.jdsp.misc.Plotting;
import org.apache.commons.math3.stat.StatUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPlotting {

    @Test
    @Order(1)
    public void createTestOutputDirectory() {
        String dirName = "./test_outputs/";
        File directory = new File(dirName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    @Test
    public void testPlotSave1() throws IOException {
        final double[] time1 = {-2.0, -1.0, 4.0, 5.0, 10.0};
        final double[] time2 = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp1.png";

        Plotting fig = new Plotting("Sample Figure", "Time", "Signal");
        fig.initialisePlot();
        fig.addSignal("Signal 1", time1, signal1, true);
        fig.addSignal("Signal 2", time2, signal2, true);
        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave2() throws IOException {
        final double[] time = {-2.0, -1.0, 4.0, 5.0, 10.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp2.png";

        Plotting fig = new Plotting("Sample Figure", "Time", "Signal");
        fig.initialisePlot();
        fig.addSignal("Signal 1", time, signal1, true);
        fig.addSignal("Signal 2", time, signal2, true);
        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave3() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp3.png";

        Plotting fig = new Plotting();
        fig.initialisePlot();
        fig.addSignal("Signal 1", signal1, false);
        fig.addSignal("Signal 2", signal2, false);
        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave4() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp4.png";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialisePlot();
        fig.addSignal("Signal 1", signal1, true);
        fig.addSignal("Signal 2", signal2, true);
        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave5() throws IOException {
        double[] plotHor1 = {2, 6, 4};
        double[] plotHor2 = {-4, 4, -9};
        double[] plotVer1 = {3, 1, 6};
        double[] plotVer2 = {6, 9, -1};

        String outputFileName = "test_outputs/temp5.png";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialisePlot();
        fig.hline(plotHor1[0], plotHor1[1], plotHor1[2]);
        fig.vline(plotVer1[0], plotVer1[1], plotVer1[2]);
        fig.hline(plotHor2[0], plotHor2[1], plotHor2[2]);
        fig.vline(plotVer2[0], plotVer2[1], plotVer2[2]);

        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave6() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};
        final double[] points1 = {3.4, 6.7, 2.2, 1.6, 3.6};
        final double[] points2 = {1.6, 2.0, 5.3, -1.3, 2.2};
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] timeInt = {0, 1, 2, 3, 4};

        double[][] verLines = new double[signal1.length][3];
        for (int i=0; i<verLines.length; i++) {
            verLines[i][0] = time[i];
            verLines[i][1] = signal1[i];
            verLines[i][2] = signal2[i];
        }

        String outputFileName = "test_outputs/temp6.png";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialisePlot();
        fig.addSignal("Signal 1", time, signal1, true);
        fig.addSignal("Signal 2", time, signal2, false);
        fig.addPoints("Points 1", time, points1, 'x');
        fig.addPoints("Points 2", timeInt, points2);
        for (int i=0; i<verLines.length; i++) {
            fig.vline(verLines[i][0], verLines[i][1], verLines[i][2]);
        }
        fig.hline(time[0], time[time.length-1], StatUtils.max(signal1));
        fig.hline(time[0], time[time.length-1], StatUtils.max(signal2));
        fig.saveAsPNG(outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }
}
