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

        String outputFileName = "test_outputs/temp1";

        Plotting fig = new Plotting("Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", time1, signal1, true);
        fig.add_signal("Signal 2", time2, signal2, true);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave2() throws IOException {
        final double[] time = {-2.0, -1.0, 4.0, 5.0, 10.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp2";

        Plotting fig = new Plotting("Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", time, signal1, true);
        fig.add_signal("Signal 2", time, signal2, true);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave3() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp3";

        Plotting fig = new Plotting();
        fig.initialise_plot();
        fig.add_signal("Signal 1", signal1, false);
        fig.add_signal("Signal 2", signal2, false);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave4() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp4";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", signal1, true);
        fig.add_signal("Signal 2", signal2, true);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testPlotSave5() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};
        final double[] points1 = {3.4, 6.7, 2.2, 1.6, 3.6};
        final double[] points2 = {1.6, 2.0, 5.3, -1.3, 2.2};

        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};

        String outputFileName = "test_outputs/temp5";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", time, signal1, true);
        fig.add_signal("Signal 2", time, signal2, false);
        fig.add_points("Points 1", time, points1, 'x');
        fig.add_points("Points 2", time, points2);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        Assertions.assertTrue(fileExists);
    }
}
