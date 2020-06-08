package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.filter.Median;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMedian {

    @Test
    public void medianTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wSize = 5;
        double[] result = {2.0, 2.0, 3.0, 4.0, 2.0, 4.0, 5.0, 4.0, 0.0};

        Median mf = new Median(signal, wSize);
        double[] out = mf.median_filter();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void medianTest2() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {3.0, 3.0, 2.0, 2.0, 5.0, 5.0, 4.0, 4.0, 4.0};

        Median mf = new Median(signal);
        double[] out = mf.median_filter();
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
