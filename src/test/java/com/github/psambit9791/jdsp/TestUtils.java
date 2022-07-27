/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


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
    public void zeroPadTest() {
        double[] signal = {2, 8, 0, 4, 1, 9, 9, 0};
        double[] zeroPadded = {2, 8, 0, 4, 1, 9, 9, 0, 0, 0, 0, 0};
        double[] out = UtilMethods.zeroPadSignal(signal, 4);
        Assertions.assertArrayEquals(zeroPadded, out, 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> UtilMethods.zeroPadSignal(signal, -1));
    }

    @Test
    public void truncateTest() {
        double[] signal = {2, 8, 0, 4, 1, 9, 9, 0};
        double[] truncated = {2, 8, 0, 4, 1};
        double[] out = UtilMethods.truncateSignal(signal, 5);
        Assertions.assertArrayEquals(truncated, out, 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> UtilMethods.truncateSignal(signal, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> UtilMethods.truncateSignal(signal, 20));
    }

    @Test
    public void padSignalLengthTest() {
        double[] signal = {2, 8, 0, 4, 1, 9, 9, 0};
        double[] reflect = {4, 0, 8, 2, 2, 8, 0, 4, 1, 9, 9, 0, 0, 9, 9, 1};
        double[] constant = {0, 0, 0, 0, 2, 8, 0, 4, 1, 9, 9, 0, 0, 0, 0, 0};
        double[] nearest = {2, 2, 2, 2, 2, 8, 0, 4, 1, 9, 9, 0, 0, 0, 0, 0};
        double[] mirror = {1, 4, 0, 8, 2, 8, 0, 4, 1, 9, 9, 0, 9, 9, 1, 4};
        double[] wrap = {1, 9, 9, 0, 2, 8, 0, 4, 1, 9, 9, 0, 2, 8, 0, 4};

        double[] out = UtilMethods.padSignal(signal, "reflect", 4);
        Assertions.assertArrayEquals(reflect, out, 0.001);

        out = UtilMethods.padSignal(signal, "constant", 4);
        Assertions.assertArrayEquals(constant, out, 0.001);

        out = UtilMethods.padSignal(signal, "nearest", 4);
        Assertions.assertArrayEquals(nearest, out, 0.001);

        out = UtilMethods.padSignal(signal, "mirror", 4);
        Assertions.assertArrayEquals(mirror, out, 0.001);

        out = UtilMethods.padSignal(signal, "wrap", 4);
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
    public void roundTest1() {
        double val = 123.45667;
        double out1 = UtilMethods.round(val, 1);
        Assertions.assertEquals(123.5, out1, 0.00001);
        double out2 = UtilMethods.round(val, 3);
        Assertions.assertEquals(123.457, out2, 0.00001);
    }

    @Test
    public void roundTest2() {
        double[] arr = {7.40241449, -14.34767505,  12.88704602,   5.81646305};
        double[] res1 = {7.4, -14.3,  12.9,   5.8};
        double[] out1 = UtilMethods.round(arr, 1);
        Assertions.assertArrayEquals(res1, out1, 0.00001);
        double[] res2 = {7.402, -14.348,  12.887,   5.816};
        double[] out2 = UtilMethods.round(arr, 3);
        Assertions.assertArrayEquals(res2, out2, 0.00001);
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

    @Test
    public void almostEqualsTest() {
        double[] test1 = {1.23320, 1.23321};
        double[] test2 = {1.23310, 1.23320};
        Assertions.assertTrue(UtilMethods.almostEquals(test1[0], test1[1], 0.0001));
        Assertions.assertFalse(UtilMethods.almostEquals(test2[0], test2[1], 0.00001));
    }

    @Test
    public void convertToPrimitiveIntFromALTest() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        int[] nums = {1, 2, 3, 4, 5};
        int[] out = UtilMethods.convertToPrimitiveInt(numbers);
        Assertions.assertArrayEquals(nums, out);
    }

    @Test
    public void convertToPrimitiveIntFromLLTest() {
        LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
        int[] nums = {1, 2, 3, 4, 5};
        int[] out = UtilMethods.convertToPrimitiveInt(numbers);
        Assertions.assertArrayEquals(nums, out);
    }

    @Test
    public void convertToPrimitiveDoubleTest() {
        ArrayList<Double> numbers = new ArrayList<>(Arrays.asList(1.1, 2.22, 3.3, 4.4, 5.55));
        double[] nums = {1.1, 2.22, 3.3, 4.4, 5.55};
        double[] out = UtilMethods.convertToPrimitiveDouble(numbers);
        Assertions.assertArrayEquals(nums, out, 0.001);
    }

    @Test
    public void argminTest() {
        double[] arr = {1, 2, 5, 3, 4, 6, 1, 6};
        int index = 0;
        int indexRev = 6;
        int out = UtilMethods.argmin(arr, false);
        Assertions.assertEquals(index, out);
        out = UtilMethods.argmin(arr, true);
        Assertions.assertEquals(indexRev, out);
    }

    @Test
    public void argmaxTest() {
        double[] arr = {1, 2, 5, 3, 4, 6, 1, 6};
        int index = 5;
        int indexRev = 7;
        int out = UtilMethods.argmax(arr, false);
        Assertions.assertEquals(index, out);
        out = UtilMethods.argmax(arr, true);
        Assertions.assertEquals(indexRev, out);
    }

    @Test
    public void argSortTest() {
        double[] test1 = {3, 1, 2};
        int[] res1 = {1, 2, 0};
        double[] test2 = {1.23, 4.55, -1.33, 2.45, 6.78, 1.29};
        int[] res2 = {2, 0, 5, 3, 1, 4};
        Assertions.assertArrayEquals(res1, UtilMethods.argsort(test1, true));
        Assertions.assertArrayEquals(res2, UtilMethods.argsort(test2, true));
    }

    @Test
    public void ecgTest() throws IOException {
        int resultLength = 108000;
        double[] data = UtilMethods.electrocardiogram();
        Assertions.assertEquals(resultLength, data.length);
    }

    @Test
    public void absoluteTest() {
        double[][] test1 = {{1.22, -3.41, -0.22}, {-0.89, 1.6, 7.65}};
        double[][] res1 = {{1.22, 3.41, 0.22}, {0.89, 1.6, 7.65}};
        double[][] out1 = UtilMethods.absoluteArray(test1);

        double[] test2 = {1.22, -3.41, -0.22, 5.44, -9.28};
        double[] res2 = {1.22, 3.41, 0.22, 5.44, 9.28};
        double[] out2 = UtilMethods.absoluteArray(test2);

        for (int i=0; i<res1.length; i++) {
            Assertions.assertArrayEquals(res1[i], out1[i], 0.001);
        }
        Assertions.assertArrayEquals(res2, out2, 0.001);
    }

    @Test
    public void transposeTest() {
        double[][] test1 = {{1, 2}, {3, 4}};
        double[][] res1 = {{1, 3}, {2, 4}};
        double[][] out1 = UtilMethods.transpose(test1);

        for (int i=0; i<res1.length; i++) {
            Assertions.assertArrayEquals(res1[i], out1[i], 0.001);
        }

        double[][] test2 = {{1.45, 2.09, 0.85}, {-3.08, 4.56, 7.45}};
        double[][] res2 = {{1.45, -3.08}, {2.09, 4.56}, {0.85, 7.45}};
        double[][] out2 = UtilMethods.transpose(test2);

        for (int i=0; i<res2.length; i++) {
            Assertions.assertArrayEquals(res2[i], out2[i], 0.001);
        }
    }

    @Test
    public void matrixMultiplyTest() {
        double[][] m1 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] m2 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] result = {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}};
        double[][] out = UtilMethods.matrixMultiply(m1, m2);
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], out[i], 0.001);
        }
    }

    @Test
    public void matrixAdditionTest() {
        double[][] m1 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] m2 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] result = {{2.0, 4.0, 6.0}, {8.0, 10.0, 12.0}, {14.0, 16.0, 18.0}};
        double[][] out = UtilMethods.matrixAddition(m1, m2);
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], out[i], 0.001);
        }
    }

    @Test
    public void matrixSubtractionTest() {
        double[][] m1 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] m2 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] result = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}};
        double[][] out = UtilMethods.matrixSubtraction(m1, m2);
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], out[i], 0.001);
        }
    }

    @Test
    public void scalarOperationTest() {
        double[] arr = {1.23, 6.54, 4.56, 9.04, 2.88};
        double[] resAdd = {2.25,  7.56,  5.58, 10.06,  3.9};
        double[] resSub = {0.21, 5.52, 3.54, 8.02, 1.86};
        double[] resRevSub = {-0.21, -5.52, -3.54, -8.02, -1.86};
        double[] resMul = {1.2546, 6.6708, 4.6512, 9.2208, 2.9376};
        double[] resDiv = {1.2059, 6.4118, 4.4706, 8.8627, 2.8235};
        double[] resPow = {1.23510311, 6.79030533, 4.70050089, 9.44695397, 2.94157779};

        double[] arrAdd = UtilMethods.scalarArithmetic(arr, 1.02, "add");
        double[] arrSub = UtilMethods.scalarArithmetic(arr, 1.02, "sub");
        double[] arrRevSub = UtilMethods.scalarArithmetic(arr, 1.02, "reverse_sub");
        double[] arrMul = UtilMethods.scalarArithmetic(arr, 1.02, "mul");
        double[] arrDiv = UtilMethods.scalarArithmetic(arr, 1.02, "div");
        double[] arrPow = UtilMethods.scalarArithmetic(arr, 1.02, "pow");

        Assertions.assertArrayEquals(resAdd, arrAdd, 0.001);
        Assertions.assertArrayEquals(resSub, arrSub, 0.001);
        Assertions.assertArrayEquals(resRevSub, arrRevSub, 0.001);
        Assertions.assertArrayEquals(resMul, arrMul, 0.001);
        Assertions.assertArrayEquals(resDiv, arrDiv, 0.001);
        Assertions.assertArrayEquals(resPow, arrPow, 0.001);
    }

    @Test
    public void trigonometricOperationTest() {
        double[] arr1 = {1.23, 6.54, 4.56, 9.04, 2.88};
        double[] resSin = {0.9424888 ,  0.254001  , -0.98841125,  0.37535334,  0.25861935};
        double[] resCos = {0.33423773,  0.96720395, -0.15179986, -0.9268818 , -0.96597931};
        double[] resTan = {2.81981573,  0.26261369,  6.51127914, -0.40496355, -0.26772763};

        double[] arr2 = {-0.92, -0.38, 0.25, 0.55, 0.98};
        double[] resAsin = {-1.16808049, -0.3897963 ,  0.25268026,  0.58236424,  1.37046148};
        double[] resAcos = {2.73887681, 1.96059262, 1.31811607, 0.98843209, 0.20033484};
        double[] resAtan = {-0.74375558, -0.36314701,  0.24497866,  0.50284321,  0.7752975};

        double[] outSin = UtilMethods.trigonometricArithmetic(arr1, "sin");
        double[] outCos = UtilMethods.trigonometricArithmetic(arr1, "cos");
        double[] outTan = UtilMethods.trigonometricArithmetic(arr1, "tan");
        double[] outAsin = UtilMethods.trigonometricArithmetic(arr2, "asin");
        double[] outAcos = UtilMethods.trigonometricArithmetic(arr2, "acos");
        double[] outAtan = UtilMethods.trigonometricArithmetic(arr2, "atan");

        Assertions.assertArrayEquals(resSin, outSin, 0.001);
        Assertions.assertArrayEquals(resCos, outCos, 0.001);
        Assertions.assertArrayEquals(resTan, outTan, 0.001);
        Assertions.assertArrayEquals(resAsin, outAsin, 0.001);
        Assertions.assertArrayEquals(resAcos, outAcos, 0.001);
        Assertions.assertArrayEquals(resAtan, outAtan, 0.001);
    }

    @Test
    public void sortedAscendTest() {
        double[] arr = {1, 2, 3, 4, 5};
        Assertions.assertTrue(UtilMethods.isSorted(arr, false));
        Assertions.assertFalse(UtilMethods.isSorted(arr, true));
    }

    @Test
    public void sortedDescendTest() {
        double[] arr = {5, 4, 3, 2, 1};
        Assertions.assertTrue(UtilMethods.isSorted(arr, true));
        Assertions.assertFalse(UtilMethods.isSorted(arr, false));
    }

    @Test
    public void interpolationSingleTest() {
        double[] xp = {1, 2, 3};
        double[] yp = {3, 2, 0};

        double out = UtilMethods.interpolate(2.5, xp, yp);
        double res = 1.0;
        Assertions.assertEquals(res, out, 0.0001);
    }

    @Test
    public void interpolationMultiTest() {
        double[] xp = {1, 2, 3};
        double[] yp = {3, 2, 0};
        double[] inputs = {1.25, 1.5, 1.75, 2.25, 2.5, 2.75};

        double[] out = UtilMethods.interpolate(inputs, xp, yp);
        double[] res = {2.75, 2.5 , 2.25, 1.5 , 1.  , 0.5 };
        Assertions.assertArrayEquals(res, out, 0.0001);
    }

    @Test
    public void chebyEvalTest() {
        double[] arr = {1000.0, 2.0, 3.4, 17.0, 50.0};

        double[] x = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] res = {-470.2, 1047.4, 23580.4,  97134.8, 263716.6};
        double[] out = UtilMethods.chebyEval(x, arr);
        Assertions.assertArrayEquals(res, out, 0.001);

        double x1 = 2.0;
        double res1 = 1047.4;
        double out1 = UtilMethods.chebyEval(x1, arr);
        Assertions.assertEquals(res1, out1, 0.001);
        double x2 = 5.0;
        double res2 = 263716.6;
        double out2 = UtilMethods.chebyEval(x2, arr);
        Assertions.assertEquals(res2, out2, 0.001);
    }

    @Test
    public void i0Test() {
        double[] x1 = {0.0, 1.0, 2.0, 3.0};
        double[] res1 = {1.0, 1.26606588, 2.2795853 , 4.88079259};
        double[] out1 = UtilMethods.i0(x1);
        Assertions.assertArrayEquals(res1, out1, 0.001);

        double[] x2 = {-20.0, -10.0, 0.0, 10.0, 20.0};
        double[] res2 = {4.35582826e+07, 2.81571663e+03, 1.00000000e+00, 2.81571663e+03, 4.35582826e+07};
        double[] out2 = UtilMethods.i0(x2);
        Assertions.assertArrayEquals(res2, out2, 0.00001E7);

        double x3 = -10;
        double res3 = 2.81571663e+03;
        double out3 = UtilMethods.i0(x3);
        Assertions.assertEquals(res3, out3, 0.001);

        double x4 = 20;
        double res4 = 4.35582826e+07;
        double out4 = UtilMethods.i0(x4);
        Assertions.assertEquals(res4, out4, 0.00001E7);
    }

    @Test
    public void i0eTest() {
        double[] x1 = {0.0, 1.0, 2.0, 3.0};
        double[] res1 = {1.0, 0.46575961, 0.30850832, 0.24300035};
        double[] out1 = UtilMethods.i0e(x1);
        Assertions.assertArrayEquals(res1, out1, 0.001);

        double[] x2 = {-20.0, -10.0, 0.0, 10.0, 20.0};
        double[] res2 = {0.08978031, 0.12783334, 1.0, 0.12783334, 0.08978031};
        double[] out2 = UtilMethods.i0e(x2);
        Assertions.assertArrayEquals(res2, out2, 0.001);
    }

    @Test
    public void sincSingleTest() {
        double x1 = 0.23;
        double res1 = 0.9152265417;
        double out1 = UtilMethods.sinc(x1);
        Assertions.assertEquals(res1, out1, 0.001);

        double x2 = 0.5;
        double res2 = 0.63661977237;
        double out2 = UtilMethods.sinc(x2);
        Assertions.assertEquals(res2, out2, 0.001);
    }

    @Test
    public void sincArrTest() {
        double[] x1 = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
        double[] res1 = {0.98363164, 0.93548928, 0.85839369, 0.75682673, 0.63661977, 0.50455115, 0.36788301, 0.23387232,
                0.1092924};
        double[] out1 = UtilMethods.sinc(x1);
        Assertions.assertArrayEquals(res1, out1, 0.001);

        double[] x2 = {0.01, 0.2, 0.65, 0.23, 0.06};
        double[] res2 = {0.99983551, 0.93548928, 0.43633259, 0.91522654, 0.99408875};
        double[] out2 = UtilMethods.sinc(x2);
        Assertions.assertArrayEquals(res2, out2, 0.001);
    }

    @Test
    public void toeplitzTest() {
        double[] c = {1,2,3,4};
        double[][] output = UtilMethods.toeplitz(c);
        double[][] result = {{1, 2, 3, 4}, {2, 1, 2, 3}, {3, 2, 1, 2}, {4, 3, 2, 1}};
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], output[i], 0.0001);
        }
    }

    @Test
    public void hankelTest() {
        double[] c = {1,2,3,4};
        double[][] output = UtilMethods.hankel(c);
        double[][] result = {{1, 2, 3, 4}, {2, 3, 4, 0}, {3, 4, 0, 0}, {4, 0, 0, 0}};
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], output[i], 0.0001);
        }
    }

    @Test
    public void hankel2Test() {
        double[] c = {1, 2, 3, 4};
        double[] r = {4, 7, 7, 8, 9};
        double[][] output = UtilMethods.hankel(c, r);
        double[][] result = {{1, 2, 3, 4, 7}, {2, 3, 4, 7, 7}, {3, 4, 7, 7, 8}, {4, 7, 7, 8, 9}};
        for (int i=0; i<result.length; i++) {
            Assertions.assertArrayEquals(result[i], output[i], 0.0001);
        }
    }

    @Test
    public void logTest() {
        int base = 2;
        int input_val = 256;
        double result = 8.0;
        double out = UtilMethods.log(input_val, base);
        Assertions.assertEquals(result, out, 0.0001);

        base = 10;
        double input_val_2 = 122.0;
        result = 2.08636;
        out = UtilMethods.log(input_val_2, base);
        Assertions.assertEquals(result, out, 0.0001);
    }

    @Test
    public void antilogTest() {
        int base = 2;
        int input_val = 8;
        double result = 256.0;
        double out = UtilMethods.antilog(input_val, base);
        Assertions.assertEquals(result, out, 0.0001);

        base = 10;
        double input_val_2 = 2.08636;
        result = 122;
        out = UtilMethods.antilog(input_val_2, base);
        Assertions.assertEquals(result, out, 0.0001);
    }

    @Test
    public void ebeMultiplyTest() {
        double[][] m1 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2_row = {{0.5, 1.0}};
        double[][] m2_col = {{0.5}, {1.0}, {2.0}};
        double[][] res = {{1.0, 4.0}, {4.0, 0.25}, {9.0, 16.0}};
        double[][] res_row = {{0.5, 2.0}, {1.0, 0.5}, {1.5, 4.0}};
        double[][] res_col = {{0.5, 1.0}, {2.0, 0.5}, {6.0, 8.0}};

        RealMatrix out = UtilMethods.ebeMultiply(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2));
        for (int i=0; i<res.length; i++) {
            Assertions.assertArrayEquals(res[i], out.getData()[i], 0.001);
        }
        RealMatrix out_row = UtilMethods.ebeMultiply(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_row), "row");
        for (int i=0; i<res_row.length; i++) {
            Assertions.assertArrayEquals(res_row[i], out_row.getData()[i], 0.001);
        }
        RealMatrix out_col = UtilMethods.ebeMultiply(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_col), "column");
        for (int i=0; i<res_col.length; i++) {
            Assertions.assertArrayEquals(res_col[i], out_col.getData()[i], 0.001);
        }
    }

    @Test
    public void ebeDivideTest() {
        double[][] m1 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2_row = {{0.5, 1.0}};
        double[][] m2_col = {{0.5}, {1.0}, {2.0}};
        double[][] res = {{1.0, 1.0}, {1.0, 1.0}, {1.0, 1.0}};
        double[][] res_row = {{2.0, 2.0}, {4.0, 0.5}, {6.0, 4.0}};
        double[][] res_col = {{2.0, 4.0}, {2.0, 0.5}, {1.5, 2.0}};

        RealMatrix out = UtilMethods.ebeDivide(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2));
        for (int i=0; i<res.length; i++) {
            Assertions.assertArrayEquals(res[i], out.getData()[i], 0.001);
        }
        RealMatrix out_row = UtilMethods.ebeDivide(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_row), "row");
        for (int i=0; i<res_row.length; i++) {
            Assertions.assertArrayEquals(res_row[i], out_row.getData()[i], 0.001);
        }
        RealMatrix out_col = UtilMethods.ebeDivide(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_col), "column");
        for (int i=0; i<res_col.length; i++) {
            Assertions.assertArrayEquals(res_col[i], out_col.getData()[i], 0.001);
        }
    }

    @Test
    public void ebeAdditionTest() {
        double[][] m1 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2_row = {{0.5, 1.0}};
        double[][] m2_col = {{0.5}, {1.0}, {2.0}};
        double[][] res = {{2.0, 4.0}, {4.0, 1.0}, {6.0, 8.0}};
        double[][] res_row = {{1.5, 3.0}, {2.5, 1.5}, {3.5, 5.0}};
        double[][] res_col = {{1.5, 2.5}, {3.0, 1.5}, {5.0, 6.0}};

        RealMatrix out = UtilMethods.ebeAdd(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2));
        for (int i=0; i<res.length; i++) {
            Assertions.assertArrayEquals(res[i], out.getData()[i], 0.001);
        }
        RealMatrix out_row = UtilMethods.ebeAdd(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_row), "row");
        for (int i=0; i<res_row.length; i++) {
            Assertions.assertArrayEquals(res_row[i], out_row.getData()[i], 0.001);
        }
        RealMatrix out_col = UtilMethods.ebeAdd(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_col), "column");
        for (int i=0; i<res_col.length; i++) {
            Assertions.assertArrayEquals(res_col[i], out_col.getData()[i], 0.001);
        }
    }

    @Test
    public void ebeSubtractTest() {
        double[][] m1 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] m2_row = {{0.5, 1.0}};
        double[][] m2_col = {{0.5}, {1.0}, {2.0}};
        double[][] res = {{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};
        double[][] res_row = {{0.5, 1.0}, {1.5, -0.5}, {2.5, 3.0}};
        double[][] res_col = {{0.5, 1.5}, {1.0, -0.5}, {1.0, 2.0}};

        RealMatrix out = UtilMethods.ebeSubtract(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2));
        for (int i=0; i<res.length; i++) {
            Assertions.assertArrayEquals(res[i], out.getData()[i], 0.001);
        }
        RealMatrix out_row = UtilMethods.ebeSubtract(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_row), "row");
        for (int i=0; i<res_row.length; i++) {
            Assertions.assertArrayEquals(res_row[i], out_row.getData()[i], 0.001);
        }
        RealMatrix out_col = UtilMethods.ebeSubtract(MatrixUtils.createRealMatrix(m1), MatrixUtils.createRealMatrix(m2_col), "column");
        for (int i=0; i<res_col.length; i++) {
            Assertions.assertArrayEquals(res_col[i], out_col.getData()[i], 0.001);
        }
    }

    @Test
    public void ebeInvertTest() {
        double[][] m1 = {{1.0, 2.0}, {2.0, 0.5}, {3.0, 4.0}};
        double[][] res = {{1.0, 0.5}, {0.5, 2.0}, {0.333, 0.25}};

        RealMatrix out = UtilMethods.ebeInvert(MatrixUtils.createRealMatrix(m1));
        for (int i=0; i<res.length; i++) {
            Assertions.assertArrayEquals(res[i], out.getData()[i], 0.001);
        }
    }

    @Test
    public void reverseDoubleMatrixTest() {
        double[][] matrix = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] result = {{3.0, 2.0, 1.0}, {6.0, 5.0, 4.0}, {9.0, 8.0, 7.0}};
        double[][] out = UtilMethods.reverseMatrix(matrix);
        Assertions.assertArrayEquals(result[0], out[0], 0.001);
        Assertions.assertArrayEquals(result[1], out[1], 0.001);
        Assertions.assertArrayEquals(result[1], out[1], 0.001);
    }

    @Test
    public void reverseIntMatrixTest() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] result = {{3, 2, 1}, {6, 5, 4}, {9, 8, 7}};
        int[][] out = UtilMethods.reverseMatrix(matrix);
        Assertions.assertArrayEquals(result[0], out[0]);
        Assertions.assertArrayEquals(result[1], out[1]);
        Assertions.assertArrayEquals(result[1], out[1]);
    }

    @Test
    public void flattenDoubleMatrixTest() {
        double[][] matrix = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[] result = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double[] out = UtilMethods.flattenMatrix(matrix);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void flattenIntMatrixTest() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] out = UtilMethods.flattenMatrix(matrix);
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void getRowTest() {
        double[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[] result1 = {1, 2, 3};
        double[] result2 = {4, 5, 6};
        double[] result3 = {7, 8, 9};

        double[] out1 = UtilMethods.getRow(matrix, 0);
        double[] out2 = UtilMethods.getRow(matrix, 1);
        double[] out3 = UtilMethods.getRow(matrix, 2);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        double[] out4 = UtilMethods.getRow(matrix, 3);
        double[] out5 = UtilMethods.getRow(matrix, -1);
        System.setErr(stderr);  // Reset to standard stderr

        Assertions.assertArrayEquals(result1, out1);
        Assertions.assertArrayEquals(result2, out2);
        Assertions.assertArrayEquals(result3, out3);
        Assertions.assertNull(out4);
        Assertions.assertNull(out5);
    }

    @Test
    public void getColumnTest() {
        double[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[] result1 = {1, 4, 7};
        double[] result2 = {2, 5, 8};
        double[] result3 = {3, 6, 9};

        double[] out1 = UtilMethods.getColumn(matrix, 0);
        double[] out2 = UtilMethods.getColumn(matrix, 1);
        double[] out3 = UtilMethods.getColumn(matrix, 2);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        double[] out4 = UtilMethods.getColumn(matrix, 3);
        double[] out5 = UtilMethods.getColumn(matrix, -1);
        System.setErr(stderr);  // Reset to standard stderr

        Assertions.assertArrayEquals(result1, out1);
        Assertions.assertArrayEquals(result2, out2);
        Assertions.assertArrayEquals(result3, out3);
        Assertions.assertNull(out4);
        Assertions.assertNull(out5);
    }

    @Test
    public void dotProductTest() {
        double[] a = {1,2,3,4};
        double[] b = {4,3,2,1};
        double[] x = {1,2,3,4};

        Assertions.assertEquals(UtilMethods.dotProduct(a, x), 30, 0.0001);
        Assertions.assertEquals(UtilMethods.dotProduct(b, x), 20, 0.0001);
    }

    @Test
    public void decibelToRatioTest() {
        double db = 20;
        Assertions.assertEquals(UtilMethods.decibelToRatio(db), 10, 0.00000001);
        Assertions.assertEquals(UtilMethods.decibelToRatio(db, false), 100, 0.00000001);
    }

    @Test
    public void ratioToDecibelsTest() {
        double ratio = 10;
        Assertions.assertEquals(UtilMethods.ratioToDecibels(1), 0, 0.00000001);
        Assertions.assertEquals(UtilMethods.ratioToDecibels(ratio), 20, 0.00000001);
        Assertions.assertEquals(UtilMethods.ratioToDecibels(ratio*10, false), 20, 0.00000001);
    }

    @Test
    public void integerToBooleanTest() {
        Assertions.assertFalse(UtilMethods.integerToBoolean(0));
        Assertions.assertTrue(UtilMethods.integerToBoolean(1));
        Assertions.assertTrue(UtilMethods.integerToBoolean(2));
        Assertions.assertTrue(UtilMethods.integerToBoolean(-1));
        Assertions.assertTrue(UtilMethods.integerToBoolean(-2));

    }
}
