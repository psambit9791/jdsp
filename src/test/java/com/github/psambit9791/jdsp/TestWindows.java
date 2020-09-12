package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.windows.BoxCar;
import com.github.psambit9791.jdsp.windows.GeneralCosine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWindows {

    @Test
    public void BoxCarSymTest() {
        int len = 10;
        double[] result = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        BoxCar w1 = new BoxCar(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void BoxCarASymTest() {
        int len = 10;
        double[] result = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        BoxCar w2 = new BoxCar(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void GeneralCosineSymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0, 0.315, -0.727, -0, 4.911, 4.911, 0, -0.727, 0.315,  0};
        GeneralCosine w1 = new GeneralCosine(len, weights);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void GeneralCosineASymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0, 0.309, -0.427, -0.809, 2.927, 6, 2.927, -0.809, -0.427, 0.309};
        GeneralCosine w2 = new GeneralCosine(len, weights, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
