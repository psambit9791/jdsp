package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.windows.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWindows {

    @Test
    public void BoxCarSymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        BoxCar w1 = new BoxCar(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void BoxCarASymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        BoxCar w2 = new BoxCar(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void GeneralCosineSymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0.0, 0.3152, -0.7267, -0.0, 4.9115, 4.9115, 0.0, -0.7267, 0.3152, 0.0};
        GeneralCosine w1 = new GeneralCosine(len, weights);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void GeneralCosineASymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0.0, 0.309, -0.4271, -0.809, 2.9271, 6.0, 2.9271, -0.809, -0.4271, 0.309};
        GeneralCosine w2 = new GeneralCosine(len, weights, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void HammingSymTest() {
        int len = 10;
        double[] result = {0.08, 0.1876, 0.4601, 0.77, 0.9723, 0.9723, 0.77, 0.4601, 0.1876, 0.08};
        Hamming w1 = new Hamming(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void HammingASymTest() {
        int len = 10;
        double[] result = {0.08, 0.1679, 0.3979, 0.6821, 0.9121, 1.0, 0.9121, 0.6821, 0.3979, 0.1679};
        Hamming w2 = new Hamming(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void HanningSymTest() {
        int len = 10;
        double[] result = {0.0, 0.117, 0.4132, 0.75, 0.9698, 0.9698, 0.75, 0.4132, 0.117, 0.0};
        Hanning w1 = new Hanning(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void HanningASymTest() {
        int len = 10;
        double[] result = {0.0, 0.0955, 0.3455, 0.6545, 0.9045, 1.0, 0.9045, 0.6545, 0.3455, 0.0955};
        Hanning w2 = new Hanning(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BlackmanSymTest() {
        int len = 10;
        double[] result = {-0.0, 0.0509, 0.258, 0.63, 0.9511, 0.9511, 0.63, 0.258, 0.0509, -0.0};
        Blackman w1 = new Blackman(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void BlackmanASymTest() {
        int len = 10;
        double[] result = {-0.0, 0.0402, 0.2008, 0.5098, 0.8492, 1.0, 0.8492, 0.5098, 0.2008, 0.0402};
        Blackman w2 = new Blackman(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BlackmanHarrisSymTest() {
        int len = 10;
        double[] result = {0.0001, 0.0151, 0.147, 0.5206, 0.9317, 0.9317, 0.5206, 0.147, 0.0151, 0.0001};
        BlackmanHarris w1 = new BlackmanHarris(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void BlackmanHarrisASymTest() {
        int len = 10;
        double[] result = {0.0001, 0.011, 0.103, 0.3859, 0.7938, 1.0, 0.7938, 0.3859, 0.103, 0.011};
        BlackmanHarris w2 = new BlackmanHarris(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void PoissonSymTest() {
        int len = 10;
        double[] result = {0.0111, 0.0302, 0.0821, 0.2231, 0.6065, 0.6065, 0.2231, 0.0821, 0.0302, 0.0111};
        Poisson w1 = new Poisson(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void PoissonASymTest() {
        int len = 10;
        double[] result = {0.0067, 0.0183, 0.0498, 0.1353, 0.3679, 1.0, 0.3679, 0.1353, 0.0498, 0.0183};
        Poisson w2 = new Poisson(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
