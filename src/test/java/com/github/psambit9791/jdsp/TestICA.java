/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.Plotting;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.ICA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestICA {
    // Mixing Ratio = Component1: [1.0, 1.0, 1.0], Component2: [0.5, 2.0, 1.0], Component3: [1.5, 1.0, 2.0]
    final double[] func_input = {0.38, 0.53, 0.80, 0.86, 0.11, 0.88, 0.72, 0.87, 0.37, 0.52};
    // 2Hz Sine + 12Hz Sine + 26Hz Sine
    final double[][] X_fit = {{0.85578, 0.74023, 1.34534}, {1.07739, 0.40654, 1.98131}, {2.0507, 1.91981, 3.23426}, {1.2674, 0.33323, 2.56414}, {2.12663, 2.42052, 3.37158}, {0.43881, -0.3084, 1.49951}, {1.38468, 2.33925, 2.1829}, {-0.71001, -1.16909, -0.19044}, {0.69305, 2.08902, 0.97242}, {-1.31433, -1.78986, -1.20207}, {0.66586, 1.99043, 0.69172}, {-1.12138, -1.99507, -1.1204}, {1.12138, 1.99507, 1.1204}, {-0.66586, -1.99043, -0.69172}, {1.31433, 1.78986, 1.20207}, {-0.69305, -2.08902, -0.97242}, {0.71001, 1.16909, 0.19044}, {-1.38468, -2.33925, -2.1829}, {-0.43881, 0.3084, -1.49951}, {-2.12663, -2.42052, -3.37158}, {-1.2674, -0.33323, -2.56414}, {-2.0507, -1.91981, -3.23426}, {-1.07739, -0.40654, -1.98131}, {-0.85578, -0.74023, -1.34534}, {0.0, 0.0, 0.0}};
    // 3Hz Sine + 13Hz Sine + 24Hz Sine
    final double[][] X_tr = {{0.31052, -0.15708, 0.40411}, {0.76496, 0.51464, 0.78222}, {-0.28216, -1.03554, -0.58145}, {-0.23724, 0.18185, -1.0189}, {-2.12663, -2.42052, -3.37158}, {-1.29577, -0.12008, -2.78494}, {-2.59713, -2.94548, -4.00158}, {-0.30919, 0.65948, -1.33836}, {-1.19359, -2.33929, -1.72322}, {1.31433, 1.78986, 1.20207}, {-0.44558, -1.88029, -0.3613}, {1.24082, 2.05478, 1.29955}, {-1.24082, -2.05478, -1.29955}, {0.44558, 1.88029, 0.3613}, {-1.31433, -1.78986, -1.20207}, {1.19359, 2.33929, 1.72322}, {0.30919, -0.65948, 1.33836}, {2.59713, 2.94548, 4.00158}, {1.29577, 0.12008, 2.78494}, {2.12663, 2.42052, 3.37158}, {0.23724, -0.18185, 1.0189}, {0.28216, 1.03554, 0.58145}, {-0.76496, -0.51464, -0.78222}, {-0.31052, 0.15708, -0.40411}, {-0.0, 0.0, -0.0}};
    final double[][] w_init = {{-0.34889445,  0.98370343,  0.58092283}, {0.07028444,  0.77753268,  0.58195875}, {1.47179053,  1.66318101, -0.26117712}};
    @Test
    public void ICAFuncLogcoshTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double[] gx= {0.36271, 0.48538, 0.66404, 0.69626, 0.10956, 0.70642, 0.61691, 0.70137, 0.35399, 0.4777};
        double g_x = 0.69701;
        ICA i1 = new ICA(this.X_fit);
        Method privMethod = i1.getClass().getDeclaredMethod("logcosh_", double[].class);
        privMethod.setAccessible(true);
        privMethod.invoke(i1, (Object) this.func_input);
        Assertions.assertArrayEquals(gx, i1.gx, 0.00001);
        Assertions.assertEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICAFuncExpTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double[] gx= {0.35353, 0.46055, 0.58092, 0.59415, 0.10934, 0.59748, 0.5556, 0.59588, 0.34552, 0.45424};
        double g_x = 0.49788;
        ICA i1 = new ICA(this.X_fit);
        Method privMethod = i1.getClass().getDeclaredMethod("exp_", double[].class);
        privMethod.setAccessible(true);
        privMethod.invoke(i1, (Object) this.func_input);
        Assertions.assertArrayEquals(gx, i1.gx, 0.00001);
        Assertions.assertEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICAFuncCubeTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double[] gx= {0.05487, 0.14888, 0.512  , 0.63606, 0.00133, 0.68147, 0.37325, 0.6585 , 0.05065, 0.14061};
        double g_x = 1.2822;
        ICA i1 = new ICA(this.X_fit);
        Method privMethod = i1.getClass().getDeclaredMethod("cube_", double[].class);
        privMethod.setAccessible(true);
        privMethod.invoke(i1, (Object) this.func_input);
        Assertions.assertArrayEquals(gx, i1.gx, 0.00001);
        Assertions.assertEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICAUnitVarLogcoshTest1() {
        ICA obj = new ICA(this.X_fit, "logcosh", "unit-variance", this.w_init, 200, 1E-4, 1);
        obj.fit();
        double[][] S_ = obj.transform();
        double[][] expected_S_ = {{0.35113, 0.68158, -0.17729}, {0.68033, 1.19464, 0.35163}, {0.96692, 1.4122, -0.52069}, {1.19302, 1.28063, 0.68122}, {1.34429, 0.83233, -0.83132}, {1.4113, 0.17845, 0.96806}, {1.38957, -0.51951, -1.08968}, {1.28054, -1.08856, 1.19408}, {1.09079, -1.38832, -1.27958}, {0.83239, -1.34425, 1.34504}, {0.52137, -0.96773, -1.38913}, {0.17757, -0.35149, 1.41143}, {-0.17757, 0.35149, -1.41143}, {-0.52137, 0.96773, 1.38913}, {-0.83239, 1.34425, -1.34504}, {-1.09079, 1.38832, 1.27958}, {-1.28054, 1.08856, -1.19408}, {-1.38957, 0.51951, 1.08968}, {-1.4113, -0.17845, -0.96806}, {-1.34429, -0.83233, 0.83132}, {-1.19302, -1.28063, -0.68122}, {-0.96692, -1.4122, 0.52069}, {-0.68033, -1.19464, -0.35163}, {-0.35113, -0.68158, 0.17729}, {0.0, -0.0, 0.0}};

        double[][] zeromean = {{0.85578, 0.74023, 1.34534}, {1.07739, 0.40654, 1.98131}, {2.0507, 1.91981, 3.23426}, {1.2674, 0.33323, 2.56414}, {2.12663, 2.42052, 3.37158}, {0.43881, -0.3084, 1.49951}, {1.38468, 2.33925, 2.1829}, {-0.71001, -1.16909, -0.19044}, {0.69305, 2.08902, 0.97242}, {-1.31433, -1.78986, -1.20207}, {0.66586, 1.99043, 0.69172}, {-1.12138, -1.99507, -1.1204}, {1.12138, 1.99507, 1.1204}, {-0.66586, -1.99043, -0.69172}, {1.31433, 1.78986, 1.20207}, {-0.69305, -2.08902, -0.97242}, {0.71001, 1.16909, 0.19044}, {-1.38468, -2.33925, -2.1829}, {-0.43881, 0.3084, -1.49951}, {-2.12663, -2.42052, -3.37158}, {-1.2674, -0.33323, -2.56414}, {-2.0507, -1.91981, -3.23426}, {-1.07739, -0.40654, -1.98131}, {-0.85578, -0.74023, -1.34534}, {0.0, 0.0, 0.0}};
        zeromean = UtilMethods.transpose(zeromean);
        for (int i=0; i<zeromean.length; i++) {
            Assertions.assertArrayEquals(obj.zm_signal[i], zeromean[i], 0.0001);
        }

        Assertions.assertEquals(S_.length, expected_S_.length);
        for (int i=0; i<expected_S_.length; i++) {
            Assertions.assertArrayEquals(S_[i], expected_S_[i], 0.0001);
        }

        double[][] S_tr_ = obj.transform(this.X_tr);
        double[][] expected_S_tr_ = {{-0.3525, 0.96781, 0.17721}, {-0.68247, 1.41085, -0.35175}, {-0.96898, 1.08889, 0.52058}, {-1.19422, 0.17623, -0.68128}, {-1.34429, -0.83233, 0.83132}, {-1.4103, -1.39037, -0.968}, {-1.38815, -1.19516, 1.08975}, {-1.27936, -0.35281, -1.19402}, {-1.0902, 0.68046, 1.27961}, {-0.83239, 1.34425, -1.34504}, {-0.52163, 1.27925, 1.38912}, {-0.17771, 0.5204, -1.41144}, {0.17771, -0.5204, 1.41144}, {0.52163, -1.27925, -1.38912}, {0.83239, -1.34425, 1.34504}, {1.0902, -0.68046, -1.27961}, {1.27936, 0.35281, 1.19402}, {1.38815, 1.19516, -1.08975}, {1.4103, 1.39037, 0.968}, {1.34429, 0.83233, -0.83132}, {1.19422, -0.17623, 0.68128}, {0.96898, -1.08889, -0.52058}, {0.68247, -1.41085, 0.35175}, {0.3525, -0.96781, -0.17721}, {-0.0, -0.0, -0.0}};
        Assertions.assertEquals(S_tr_.length, expected_S_tr_.length);
        for (int i=0; i<expected_S_tr_.length; i++) {
            Assertions.assertArrayEquals(S_tr_[i], expected_S_tr_[i], 0.0001);
        }
    }

    @Test
    public void ICAUnitVarLogcoshTest2() {
        ICA obj = new ICA(this.X_fit, "logcosh", "unit-variance", this.w_init, 200, 1E-4, 1.5);
        obj.fit();
        double[][] S_ = obj.transform();
        double[][] expected_S_ = {{0.28371, 0.68079, 0.27444}, {1.06174, 0.84818, 0.40858}, {0.56048, 1.64764, 0.41412}, {1.57375, 1.01883, 0.1126}, {0.21888, 1.75451, -0.25449}, {1.43443, 0.40355, -0.8604}, {-0.53806, 1.21721, -1.27165}, {1.03497, -0.47928, -1.71747}, {-1.17712, 0.68318, -1.70359}, {0.8686, -0.97928, -1.61107}, {-1.32152, 0.61767, -1.00503}, {1.07178, -0.89703, -0.44029}, {-1.07178, 0.89703, 0.44029}, {1.32152, -0.61767, 1.00503}, {-0.8686, 0.97928, 1.61107}, {1.17712, -0.68318, 1.70359}, {-1.03497, 0.47928, 1.71747}, {0.53806, -1.21721, 1.27165}, {-1.43443, -0.40355, 0.8604}, {-0.21888, -1.75451, 0.25449}, {-1.57375, -1.01883, -0.1126}, {-0.56048, -1.64764, -0.41412}, {-1.06174, -0.84818, -0.40858}, {-0.28371, -0.68079, -0.27444}, {0.0, 0.0, -0.0}};
        Assertions.assertEquals(S_.length, expected_S_.length);
        for (int i=0; i<expected_S_.length; i++) {
            Assertions.assertArrayEquals(S_[i], expected_S_[i], 0.0001);
        }

        double[][] S_tr_ = obj.transform(this.X_tr);
        double[][] expected_S_tr_ = {{0.40559, 0.19141, 0.94402}, {0.02711, 0.52959, 1.51617}, {0.48474, -0.32507, 1.43352}, {-0.96491, -0.24844, 0.96363}, {-0.21888, -1.75451, 0.25449}, {-1.9409, -1.0444, -0.03488}, {-0.17851, -2.12392, 0.00497}, {-1.63733, -0.28292, 0.65268}, {0.8813, -1.0575, 1.18067}, {-0.8686, 0.97928, 1.61107}, {1.45171, -0.45294, 1.23516}, {-1.00119, 0.98635, 0.56507}, {1.00119, -0.98635, -0.56507}, {-1.45171, 0.45294, -1.23516}, {0.8686, -0.97928, -1.61107}, {-0.8813, 1.0575, -1.18067}, {1.63733, 0.28292, -0.65268}, {0.17851, 2.12392, -0.00497}, {1.9409, 1.0444, 0.03488}, {0.21888, 1.75451, -0.25449}, {0.96491, 0.24844, -0.96363}, {-0.48474, 0.32507, -1.43352}, {-0.02711, -0.52959, -1.51617}, {-0.40559, -0.19141, -0.94402}, {-0.0, -0.0, 0.0}};
        Assertions.assertEquals(S_tr_.length, expected_S_tr_.length);
        for (int i=0; i<expected_S_tr_.length; i++) {
            Assertions.assertArrayEquals(S_tr_[i], expected_S_tr_[i], 0.0001);
        }
    }

    @Test
    public void ICAUnitVarExpTest1() {
        ICA obj = new ICA(this.X_fit, "exp", "unit-variance", this.w_init, 200, 1E-4, 1.0);
        obj.fit();
        double[][] S_ = obj.transform();
        double[][] expected_S_ = {{0.35103, 0.68164, -0.17727}, {0.68012, 1.19474, 0.35167}, {0.9667, 1.41236, -0.52065}, {1.19279, 1.28082, 0.68127}, {1.34418, 0.83256, -0.83128}, {1.41125, 0.17867, 0.96809}, {1.38968, -0.51927, -1.08965}, {1.28069, -1.08837, 1.19409}, {1.09104, -1.38812, -1.27958}, {0.83258, -1.34414, 1.34504}, {0.52156, -0.96762, -1.38914}, {0.17759, -0.35148, 1.41143}, {-0.17759, 0.35148, -1.41143}, {-0.52156, 0.96762, 1.38914}, {-0.83258, 1.34414, -1.34504}, {-1.09104, 1.38812, 1.27958}, {-1.28069, 1.08837, -1.19409}, {-1.38968, 0.51927, 1.08965}, {-1.41125, -0.17867, -0.96809}, {-1.34418, -0.83256, 0.83128}, {-1.19279, -1.28082, -0.68127}, {-0.9667, -1.41236, 0.52065}, {-0.68012, -1.19474, -0.35167}, {-0.35103, -0.68164, 0.17727}, {0.0, -0.0, 0.0}};
        Assertions.assertEquals(S_.length, expected_S_.length);
        for (int i=0; i<expected_S_.length; i++) {
            Assertions.assertArrayEquals(S_[i], expected_S_[i], 0.0001);
        }

        double[][] S_tr_ = obj.transform(this.X_tr);
        double[][] expected_S_tr_ = {{-0.35266, 0.96775, 0.17722}, {-0.6827, 1.41074, -0.35174}, {-0.96917, 1.08873, 0.52057}, {-1.19423, 0.17605, -0.68131}, {-1.34418, -0.83256, 0.83128}, {-1.41005, -1.39059, -0.96806}, {-1.38799, -1.1954, 1.0897}, {-1.27927, -0.353, -1.19405}, {-1.09034, 0.68026, 1.2796}, {-0.83258, 1.34414, -1.34504}, {-0.52187, 1.27914, 1.38913}, {-0.17776, 0.52039, -1.41144}, {0.17776, -0.52039, 1.41144}, {0.52187, -1.27914, -1.38913}, {0.83258, -1.34414, 1.34504}, {1.09034, -0.68026, -1.2796}, {1.27927, 0.353, 1.19405}, {1.38799, 1.1954, -1.0897}, {1.41005, 1.39059, 0.96806}, {1.34418, 0.83256, -0.83128}, {1.19423, -0.17605, 0.68131}, {0.96917, -1.08873, -0.52057}, {0.6827, -1.41074, 0.35174}, {0.35266, -0.96775, -0.17722}, {-0.0, -0.0, -0.0}};
        Assertions.assertEquals(S_tr_.length, expected_S_tr_.length);
        for (int i=0; i<expected_S_tr_.length; i++) {
            Assertions.assertArrayEquals(S_tr_[i], expected_S_tr_[i], 0.0001);
        }
    }

    @Test
    public void ICAUnitVarCubeTest1() {
        ICA obj = new ICA(this.X_fit, "cube", "unit-variance", this.w_init, 200, 1E-4, 1.0);
        obj.fit();
        double[][] S_ = obj.transform();
        double[][] expected_S_ = {{0.04458, -0.70543, 0.34593}, {0.72484, -1.09564, 0.53649}, {-0.00471, -1.49545, 0.98181}, {1.16394, -1.11197, 0.96754}, {-0.34385, -0.99693, 1.44183}, {1.25475, 0.02493, 1.17716}, {-0.8619, 0.26859, 1.60411}, {1.18097, 1.31164, 1.0656}, {-1.28554, 1.07846, 1.39244}, {1.17948, 1.59582, 0.60927}, {-1.42093, 0.647, 0.83689}, {1.31331, 0.64323, -0.09318}, {-1.31331, -0.64323, 0.09318}, {1.42093, -0.647, -0.83689}, {-1.17948, -1.59582, -0.60927}, {1.28554, -1.07846, -1.39244}, {-1.18097, -1.31164, -1.0656}, {0.8619, -0.26859, -1.60411}, {-1.25475, -0.02493, -1.17716}, {0.34385, 0.99693, -1.44183}, {-1.16394, 1.11197, -0.96754}, {0.00471, 1.49545, -0.98181}, {-0.72484, 1.09564, -0.53649}, {-0.04458, 0.70543, -0.34593}, {0.0, 0.0, 0.0}};
        Assertions.assertEquals(S_.length, expected_S_.length);
        for (int i=0; i<expected_S_.length; i++) {
            Assertions.assertArrayEquals(S_[i], expected_S_[i], 0.0001);
        }

        double[][] S_tr_ = obj.transform(this.X_tr);
        double[][] expected_S_tr_ = {{0.2972, -0.9061, -0.42776}, {-0.18495, -1.45001, -0.66576}, {0.52298, -0.94821, -1.1059}, {-0.86206, -0.31144, -1.03982}, {0.34385, 0.99693, -1.44183}, {-1.50588, 1.15916, -1.11703}, {0.5066, 1.4067, -1.51904}, {-1.47964, 0.09664, -0.99409}, {1.13886, -0.38686, -1.35732}, {-1.17948, -1.59582, -0.60927}, {1.48548, -0.95136, -0.85234}, {-1.27831, -0.80826, 0.0848}, {1.27831, 0.80826, -0.0848}, {-1.48548, 0.95136, 0.85234}, {1.17948, 1.59582, 0.60927}, {-1.13886, 0.38686, 1.35732}, {1.47964, -0.09664, 0.99409}, {-0.5066, -1.4067, 1.51904}, {1.50588, -1.15916, 1.11703}, {-0.34385, -0.99693, 1.44183}, {0.86206, 0.31144, 1.03982}, {-0.52298, 0.94821, 1.1059}, {0.18495, 1.45001, 0.66576}, {-0.2972, 0.9061, 0.42776}, {-0.0, -0.0, -0.0}};
        Assertions.assertEquals(S_tr_.length, expected_S_tr_.length);
        for (int i=0; i<expected_S_tr_.length; i++) {
            Assertions.assertArrayEquals(S_tr_[i], expected_S_tr_[i], 0.0001);
        }
    }
}
