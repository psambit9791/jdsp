package com.github.psambit9791.jdsp.transform;

import org.apache.commons.math3.complex.Complex;

/**
 * <h1>Discrete Fourier Transform</h1>
 * The DiscreteFourier class applies the discrete fourier transform on the input signal and
 * provides different representations of the output to be returned (absolute values or complex values)
 * and if the output should be mirrored or not-mirrored
 * Reference <a href="https://mathworld.wolfram.com/DiscreteFourierTransform.html">article</a> for more information on discrete fourier transform.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class DiscreteFourier {

    private double[] signal;
    private Complex[] output = null;

    /**
     * This constructor initialises the prerequisites required to use DiscreteFourier.
     * @param s Signal to be transformed
     */
    public DiscreteFourier(double[] s) {
        this.signal = s;
    }

    /**
     * This function performs the fourier transform on the input signal
     */
    public void dft() {
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

    /**
     * Returns the absolute value of the discrete fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[] The decimated signal
     */
    public double[] returnAbsolute(boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute dft() function before returning result");
        }
        double[] dftout;

        if (onlyPositive) {
            dftout = new double[this.output.length/2];
            for (int i=0; i<dftout.length; i++) {
                dftout[i] = this.output[i].abs();
            }
        }
        else{
            dftout = new double[this.output.length];
            for (int i=0; i<dftout.length; i++) {
                dftout[i] = this.output[i].abs();
            }
        }
        return dftout;
    }

    /**
     * Returns the complex value of the discrete fourier transformed sequence
     * @param onlyPositive Set to True if non-mirrored output is required
     * @return double[][] The decimated signal
     */
    public double[][] returnFull(boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute dft() function before returning result");
        }
        double[][] dftout;

        if (onlyPositive) {
            dftout = new double[this.output.length/2][2];
            for (int i=0; i<dftout.length; i++) {
                dftout[i][0] = this.output[i].getReal();
                dftout[i][1] = this.output[i].getImaginary();
            }
        }
        else{
            dftout = new double[this.output.length][2];
            for (int i=0; i<dftout.length; i++) {
                dftout[i][0] = this.output[i].getReal();
                dftout[i][1] = this.output[i].getImaginary();
            }
        }
        return dftout;
    }
}
