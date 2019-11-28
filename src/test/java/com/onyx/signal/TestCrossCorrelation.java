package com.onyx.signal;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;

public class TestCrossCorrelation {

    @Test
    public void fullCrossCorrelationTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {0.5, 1.0, 0.0, 1.0};
        final double[] result = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};

        CrossCorrelation cc1 = new CrossCorrelation(signal, kernel, "full");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }

    @Test
    public void sameCrossCorrelationTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {0.5, 1.0, 0.0, 1.0};
        final double[] result = {2.0, 4.0, 6.5, 9.0, 5.5};

        CrossCorrelation cc1 = new CrossCorrelation(signal, kernel, "same");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }

    @Test
    public void validCrossCorrelationTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {0.5, 1.0, 0.0, 1.0};
        final double[] result = {6.5, 9.0};

        CrossCorrelation cc1 = new CrossCorrelation(signal, kernel, "valid");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }
}
