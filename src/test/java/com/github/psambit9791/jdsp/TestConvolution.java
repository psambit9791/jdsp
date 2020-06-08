package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.signal.Convolution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConvolution {

    final double[] signal1 = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel1 = {1.0, 0.0, 1.0, 0.5};

    final double[] signal2 = {2, 8, 0, 4, 1, 9, 9, 0};
    final double[] kernel2 = {1, 3, 1, 3};

    @Test
    public void fullConvolutionTest() {
        final double[] result1 = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};
        final double[] result2 = {2, 14, 26, 18, 37, 16, 49, 39, 36, 27,  0};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("full");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve();
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void sameConvolutionTest() {
        final double[] result1 = {2.0, 4.0, 6.5, 9.0, 5.5};
        final double[] result2 = {14, 26, 18, 37, 16, 49, 39, 36};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("same");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve("same");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void validConvolutionTest() {
        final double[] result1 = {6.5, 9.0};
        final double[] result2 = {18, 37, 16, 49, 39};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve("valid");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve("valid");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DReflectTest() {
        final double[] result1 = {4.5,  6.5,  9. , 10.5, 11};
        final double[] result2 = {32, 18, 37, 16, 49, 39, 36, 36};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("reflect");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d();
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DConstantTest() {
        final double[] result1 = {4. , 6.5, 9. , 5.5, 7.};
        final double[] result2 = {26, 18, 37, 16, 49, 39, 36, 27};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("constant");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("constant");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DNearestTest() {
        final double[] result1 = {4.5,  6.5,  9. , 10.5, 12.};
        final double[] result2 = {32, 18, 37, 16, 49, 39, 36, 27};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("nearest");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("nearest");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DMirrorTest() {
        final double[] result1 = {5. ,  6.5,  9. ,  9.5, 10.};
        final double[] result2 = {50, 18, 37, 16, 49, 39, 45, 63};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("mirror");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("mirror");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }

    @Test
    public void convolve1DWrapTest() {
        final double[] result1 = {6.5, 6.5, 9. , 6.5, 9.};
        final double[] result2 = {26, 18, 37, 16, 49, 39, 38, 41};

        Convolution con1 = new Convolution(this.signal1, this.kernel1);
        double[] out = con1.convolve1d("wrap");
        Assertions.assertArrayEquals(result1, out, 0.001);

        Convolution con2 = new Convolution(this.signal2, this.kernel2);
        out = con2.convolve1d("wrap");
        Assertions.assertArrayEquals(result2, out, 0.001);
    }
}
