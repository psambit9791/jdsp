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

import java.util.Arrays;

/**
 * <h1>Generalized Normalized Gradient Descent (GNGD) adaptive filter</h1>
 * The GNGD adaptive filter is an extension of the normalised least mean squares (NLMS) adaptive filter. It adds an
 * additional gradient adaptive term in the denominator of the learning rate term, which allows it to adapt its learning
 * rate according to the dynamics of the input signal.
 *
 * Cite: Mandic, D. P. (2004). A generalized normalized gradient descent algorithm. IEEE signal processing letters, 11(2), 115-118.
 *
 * @author Sambit Paul
 * @version 1.0
 */
public class GNGD implements _Adaptive{

    private double[] weights;           // Weights of the filter
    private double[] error;
    private double[] output;
    private double mu;                  // Learning rate
    private double ro;                  // Adaptation parameter
    private double eps;                 // Compensation term

    private double last_e;
    private double[] last_x;

    /**
     * This constructor initialises the prerequisites required for the GNGD adaptive filter.
     * @param learningRate learning rate
     * @param eps compensation term
     * @param ro step size adaptation parameter
     * @param weights Initialised set of weights
     */
    public GNGD(double learningRate, double eps, double ro, double[] weights) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must be non-null and with a length greater than 0");
        }
        this.weights = weights;
        this.mu = learningRate;
        this.ro = ro;
        this.eps = eps;
        int length = weights.length;

        this.last_e = 0.0;
        this.last_x = new double[length];
        Arrays.fill(this.last_x, 0.0);
    }

    /**
     * This constructor initialises the prerequisites required for the GNGD adaptive filter.
     * 'mu' is set to 1.0, 'eps' is set to 1.0 and 'ro' is set to 0.1 as default values.
     * @param weights Initialised set of weights
     */
    public GNGD(double[] weights) {
        this(1.0, 1.0, 0.1, weights);
    }

    /**
     * This constructor initialises the prerequisites required for the GNGD adaptive filter.
     * @param length length (number of taps) of the filter
     * @param learningRate learning rate
     * @param eps compensation term
     * @param ro step size adaptation parameter
     * @param fillMethod determines how the weights should be initialized
     */
    public GNGD(int length, double learningRate, double eps, double ro, WeightsFillMethod fillMethod) {
        this.weights = new double[length];
        this.mu = learningRate;
        this.eps = eps;
        this.ro = ro;
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

        this.last_e = 0.0;
        this.last_x = new double[length];
        Arrays.fill(this.last_x, 0.0);
    }

    /**
     * This constructor initialises the prerequisites required for the GNGD adaptive filter.
     * 'mu' is set to 1.0, 'eps' is set to 1.0 and 'ro' is set to 0.1 as default values.
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public GNGD(int length, WeightsFillMethod fillMethod) {
        this(length, 1.0, 1.0, 0.1, fillMethod);
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

        double temp = UtilMethods.dotProduct(x, last_x)/Math.pow(UtilMethods.dotProduct(last_x, last_x) + this.eps, 2);
        temp = this.ro * this.mu * error * this.last_e * temp;
        this.eps = this.eps - temp;

        double nu = this.mu / (UtilMethods.dotProduct(x, x) + this.eps);
        for (int i=0; i<this.weights.length; i++) {
            this.weights[i] = this.weights[i] + nu * error * x[i];
        }

        this.last_e = error;
        return new double[] {y, error};
    }


    /**
     * Run the GNGD adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
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
     * Returns the final weights of the GNGD adaptive filter
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
