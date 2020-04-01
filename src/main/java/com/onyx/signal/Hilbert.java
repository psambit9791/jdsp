package com.onyx.signal;

import java.util.Arrays;

public class Hilbert {

    private double[] signal;
    private double[] h;

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

    public double[] hilbert_transform() {
        DiscreteFourier dft = new DiscreteFourier(this.signal);
        dft.fft();
        double[][] dftOut = dft.returnFull(false);

        double[][] modOut = new double[dftOut.length][dftOut[0].length];

        for (int i=0; i<modOut.length; i++) {
            modOut[i][0] = dftOut[i][0] * this.h[i];
            modOut[i][1] = dftOut[i][1] * this.h[i];
        }

        InverseDiscreteFourier idft = new InverseDiscreteFourier(modOut);
        double[] hilbert_out = idft.ifft();

        return hilbert_out;
    }
}
