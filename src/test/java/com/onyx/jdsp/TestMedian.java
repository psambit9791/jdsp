package com.onyx.jdsp;

import com.onyx.jdsp.filter.Median;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMedian {

    @Test
    public void medianTest() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wSize = 5;
        double[] result = {2.0, 2.0, 3.0, 4.0, 2.0, 4.0, 5.0, 4.0, 0.0};

        Median mf = new Median(signal, wSize);
        double[] out = mf.median_filter();
        assertArrayEquals(result, out, 0.001);
    }
}
