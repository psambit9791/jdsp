package com.onyx.jdsp;

import org.apache.commons.math3.complex.Complex;

public class InverseDiscreteFourier {

    private double[][] complex_sequence;
    private double[] real_sequence;
    private String seqType;
    private Complex[] signal;

    public InverseDiscreteFourier(double[][] seq) {
        this.complex_sequence = seq;
        this.seqType = "complex";
    }

    public InverseDiscreteFourier(double[] seq) {
        this.real_sequence = seq;
        this.seqType = "real";
    }

    public void ifft() {
        if (this.seqType.equals("complex")) {
            this.ifft_complex();
        }
        else if (this.seqType.equals("real")) {
            this.ifft_real();
        }
    }

    public double[] get_real_signal() {
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].getReal();
        }
        return ret;
    }

    public double[] get_absolute_signal() {
        double[] ret = new double[this.signal.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = this.signal[i].abs();
        }
        return ret;
    }

    public double[][] get_complex_signal() {
        double[][] ret = new double[this.signal.length][2];
        for (int i=0; i<ret.length; i++) {
            ret[i][0] = this.signal[i].getReal();
            ret[i][1] = this.signal[i].getImaginary();
        }
        return ret;
    }

    protected Complex[] get_as_complex() {
        return this.signal;
    }


    private void ifft_real() {
        Complex[] out = new Complex[this.real_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigValR += this.real_sequence[m]*Math.cos(angle);
                sigValI += this.real_sequence[m]*Math.sin(angle);
            }
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }

    private void ifft_complex() {
        Complex[] out = new Complex[this.complex_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigValR = 0;
            double sigValI = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigValR += (this.complex_sequence[m][0]*Math.cos(angle) - this.complex_sequence[m][1]*Math.sin(angle));
                sigValI += (this.complex_sequence[m][0]*Math.sin(angle) + this.complex_sequence[m][1]*Math.cos(angle));
            }
//            System.out.println(sigValR+","+sigValI);
            out[t] = Complex.valueOf(sigValR/out.length, sigValI/out.length);
        }
        this.signal = out;
    }
}
