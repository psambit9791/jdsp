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

import com.github.psambit9791.jdsp.misc.Random;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

public class ICA {

    private double[][] signal;
    private double[][] zm_signal;
    private double[][] output;
    private double alpha = 1.0;
    public double[][] gx;
    public double[] g_x;
    public double[][] w_init;
    private int max_iter = 200;
    private double tol = 1E-4;
    private String whiten = "unit-variance";
    private String func = "logcosh";
    private long seed = 42;
    private double[] mean_;
    private int features;

    public double[][] components;
    public double[][] mixing;


    public void logcosh_(double[][] x) {
        if (this.alpha < 1.0 || this.alpha > 2.0) {
            throw new IllegalArgumentException("alpha should be between 1.0 and 2.0");
        }

        for (int i=0; i<this.gx.length; i++) {
            double temp = 0.0;
            for (int j=0; j<this.gx[0].length; j++) {
                this.gx[i][j] = FastMath.tanh(x[i][j] * this.alpha);
                temp += this.alpha * (1 - Math.pow(this.gx[i][j], 2));
            }
            this.g_x[i] = temp/this.gx[i].length;
        }
    }

    public void exp_(double[][] x) {
        for (int i=0; i<this.gx.length; i++) {
            double temp = 0.0;
            for (int j=0; j<this.gx[0].length; j++) {
                double exp = FastMath.exp((0 - Math.pow(this.signal[i][j], 2))/2.0);
                this.gx[i][j] = x[i][j] * exp;
                temp += (1 - Math.pow(x[i][j], 2)) * exp;
            }
            this.g_x[i] = temp/this.gx[i].length;
        }
    }

