package com.onyx.jdsp;

import com.onyx.jdsp.signal.Convolution;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestConvolution {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel = {1.0, 0.0, 1.0, 0.5};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};
    @Test
    public void fullConvolutionTest() {
        final double[] result = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};

        Convolution con1 = new Convolution(this.signal, this.kernel);
        double[] out = con1.convolve("full");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void sameConvolutionTest() {
        final double[] result = {2.0, 4.0, 6.5, 9.0, 5.5};

        Convolution con1 = new Convolution(this.signal, this.kernel);
        double[] out = con1.convolve("same");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void validConvolutionTest() {
        final double[] result = {6.5, 9.0};

        Convolution con1 = new Convolution(this.signal, this.kernel);
        double[] out = con1.convolve("valid");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void convolve1DReflectTest() {
        double[] result = {32, 18, 37, 16, 49, 39, 36, 36};
        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        double[] out = con2.convolve1d("reflect");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void convolve1DConstantTest() {
        double[] result = {26, 18, 37, 16, 49, 39, 36, 27};
        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        double[] out = con2.convolve1d("constant");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void convolve1DNearestTest() {
        double[] result = {32, 18, 37, 16, 49, 39, 36, 27};
        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        double[] out = con2.convolve1d("nearest");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void convolve1DMirrorTest() {
        double[] result = {50, 18, 37, 16, 49, 39, 45, 63};
        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        double[] out = con2.convolve1d("mirror");
        assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void convolve1DWrapTest() {
        double[] result = {26, 18, 37, 16, 49, 39, 38, 41};
        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        double[] out = con2.convolve1d("wrap");
        assertArrayEquals(result, out, 0.001);
    }
}
