package com.onyx.signal;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestConvolution {

    final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
    final double[] kernel = {1.0, 0.0, 1.0, 0.5};
    @Test
    public void fullConvolutionTest() {
        final double[] result = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};

        Convolution con1 = new Convolution(this.signal, this.kernel, "full");
        double[] out = con1.convolve();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void sameConvolutionTest() {
        final double[] result = {2.0, 4.0, 6.5, 9.0, 5.5};

        Convolution con1 = new Convolution(this.signal, this.kernel, "same");
        double[] out = con1.convolve();
        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void validConvolutionTest() {
        final double[] result = {6.5, 9.0};

        Convolution con1 = new Convolution(this.signal, this.kernel, "valid");
        double[] out = con1.convolve();
        assertArrayEquals(out, result, 0.001);
    }
}
