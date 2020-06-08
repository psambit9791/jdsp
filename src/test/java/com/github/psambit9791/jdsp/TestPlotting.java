package com.github.psambit9791.jdsp;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class TestPlotting {

    @Test
    public void testPlotSave1() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp1";

        Plotting fig = new Plotting("Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", time, signal1, true);
        fig.add_signal("Signal 2", time, signal2, true);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }

    @Test
    public void testPlotSave2() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp2";

        Plotting fig = new Plotting();
        fig.initialise_plot();
        fig.add_signal("Signal 1", signal1, false);
        fig.add_signal("Signal 2", signal2, false);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }

    @Test
    public void testPlotSave3() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "test_outputs/temp3";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal 1", signal1, true);
        fig.add_signal("Signal 2", signal2, true);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }

    @Test
    public void testPlotSave4() throws IOException {
        final double[] signal = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] points1 = {3.4, 6.7, 2.2, 1.6, 3.6};
        final double[] points2 = {1.6, 2.0, 5.3, -1.3, 2.2};

        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};

        String outputFileName = "test_outputs/temp4";

        Plotting fig = new Plotting(600, 500, "Sample Figure", "Time", "Signal");
        fig.initialise_plot();
        fig.add_signal("Signal", time, signal, true);
        fig.add_points("Points 1", time, points1, 'x');
        fig.add_points("Points 2", time, points2);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }
}
