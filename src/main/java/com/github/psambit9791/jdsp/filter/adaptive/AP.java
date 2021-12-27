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

package com.github.psambit9791.jdsp.filter.adaptive;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Affine Projection (AP) adaptive filter</h1>
 * The AP adaptive filter uses affine projection algorithm to improve the performance of LMS-based adaptive filters.
 * This filter is useful when the input data is highly correlated.
 * AP uses multiple input vectors for each sample where the number of input vectors depends on the projection order.
 *
 * Cite: Gonzalez, A., Ferrer, M., Albu, F., and De Diego, M. (2012, August). Affine projection algorithms:
 * Evolution to smart and fast algorithms and applications. In 2012 Proceedings of the 20th European Signal Processing
 * Conference (EUSIPCO) (pp. 1965-1969). IEEE.
 *
 * @author Sambit Paul
 * @version 1.0
 */
public class AP implements _Adaptive {

    private double[][] x_mem;
    private double[] d_mem;
    private double mu;
    private double eps;
    private double[][] ide;
    private double[][] ide_eps;

    private double[] weights;           // Weights of the filter
    private double[] error;
    private double[] output;

    /**
     * This constructor initialises the prerequisites required for the AP adaptive filter.
     * @param order projection order to determine the memory
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to weights.length
     * @param eps initial offset covariance
     * @param weights initialized weights (size = number of taps of the filter)
     */
    public AP(int order, double learningRate, double eps, double[] weights) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must be non-null and with a length greater than 0");
        }
        this.x_mem = new double[weights.length][order];
        this.d_mem = new double[order];
        this.mu = learningRate;
        this.eps = eps;
        this.weights = weights;
        this.ide = MatrixUtils.createRealIdentityMatrix(order).getData();
        this.ide_eps = MatrixUtils.createRealIdentityMatrix(order).getData();
        for (int i=0; i<this.ide_eps.length; i++) {
            this.ide_eps[i] = MathArrays.scale(this.eps, this.ide_eps[i]);
        }
    }

    /**
     * This constructor initialises the prerequisites required for the AP adaptive filter.
     * 'order' and 'eps' is set to 5 and 0.001 respectively by default.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to weights.length
     * @param weights initialized weights (size = number of taps of the filter)
     */
    public AP(double learningRate, double[] weights) {
        this(5, learningRate, 0.001, weights);
    }

    /**
     * This constructor initialises the prerequisites required for the AP adaptive filter.
     * @param length length (number of taps) of the filter
     * @param order projection order to determine the memory
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to length
     * @param eps initial offset covariance
     * @param fillMethod determines how the weights should be initialized
     */
    public AP(int length, int order, double learningRate, double eps, WeightsFillMethod fillMethod) {
        this.mu = learningRate;
        this.eps = eps;
        this.weights = new double[length];
        this.ide = MatrixUtils.createRealIdentityMatrix(order).getData();
        this.ide_eps = MatrixUtils.createRealIdentityMatrix(order).getData();
        for (int i=0; i<this.ide_eps.length; i++) {
            this.ide_eps[i] = MathArrays.scale(this.eps, this.ide_eps[i]);
        }
        switch (fillMethod) {
            // Create random weights between 0 and 1
            case RANDOM:
                for (int i = 0; i < length; i++) {
                    this.weights[i] = Math.random();
                }
                break;
            // Fill weights with zero
            case ZEROS:
                Arrays.fill(this.weights, 0);
                break;
            default:
                throw new IllegalArgumentException("Unknown weights fill method");
        }
        this.x_mem = new double[length][order];
        this.d_mem = new double[order];
    }

    /**
     * This constructor initialises the prerequisites required for the AP adaptive filter.
     * 'order' and 'eps' is set to 5 and 0.001 respectively by default.
     * @param length length (number of taps) of the filter
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to length
     * @param fillMethod determines how the weights should be initialized
     */
    public AP(int length, double learningRate, WeightsFillMethod fillMethod) {
        this(length, 5, learningRate, 0.001, fillMethod);
    }

    private double[][] update_memory(double[] data, double[][] mem) {
        mem = UtilMethods.transpose(mem);
        for (int i=mem.length-2; i>=0; i--) {
            mem[i+1] = mem[i];
        }
        mem[0] = data;
        return UtilMethods.transpose(mem);
    }

    private double[] update_memory(double data, double[] mem) {
        for (int i=mem.length-2; i>=0; i--) {
            mem[i+1] = mem[i];
        }
        mem[0] = data;
        return mem;
    }

    /**
     * Adapt weights according one desired value and its input, for a certain k-sample of x.
     * @param desired desired value for a sample 'k' in the input signal
     * @param x array of input samples, starting at index 'k - N' until index 'k', with 'N' being the filter length.
     * @return double[] with first element the filter output 'y' and second element the filter error 'e'
     */
    private double[] adaptWeights(double desired, double[] x) {

        // Generate the inut matrices
        this.x_mem = this.update_memory(x, x_mem);
        this.d_mem = this.update_memory(desired, d_mem);

        // Compute y and error
        double[][] x_mem_T = UtilMethods.transpose(this.x_mem);
        double[] y_mem = UtilMethods.flattenMatrix(UtilMethods.matrixMultiply(x_mem_T, UtilMethods.transpose(new double[][] {weights})));
        double[] e_mem = MathArrays.ebeSubtract(this.d_mem, y_mem);

        // Update
        double[][] dw_p1 = UtilMethods.matrixMultiply(x_mem_T, this.x_mem);
        dw_p1 = UtilMethods.matrixAddition(dw_p1, this.ide_eps);
        double[][] dw_p2 = new double[dw_p1.length][dw_p1[0].length];
        RealVector soln;
        try {
            DecompositionSolver solver = new LUDecomposition(MatrixUtils.createRealMatrix(dw_p1)).getSolver();
            for (int i=0; i<dw_p2.length; i++) {
                soln = solver.solve(new ArrayRealVector(this.ide[i], false));
                dw_p2[i] = soln.toArray();
            }

        }
        catch (SingularMatrixException e) {
            DecompositionSolver solver = new SingularValueDecomposition(MatrixUtils.createRealMatrix(dw_p1)).getSolver();
            for (int i=0; i<dw_p2.length; i++) {
                soln = solver.solve(new ArrayRealVector(this.ide[i], false));
                dw_p2[i] = soln.toArray();
            }
        }

        double[] dw = UtilMethods.flattenMatrix(UtilMethods.matrixMultiply(this.x_mem, UtilMethods.matrixMultiply(dw_p2, UtilMethods.transpose(new double[][] {e_mem}))));
        for (int i=0; i< weights.length; i++) {
            weights[i] = weights[i] + this.mu * dw[i];
        }

        return new double[] {y_mem[0], e_mem[0]};
    }

    /**
     * Run the adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
     * match the desired signal.
     * @param desired desired signal that you want after filtering of x
     * @param x input signal that you want to filter with the LMS adaptive filter to achieve the desired signal
     */
    public void filter(double[] desired, double[] x) {
        if (desired == null || desired.length == 0) {
            throw new IllegalArgumentException("Desired signal cannot be null, or with size 0");
        }
        if (x == null || x.length == 0) {
            throw new IllegalArgumentException("Input signal cannot be null, or with size 0");
        }
        if (x.length != desired.length) {
            throw new IllegalArgumentException("The length of the desired signal and input signal must be equal.");
        }
        if (this.weights.length > x.length) {
            throw new IllegalArgumentException("Filter length must not be greater than the signal length");
        }

        this.error = new double[x.length];
        this.output = new double[x.length];

        // Iterate to adapt the filter
        for (int i = 0; i < x.length; i++) {
            // Get a subset of x ranging from 'i-N' to 'i', with 'N' being the window length
            double[] x_subset = new double[this.weights.length];
            Arrays.fill(x_subset, 0);

            // Fill in the x subset
            for (int j = 0; j < x_subset.length; j++) {
                if (i - j > 0) {
                    x_subset[x_subset.length - 1 - j] = x[i - j];
                }
            }

            // Adapt the filter weights
            double[] out = adaptWeights(desired[i], x_subset);
            this.output[i] = out[0];
            this.error[i] = out[1];
        }
    }

    /**
     * Returns the final weights of the RLS adaptive filter
     * @return double[] final filter weights
     */
    public double[] getWeights() {
        checkOutput();
        return weights;
    }

    /**
     * Returns the error over the entire signal. This equals the difference between the filter output and the desired
     * filter output.
     * @return double[] filter error
     */
    public double[] getError() {
        checkOutput();
        return error;
    }

    /**
     * Returns the filter output values over the entire signal.
     * @return double[] filter output (= filtered input signal)
     */
    public double[] getOutput() {
        checkOutput();
        return output;
    }

    private void checkOutput() {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute filter() function before returning result");
        }
    }
}
