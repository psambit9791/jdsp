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

import com.github.psambit9791.jdsp.misc.Random;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRandom {

    @Test
    public void SetSeedNormalTest() {
        Random r1 = new Random();
        double x1 = r1.randomNormalSample();
        double x2 = r1.randomNormalSample();
        r1.setSeed(42);
        double x3 = r1.randomNormalSample();
        Assertions.assertEquals(x1, x3);
        Assertions.assertNotEquals(x1, x2);
    }

    @Test
    public void RandomNormalSampleTest1() {
        Random r1 = new Random();
        double x = r1.randomNormalSample();
        Assertions.assertEquals(x, -0.19138793, 0.0001);
    }

    @Test
    public void RandomNormalSampleTest2() {
        Random r1 = new Random(110);
        double x = r1.randomNormalSample();
        Assertions.assertEquals(x, 0.44693599, 0.0001);
    }

    @Test
    public void RandomNormal1DTest1() {
        Random r1 = new Random();
        double[] x = r1.randomNormal1D(new int[]{4});
        double[] result = {-0.19138793,  0.78346759,  1.18426079, -2.29774864};
//        System.out.println(Arrays.toString(x));
        Assertions.assertArrayEquals(x, result, 0.0001);
    }

    @Test
    public void RandomNormal1DTest2() {
        Random r1 = new Random(110);
        double[] x = r1.randomNormal1D(new int[]{4});
        double[] result = {0.446936  ,  0.38804182, -0.59835451,  0.65971754};
        Assertions.assertArrayEquals(x, result, 0.0001);
    }

    @Test
    public void RandomNormal1DCustomMeanAndSDTest() {
        for ( int i=0; i<25; i++) {
            Random r1 = new Random(i);
            r1.setMeanAndSD(10.0, 1.0);
            double[] x = r1.randomNormal1D(new int[]{4});
            for (double v : x) {
                double temp = UtilMethods.round(v, 0);
                Assertions.assertTrue((temp <= 13) && (temp >= 3)); //99.7% data stays within 3 SD
            }
        }
    }

    @Test
    public void RandomNormal2DTest1() {
        Random r1 = new Random();
        double[][] x = r1.randomNormal2D(new int[]{2,2});
        double[][] result = {{-0.19138793,  0.78346759},  {1.18426079, -2.29774864}};
        for (int i=0; i<2; i++) {
            Assertions.assertArrayEquals(x[i], result[i], 0.0001);
        }
    }

    @Test
    public void RandomNormal2DTest2() {
        Random r1 = new Random(110);
        double[][] x = r1.randomNormal2D(new int[]{2,2});
        double[][] result = {{0.446936  ,  0.38804182}, {-0.59835451,  0.65971754}};
        for (int i=0; i<2; i++) {
            Assertions.assertArrayEquals(x[i], result[i], 0.0001);
        }
    }

    @Test
    public void RandomNormal3DTest1() {
        Random r1 = new Random();
        double[][][] x = r1.randomNormal3D(new int[]{2,2,2});
        double[][][] result = {{{-0.19138793,  0.78346759},  {1.18426079, -2.29774864}}, {{0.21612532,  -0.187860745},  {-0.90168376, -0.86411062}}};
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                Assertions.assertArrayEquals(x[i][j], result[i][j], 0.0001);
            }
        }
    }

    @Test
    public void RandomNormal3DTest2() {
        Random r1 = new Random(110);
        double[][][] x = r1.randomNormal3D(new int[]{2,2,2});
        double[][][] result = {{{0.446936  ,  0.38804182}, {-0.59835451,  0.65971754}}, {{1.01605335  ,  0.411612084}, {0.05522813,  0.109814776}}};
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                Assertions.assertArrayEquals(x[i][j], result[i][j], 0.0001);
            }
        }
    }

    @Test
    public void RandomDoubleSampleTest1() {
        Random r1 = new Random();
        for (int i=0; i<25; i++){
            double x = r1.randomDoubleSample();
            Assertions.assertTrue(x <= 1.0 && x >= 0.0);
        }
    }

    @Test
    public void RandomDouble1DTest1() {
        Random r1 = new Random();
        double[] x = r1.randomDouble1D(new int[]{10});
        for (int i=0; i<10; i++){
            Assertions.assertTrue(x[i] <= 1.0 && x[i] >= 0.0);
        }
    }

    @Test
    public void RandomDouble2DTest1() {
        Random r1 = new Random();
        double[][] x = r1.randomDouble2D(new int[]{5,5});
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++){
                Assertions.assertTrue(x[i][j] <= 1.0 && x[i][j]>=0.0);
            }
        }
    }

    @Test
    public void RandomDouble3DTest1() {
        Random r1 = new Random();
        double[][][] x = r1.randomDouble3D(new int[]{4,4,4});
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++){
                for (int k=0; k<4; k++) {
                    Assertions.assertTrue(x[i][j][k] <= 1.0 && x[i][j][k]>=0.0);
                }
            }
        }
    }

    @Test
    public void SetSeedIntTest() {
        Random r1 = new Random();
        double x1 = r1.randomIntSample(10);
        double x2 = r1.randomIntSample(10);
        r1.setSeed(42);
        double x3 = r1.randomIntSample(10);
        Assertions.assertEquals(x1, x3);
        Assertions.assertNotEquals(x1, x2);
    }

    @Test
    public void RandomIntSampleTest1() {
        Random r1 = new Random();
        for (int i=0; i<25; i++){
            int x = r1.randomIntSample(10);
            Assertions.assertTrue(x <= 10 && x>=0);
        }
    }

    @Test
    public void RandomIntSampleTest2() {
        Random r1 = new Random();
        for (int i=0; i<25; i++){
            int x = r1.randomIntSample(5, 15);
            Assertions.assertTrue(x <= 15 && x>=5);
        }
    }

    @Test
    public void RandomInt1DTest1() {
        Random r1 = new Random();
        int[] x = r1.randomInt1D(new int[]{10}, 10);
        for (int i=0; i<10; i++){
            Assertions.assertTrue(x[i] <= 10 && x[i]>=0);
        }
    }

    @Test
    public void RandomInt1DTest2() {
        Random r1 = new Random();
        int[] x = r1.randomInt1D(new int[]{10}, 5, 15);
        for (int i=0; i<10; i++){
            Assertions.assertTrue(x[i] <= 15 && x[i]>=5);
        }
    }

    @Test
    public void RandomInt2DTest1() {
        Random r1 = new Random();
        int[][] x = r1.randomInt2D(new int[]{5,5}, 10);
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++){
                Assertions.assertTrue(x[i][j] <= 10 && x[i][j]>=0);
            }
        }
    }

    @Test
    public void RandomInt2DTest2() {
        Random r1 = new Random();
        int[][] x = r1.randomInt2D(new int[]{5,5}, 5, 15);
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++){
                Assertions.assertTrue(x[i][j] <= 15 && x[i][j]>=5);
            }
        }
    }

    @Test
    public void RandomInt3DTest1() {
        Random r1 = new Random();
        int[][][] x = r1.randomInt3D(new int[]{4,4,4}, 10);
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++){
                for (int k=0; k<4; k++) {
                    Assertions.assertTrue(x[i][j][k] <= 10 && x[i][j][k]>=0);
                }
            }
        }
    }

    @Test
    public void RandomInt3DTest2() {
        Random r1 = new Random();
        int[][][] x = r1.randomInt3D(new int[]{4,4,4}, 5, 15);
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++){
                for (int k=0; k<4; k++) {
                    Assertions.assertTrue(x[i][j][k] <= 15 && x[i][j][k]>=5);
                }
            }
        }
    }

}
