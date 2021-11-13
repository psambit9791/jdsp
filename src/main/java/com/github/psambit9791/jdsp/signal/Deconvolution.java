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

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.InverseDiscreteFourier;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.*;

import java.util.Arrays;

/**
 * <h1>Deconvolution</h1>
 * The Deconvolution class implements methods to recover signals which are convolved with a kernel. This function only
 * works if the signal has been convolved in 'same' mode or 'full' mode. Given the convolved signal, the convolutional
 * kernel used and the mode of convolution; the process can recover the original signal.
 * For 'full' mode, FFT-based deconvolution is used.
 * For 'same' mode, the overlap-and-add based deconvolution is used.
 * NOTE: Outputs may vary from the *scipy.signal.deconvolve* implementation which uses inverse filtering.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Deconvolution {

    private double[] signal;
    private double[] kernel;
    private final int sig_len;
    private final int ker_len;

    /**
     * This constructor initialises the prerequisites required to perform deconvolution.
     * @param signal The convolved signal
     * @param window Kernel used for convolution
     */
    public Deconvolution(double[] signal, double[] window) {
        this.sig_len = signal.length;
        this.ker_len = window.length;
        this.kernel = window;
        this.signal = signal;
    }

    /**
     * This method configures the relevant variables for deconvolution using DFT
     */
    private void preprocess_dft() {
        int shape = Math.max(this.signal.length, this.kernel.length);
        double[] padding = new double[shape - Math.min(this.signal.length, this.kernel.length)];
        Arrays.fill(padding, 0.0);
        if (this.signal.length < shape) {
            this.signal = UtilMethods.concatenateArray(this.signal, padding);
        }
        else if (this.kernel.length < shape){
            this.kernel = UtilMethods.concatenateArray(this.kernel, padding);
        }
    }

    /**
     * This method performs deconvolution using DFT
     * Used for the 'full' mode operation
     */
    private double[] deconvolve_dft() {
        this.preprocess_dft();
        DiscreteFourier ffts = new DiscreteFourier(this.signal);
        ffts.transform();
        DiscreteFourier fftk = new DiscreteFourier(this.kernel);
        fftk.transform();

        Complex[] s = ffts.getComplex(false);
        Complex[] w = fftk.getComplex(false);

        Complex[] s_w = new Complex[s.length];

        for (int i=0; i<s_w.length; i++) {
            s_w[i] = s[i].divide(w[i].add(Float.MIN_NORMAL));
        }

        InverseDiscreteFourier idf = new InverseDiscreteFourier(UtilMethods.complexTo2D(s_w), false);
        idf.transform();

        return Arrays.copyOfRange(UtilMethods.round(idf.getReal(), 3), 0, this.sig_len - this.ker_len + 1);
    }

    /**
     * This method performs deconvolution by solving a multivariate equation formed from the Overlap and Add method
     * Used for the 'same' mode operation
     */
    private double[] deconvolve_ola() {
        double[][] matA = new double[this.sig_len][this.sig_len];
        double[] krn_temp = UtilMethods.padSignal(UtilMethods.reverse(this.kernel), "constant", this.sig_len - 1);
        int index = 0;
        int start_point = this.ker_len/2 + this.sig_len - 1;
        for (int i = start_point; i >= start_point - this.sig_len + 1; i--) {
            matA[index] = UtilMethods.splitByIndex(krn_temp, i, i + this.sig_len);
            index++;
        }
        RealVector soln;
        try {
            DecompositionSolver solver = new LUDecomposition(MatrixUtils.createRealMatrix(matA)).getSolver();
            soln = solver.solve(new ArrayRealVector(this.signal, false));
        }
        catch (SingularMatrixException e) {
            DecompositionSolver solver = new SingularValueDecomposition(MatrixUtils.createRealMatrix(matA)).getSolver();
            soln = solver.solve(new ArrayRealVector(this.signal, false));
        }

        return UtilMethods.round(soln.toArray(), 3);
    }

    /**
     * This function is a hyper-function which determines which type of deconvolution to perform depending on the mode.
     * @param mode String to state which mode of convolution was performed on the convolved signal.
     *             Can  either be 'full' or 'same'. 'valid' mode is not supported.
     * @return double[] The original signal
     */
    public double[] deconvolve(String mode) {
        double[] out;
        if (mode.equals("full")) {
            out = this.deconvolve_dft();
        }
        else if (mode.equals("same")) {
            out = this.deconvolve_ola();
        }
        else {
            throw new IllegalArgumentException("mode has to be one of 'full' or 'same'.");
        }
        return out;
    }
}
