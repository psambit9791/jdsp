package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.windows.BoxCar;
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
}
