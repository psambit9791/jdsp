/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;


/**
 * <h1>Principal Component Analysis (PCA)</h1>
 * The PCA class reduces the dimensionality of the input multi-channel input and
 * provide a signal with reduced dimensions by transforming the input to a new set of
 * variables called Principal Components.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class PCA {

    private double[][] signal;
    private double[][] zm_signal;
    private double[][] output;
    private int n_components;
    private int n_samples;

    private double[][] U;
    private double[][] S;
    private double[][] V;

    public double[] explained_variance_;
    public double[] explained_variance_ratio_;
    public double[] singular_values_;
    private double[] mean_;


    /**
     * This constructor initialises the prerequisites required to use PCA.
     * @throws java.lang.ExceptionInInitializerError if n_components less than 1 or greater than total channels in signal
     * @throws java.lang.IllegalArgumentException if signal length is less than the number of channels
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param n_components Number of components to keep. Must be greater than 0 and less than the original number of channels in the signal
     */
    public PCA(double[][] signal, int n_components) throws ExceptionInInitializerError, IllegalArgumentException {
        if (signal.length < signal[0].length) {
            throw new ExceptionInInitializerError("Signal length must be more than number of channels");
        }
        if ((n_components > signal[0].length) || (n_components <= 0)) {
            throw new ExceptionInInitializerError("n_components must be greater than 0 and less than total channels in signal");
        }
        this.signal = signal;
        this.n_samples = signal.length;
        this.n_components = n_components;
        this.output = new double[n_components][signal.length];
    }

    /**
     * Returns the U, S, V matrices after performing the PCA fit()
     * @throws java.lang.ExceptionInInitializerError if called before executing fit()
     * @return double[][][] The U, S, V matrices in the same order
     */
    public double[][][] getUSV() throws ExceptionInInitializerError {
        if (this.singular_values_ == null) {
            throw new ExceptionInInitializerError("Execute fit() before calling this function");
        }
        double[][][] usv = {this.U, this.S, this.V};
        return usv;
    }

    /**
     * Performs PCA and fits the model with the input signals
     */
    public void fit() {
        double[][] sigT = UtilMethods.transpose(this.signal);

        this.zm_signal = new double[this.signal.length][this.signal[0].length];
        this.zm_signal = UtilMethods.transpose(this.zm_signal);
        this.mean_ = new double[sigT.length];
        for (int i=0; i<sigT.length; i++) {
            this.mean_[i] = StatUtils.mean(sigT[i]);
            this.zm_signal[i] = UtilMethods.zeroCenter(sigT[i]);
        }
        this.zm_signal = UtilMethods.transpose(this.zm_signal);

        RealMatrix m = MatrixUtils.createRealMatrix(this.zm_signal);
        SingularValueDecomposition svdM = new SingularValueDecomposition(m);

        double[][] U = svdM.getU().getData();
        double[][] S = svdM.getS().getData();
        double[][] V = svdM.getVT().getData();

        double[][][] temp2 = this.svdFlip(U, V);
        U = temp2[0];
        V = temp2[1];

        this.singular_values_ = svdM.getSingularValues();

        this.explained_variance_ = MathArrays.ebeMultiply(this.singular_values_, this.singular_values_);
        for (int i=0; i<this.explained_variance_.length; i++) {
            this.explained_variance_[i] = this.explained_variance_[i]/(n_samples - 1);
        }
        double total_var = StatUtils.sum(this.explained_variance_);

        this.explained_variance_ratio_ = new double[S.length];
        for (int i=0; i< this.explained_variance_.length; i++) {
            this.explained_variance_ratio_[i] = this.explained_variance_[i]/total_var;
        }

        this.singular_values_ = UtilMethods.splitByIndex(this.singular_values_, 0, this.n_components);
        this.explained_variance_ = UtilMethods.splitByIndex(this.explained_variance_, 0, this.n_components);
        this.explained_variance_ratio_ = UtilMethods.splitByIndex(this.explained_variance_ratio_, 0, this.n_components);

        this.U = U;
        this.S = S;
        this.V = V;

    }

    /**
     * Applies dimensionality reduction on the original signal
     * @throws java.lang.ExceptionInInitializerError if called before executing fit()
     * @return double[][] The signal with reduced dimensions
     */
    public double[][] transform() throws ExceptionInInitializerError{
        if (this.singular_values_ == null) {
            throw new ExceptionInInitializerError("Execute fit() before calling this function");
        }

        double[][] components = new double[this.n_components][this.n_samples];
        for (int i=0; i<this.n_components; i++) {
            components[i] = this.V[i];
        }

        double[][] components_T = UtilMethods.transpose(components);
        this.output = UtilMethods.matrixMultiply(this.zm_signal, components_T);
        return this.output;
    }

    /**
     * Applies dimensionality reduction on the input signal
     * @param x The signal to be processed
     * @throws java.lang.ExceptionInInitializerError if called before executing fit()
     * @throws java.lang.ArithmeticException if number of channels in input signal is different from the original signal
     * @return double[][] The signal with reduced dimensions
     */
    public double[][] transform(double[][] x) throws ExceptionInInitializerError, ArithmeticException{
        if (this.singular_values_ == null) {
            throw new ExceptionInInitializerError("Execute fit() before calling this function");
        }
        if (x[0].length != this.signal[0].length) {
            throw new ArithmeticException("Number of channels has to be same as original signal");
        }

        double[][] xT = UtilMethods.transpose(x);

        double[][] zm_x = new double[x.length][x[0].length];
        zm_x = UtilMethods.transpose(zm_x);
        for (int i=0; i<xT.length; i++) {
            zm_x[i] = UtilMethods.scalarArithmetic(xT[i], this.mean_[i], "sub");
        }
        zm_x = UtilMethods.transpose(zm_x);

        double[][] components = new double[this.n_components][this.n_samples];
        for (int i=0; i<this.n_components; i++) {
            components[i] = this.V[i];
        }

        double[][] components_T = UtilMethods.transpose(components);
        double[][] out = UtilMethods.matrixMultiply(zm_x, components_T);
        return out;
    }

    // Flip eigenvectors' sign to enforce deterministic output
    // Use reference to understand: https://prod-ng.sandia.gov/techlib-noauth/access-control.cgi/2007/076422.pdf
    private double[][][] svdFlip(double[][] U, double[][] V) {
        double[][] U_new = UtilMethods.absoluteArray(U);
        int[] max_abs_cols = new int[U[0].length];
        double[] signs = new double[U[0].length];

        for (int j=0; j<U_new[0].length; j++) {
            double[] column_vals = new double[U_new.length];
            for(int i=0; i<U_new.length; i++) {
                column_vals[i] = U_new[i][j];
            }
            max_abs_cols[j] = UtilMethods.argmax(column_vals, false);
        }

        for (int i=0; i< max_abs_cols.length; i++) {
            signs[i] = Math.signum(U[max_abs_cols[i]][i]);
        }

        for (int i=0; i<U.length; i++) {
            U[i] = MathArrays.ebeMultiply(U[i], signs);
        }

        V = UtilMethods.transpose(V);
        for (int i=0; i<V.length; i++) {
            V[i] = MathArrays.ebeMultiply(V[i], signs);
        }
        V = UtilMethods.transpose(V);

        double[][][] out = {U, V};
        return out;
    }
}
