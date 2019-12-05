package com.onyx.signal;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;


public class TestDetrend {

    @Test
    public void linearDetrendTest() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Detrend d1 = new Detrend(original, "linear");
        d1.detrendSignal();
        double[] out = d1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }

    @Test
    public void constantDetrendTest() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        final double[] result = {-2.0, -1.0, 0.0, 1.0, 2.0};

        Detrend d1 = new Detrend(original, "constant");
        d1.detrendSignal();
        double[] out = d1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }
}
