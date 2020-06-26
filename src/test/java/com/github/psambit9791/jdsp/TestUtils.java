package com.github.psambit9791.jdsp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestUtils {

    @Test
    public void linspaceSingleTest() {
        double[] result1 = {2.0, 2.25, 2.50, 2.75, 3.0};
        double[] out1 = UtilMethods.linspace(2, 3, 5, true);
        Assertions.assertArrayEquals(result1, out1, 0.001);

        double[] result2 = {2.0, 2.2, 2.4, 2.6, 2.8};
        double[] out2 = UtilMethods.linspace(2, 3, 5, false);
        Assertions.assertArrayEquals(result2, out2, 0.001);
    }

    @Test
    public void linspaceRepeatTest() {
        double[] result = {2.0, 2.25, 2.50, 2.75, 3.0, 2.0, 2.25, 2.50, 2.75, 3.0};
        double[] out = UtilMethods.linspace(2, 3, 5, 2);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void arangeDoubleTest() {
        double[] result1 = {3.0, 5.0, 7.0};
        double[] out1 = UtilMethods.arange(3.0, 9.0, 2.0);
        Assertions.assertArrayEquals(result1, out1, 0.001);

        double[] result2 = {3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5};
        double[] out2 = UtilMethods.arange(3.0, 9.0, 0.5);
        Assertions.assertArrayEquals(result2, out2, 0.001);
    }

    @Test
    public void arangeIntTest() {
        int[] result = {3, 5, 7};
        int[] out = UtilMethods.arange(3, 9, 2);
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void reverseDoubleTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] result = {6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        double[] out = UtilMethods.reverse(signal);
        Assertions.assertArrayEquals(result, out, 0.0);
    }

    @Test
    public void reverseIntTest() {
        int[] signal = {1, 2, 3, 4, 5, 6};
        int[] result = {6, 5, 4, 3, 2, 1};
        int[] out = UtilMethods.reverse(signal);
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void concatenateDoubleTest() {
        double[] arr1 = {1.0, 2.0};
        double[] arr2 = {3.0, 4.0, 5.0, 6.0};
        double[] result = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] out = UtilMethods.concatenateArray(arr1, arr2);
        Assertions.assertArrayEquals(result, out, 0.0);
    }

    @Test
    public void concatenateIntTest() {
        int[] arr1 = {1, 2};
        int[] arr2 = {3, 4, 5, 6};
        int[] result = {1, 2, 3, 4, 5, 6};
        int[] out = UtilMethods.concatenateArray(arr1, arr2);
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void splitByIndexDoubleTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] result = {3.0, 4.0};
        double[] out = UtilMethods.splitByIndex(signal, 2, 4);
        Assertions.assertArrayEquals(result, out, 0.0);
    }

    @Test
    public void splitByIndexIntTest() {
        int[] signal = {1, 2, 3, 4, 5, 6};
        int[] result = {3, 4};
        int[] out = UtilMethods.splitByIndex(signal, 2, 4);
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void pseudoInverseTest() {
        double[][] matrix = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] result = {{-0.639, -0.167, 0.306}, {-0.056, 0.000, 0.056}, {0.528, 0.167, -0.194}};
        double[][] out = UtilMethods.pseudoInverse(matrix);
        Assertions.assertArrayEquals(result[0], out[0], 0.001);
        Assertions.assertArrayEquals(result[1], out[1], 0.001);
        Assertions.assertArrayEquals(result[1], out[1], 0.001);
    }

    @Test
    public void padSignalTest() {
        double[] signal = {2, 8, 0, 4, 1, 9, 9, 0};
        double[] reflect = {0, 9, 9, 1, 4, 0, 8, 2, 2, 8, 0, 4, 1, 9, 9, 0, 0, 9, 9, 1, 4, 0, 8, 2};
        double[] constant = {0, 0, 0, 0, 0, 0, 0, 0, 2, 8, 0, 4, 1, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0.};
        double[] nearest = {2, 2, 2, 2, 2, 2, 2, 2, 2, 8, 0, 4, 1, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0.};
        double[] mirror = {9, 0, 9, 9, 1, 4, 0, 8, 2, 8, 0, 4, 1, 9, 9, 0, 9, 9, 1, 4, 0, 8, 2, 8};
        double[] wrap = {2, 8, 0, 4, 1, 9, 9, 0, 2, 8, 0, 4, 1, 9, 9, 0, 2, 8, 0, 4, 1, 9, 9, 0};

        double[] out = UtilMethods.padSignal(signal, "reflect");
        Assertions.assertArrayEquals(reflect, out, 0.001);

        out = UtilMethods.padSignal(signal, "constant");
        Assertions.assertArrayEquals(constant, out, 0.001);

        out = UtilMethods.padSignal(signal, "nearest");
        Assertions.assertArrayEquals(nearest, out, 0.001);

        out = UtilMethods.padSignal(signal, "mirror");
        Assertions.assertArrayEquals(mirror, out, 0.001);

        out = UtilMethods.padSignal(signal, "wrap");
        Assertions.assertArrayEquals(wrap, out, 0.001);

    }

    @Test
    public void diffTest() {
        double[] seq = {1, 2, 3, 4, 6, -4};
        double[] result = {1, 1, 1, 2, -10};
        double[] out = UtilMethods.diff(seq);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void unwrapTest() {
        double[] seq1 = {0.0 , 0.78539816, 1.57079633, 5.49778714, 6.28318531};
        double[] seq2 = {-0.5836157665365642, 0.9228635199201507, 1.7407486118278503, 2.570531442761638, -2.710817667585861, -1.8187666240910918};
        double[] result1 = {0.0,  0.785,  1.571, -0.785,  0.0};
        double[] result2 = {-0.584,  0.923,  1.741,  2.571,  3.572,  4.464};
        double[] out1 = UtilMethods.unwrap(seq1);
        double[] out2 = UtilMethods.unwrap(seq2);
        Assertions.assertArrayEquals(result1, out1, 0.001);
        Assertions.assertArrayEquals(result2, out2, 0.001);
    }

    @Test
    public void roundTest() {
        double val = 123.45667;
        double out1 = UtilMethods.round(val, 1);
        Assertions.assertEquals(123.5, out1, 0.00001);
        double out2 = UtilMethods.round(val, 3);
        Assertions.assertEquals(123.457, out2, 0.00001);
    }

    @Test
    public void moduloTest() {
        double divisor = -2;
        double dividend = 4;
        double out = UtilMethods.modulo(divisor, dividend);
        Assertions.assertEquals(2, out, 0.001);
    }

    @Test
    public void rescaleTest() {
        double[] arr1 = {12, 14, 15, 15, 16};
        double[] result1 = {10, 15, 17.5, 17.5, 20};
        double[] out1 = UtilMethods.rescale(arr1, 10, 20);
        Assertions.assertArrayEquals(result1, out1, 0.001);
    }

    @Test
    public void standardizeTest() {
        double[] arr1 = {12, 14, 15, 15, 16};
        double[] result1 = {0, 0.5, 0.75, 0.75, 1};
        double[] out1 = UtilMethods.standardize(arr1);
        Assertions.assertArrayEquals(result1, out1, 0.001);
    }

    @Test
    public void normalizeTest() {
        double[] arr1 = {12, 14, 15, 15, 16};
        double[] result1 = {-1.583, -0.264, 0.396, 0.396, 1.055};
        double[] out1 = UtilMethods.normalize(arr1);
        Assertions.assertArrayEquals(result1, out1, 0.001);
    }

    @Test
    public void zeroCentreTest() {
        double[] arr1 = {12, 14, 15, 15, 16};
        double[] result1 = {-2.4, -0.4, 0.6, 0.6, 1.6};
        double[] out1 = UtilMethods.zeroCenter(arr1);
        Assertions.assertArrayEquals(result1, out1, 0.001);
    }
}
