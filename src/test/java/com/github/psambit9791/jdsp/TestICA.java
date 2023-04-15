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

public class TestICA {

    final double[][] func_input = {{0.38, 0.53}, {0.80, 0.86}, {0.11, 0.88}, {0.72, 0.87}, {0.37, 0.52}};

    @Test
    public void ICAFuncLogcoshTest1() {
        double[][] gx= {{0.36270747, 0.48538109}, {0.66403677, 0.69625767}, {0.10955847, 0.70641932}, {0.6169093 , 0.70137413}, {0.35399171, 0.47770001}};
        double[] g_x = {0.81642424, 0.53714021, 0.74448434, 0.56374862, 0.82324628};
        ICA i1 = new ICA(this.func_input);
        i1.logcosh_(this.func_input);
        for (int i=0; i< gx.length; i++) {
            Assertions.assertArrayEquals(gx[i], i1.gx[i], 0.00001);
        }
        Assertions.assertArrayEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICAFuncExpTest1() {
        double[][] gx= {{0.35353103, 0.46055257}, {0.58091923, 0.59415034}, {0.10933651, 0.59748066}, {0.55560145, 0.59588233}, {0.34552086, 0.45424142}};
        double[] g_x = {0.71043863, 0.22065843, 0.56755678, 0.26907011, 0.72166654};
        ICA i1 = new ICA(this.func_input);
        i1.exp_(this.func_input);
        for (int i=0; i< gx.length; i++) {
            Assertions.assertArrayEquals(gx[i], i1.gx[i], 0.00001);
        }
        Assertions.assertArrayEquals(g_x, i1.g_x, 0.00001);
    }

    @Test
    public void ICAFuncCubeTest1() {
        double[][] gx= {{0.054872, 0.148877}, {0.512   , 0.636056}, {0.001331, 0.681472}, {0.373248, 0.658503}, {0.050653, 0.140608}};
        double[] g_x = {0.63795, 2.0694 , 1.17975, 1.91295, 0.61095};
        ICA i1 = new ICA(this.func_input);
        i1.cube_(this.func_input);
        for (int i=0; i< gx.length; i++) {
            Assertions.assertArrayEquals(gx[i], i1.gx[i], 0.00001);
        }
        Assertions.assertArrayEquals(g_x, i1.g_x, 0.00001);
    }
}
