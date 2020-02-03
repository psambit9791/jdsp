package com.onyx.signal;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TestPeakDetect {

    private double[] signal = {0.228, 0.423, 0.304, 0.16, 0.241, 0.265, 0.168, 0.038, -0.017, 0.54, 0.234, -0.069,
            -0.094, -0.086, -0.04, 0.007, 0.056, 0.129, 0.112, 0.003, -0.008, 0.051, 0.013, -0.129, -0.221, 0.214,
            0.345, -0.22, -0.289, -0.293};

    @Test
    public void peakTest() {
        int[] result = {1, 5, 9, 17, 21, 26};

        FindPeak fp = new FindPeak(this.signal);
        int[] out = fp.detect_peaks();

        assertArrayEquals(out, result);;
    }

    @Test
    public void troughTest() {
        int[] result = {3, 8, 12, 20, 24};

        FindPeak fp = new FindPeak(this.signal);
        int[] out = fp.detect_troughs();

        assertArrayEquals(out, result);
    }
}
