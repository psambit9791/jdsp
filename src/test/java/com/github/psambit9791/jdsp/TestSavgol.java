package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.filter.Savgol;
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
    public void savgolFilterNearestTest() {
        final double[] result = {0.435,  0.437,  0.428,  0.419,  0.415,  0.419,  0.43 ,  0.437, 0.443,  0.451,  0.458,
                0.455,  0.447,  0.436,  0.424,  0.404, 0.374,  0.346,  0.331,  0.33 ,  0.328,  0.32 ,  0.294,  0.26 ,
                0.231,  0.206,  0.162,  0.088,  0.009, -0.026,  0.002,  0.085, 0.173,  0.225,  0.243,  0.249,  0.246,
                0.244,  0.236,  0.223, 0.215,  0.22 ,  0.234,  0.252,  0.26 ,  0.255,  0.257,  0.27 , 0.289,  0.306};

        Savgol s1 = new Savgol(this.signal, 7, 2);
        double[] out = s1.savgol_filter("nearest");
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void savgolFilterConstantTest() {
        final double[] result = {0.294,  0.417,  0.468,  0.419,  0.415,  0.419,  0.43 ,  0.437, 0.443,  0.451,  0.458,
                0.455,  0.447,  0.436,  0.424,  0.404, 0.374,  0.346,  0.331,  0.33 ,  0.328,  0.32 ,  0.294,  0.26 ,
                0.231,  0.206,  0.162,  0.088,  0.009, -0.026,  0.002,  0.085, 0.173,  0.225,  0.243,  0.249,  0.246,
                0.244,  0.236,  0.223, 0.215,  0.22 ,  0.234,  0.252,  0.26 ,  0.255,  0.257,  0.299, 0.274,  0.202};

        Savgol s1 = new Savgol(this.signal, 7, 2);
        double[] out = s1.savgol_filter("constant");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void savgolFilterMirrorTest() {
        final double[] result = {0.446,  0.438,  0.426,  0.419,  0.415,  0.419,  0.43 ,  0.437, 0.443,  0.451,  0.458,
                0.455,  0.447,  0.436,  0.424,  0.404, 0.374,  0.346,  0.331,  0.33 ,  0.328,  0.32 ,  0.294,  0.26 ,
                0.231,  0.206,  0.162,  0.088,  0.009, -0.026,  0.002,  0.085, 0.173,  0.225,  0.243,  0.249,  0.246,
                0.244,  0.236,  0.223, 0.215,  0.22 ,  0.234,  0.252,  0.26 ,  0.255,  0.257,  0.271, 0.292,  0.301};

        Savgol s1 = new Savgol(this.signal, 7, 2);
        double[] out = s1.savgol_filter("mirror");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void savgolFilterWrapTest() {
        final double[] result = {0.4  ,  0.432,  0.439,  0.419,  0.415,  0.419,  0.43 ,  0.437, 0.443,  0.451,  0.458,
                0.455,  0.447,  0.436,  0.424,  0.404, 0.374,  0.346,  0.331,  0.33 ,  0.328,  0.32 ,  0.294,  0.26 ,
                0.231,  0.206,  0.162,  0.088,  0.009, -0.026,  0.002,  0.085, 0.173,  0.225,  0.243,  0.249,  0.246,
                0.244,  0.236,  0.223, 0.215,  0.22 ,  0.234,  0.252,  0.26 ,  0.255,  0.257,  0.259, 0.293,  0.345};

        Savgol s1 = new Savgol(this.signal, 7, 2);
        double[] out = s1.savgol_filter("wrap");
        assertArrayEquals(result, out, 0.001);
    }
}
