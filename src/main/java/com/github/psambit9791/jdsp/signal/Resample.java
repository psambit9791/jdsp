/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;


import com.github.psambit9791.jdsp.filter.FIRWin1;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform.InverseDiscreteFourier;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Resample</h1>
 * The Resample class samples the signal again with a new number of samples. Resampling works in two modes - using the
 * Fourier transform and the Polyphase filtering.
 * For the Fourier transform method; the new spacing equals the number of previous samples divided by the new number of samples
 * and the previous spacing. The signal is expected to be periodic.
 * For the Polyphase filtering method; the signal is upsampled by 'up' factor, filtered using a zero-phase low-pass FIR filter
 * (FIRWin1), and downsampled by 'down' factor.
 * Resampling works only for real signals.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class Resample {

    private double[] signal;
    private boolean poly;
    private int num;
    private int up;
    private int down;
    private double beta;
    private double cval;
    private double[] output;
    private String padtype;
    private String upfirdn_mode;

    /**
     * This constructor initialises the prerequisites required to use Resample.
     * @param num The number of samples required after resampling
     */
    public Resample(int num) {
        this.num = num;
        this.poly = false;
    }

    /**
     * This constructor initialises the prerequisites required to use Polyphase Resample. Uses a Kaiser window of beta
     * 5 to construct the low-pass filter.
     * @param up The number of samples required after resampling
     * @param down The number of samples required after resampling
     * @param padtype Kind of signal extension to be used to extend the boundaries. Can be one of 'mean', 'median', 'min',
     *                'max' or 'constant'
     * @param cval Only used when padtype is "constant"
     */
    public Resample(int up, int down, String padtype, double cval) {
        if ((up < 1) && (down < 1)) {
            throw new IllegalArgumentException("up and down must be greater than 0");
        }
        this.up = up;
        this.down = down;
        this.beta = 5.0;
        this.cval = cval;
        if (!padtype.equals("mean") && !padtype.equals("median") && !padtype.equals("min") && !padtype.equals("max") &&
                !padtype.equals("constant") && !padtype.equals("edge")) {
            throw new ArithmeticException("padtype must be 'mean', 'median', 'min', 'max', 'constant' or 'edge'");
        }
        this.padtype = padtype;
        this.upfirdn_mode = padtype;
        this.poly = true;
    }

    /**
     * This constructor initialises the prerequisites required to use Polyphase Resample. Uses a Kaiser window of provided
     * width to construct the low-pass filter. Sets cval to 0 for "constant" padtype.
     * @param up The number of samples required after resampling
     * @param down The number of samples required after resampling
     * @param beta Beta parameter of the Kaiser window for designing the low-pass filter
     * @param padtype Kind of signal extension to be used to extend the boundaries. Can be one of 'mean', 'median', 'min',
     *                'max' or 'constant'
     */
    public Resample(int up, int down, double beta, String padtype) {
        if ((up < 1) && (down < 1)) {
            throw new IllegalArgumentException("up and down must be greater than 0");
        }
        this.up = up;
        this.down = down;
        this.beta = beta;
        this.cval = 0;
        if (!padtype.equals("mean") && !padtype.equals("median") && !padtype.equals("min") && !padtype.equals("max") &&
                !padtype.equals("constant") && !padtype.equals("edge")) {
            throw new ArithmeticException("padtype must be 'mean', 'median', 'min', 'max', 'constant' or 'edge'");
        }
        this.padtype = padtype;
        this.upfirdn_mode = padtype;
        this.poly = true;
    }

    /**
     * This constructor initialises the prerequisites required to use Polyphase Resample. Uses a Kaiser window of beta
     * 5 to construct the low-pass filter. Sets cval to 0 for "constant" padtype.
     * @param up The number of samples required after resampling
     * @param down The number of samples required after resampling
     * @param padtype Kind of signal extension to be used to extend the boundaries. Can be one of 'mean', 'median', 'min',
     *                'max' or 'constant'
     */
    public Resample(int up, int down, String padtype) {
        if ((up < 1) && (down < 1)) {
            throw new IllegalArgumentException("up and down must be greater than 0");
        }
        this.up = up;
        this.down = down;
        this.beta = 5.0;
        this.cval = 0.0;
        if (!padtype.equals("mean") && !padtype.equals("median") && !padtype.equals("min") && !padtype.equals("max") &&
                !padtype.equals("constant") && !padtype.equals("edge")) {
            throw new ArithmeticException("padtype must be 'mean', 'median', 'min', 'max', 'constant' or 'edge'");
        }
        this.padtype = padtype;
        this.upfirdn_mode = padtype;
        this.poly = true;
    }

    /**
     * This constructor initialises the prerequisites required to use Polyphase Resample. Uses a Kaiser window of provided
     * width to construct the low-pass filter.
     * @param up The number of samples required after resampling
     * @param down The number of samples required after resampling
     * @param beta Beta parameter of the Kaiser window for designing the low-pass filter
     * @param padtype Kind of signal extension to be used to extend the boundaries. Can be one of 'mean', 'median', 'min',
     *                'max' or 'constant'
     * @param cval Only used when padtype is "constant"
     */
    public Resample(int up, int down, double beta, String padtype, double cval) {
        if ((up < 1) && (down < 1)) {
            throw new IllegalArgumentException("up and down must be greater than 0");
        }
        this.up = up;
        this.down = down;
        this.beta = beta;
        this.cval = cval;
        if (!padtype.equals("mean") && !padtype.equals("median") && !padtype.equals("min") && !padtype.equals("max") &&
                !padtype.equals("constant") && !padtype.equals("edge")) {
            throw new ArithmeticException("padtype must be 'mean', 'median', 'min', 'max', 'constant' or 'edge'");
        }
        this.padtype = padtype;
        this.upfirdn_mode = padtype;
        this.poly = true;
    }

    /**
     * This method resamples using the Fourier method to change the number of samples to the given number of samples.
     */
    private void resample_fft() {
        int Nx = this.signal.length;

        DiscreteFourier df = new DiscreteFourier(this.signal);
        df.transform();
        double[][] X = df.getComplex2D(true);
        double[][] Y = new double[this.num/2+1][2];

        int N = Math.min(this.num, Nx);
        int nyquist_idx = N/2+1;
        for (int i=0; i<nyquist_idx; i++) {
            Y[i][0] = X[i][0];
            Y[i][1] = X[i][1];
        }

        if (N%2 == 0) {
            // Downsampling
            if (this.num < Nx) {
                Y[N/2][0] = Y[N/2][0]*2;
                Y[N/2][1] = Y[N/2][1]*2;
            }
            // Upsampling
            else {
                Y[N/2][0] = Y[N/2][0]*0.5;
                Y[N/2][1] = Y[N/2][1]*0.5;
            }
        }

        InverseDiscreteFourier idf = new InverseDiscreteFourier(Y, true);
        idf.transform();
        double[] y = idf.getReal();
        this.output = MathArrays.scale((float)this.num/(float)Nx, y);
    }

    private double _funcs(double[] signal, String action) {
        double out;
        switch (action) {
            case "mean":
                out = StatUtils.mean(signal);
                this.upfirdn_mode = "constant";
                this.cval = 0.0;
                break;
            case "median":
                out = new Median().evaluate(signal);
                this.upfirdn_mode = "constant";
                this.cval = 0.0;
                break;
            case "max":
                out = StatUtils.max(signal);
                this.upfirdn_mode = "constant";
                this.cval = 0.0;
                break;
            case "min":
                out = StatUtils.min(signal);
                this.upfirdn_mode = "constant";
                this.cval = 0.0;
                break;
            default:
                out = 0.0;
                break;
        }
        return out;
    }

    private static int _output_len(int hlen, int siglen, int up, int down) {
        return (((siglen - 1) * up + hlen) - 1) / down + 1;
    }

    /**
     * This method resamples using Polyphase filtering by upsampling by factor 'up', applying a zero-phase low-pass FIR
     * filter, and then downsampling by factor 'down'. The resultant resampling factor is up/down * original sampling rate.
     */
    private void resample_poly() {
        int g = ArithmeticUtils.gcd(this.up, this.down);
        this.up = this.up/g;
        this.down = this.down/g;
        if ((this.up == 1) && (this.down == 1)) {
            this.output = this.signal;
        }

        int n_in = this.signal.length;
        int n_out = n_in * this.up;
        n_out = n_out / this.down + ((UtilMethods.modulo(n_out, this.down) > 0) ? 1 : 0);

        int max_rate = Math.max(this.up, this.down);
        double f_c = 1.0/max_rate;
        int half_len = 10 * max_rate;
        FIRWin1 fw = new FIRWin1(2*half_len+1, this.beta, true);
        double[] h = fw.computeCoefficients(new double[] {f_c}, FIRWin1.FIRfilterType.LOWPASS, true);
        h = UtilMethods.scalarArithmetic(h, this.up, "mul");

        int n_pre_pad = (this.down - half_len % this.down);
        int n_post_pad = 0;
        int n_pre_remove = (half_len + n_pre_pad) / this.down;
        int n_pre_remove_end = n_pre_remove + n_out;
        while (_output_len(h.length + n_pre_pad + n_post_pad, n_in, this.up, this.down) < n_out + n_pre_remove) {
            n_post_pad++;
        }

        double[] pre = new double[n_pre_pad];
        Arrays.fill(pre, 0.0);
        double[] post = new double[n_post_pad];
        Arrays.fill(post, 0.0);
        h = UtilMethods.concatenateArray(pre, h);
        h = UtilMethods.concatenateArray(h, post);

        // Subtract background values
        double bg_val = this._funcs(this.signal, this.padtype);
        if (this.padtype.equals("mean") || this.padtype.equals("median") || this.padtype.equals("max") || this.padtype.equals("min")) {
            this.signal = UtilMethods.scalarArithmetic(this.signal, bg_val, "sub");
        }

        // Add main functionality
        _UpFIRDown ufd = new _UpFIRDown(h, this.up, this.down, this.cval);
        this.output = ufd.apply_filter(this.signal, this.upfirdn_mode);
        this.output = UtilMethods.splitByIndex(this.output, n_pre_remove, n_pre_remove_end);

        // Add background values
        if (this.padtype.equals("mean") || this.padtype.equals("median") || this.padtype.equals("max") || this.padtype.equals("min")) {
            this.output = UtilMethods.scalarArithmetic(this.output, bg_val, "add");
        }

    }

    /**
     * Calls the relevant resampling method depending on the constructor. If "poly" is true, plyphase resampling s performed,
     * otherwise basic resampling.
     * @param signal Signal to be resampled
     * @return double[] The resampled signal
     */
    public double[] resampleSignal(double[] signal) {
        this.signal = signal;
        if (this.poly) {
            this.resample_poly();
        }
        else {
            this.resample_fft();
        }
        return this.output;
    }

    /**
     * Polyphase Resampling Helper Class
     */
    class _UpFIRDown {

        private double[] _padH(double[] h, int up) {
            int h_padlen = h.length + UtilMethods.modulo(-h.length, up);
            double[][] htemp = new double[h_padlen/up][up];
            for (double[] row: htemp) {
                Arrays.fill(row, 0.0);
            }
            for (int i=0; i<h.length; i++){
                htemp[i/up][i%up] = h[i];
            }
            htemp = UtilMethods.transpose(htemp);
            htemp = UtilMethods.reverseMatrix(htemp);
            return UtilMethods.flattenMatrix(htemp);
        }

        private int up;
        private int down;
        private double[] h_trans_flip;
        private int h_orig_len;
        private double cval;

        private _UpFIRDown(double[] h, int up, int down, double cval) {
            this.up = up;
            this.down = down;
            this.h_orig_len = h.length;
            this.h_trans_flip = this._padH(h, up);
            this.cval = cval;
        }

        private double _extend_left(double[] signal, String padtype, double cval) {
            double out;
            if (padtype.equals("constant")){
                out = cval;
            }
            else if (padtype.equals("edge")) {
                out = signal[0];
            }
            else {
                out = -1;
            }
            return out;
        }

        private double _extend_right(double[] signal, String padtype, double cval) {
            double out;
            if (padtype.equals("constant")){
                out = cval;
            }
            else if (padtype.equals("edge")) {
                out = signal[signal.length - 1];
            }
            else {
                out = -1;
            }
            return out;
        }

        private double[] apply_filter(double[] signal, String padtype) {
            int len_out = _output_len(this.h_orig_len, signal.length, this.up, this.down);
            double[] output = new double[len_out];
            Arrays.fill(output, 0.0);

            int len_x = signal.length;
            int len_h = this.h_trans_flip.length;
            int h_per_phase = len_h / this.up;
            int padded_len = len_x + h_per_phase - 1;
            int x_idx, y_idx, h_idx, x_conv_idx, t;
            x_idx = y_idx = h_idx = x_conv_idx = t = 0;
            double x_val;
            boolean zpad = padtype.equals("constant") && this.cval == 0;

            // Where the actual computation happens
            while (x_idx < len_x) {
                h_idx = t * h_per_phase;
                x_conv_idx = x_idx - h_per_phase + 1;

                if (x_conv_idx < 0) {
                    if (zpad) {
                        h_idx = h_idx - x_conv_idx;
                    }
                    else {
                        for (int xcidx=x_conv_idx; xcidx<0; xcidx++) {
                            x_val = this._extend_left(signal, padtype, this.cval);
                            output[y_idx] = output[y_idx] + x_val * this.h_trans_flip[h_idx];
                            h_idx++;
                        }
                    }
                    x_conv_idx = 0;
                }
                for (int xcidx=x_conv_idx; xcidx < x_idx + 1; xcidx++) {
                    output[y_idx] = output[y_idx] + signal[xcidx] * this.h_trans_flip[h_idx];
                    h_idx++;
                }
                y_idx++;
                if (y_idx >= len_out) {
                    return output;
                }
                t = t + this.down;
                x_idx = x_idx + t/this.up;
                t = UtilMethods.modulo(t, this.up);
            }

            // Use a second simplified loop to flush out the last bits
            while (x_idx < padded_len) {
                h_idx = t * h_per_phase;
                x_conv_idx = x_idx - h_per_phase + 1;
                for (int xcidx=x_conv_idx; xcidx < x_idx + 1; xcidx++) {
                    if (xcidx >= len_x) {
                        x_val = this._extend_right(signal, padtype, this.cval);
                    }
                    else if (xcidx < 0) {
                        x_val = this._extend_left(signal, padtype, this.cval);
                    }
                    else {
                        x_val = signal[xcidx];
                    }
                    output[y_idx] = output[y_idx] + x_val * this.h_trans_flip[h_idx];
                    h_idx++;
                }
                y_idx++;
                if (y_idx >= len_out) {
                    return output;
                }
                t = t + this.down;
                x_idx = x_idx + t/this.up;
                t = UtilMethods.modulo(t, this.up);
            }
            return output;
        }
    }
}
