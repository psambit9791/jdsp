package com.onyx.signal;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestCrossCorrelation {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel = {0.5, 1.0, 0.0, 1.0};

    @Test
    public void fullCrossCorrelationTest() {
        final double[] result = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal, this.kernel, "full");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void sameCrossCorrelationTest() {
        final double[] result = {2.0, 4.0, 6.5, 9.0, 5.5};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal, this.kernel, "same");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void validCrossCorrelationTest() {
        final double[] result = {6.5, 9.0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal, this.kernel, "valid");
        cc1.crossCorrelate();
        double[] out = cc1.getOutput();
        assertArrayEquals(out, result, 0.001);
    }
}
