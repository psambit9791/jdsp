package com.onyx.signal;

import org.apache.commons.math3.analysis.function.Atan;
import org.apache.commons.math3.analysis.function.Atan2;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

public class Hilbert {

    private double[] signal;
    private double[] h;
    private Complex[] output;

    public Hilbert(double[] s) {
        this.signal = s;
        this.h = new double[s.length];
        Arrays.fill(this.h, 0);
        this.fillH();
    }

    private void fillH() {
        this.h[0] = 1;
        if (this.h.length%2 == 0) {
            for (int i=0; i<this.h.length/2; i++) {
                this.h[i] = 2;
            }
            this.h[this.h.length/2] = 1;
        }
        else {
            for (int i=0; i<(this.h.length+1)/2; i++) {
                this.h[i] = 2;
            }
        }
    }

    public void hilbert_transform() {
        DiscreteFourier dft = new DiscreteFourier(this.signal);
        dft.fft();
        double[][] dftOut = dft.returnFull(false);

        double[][] modOut = new double[dftOut.length][dftOut[0].length];

        for (int i=0; i<modOut.length; i++) {
            modOut[i][0] = dftOut[i][0] * this.h[i];
            modOut[i][1] = dftOut[i][1] * this.h[i];
        }

        InverseDiscreteFourier idft = new InverseDiscreteFourier(modOut);
        idft.ifft();
        this.output = idft.get_as_complex();
    }

    public double[][] get_output() {
        double[][] out = new double[this.output.length][2];
        for (int i=0; i<out.length; i++) {
            out[i][0] = this.output[i].getReal();
            out[i][1] = this.output[i].getImaginary();
        }
        return out;
    }

    public double[] get_amplitude_envelope() {
        double[] sig = new double[this.output.length];
        for (int i=0; i<sig.length; i++) {
            sig[i] = this.output[i].abs();
        }
        return sig;
    }

    public double[] get_instantaneous_phase() {
        double[] sig = new double[this.output.length];
        Atan2 ang = new Atan2();
        for (int i=0; i<sig.length; i++) {
            sig[i] = ang.value(this.output[i].getImaginary(), output[i].getReal());
        }
        double[] out = UtilMethods.unwrap(sig);
        return out;
    }

    public double[] get_instantaneous_frequqncy(double Fs) {
        double[] temp = this.get_instantaneous_phase();
        double cons = 2 * Math.PI;
        double[] sig = UtilMethods.diff(temp);
        for (int i=0; i<sig.length; i++) {
            sig[i] = (sig[i]/cons)*Fs;
        }
        return sig;
    }
}
