package com.github.psambit9791.jdsp;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class TestLinePlot {

    @Test
    public void testPlotSave1() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "temp1";

        LinePlot fig = new LinePlot("Sample Figure", "Signal", "Time");
        fig.initialise_graph();
        fig.add_signal("Signal 1", time, signal1);
        fig.add_signal("Signal 2", time, signal2);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }

    @Test
    public void testPlotSave2() throws IOException {
        final double[] time = {0.0, 1.0, 2.0, 3.0, 4.0};
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "temp2";

        LinePlot fig = new LinePlot();
        fig.initialise_graph();
        fig.add_signal("Signal 1", signal1);
        fig.add_signal("Signal 2", signal2);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }

    @Test
    public void testPlotSave3() throws IOException {
        final double[] signal1 = {2.0, 4.0, 2.0, 3.0, 1.0};
        final double[] signal2 = {3.4, 6.7, 2.2, 1.6, 3.6};

        String outputFileName = "temp3";

        LinePlot fig = new LinePlot(600, 500, "Sample Figure", "Signal", "Time");
        fig.initialise_graph();
        fig.add_signal("Signal 1", signal1);
        fig.add_signal("Signal 2", signal2);
        fig.save_as_png(outputFileName);
        boolean fileExists = new File("./"+outputFileName+".png").exists();
        assertTrue(fileExists);
    }
}
