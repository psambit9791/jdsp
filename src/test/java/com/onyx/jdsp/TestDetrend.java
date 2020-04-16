package com.onyx.jdsp;

import com.onyx.jdsp.signal.Detrend;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestDetrend {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] polySignal = {1.0, 4.0, 9.0, 16.0, 25.0};

    @Test
    public void linearDetrendTest() {
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(this.signal, "linear");
        double[] out = d1.detrendSignal();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void constantDetrendTest() {
        final double[] result = {-2.0, -1.0, 0.0, 1.0, 2.0};

        Detrend d1 = new Detrend(this.signal, "constant");
        double[] out = d1.detrendSignal();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void polyDetrendTest() {
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(this.polySignal, 2);
        double[] out = d1.detrendSignal();
        assertArrayEquals(out, result, 0.001);
    }
}
