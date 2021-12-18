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

package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.signal.ComplexDeconvolution;
import com.github.psambit9791.jdsp.signal.Deconvolution;
import com.github.psambit9791.jdsp.signal.Generate;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.StatUtils;


/**
 * <h1>Inverse Continuous Wavelet Transform</h1>
 * The InverseContinuousWavelet class recovers a signal from the CWT output given the wavelet used to compute the CWT. The Wavelet
 * Transform is a redundant transform, however since the deconvolution process may use an least-squares solution, the
 * final signal is averaged over multiple (usually not all) inverse CWT outputs.
 * The inverse wavelet transform works on the same wavelet wavelet functions as the WaveletTransform class - Ricker,
 * Morlet and Paul.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class InverseContinuousWavelet {

    private Complex[][] transformed;
    private int[] widths;

    public enum waveletType {
        MORLET,
        RICKER,
        PAUL
    }

    /**
     * This constructor initialises the prerequisites required to perform inverse wavelet transform.
     * @param transformed The wavelet transformed signal
     * @param widths The widths used for the wavelet functions
     */
    public InverseContinuousWavelet(Complex[][] transformed, int[] widths) {
        if (widths.length != transformed.length) {
            throw new IllegalArgumentException("Number of widths should be same as number of wavelet transformations");
        }
        if (transformed.length == 0) {
            throw new IllegalArgumentException("transformed matrix cannot be empty.");
        }
        else if (transformed.length <= 3) {
            this.transformed = transformed;
            this.widths = widths;
        }
        else {
            this.transformed = new Complex[][] {transformed[0], transformed[widths.length/2], transformed[widths.length-1]};
            this.widths = new int[] {widths[0], widths[widths.length/2], widths[widths.length-1]};
        }

    }

    /**
     * Performs ICWT for signals transformed using the Paul wavelet
     * @param args Wavelet property
     * @return Returns the reconstructed signal for each transformed input and their corresponding wavelet
     */
    private double[][] icwt_paul(int args) {
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();

        for (int i=0; i<out.length; i++) {
            double norm = Math.sqrt(1.0/this.widths[i]);
            Complex[] wavelet = gp.generatePaulComplex(args, this.widths[i], (double)this.widths[i]);
            for (int w=0; w<wavelet.length; w++) {
                wavelet[w] = wavelet[w].multiply(norm); //Normalization
            }
            ComplexDeconvolution cdc = new ComplexDeconvolution(this.transformed[i], wavelet);
            cdc.deconvolve("same");
            out[i] = cdc.getRealOutput();
        }
        return out;
    }

    /**
     * Performs ICWT for signals transformed using the Morlet wavelet
     * @param args Wavelet property
     * @return Returns the reconstructed signal for each transformed input and their corresponding wavelet
     */
    private double[][] icwt_morlet(double args) {
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();
        for (int i=0; i<out.length; i++) {
            int N = Math.min(10*this.widths[i], this.transformed[0].length);
            Complex[] wavelet = gp.generateMorletCWTComplex(N, args, this.widths[i]);
            wavelet = UtilMethods.reverse(wavelet);
            ComplexDeconvolution cdc = new ComplexDeconvolution(this.transformed[i], wavelet);
            cdc.deconvolve("same");
            out[i] = cdc.getRealOutput();
        }
        return out;
    }

    /**
     * Performs ICWT for signals transformed using the Ricker wavelet
     * @return Returns the reconstructed signal for each transformed input and their corresponding wavelet
     */
    private double[][] icwt_ricker() {
        double[][] out = new double[this.transformed.length][this.transformed[0].length];
        Generate gp = new Generate();
        for (int i=0; i<out.length; i++) {
            int N = Math.min(10*this.widths[i], this.transformed[0].length);
            double[] data = new double[this.transformed[0].length];
            for (int j=0; j<data.length; j++) {
                data[j] = this.transformed[i][j].getReal();
            }
            double[] wavelet = gp.generateRicker(N, this.widths[i]);
            wavelet = UtilMethods.reverse(wavelet);
            Deconvolution cdc = new Deconvolution(data, wavelet);
            out[i] = cdc.deconvolve("same");
        }
        return out;
    }

    /**
     * This function is a hyper-function which determines the ICWT process depending on the wavelet type and returns the
     * recovered signal.
     * @param wavelet_type Which wavelet was used for the CWT transformation
     * @param wavelet_args The wavelet property used for wavelet generation.
     *                     Ignored for Ricker, omega0 for Morlet and wavelet order for Paul
     * @return double[] The recovered signal averaged over all the inverse transformed outputs
     * @throws IllegalArgumentException If wavelet_type is not one of RICKER, MORLET or PAUL
     */
    public double[] transform(waveletType wavelet_type, double wavelet_args) throws IllegalArgumentException{
        double[][] out;
        switch (wavelet_type) {
            case RICKER:
                out = this.icwt_ricker();
                break;
            case MORLET:
                out = this.icwt_morlet(wavelet_args);
                break;
            case PAUL:
                out = this.icwt_paul((int)wavelet_args);
                break;
            default:
                throw new ArithmeticException("wavelet_type must be RICKER, MORLET or PAUL");
        }
        out = UtilMethods.transpose(out);
        double[] signal = new double[this.transformed[0].length];
        for (int i=0; i<signal.length; i++) {
            signal[i] = StatUtils.mean(out[i]);
        }
        return signal;
    }
}
