package com.onyx.signal;

import org.junit.Test;

public class TestSmooth {

    @Test
    public void rectSmoothTest() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        final double[] result = {0.0, 0.0, 0.0, 0.0, 0.0};

        Smooth s1 = new Smooth(original, 4, "rectangular");
        s1.smoothSignal();
    }
}
