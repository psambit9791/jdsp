package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.UtilMethods;
import org.apache.commons.math3.analysis.function.Atan2;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

/**
 * <h1>Hilbert Transform</h1>
 * The Hilbert class applies the Hilbert transform on the input signal and produces an analytical signal.
 * The analytical signal can be used for finding the amplitude envelope, instantaneous phase and instantaneous frequency of the original signal.
 * Reference <a href="https://en.wikipedia.org/wiki/Hilbert_transform">article</a> for more information on Hilbert transform.
 * Reference <a href="https://tomroelandts.com/articles/what-is-an-analytic-signal">article</a> for more information on analytical signals.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Hilbert {

    private double[] signal;
    private double[] h;
    private Complex[] output;

    /**
     * This constructor initialises the prerequisites required to use Hilbert.
     * @param s Signal to be transformed
     */
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

    /**
     * This function performs the hilbert transform on the input signal
     */
    public void hilbert_transform() {
        DiscreteFourier dft = new DiscreteFourier(this.signal);
        dft.dft();
        double[][] dftOut = dft.returnFull(false);

        double[][] modOut = new double[dftOut.length][dftOut[0].length];

        for (int i=0; i<modOut.length; i++) {
            modOut[i][0] = dftOut[i][0] * this.h[i];
            modOut[i][1] = dftOut[i][1] * this.h[i];
        }

        InverseDiscreteFourier idft = new InverseDiscreteFourier(modOut);
        idft.idft();
        this.output = idft.get_as_complex();
    }

    /**
     * Returns the complex value of the generated analytical signal as a 2D matrix.
     * @return double[][] The decimated signal
     */
    public double[][] get_output() {
        double[][] out = new double[this.output.length][2];
        for (int i=0; i<out.length; i++) {
            out[i][0] = this.output[i].getReal();
            out[i][1] = this.output[i].getImaginary();
        }
        return out;
    }

    /**
     * Returns the amplitude envelope generated analytical signal.
     * @return double[] The decimated signal
     */
    public double[] get_amplitude_envelope() {
        double[] sig = new double[this.output.length];
        for (int i=0; i<sig.length; i++) {
            sig[i] = this.output[i].abs();
        }
        return sig;
    }

    /**
     * Returns the instantaneous phase generated analytical signal.
     * @return double[] The decimated signal
     */
    public double[] get_instantaneous_phase() {
        double[] sig = new double[this.output.length];
        Atan2 ang = new Atan2();
        for (int i=0; i<sig.length; i++) {
            sig[i] = ang.value(this.output[i].getImaginary(), output[i].getReal());
        }
        double[] out = UtilMethods.unwrap(sig);
        return out;
    }

    /**
     * Returns the instantaneous frequency generated analytical signal.
     * @param Fs Sampling Frequency to be used
     * @return double[] The decimated signal
     */
    public double[] get_instantaneous_frequency(double Fs) {
        double[] temp = this.get_instantaneous_phase();
        double cons = 2 * Math.PI;
        double[] sig = UtilMethods.diff(temp);
        for (int i=0; i<sig.length; i++) {
            sig[i] = (sig[i]/cons)*Fs;
        }
        return sig;
    }
}
