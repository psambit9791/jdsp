package com.onyx.jdsp;

import com.onyx.jdsp.signal.CrossCorrelation;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCrossCorrelation {

    final double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel1 = {0.5, 1.0, 0.0, 1.0};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};

    @Test
    public void fullCrossCorrelationTest() {
        final double[] result1 = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        final double[] result2 = {6, 26, 14, 38, 15, 40, 43, 37, 36,  9,  0};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.cross_correlate("full");
        assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.cross_correlate("full");
        assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void sameCrossCorrelationTest() {
        final double[] result1 = {2.0, 4.0, 6.5, 9.0, 5.5};
        final double[] result2 = {26, 14, 38, 15, 40, 43, 37, 36};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.cross_correlate("same");
        assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.cross_correlate("same");
        assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void validCrossCorrelationTest() {
        final double[] result1 = {6.5, 9.0};
        final double[] result2 = {38, 15, 40, 43, 37};

        CrossCorrelation cc1 = new CrossCorrelation(this.signal1, this.kernel1);
        double[] out = cc1.cross_correlate("valid");
        assertArrayEquals(result1, out, 0.001);

        CrossCorrelation cc2 = new CrossCorrelation(this.signal2, this.kernel2);
        out = cc2.cross_correlate();
        assertArrayEquals(result2, out, 0.001);
    }
}