    public void cube_(double[][] x) {
        for (int i=0; i<this.gx.length; i++) {
            double temp = 0.0;
            for (int j=0; j<this.gx[0].length; j++) {
                this.gx[i][j] = Math.pow(x[i][j], 3);
                temp += 3 * Math.pow(x[i][j], 2);
            }
            this.g_x[i] = temp/this.gx[i].length;
        }
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param func The functional form of the G function used in the approximation to neg-entropy. Can be "logcosh", "exp" or "cube".
     * @param whiten Specifies the whitening strategy. Can be one of "unit-variance", "arbitrary-variance" or empty string.
     * @param w_init Initial un-mixing array. Defaults to values drawn from a normal distribution.
     * @param max_iter Maximum number of iterations during fit.
     * @param tol A positive scalar giving the tolerance at which the un-mixing matrix is considered to have converged. Defaults to 1E-4.
     * @param alpha G Function argument - only used in case of logcosh
     */
    public ICA(double[][] signal, String func, String whiten, double[][] w_init, int max_iter, double tol, double alpha) {
        this.signal = signal;
        this.features = this.signal[0].length;
        if ((w_init.length != w_init[0].length) || (w_init.length != this.features)) {
            throw new IllegalArgumentException("w_init should be a square matrix and the shape should be same as the number of components in signal");
        }
        if (!func.equals("logcosh") && !func.equals("exp") && !func.equals("cube")) {
            throw new IllegalArgumentException("func should be one of logcosh, exp or cube");
        }
        if (func.equals("logcosh")) {
            if ((alpha > 2) || (alpha < 1)) {
                throw new IllegalArgumentException("alpha should be between 1 and 2");
            }
        }
        if (!whiten.equals("unit-variance") && !whiten.equals("arbitrary-variance") && !whiten.equals("")) {
            throw new IllegalArgumentException("whiten must be one of \"unit-variance\", \"arbitrary-variance\" or an empty string. ");
        }
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        this.func = func;
        this.whiten = whiten;
        this.w_init = w_init;
        this.max_iter = max_iter;
        this.tol = tol;
        this.alpha = alpha;
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param func The functional form of the G function used in the approximation to neg-entropy. Can be "logcosh", "exp" or "cube".
     * @param whiten Specifies the whitening strategy. Can be one of "unit-variance", "arbitrary-variance" or empty string.
     * @param max_iter Maximum number of iterations during fit.
     * @param alpha G Function argument - only used in case of logcosh.
     * @param random_state Random seed to initialise w_init.
     */
    public ICA(double[][] signal, String func, String whiten, int max_iter, double alpha, long random_state) {
        this.signal = signal;
        this.features = this.signal[0].length;
        if (!func.equals("logcosh") && !func.equals("exp") && !func.equals("cube")) {
            throw new IllegalArgumentException("func should be one of logcosh, exp or cube");
        }
        if (func.equals("logcosh")) {
            if ((alpha > 2) || (alpha < 1)) {
                throw new IllegalArgumentException("alpha should be between 1 and 2");
            }
        }
        if (!whiten.equals("unit-variance") && !whiten.equals("arbitrary-variance") && !whiten.equals("")) {
            throw new IllegalArgumentException("whiten must be one of \"unit-variance\", \"arbitrary-variance\" or an empty string. ");
        }
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        this.func = func;
        this.whiten = whiten;
        this.seed = random_state;
        this.max_iter = max_iter;
        Random r1 = new Random(this.seed, new int[] {this.features, this.features});
        this.w_init = r1.randomNormal2D();
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param func The functional form of the G function used in the approximation to neg-entropy. Can be "logcosh", "exp" or "cube".
     * @param alpha G Function argument - only used in case of logcosh.
     * @param random_state Random seed to initialise w_init.
     */
    public ICA(double[][] signal, String func, double alpha, long random_state) {
        this.signal = signal;
        this.features = this.signal[0].length;
        if (!func.equals("logcosh") && !func.equals("exp") && !func.equals("cube")) {
            throw new IllegalArgumentException("func should be one of logcosh, exp or cube");
        }
        if (func.equals("logcosh")) {
            if ((alpha > 2) || (alpha < 1)) {
                throw new IllegalArgumentException("alpha should be between 1 and 2");
            }
        }
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        this.func = func;
        this.alpha = alpha;
        this.seed = random_state;
        Random r1 = new Random(this.seed, new int[] {this.features, this.features});
        this.w_init = r1.randomNormal2D();
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param func The functional form of the G function used in the approximation to neg-entropy. Can be "logcosh", "exp" or "cube".
     * @param alpha G Function argument - only used in case of logcosh.
     */
    public ICA(double[][] signal, String func, double alpha) {
        this.signal = signal;
        this.features = this.signal[0].length;
        if (!func.equals("logcosh") && !func.equals("exp") && !func.equals("cube")) {
            throw new IllegalArgumentException("func should be one of logcosh, exp or cube");
        }
        if (func.equals("logcosh")) {
            if ((alpha > 2) || (alpha < 1)) {
                throw new IllegalArgumentException("alpha should be between 1 and 2");
            }
        }
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        this.func = func;
        this.alpha = alpha;
        Random r1 = new Random(this.seed, new int[] {this.features, this.features});
        this.w_init = r1.randomNormal2D();
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     * @param func The functional form of the G function used in the approximation to neg-entropy. Can be "logcosh", "exp" or "cube". For logcosh, alpha is set to 1.
     */
    public ICA(double[][] signal, String func) {
        this.signal = signal;
        this.features = this.signal[0].length;
        if (!func.equals("logcosh") && !func.equals("exp") && !func.equals("cube")) {
            throw new IllegalArgumentException("func should be one of logcosh, exp or cube");
        }
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        this.func = func;
        Random r1 = new Random(this.seed, new int[] {this.features, this.features});
        this.w_init = r1.randomNormal2D();
    }

    /**
     * This constructor initialises the prerequisites required to use ICA.
     * @param signal Multi-dimensional signal to be transformed. Dimension 1: Samples, Dimension 2: Channels
     */
    public ICA(double[][] signal) {
        this.signal = signal;
        this.features = this.signal[0].length;
        this.gx = new double[this.signal.length][this.features];
        this.g_x = new double[this.signal.length];
        Random r1 = new Random(this.seed, new int[] {this.features, this.features});
        this.w_init = r1.randomNormal2D();
    }

    public void fit() {
        double n_samples = this.signal.length;
        double[][] sigT = UtilMethods.transpose(this.signal);
        double[][] X1;
        double[][] K = new double[this.features][this.features];

        if (!this.whiten.equals("")) {
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
            for (int i=0; i<U.length; i++) {
                U[i] = MathArrays.ebeMultiply(U[i], UtilMethods.sign(U[0]));
            }

            double[][] filled_S = new double[S.length][S.length];
            for (int i=0; i<S.length; i++) {
                for (int j=0; j<S[0].length; j++) {
                    filled_S[i][j] = S[i][i];
                }
            }

            K = UtilMethods.ebeDivide(MatrixUtils.createRealMatrix(U), MatrixUtils.createRealMatrix(filled_S)).getData();
            K = UtilMethods.transpose(K);
            X1 = UtilMethods.matrixMultiply(K, this.zm_signal);
            for (int i=0; i<X1.length; i++) {
                X1[i] = UtilMethods.scalarArithmetic(X1[i], Math.sqrt(n_samples), "mul");
            }
        }
        else {
            X1 = sigT;
        }

        double[][] W = this.icaDef(X1, this.tol, this.alpha, this.max_iter, this.w_init);
        double[][] temp_source = new double[0][];

        if (!this.whiten.equals("")) {
            if (this.whiten.equals("unit-variance")) {
                temp_source = UtilMethods.matrixMultiply(W, K);
                temp_source = UtilMethods.matrixMultiply(temp_source, this.zm_signal);
                double[][] temp_source_T = UtilMethods.transpose(temp_source);
                double[] source_std = new double[temp_source[0].length];
                for (int i=0; i<source_std.length; i++){
                    source_std[i] = StatUtils.populationVariance(temp_source_T[i]);
                }
                double[][] source_std_2D = new double[temp_source.length][temp_source[0].length];
                Arrays.setAll(source_std_2D, i -> source_std.clone());
                temp_source = UtilMethods.matrixDivide(temp_source, source_std_2D);
                W = UtilMethods.matrixDivide(W, UtilMethods.transpose(source_std_2D));
            }
            this.components = UtilMethods.matrixMultiply(W, K);
        }
        else {
            this.components = W;
        }
        this.mixing = UtilMethods.pseudoInverse(this.components);
    }

    public double[][] transform() {
        return new double[1][1];

    }


    private double[][] icaDef(double[][] X, double tolerance, double alpha, int max_iterations, double[][] w_init) {
        int n_components = w_init.length;
        double[][] out = new double[this.features][this.features];
        for (double[] doubles : out) {
            Arrays.fill(doubles, 0.0);
        }

        for (int j=0; j<n_components; j++) {
            double[] w = w_init[j];
            double divisor = Math.sqrt(StatUtils.sum(UtilMethods.scalarArithmetic(w, 2, "pow")));
            w = UtilMethods.scalarArithmetic(w, divisor, "div");
            for (int i = 0; i<max_iterations; i++) {

                if (this.func.equals("logcosh")) {
                    this.logcosh_(UtilMethods.matrixMultiply(UtilMethods.transpose(w), X));
                }
                if (this.func.equals("exp")) {
                    this.exp_(UtilMethods.matrixMultiply(UtilMethods.transpose(w), X));
                }
                if (this.func.equals("cube")) {
                    this.cube_(UtilMethods.matrixMultiply(UtilMethods.transpose(w), X));
                }

                double[][] temp = UtilMethods.transpose(UtilMethods.matrixMultiply(X, this.gx));
                double[] mean_list = new double[temp.length];
                double g_x_mean = 0;
                int iter = 0;
                for (int k = 0; k<mean_list.length; k++) {
                    mean_list[k] = StatUtils.mean(temp[k]);
                    g_x_mean += StatUtils.sum(temp[k]);
                    iter += temp[k].length;
                }
                g_x_mean = g_x_mean/iter;
                double[] w1  = MathArrays.ebeSubtract(mean_list, UtilMethods.scalarArithmetic(w, g_x_mean, "mul"));
//              w1 = gs_decorrelate(w1, W, j)

                w1 = UtilMethods.scalarArithmetic(w1, Math.sqrt(StatUtils.sum(UtilMethods.scalarArithmetic(w1, 2, "pow"))), "div");
                double lim = Math.abs(Math.abs(StatUtils.sum(MathArrays.ebeMultiply(w1, w))) - 1);
                w = w1;

                if (lim < tolerance) {
                    break;
                }

            }
            out[j] = w;
        }

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
