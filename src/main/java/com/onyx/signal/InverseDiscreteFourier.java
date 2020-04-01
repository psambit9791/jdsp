package com.onyx.signal;

public class InverseDiscreteFourier {

    private double[][] complex_sequence;
    private double[] real_sequence;
    private String seqType;
    private double[] signal;

    public InverseDiscreteFourier(double[][] seq) {
        this.complex_sequence = seq;
        this.seqType = "complex";
    }

    public InverseDiscreteFourier(double[] seq) {
        this.real_sequence = seq;
        this.seqType = "real";
    }

    public double[] ifft() {
        if (this.seqType.equals("complex")) {
            this.ifft_complex();
        }
        else if (this.seqType.equals("real")) {
            this.ifft_real();
        }
        return this.signal;
    }


    private void ifft_real() {
        double[] out = new double[this.real_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigVal = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigVal += this.real_sequence[m]*Math.cos(angle);
            }
            out[t] = sigVal/out.length;
        }
        this.signal = out;
    }

    private void ifft_complex() {
        double[] out = new double[this.complex_sequence.length];

        for (int t=0; t<out.length; t++) {
            double sigVal = 0;
            for (int m=0; m<out.length; m++) {
                double angle = 2*Math.PI*t*m/out.length;
                sigVal += (this.complex_sequence[m][0]*Math.cos(angle) - this.complex_sequence[m][1]*Math.sin(angle));
            }
            out[t] = sigVal/out.length;
        }
        this.signal = out;
    }
}
