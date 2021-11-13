/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.FastFourier;
import com.github.psambit9791.jdsp.transform.InverseFastFourier;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestFFTAndIFFT {

    public double[] signal = new double[0];

    private void buildSignal(int length) throws IOException {
        double[] temp = UtilMethods.electrocardiogram();
        int counts = length/temp.length;
        int rems = length%temp.length;
        for (int i=0; i<counts; i++) {
            this.signal = UtilMethods.concatenateArray(this.signal, temp);
        }
        double[] extra = UtilMethods.splitByIndex(temp, 0, rems);
        this.signal = UtilMethods.concatenateArray(this.signal, extra);
    }

    private void extendSignal() {
        double power = Math.log(this.signal.length)/Math.log(2);
        double raised_power = Math.ceil(power);
        int new_length = (int)(Math.pow(2, raised_power));
        if (new_length != this.signal.length) {
            this.signal = UtilMethods.zeroPadSignal(this.signal, new_length - this.signal.length);
        }
    }

    /**
     * FFT improvements work for signals with more than 200 datapoints. If signal is less than 200 points, use DFT.
     * @throws IOException
     */
    @Test
    public void fftTimeTest() throws IOException{
        this.buildSignal(1000);
        this.extendSignal();
        FastFourier f1 = new FastFourier(this.signal);
        DiscreteFourier f2 = new DiscreteFourier(this.signal);

        long start = System.nanoTime();
        f1.transform();
        long stop = System.nanoTime();
        long fft_dur = stop - start;

        start = System.nanoTime();
        f2.transform();
        stop = System.nanoTime();
        long dft_dur = stop - start;

        Assertions.assertTrue(fft_dur < dft_dur);
        Assertions.assertArrayEquals(f1.getMagnitude(false), f2.getMagnitude(false), 0.001);
        Assertions.assertArrayEquals(f1.getMagnitude(true), f2.getMagnitude(true), 0.001);
    }

    @Test
    public void fftIfftMirror() throws IOException {
        this.buildSignal(10);
        this.extendSignal();

        double[] imag = new double[this.signal.length];
        Arrays.fill(imag, 0.0);

        FastFourier f1 = new FastFourier(this.signal);
        f1.transform();
        Complex[] output = f1.getComplex(true);

        InverseFastFourier f2 = new InverseFastFourier(output, true);
        f2.transform();

        Assertions.assertArrayEquals(this.signal, f2.getReal(), 0.0001);
        Assertions.assertArrayEquals(imag, f2.getImaginary(), 0.0001);
    }

    @Test
    public void fftIfftNonMirror() throws IOException {
        this.buildSignal(10);
        this.extendSignal();

        double[] imag = new double[this.signal.length];
        Arrays.fill(imag, 0.0);

        FastFourier f1 = new FastFourier(this.signal);
        f1.transform();
        Complex[] output = f1.getComplex(false);

        InverseFastFourier f2 = new InverseFastFourier(output, false);
        f2.transform();
        double[] recovered = f2.getReal();

        Assertions.assertArrayEquals(this.signal, recovered, 0.0001);
        Assertions.assertArrayEquals(imag, f2.getImaginary(), 0.0001);
    }
}
