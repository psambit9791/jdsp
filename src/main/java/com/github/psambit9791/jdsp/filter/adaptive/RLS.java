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
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

/**
 * <h1>Reduced Least-Squares (RLS) adaptive filter</h1>
 * The RLS adaptive filter is a filter that adapts its filter weights to get an input signal x to match a desired output
 * signal (= the output of the filter) by recursively reducing the least squares error with respect to the weights.
 *
 * @author Sambit Paul
 * @version 1.0
 */
public class RLS implements _Adaptive{
    private double[] weights;           // Weights of the filter
    private double[] error;
    private double[] output;
    private double mu;                  // Forgetting factor
    private double[][] R;

    /**
     * This constructor initialises the prerequisites required for the RLS adaptive filter.
     * @param mu forgetting factor. It is introduced to give exponentially less weight to older error samples.
     * @param eps initialisation value.
     * @param weights Initialised set of weights
     */
    public RLS(double mu, double eps, double[] weights) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must be non-null and with a length greater than 0");
        }
        this.weights = weights;
        this.mu = mu;

        int length = weights.length;

        this.R = new double[length][length];
        for (int i=0; i<length; i++) {
            R[i][i] = 1/eps;
        }
    }

    /**
     * This constructor initialises the prerequisites required for the RLS adaptive filter.
     * 'mu' and 'eps' are set to default values of 0.99 and 0.1.
     * @param weights Initialised set of weights
     */
    public RLS(double[] weights) {
        this(0.99, 0.1, weights);
    }

    /**
     * This constructor initialises the prerequisites required for the RLS adaptive filter.
     * @param mu forgetting factor. It is introduced to give exponentially less weight to older error samples.
     * @param eps initialisation value.
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public RLS(int length, double mu, double eps, WeightsFillMethod fillMethod) {
        this.weights = new double[length];
        this.mu = mu;
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

        this.R = new double[length][length];
        for (int i=0; i<length; i++) {
            R[i][i] = 1/eps;
        }
    }

    /**
     * This constructor initialises the prerequisites required for the RLS adaptive filter.
     * 'mu' and 'eps' are set to default values of 0.99 and 0.1.
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public RLS(int length, WeightsFillMethod fillMethod) {
        this(length, 0.99, 0.1, fillMethod);
    }


    /**
     * Adapt weights according one desired value and its input, for a certain k-sample of x.
     * @param desired desired value for a sample 'k' in the input signal
     * @param x array of input samples, starting at index 'k - N' until index 'k', with 'N' being the filter length.
     * @return double[] with first element the filter output 'y' and second element the filter error 'e'
     */
    private double[] adaptWeights(double desired, double[] x) {
        double y = UtilMethods.dotProduct(x, weights);
        // Calculate error
        double error = desired - y;

        double[][] x_squared = UtilMethods.matrixMultiply(UtilMethods.transpose(x), new double[][] {x});

        double[][] R1 = UtilMethods.matrixMultiply(this.R, x_squared);
        R1 = UtilMethods.matrixMultiply(R1, this.R);

        double[][] R2 = UtilMethods.matrixMultiply(new double[][] {x}, this.R);
        R2 = UtilMethods.matrixMultiply(R2, UtilMethods.transpose(x));
        double r2 = R2[0][0] + this.mu;

        for (int i=0; i<R.length; i++) {
            for (int j=0; j<R[i].length; j++) {
                R[i][j] = 1/this.mu * (R[i][j] - R1[i][j]/r2);
            }
        }

        double[] dw = UtilMethods.flattenMatrix(UtilMethods.matrixMultiply(this.R, UtilMethods.transpose(x)));
        dw = UtilMethods.scalarArithmetic(dw, error, "mul");

        this.weights = MathArrays.ebeAdd(this.weights, dw);

        return new double[] {y, error};
    }

    /**
     * Run the RLS adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
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
