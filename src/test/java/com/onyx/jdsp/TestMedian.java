package com.onyx.jdsp;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestMedian {

    @Test
    public void medianTest() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wSize = 5;
        double[] result = {2.0, 2.0, 3.0, 4.0, 2.0, 4.0, 5.0, 4.0, 0.0};

        MedianFilter mf = new MedianFilter(signal, wSize);
        double[] out = mf.median_filter();
        assertArrayEquals(out, result, 0.001);
    }
}
