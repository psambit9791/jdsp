package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.windows.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestWindows {

    @Test
    public void BoxCarSymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w1 = new Boxcar(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Boxcar w0 = new Boxcar(-2);});
    }

    @Test
    public void BoxCarASymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w2 = new Boxcar(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Boxcar w0 = new Boxcar(-2, false);});
    }

    @Test
    public void GeneralCosineSymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0.0, 0.3152, -0.7267, -0.0, 4.9115, 4.9115, 0.0, -0.7267, 0.3152, 0.0};
        _Window w1 = new GeneralCosine(len, weights);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {GeneralCosine w0 = new GeneralCosine(-2, weights);});
    }

    @Test
    public void GeneralCosineASymTest() {
        int len = 10;
        double[] weights = {1, 2, 2, 1};
        double[] result = {0.0, 0.309, -0.4271, -0.809, 2.9271, 6.0, 2.9271, -0.809, -0.4271, 0.309};
        _Window w2 = new GeneralCosine(len, weights, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {GeneralCosine w0 = new GeneralCosine(-2, weights, false);});
    }

    @Test
    public void HammingSymTest() {
        int len = 10;
        double[] result = {0.08, 0.1876, 0.4601, 0.77, 0.9723, 0.9723, 0.77, 0.4601, 0.1876, 0.08};
        _Window w1 = new Hamming(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Hamming w0 = new Hamming(-2);});

    }

    @Test
    public void HammingASymTest() {
        int len = 10;
        double[] result = {0.08, 0.1679, 0.3979, 0.6821, 0.9121, 1.0, 0.9121, 0.6821, 0.3979, 0.1679};
        _Window w2 = new Hamming(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Hamming w0 = new Hamming(-2, false);});
    }

    @Test
    public void HanningSymTest() {
        int len = 10;
        double[] result = {0.0, 0.117, 0.4132, 0.75, 0.9698, 0.9698, 0.75, 0.4132, 0.117, 0.0};
        _Window w1 = new Hanning(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Hanning w0 = new Hanning(-2);});
    }

    @Test
    public void HanningASymTest() {
        int len = 10;
        double[] result = {0.0, 0.0955, 0.3455, 0.6545, 0.9045, 1.0, 0.9045, 0.6545, 0.3455, 0.0955};
        _Window w2 = new Hanning(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Hanning w0 = new Hanning(-2, false);});
    }

    @Test
    public void BlackmanSymTest() {
        int len = 10;
        double[] result = {-0.0, 0.0509, 0.258, 0.63, 0.9511, 0.9511, 0.63, 0.258, 0.0509, -0.0};
        _Window w1 = new Blackman(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Blackman w0 = new Blackman(-2);});
    }

    @Test
    public void BlackmanASymTest() {
        int len = 10;
        double[] result = {-0.0, 0.0402, 0.2008, 0.5098, 0.8492, 1.0, 0.8492, 0.5098, 0.2008, 0.0402};
        _Window w2 = new Blackman(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Blackman w0 = new Blackman(-2, false);});
    }

    @Test
    public void BlackmanHarrisSymTest() {
        int len = 10;
        double[] result = {0.0001, 0.0151, 0.147, 0.5206, 0.9317, 0.9317, 0.5206, 0.147, 0.0151, 0.0001};
        _Window w1 = new BlackmanHarris(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {BlackmanHarris w0 = new BlackmanHarris(-2);});
    }

    @Test
    public void BlackmanHarrisASymTest() {
        int len = 10;
        double[] result = {0.0001, 0.011, 0.103, 0.3859, 0.7938, 1.0, 0.7938, 0.3859, 0.103, 0.011};
        _Window w2 = new BlackmanHarris(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {BlackmanHarris w0 = new BlackmanHarris(-2, false);});
    }

    @Test
    public void PoissonSymTest() {
        int len = 10;
        double[] result = {0.0111, 0.0302, 0.0821, 0.2231, 0.6065, 0.6065, 0.2231, 0.0821, 0.0302, 0.0111};
        _Window w1 = new Poisson(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Poisson w0 = new Poisson(-2);});
    }

    @Test
    public void PoissonASymTest() {
        int len = 10;
        double[] result = {0.0067, 0.0183, 0.0498, 0.1353, 0.3679, 1.0, 0.3679, 0.1353, 0.0498, 0.0183};
        _Window w2 = new Poisson(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Poisson w0 = new Poisson(-2, false);});
    }

    @Test
    public void FlatTopSymTest() {
        int len = 10;
        double[] result = {-0.0004, -0.0202, -0.0702, 0.1982, 0.8625, 0.8625, 0.1982, -0.0702, -0.0202, -0.0004};
        _Window w1 = new FlatTop(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {FlatTop w0 = new FlatTop(-2);});
    }

    @Test
    public void FlatTopASymTest() {
        int len = 10;
        double[] result = {-0.0004, -0.0156, -0.0677, 0.0545, 0.6069, 1.0, 0.6069, 0.0545, -0.0677, -0.0156};
        _Window w2 = new FlatTop(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {FlatTop w0 = new FlatTop(-2, false);});
    }

    @Test
    public void NuttallSymTest() {
        int len = 10;
        double[] result = {0.0004, 0.0179, 0.1556, 0.5292, 0.9332, 0.9332, 0.5292, 0.1556, 0.0179, 0.0004};
        _Window w1 = new Nuttall(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Nuttall w0 = new Nuttall(-2);});
    }

    @Test
    public void NuttallASymTest() {
        int len = 10;
        double[] result = {0.0004, 0.0133, 0.1105, 0.3956, 0.7983, 1.0, 0.7983, 0.3956, 0.1105, 0.0133};
        _Window w2 = new Nuttall(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Nuttall w0 = new Nuttall(-2, false);});
    }

    @Test
    public void GaussianSymTest() {
        int len = 10;
        double std = 2.0;
        double[] result = {0.0796, 0.2163, 0.4578, 0.7548, 0.9692, 0.9692, 0.7548, 0.4578, 0.2163, 0.0796};
        _Window w1 = new Gaussian(len, std);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Gaussian w0 = new Gaussian(-2, std);});
    }

    @Test
    public void GaussianASymTest() {
        int len = 10;
        double std = 2.0;
        double[] result = {0.0439, 0.1353, 0.3247, 0.6065, 0.8825, 1.0, 0.8825, 0.6065, 0.3247, 0.1353};
        _Window w2 = new Gaussian(len, std,false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Gaussian w0 = new Gaussian(-2, std, false);});
    }

    @Test
    public void TukeySymTest() {
        int len = 10;
        double alpha = 0.5;
        double[] result = {0.0, 0.4132, 0.9698, 1.0, 1.0, 1.0, 1.0, 0.9698, 0.4132, 0.0};
        _Window w1 = new Tukey(len, alpha);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Tukey w0 = new Tukey(-2, alpha);});
    }

    @Test
    public void TukeyASymTest() {
        int len = 10;
        double alpha = 0.5;
        double[] result = {0.0, 0.3455, 0.9045, 1.0, 1.0, 1.0, 1.0, 1.0, 0.9045, 0.3455};
        _Window w2 = new Tukey(len, alpha,false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Tukey w0 = new Tukey(-2, alpha, false);});
    }

    @Test
    public void TriangularEvenSymTest() {
        int len = 10;
        double[] result = {0.1, 0.3, 0.5, 0.7, 0.9, 0.9, 0.7, 0.5, 0.3, 0.1};
        _Window w1 = new Triangular(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Triangular w0 = new Triangular(-2);});
    }

    @Test
    public void TriangularEvenASymTest() {
        int len = 10;
        double[] result = {0.1667, 0.3333, 0.5, 0.6667, 0.8333, 1.0, 0.8333, 0.6667, 0.5, 0.3333};
        _Window w2 = new Triangular(len,false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Triangular w0 = new Triangular(-2, false);});
    }

    @Test
    public void TriangularOddSymTest() {
        int len = 13;
        double[] result = {0.1429, 0.2857, 0.4286, 0.5714, 0.7143, 0.8571, 1.0, 0.8571, 0.7143, 0.5714, 0.4286, 0.2857, 0.1429};
        _Window w1 = new Triangular(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void TriangularOddASymTest() {
        int len = 13;
        double[] result = {0.0714, 0.2143, 0.3571, 0.5, 0.6429, 0.7857, 0.9286, 0.9286, 0.7857, 0.6429, 0.5, 0.3571, 0.2143};
        _Window w2 = new Triangular(len,false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
    }

    @Test
    public void BartlettSymTest() {
        int len = 10;
        double[] result = {0.0, 0.2222, 0.4444, 0.6667, 0.8889, 0.8889, 0.6667, 0.4444, 0.2222, 0.0};
        _Window w1 = new Bartlett(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Bartlett w0 = new Bartlett(-2);});
    }

    @Test
    public void BartlettASymTest() {
        int len = 10;
        double[] result = {0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 0.8, 0.6, 0.4, 0.2};
        _Window w2 = new Bartlett(len,false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Bartlett w0 = new Bartlett(-2, false);});
    }

    @Test
    public void BartlettHannSymTest() {
        int len = 10;
        double[] result = {0.0, 0.1422, 0.4207, 0.73, 0.9504, 0.9504, 0.73, 0.4207, 0.1422, 0.0};
        _Window w1 = new BartlettHann(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {BartlettHann w0 = new BartlettHann(-2);});
    }

    @Test
    public void BartlettHannASymTest() {
        int len = 10;
        double[] result = {0.0, 0.1206, 0.3586, 0.6414, 0.8794, 1.0, 0.8794, 0.6414, 0.3586, 0.1206};
        _Window w2 = new BartlettHann(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {BartlettHann w0 = new BartlettHann(-2, false);});
    }

    @Test
    public void BohmanSymTest() {
        int len = 10;
        double[] result = {0.0, 0.0344, 0.2363, 0.609, 0.9442, 0.9442, 0.609, 0.2363, 0.0344, 0.0};
        _Window w1 = new Bohman(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Bohman w0 = new Bohman(-2);});
    }

    @Test
    public void BohmanASymTest() {
        int len = 10;
        double[] result = {0.0, 0.0253, 0.1791, 0.4881, 0.8343, 1.0, 0.8343, 0.4881, 0.1791, 0.0253};
        _Window w2 = new Bohman(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Bohman w0 = new Bohman(-2, false);});
    }

    @Test
    public void KaiserSymTest() {
        int len = 10;
        double[] result = {0.0 , 0.007 , 0.1038, 0.4627, 0.9199, 0.9199, 0.4627, 0.1038, 0.007 , 0.0};
        _Window w1 = new Kaiser(len, 14);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Kaiser w0 = new Kaiser(-2, 14);});
    }

    @Test
    public void KaiserASymTest() {
        int len = 10;
        double[] result = {0.0, 0.0048, 0.0682, 0.3249, 0.7615, 1.0, 0.7615, 0.3249, 0.0682, 0.0048};
        _Window w2 = new Kaiser(len, 14, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Kaiser w0 = new Kaiser(-2, 14, false);});
    }
}
