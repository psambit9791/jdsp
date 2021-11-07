package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.windows.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class TestWindows {
    private double[] signal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Test
    public void RectangularSymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w1 = new Rectangular(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Rectangular w0 = new Rectangular(-2);});
    }

    @Test
    public void RectangularASymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w2 = new Rectangular(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Rectangular w0 = new Rectangular(-2, false);});
    }

    @Test
    public void RectangularApplyTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Rectangular(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void RectangularApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Rectangular(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BoxcarSymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w1 = new Boxcar(len);
        double[] out = w1.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Boxcar w0 = new Boxcar(-2);});
    }

    @Test
    public void BoxcarASymTest() {
        int len = 10;
        double[] result = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        _Window w2 = new Boxcar(len, false);
        double[] out = w2.getWindow();
        Assertions.assertArrayEquals(result, out, 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Boxcar w0 = new Boxcar(-2, false);});
    }

    @Test
    public void BoxcarApplyTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Boxcar(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void GeneralCosineApplyTest() {
        double[] result = {0, 0.6304, -2.1801, 0, 24.5575, 29.4690, 0, -5.8136, 2.8368, 0};
        double[] weights = {1, 2, 2, 1};
        _Window w = new GeneralCosine(signal.length, weights);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void GeneralCosineApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        double[] weights = {1, 2, 2, 1};
        _Window w = new GeneralCosine(signal.length, weights);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void HammingApplyTest() {
        double[] result = {8.000000e-02,3.752391e-01,1.380366e+00,3.080000e+00,4.861293e+00,5.833552e+00,5.390000e+00,3.680975e+00,1.688576e+00,8.000000e-01};
        _Window w = new Hamming(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void HammingApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Hamming(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void HanningApplyTest() {
        double[] result = {0,2.339556e-01,1.239528e+00,3.000000e+00,4.849232e+00,5.819078e+00,5.250000e+00,3.305407e+00,1.052800e+00,0};
        _Window w = new Hanning(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void HanningApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        _Window w = new Hanning(signal.length);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void BlackmanApplyTest() {
        double[] result = {0,1.017393e-01,7.740015e-01,2.520000e+00,4.755649e+00,5.706779e+00,4.410000e+00,2.064004e+00,4.578267e-01,0};
        _Window w = new Blackman(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BlackmanApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Blackman(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void BlackmanHarrisApplyTest() {
        double[] result = {6.000000e-05,3.014235e-02,4.411187e-01,2.082300e+00,4.658296e+00,5.589956e+00,3.644025e+00,1.176316e+00,1.356406e-01,6.000000e-04};
        _Window w = new BlackmanHarris(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BlackmanHarrisApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new BlackmanHarris(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void PoissonApplyTest() {
        double[] result = {1.110000e-02,6.040000e-02,2.463000e-01,8.924000e-01,3.032500e+00,3.639000e+00,1.561700e+00,6.568000e-01,2.718000e-01,1.110000e-01};
        _Window w = new Poisson(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void PoissonApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Poisson(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void FlatTopApplyTest() {
        double[] result = {-4.210510e-04,-4.034406e-02,-2.105971e-01,7.928421e-01,4.312382e+00,5.174858e+00,1.387474e+00,-5.615923e-01,-1.815483e-01,-4.210510e-03};
        _Window w = new FlatTop(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void FlatTopApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new FlatTop(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void NuttallApplyTest() {
        double[] result = {3.628000e-04,3.578200e-02,4.667884e-01,2.116919e+00,4.666101e+00,5.599321e+00,3.704609e+00,1.244769e+00,1.610190e-01,3.628000e-03};
        _Window w = new Nuttall(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void NuttallApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Nuttall(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void GaussianApplyTest() {
        double[] result = {7.960000e-02,4.326000e-01,1.373400e+00,3.019200e+00,4.846000e+00,5.815200e+00,5.283600e+00,3.662400e+00,1.946700e+00,7.960000e-01};
        double std = 2.0;
        _Window w = new Gaussian(signal.length, std);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void GaussianApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double std = 2.0;
        _Window w = new Gaussian(signal.length, std);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void TukeyApplyTest() {
        double[] result = {0,8.263518e-01,2.909539e+00,4,5,6,7,7.758770e+00,3.718583e+00,0};
        double alpha = 0.5;
        _Window w = new Tukey(signal.length, alpha);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void TukeyApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        double alpha = 0.5;
        _Window w = new Tukey(signal.length, alpha);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void TriangularEvenApplyTest() {
        double[] result = {1.000000e-01,6.000000e-01,1.500000e+00,2.800000e+00,4.500000e+00,5.400000e+00,4.900000e+00,4,2.700000e+00,1};
        _Window w = new Triangular(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void TriangularEvenApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Triangular(signal.length);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void BartlettApplyTest() {
        double[] result = {0,4.444444e-01,1.333333e+00,2.666667e+00,4.444444e+00,5.333333e+00,4.666667e+00,3.555556e+00,2,0};
        _Window w = new Bartlett(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BartlettApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        _Window w = new Bartlett(signal.length);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void BartlettHannApplyTest() {
        double[] result = {0,2.844729e-01,1.262041e+00,2.920000e+00,4.752083e+00,5.702499e+00,5.110000e+00,3.365443e+00,1.280128e+00,0};
        _Window w = new BartlettHann(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BartlettHannApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        _Window w = new BartlettHann(signal.length);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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
    public void BohmanApplyTest() {
        double[] result = {0,6.874710e-02,7.088912e-01,2.435991e+00,4.720754e+00,5.664904e+00,4.262984e+00,1.890377e+00,3.093620e-01,0};
        _Window w = new Bohman(signal.length);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void BohmanApplyInverseTest() {
        double[] result = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        _Window w = new Bohman(signal.length);
        double[] out = w.applyWindow(signal);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        out = w.applyInverseWindow(out);
        System.setErr(stderr);  // Reset to standard stderr
        Assertions.assertArrayEquals(result, out, 0.001);
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

    @Test
    public void KaiserApplyTest() {
        double[] result = {7.726867e-06,1.399204e-02,3.114775e-01,1.850866e+00,4.599353e+00,5.519223e+00,3.239015e+00,8.306066e-01,6.296416e-02,7.726867e-05};
        _Window w = new Kaiser(signal.length, 14);
        double[] out = w.applyWindow(signal);
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void KaiserApplyInverseTest() {
        double[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        _Window w = new Kaiser(signal.length, 14);
        double[] out = w.applyWindow(signal);
        out = w.applyInverseWindow(out);
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
