package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform._Fourier;
import com.github.psambit9791.jdsp.transform.InverseShortTimeFourier;
import com.github.psambit9791.jdsp.transform.ShortTimeFourier;
import com.github.psambit9791.jdsp.windows.Bartlett;
import com.github.psambit9791.jdsp.windows.Hamming;
import com.github.psambit9791.jdsp.windows._Window;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class TestInverseShortTimeFourier {
    private final double[] signal1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    // 20Hz Sine + 200Hz Sine sampled @ 100Hz with Nyquist of 50Hz
    private final double[] signal2 = {0.   ,  0.658,  0.293, -0.04 ,  0.634,  1.212,  0.756,  0.402, 1.035,  1.482,  0.901,
            0.496,  1.062,  1.362,  0.655,  0.208, 0.718,  0.895,  0.1  , -0.346,  0.154,  0.26 , -0.562, -0.943,
            -0.396, -0.302, -1.085, -1.344, -0.703, -0.576, -1.279, -1.384, -0.632, -0.463, -1.08 , -1.04 , -0.2  ,
            -0.017, -0.579, -0.437, 0.437,  0.579,  0.017,  0.2  ,  1.04 ,  1.08 ,  0.463,  0.632, 1.384,  1.279,
            0.576,  0.703,  1.344,  1.085,  0.302,  0.396, 0.943, 0.562, -0.26 , -0.154,  0.346, -0.1  , -0.895, -0.718,
            -0.208, -0.655, -1.362, -1.062, -0.496, -0.901, -1.482, -1.035, -0.402, -0.756, -1.212, -0.634,  0.04 ,
            -0.293, -0.658,  0.};

    @Test
    public void testInverseShortTimeFourierOverlap1() {
        double[] expected = signal1;

        int frameLength = 10;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierOverlap2() {
        double[] expected = signal2;

        int frameLength = 10;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierNoOverlap1() {
        double[] expected = signal1;

        int frameLength = 10;
        int overlap = 0;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength, overlap);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierNoOverlap2() {
        double[] expected = signal2;

        int frameLength = 10;
        int overlap = 0;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength, overlap);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierZeroPadOverlap1() {
        double[] expected = signal1;

        int frameLength = 10;
        int overlap = frameLength/2;
        int fourierLength = 17;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength, overlap, fourierLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierZeroPadOverlap2() {
        double[] expected = signal2;

        int frameLength = 10;
        int overlap = frameLength/2;
        int fourierLength = 17;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength, overlap, fourierLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierZeroPadNoOverlap1() {
        double[] expected = signal1;

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength, overlap, fourierLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierZeroPadNoOverlap2() {
        double[] expected = signal2;

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength, overlap, fourierLength);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierHamming1() {
        double[] expected = signal1;

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;
        _Window window = new Hamming(frameLength);

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength, overlap, fourierLength, window);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap, window);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierHamming2() {
        double[] expected = signal2;

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;
        _Window window = new Hamming(frameLength);

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength, overlap, fourierLength, window);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap, window);
        istft.transform();
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierBartlett1() {
        double[] expected = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 0};

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;
        _Window window = new Bartlett(frameLength);

        ShortTimeFourier stft = new ShortTimeFourier(this.signal1, frameLength, overlap, fourierLength, window);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap, window);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        istft.transform();
        System.setErr(stderr);  // Reset to standard stderr
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierBartlett2() {
        double[] expected = {0,  0.658,  0.293, -0.04 ,  0.634,  1.212,  0.756,  0.402, 1.035,  0,  0,
                0.496,  1.062,  1.362,  0.655,  0.208, 0.718,  0.895,  0.1  , 0,  0,  0.26 , -0.562, -0.943,
                -0.396, -0.302, -1.085, -1.344, -0.703, 0, 0, -1.384, -0.632, -0.463, -1.08 , -1.04 , -0.2  ,
                -0.017, -0.579, 0, 0,  0.579,  0.017,  0.2  ,  1.04 ,  1.08 ,  0.463,  0.632, 1.384,  0,
                0,  0.703,  1.344,  1.085,  0.302,  0.396, 0.943, 0.562, -0.26 , 0,  0, -0.1  , -0.895, -0.718,
                -0.208, -0.655, -1.362, -1.062, -0.496, 0, 0, -1.035, -0.402, -0.756, -1.212, -0.634,  0.04 ,
                -0.293, -0.658,  0};

        int frameLength = 10;
        int fourierLength = 17;
        int overlap = 0;
        _Window window = new Bartlett(frameLength);

        ShortTimeFourier stft = new ShortTimeFourier(this.signal2, frameLength, overlap, fourierLength, window);
        stft.transform();
        _Fourier[] dfts = stft.getOutput();
        InverseShortTimeFourier istft = new InverseShortTimeFourier(dfts, frameLength, overlap, window);
        // We don't want the System.err.println message
        PrintStream stderr = System.err;    // Save standard stderr
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) { }
        }));
        istft.transform();
        System.setErr(stderr);  // Reset to standard stderr
        double[] outputReal = istft.getReal();

        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierLongSignal1Divisible() throws IOException {
        double[] expected = UtilMethods.electrocardiogram();
        int frameLength = 6000;
        ShortTimeFourier stft = new ShortTimeFourier(expected, frameLength);
        stft.transform();
        _Fourier[] sf1 = stft.getOutput();
        InverseShortTimeFourier isf1 = new InverseShortTimeFourier(sf1, frameLength);
        isf1.transform();
        double[] outputReal = isf1.getReal();
        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }

    @Test
    public void testInverseShortTimeFourierLongSignal1Indivisible() throws IOException {
        double[] expected = UtilMethods.electrocardiogram();
        int frameLength = 20000;
        int outputLength = expected.length - expected.length%frameLength;
        ShortTimeFourier stft = new ShortTimeFourier(expected, frameLength);
        stft.transform();
        _Fourier[] sf1 = stft.getOutput();
        InverseShortTimeFourier isf1 = new InverseShortTimeFourier(sf1, frameLength);
        isf1.transform();
        double[] outputReal = isf1.getReal();
        expected = UtilMethods.splitByIndex(expected, 0, outputLength);
        Assertions.assertArrayEquals(expected, outputReal, 0.001);
    }
}
