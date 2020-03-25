package com.onyx.signal;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSavgol {
    private double[] signal = {0.425, 0.445, 0.445, 0.405, 0.4, 0.43, 0.44, 0.43, 0.435, 0.455, 0.465, 0.455, 0.44,
            0.435, 0.43, 0.405, 0.375, 0.345, 0.32, 0.33, 0.335, 0.325, 0.29, 0.265, 0.22, 0.2, 0.175, 0.115, 0.005,
            -0.07, -0.01, 0.11, 0.18, 0.23, 0.24, 0.245, 0.245, 0.245, 0.235, 0.23, 0.21, 0.21, 0.235, 0.26, 0.265,
            0.255, 0.25, 0.26, 0.3,0.31};

    @Test
    public void savgolCoeffTest() {
        double[] coeffs = {-0.086, 0.343, 0.486, 0.343, -0.086};

        Savgol s1 = new Savgol(this.signal, 5, 2);
        double[] out = s1.savgol_coeffs();
        assertArrayEquals(out, coeffs, 0.001);
    }

    @Test
    public void savgolFilterTest() {
        final double[] result = {0.44, 0.429, 0.422, 0.419, 0.415, 0.419, 0.43, 0.437, 0.443, 0.451, 0.458, 0.455,
                0.447, 0.436, 0.424, 0.404, 0.374, 0.346, 0.331, 0.33, 0.328, 0.32, 0.294, 0.26, 0.231, 0.206, 0.162,
                0.088, 0.009, -0.026, 0.002, 0.085, 0.173, 0.225, 0.243, 0.249, 0.246, 0.244, 0.236, 0.223, 0.215, 0.22,
                0.234, 0.252, 0.26, 0.255, 0.257, 0.269, 0.288, 0.314};

//        Savgol s1 = new Savgol(this.signal, 7, 2);
//        double[] out = s1.savgol_filter();
//        assertArrayEquals(out, result, 0.001);
    }
}
