package com.onyx.jdsp.transform;

import org.apache.commons.math3.complex.Complex;

public class DiscreteFourier {

    private double[] signal;
    private Complex[] output = null;

    public DiscreteFourier(double[] s) {
        this.signal = s;
    }

    public void fft() {
        Complex[] out = new Complex[this.signal.length];

        for (int k=0; k<out.length; k++) {
            double real = 0;
            double imag = 0;
            for (int t=0; t<out.length; t++) {
                double angle = 2*Math.PI*t*k/out.length;
                real += this.signal[t]*Math.cos(angle);
                imag += -this.signal[t]*Math.sin(angle);
            }
            out[k] = new Complex(real, imag);
        }
        this.output = out;
    }

    // Refer to this post to know the relevance of only positive: https://dsp.stackexchange.com/a/4827
    // About plotting, please refer here: https://stackoverflow.com/a/25735274

    public double[] returnAbsolute(boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute fft() function before returning result");
        }
        double[] fftout;

        if (onlyPositive) {
            fftout = new double[this.output.length/2];
            for (int i=0; i<fftout.length; i++) {
                fftout[i] = this.output[i].abs();
            }
        }
        else{
            fftout = new double[this.output.length];
            for (int i=0; i<fftout.length; i++) {
                fftout[i] = this.output[i].abs();
            }
        }
        return fftout;
    }

    public double[][] returnFull(boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute fft() function before returning result");
        }
        double[][] fftout;

        if (onlyPositive) {
            fftout = new double[this.output.length/2][2];
            for (int i=0; i<fftout.length; i++) {
                fftout[i][0] = this.output[i].getReal();
                fftout[i][1] = this.output[i].getImaginary();
            }
        }
        else{
            fftout = new double[this.output.length][2];
            for (int i=0; i<fftout.length; i++) {
                fftout[i][0] = this.output[i].getReal();
                fftout[i][1] = this.output[i].getImaginary();
            }
        }
        return fftout;
    }
}
