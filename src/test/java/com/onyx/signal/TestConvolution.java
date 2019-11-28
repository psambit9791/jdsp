package com.onyx.signal;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestConvolution {

    @Test
    public void fullConvolutionTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {1.0, 0.0, 1.0, 0.5};
        final double[] result = {1.0, 2.0, 4.0, 6.5, 9.0, 5.5, 7.0, 2.5};

        Convolution con1 = new Convolution(signal, kernel, "full");
        con1.convolve();
        double[] out = con1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }

    @Test
    public void sameConvolutionTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {1.0, 0.0, 1.0, 0.5};
        final double[] result = {2.0, 4.0, 6.5, 9.0, 5.5};

        Convolution con1 = new Convolution(signal, kernel, "same");
        con1.convolve();
        double[] out = con1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }

    @Test
    public void validConvolutionTest() {
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] kernel = {1.0, 0.0, 1.0, 0.5};
        final double[] result = {6.5, 9.0};

        Convolution con1 = new Convolution(signal, kernel, "valid");
        con1.convolve();
        double[] out = con1.getOutput();
        assertTrue(Arrays.equals(out, result));
    }
}
