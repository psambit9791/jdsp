package com.onyx.signal;

import org.junit.Test;
import static org.junit.Assert.*;


public class TestDetrend {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};

    @Test
    public void linearDetrendTest() {
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(this.signal, "linear");
        d1.detrendSignal();
        double[] out = d1.getOutput();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void constantDetrendTest() {
        final double[] result = {-2.0, -1.0, 0.0, 1.0, 2.0};

        Detrend d1 = new Detrend(this.signal, "constant");
        d1.detrendSignal();
        double[] out = d1.getOutput();
        assertArrayEquals(out, result, 0.001);
    }
}
