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

    final double[] func_input = {0.38, 0.53, 0.80, 0.86, 0.11, 0.88, 0.72, 0.87, 0.37, 0.52};
    final double[][] signal = {{0.85889,0.91091,0.49403}, {0.37271,0.09291,0.52412}, {0.55513,0.90252,0.29854}, {0.95566,0.46096,0.46311}, {0.73667,0.45202,0.98478}, {0.81621,0.99943,0.50113}, {0.10109,0.16242,0.39807}, {0.92849,0.70937,0.72791}, {0.60911,0.16062,0.86333}, {0.59655,0.81078,0.02617}, {0.09178,0.03515,0.29002}, {0.34519,0.53489,0.78907}, {0.66275,0.1665,0.45712}, {0.44171,0.30841,0.00693}, {0.55149,0.04506,0.41934}, {0.70371,0.23858,0.33067}, {0.5894,0.67483,0.60415}, {0.04993,0.78238,0.32462}, {0.56179,0.6952,0.98125}, {0.76636,0.32895,0.58823}};

    @Test
    public void ICAFuncLogcoshTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double[] gx= {0.36271, 0.48538, 0.66404, 0.69626, 0.10956, 0.70642, 0.61691, 0.70137, 0.35399, 0.4777};
        double g_x = 0.69701;
        ICA i1 = new ICA(this.signal);
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
        ICA i1 = new ICA(this.signal);
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
        ICA i1 = new ICA(this.signal);
        Method privMethod = i1.getClass().getDeclaredMethod("cube_", double[].class);
        privMethod.setAccessible(true);
        privMethod.invoke(i1, (Object) this.func_input);
        Assertions.assertArrayEquals(gx, i1.gx, 0.00001);
        Assertions.assertEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICATest1() {
        double[][] w_init = {{-0.34889445,  0.98370343,  0.58092283}, {0.07028444,  0.77753268,  0.58195875}, {1.47179053,  1.66318101, -0.26117712}};
        ICA obj = new ICA(this.signal, "logcosh", "unit-variance", w_init, 200, 1E-4, 1);
        obj.fit();
    }
}
