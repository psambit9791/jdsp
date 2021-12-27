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
 * <h1>Sign-Sign Least-mean-squares (SSLMS) adaptive filter</h1>
 * The SSLMS adaptive filter is a filter that adapts its filter weights to get an input signal to match a desired output
 * signal (= the output of the filter). It does this by trying to minimize the squared error between the desired signal
 * and the filter output signal. Unlike the LMS, the weights are updated based on only the sign of the error and the signal
 * instead of the actual error and signal values.
 * Additionally, you can use a Leaky SSLMS filter by setting the leakage factor in the SSLMS constructor.
 * A leakage factor of less than 1 results in improved stability and tracking of the filter.
 *
 * It is very similar to the LMS-filter, with the difference that the learning rate gets automatically adjusted according
 * to the input signal's power.
 *
 * @author Sambit Paul
 * @version 1.0
 */
public class SSLMS implements _Adaptive{
    private final double learningRate;  // Learning rate (= step size)
    private final double leakageFactor; // Leakage factor
    private double[] weights;           // Weights of the filter
    private double[] error;             // Error of the filter
    private double[] output;            // Filtered output

    /**
     * Returns the sign of the input number.
     * @param err Value whose sign is to be determined
     * @return the sign of the value
     */
    private double sign(double err) {
        double val = 0;
        if (err < 0) {
            val = -1;
        }
        else if (err == 0) {
            val = 0;
        }
        else {
            val = 1;
        }
        return val;
    }

    /**
     * This constructor initialises the prerequisites required for the SSLMS adaptive filter.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to weights.length
     * @param leakageFactor defines how much leakage the Leaky LMS filter should have
     *                          0 ≤ leakageFactor ≤ 1
     *                          leakageFactor of 1 implies no leakage; leakageFactor of less than 1 implies leakage
     * @param weights initialized weights (size = number of taps of the filter)
     */
    public SSLMS(double learningRate, double leakageFactor, double[] weights) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must be non-null and with a length greater than 0");
        }
        this.learningRate = learningRate;
        this.leakageFactor = leakageFactor;
        this.weights = weights;
    }

    /**
     * This constructor initialises the prerequisites required for the SSLMS adaptive filter, without leakage.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to weights.length
     * @param weights initialized weights (size = number of taps of the filter)
     */
    public SSLMS(double learningRate, double[] weights) {
        this(learningRate, 1, weights);
    }

    /**
     * This constructor initialises the prerequisites required for the SSLMS adaptive filter.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to length
     * @param leakageFactor defines how much leakage the Leaky LMS filter should have
     *                          0 ≤ leakageFactor ≤ 1
     *                          leakageFactor of 1 implies no leakage; leakageFactor of less than 1 implies leakage
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public SSLMS(double learningRate, double leakageFactor, int length, WeightsFillMethod fillMethod) {
        this.learningRate = learningRate;
        this.leakageFactor = leakageFactor;
        this.weights = new double[length];
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
    }

    /**
     * This constructor initialises the prerequisites required for the SSLMS adaptive filter, without leakage.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2 / (sum(x^2(k-n)))
     *                              With 'k' being a sample index, and n ranging from 0 to length
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public SSLMS(double learningRate, int length, WeightsFillMethod fillMethod) {
        this(learningRate, 1, length, fillMethod);
    }

    /**
     * Adapt weights according one desired value and its input, for a certain k-sample of x.
     * @param desired desired value for a sample 'k' in the input signal
     * @param x array of input samples, starting at index 'k - N' until index 'k', with 'N' being the filter length.
     * @return double[] with first element the filter output 'y' and second element the filter error 'e'
     */
    private double[] adaptWeights(double desired, double[] x) {
        // Calculate output
        double y = UtilMethods.dotProduct(weights, x);

        // Calculate error
        double error = desired - y;

        // Update filter coefficients
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = this.leakageFactor*this.weights[i] + this.learningRate * this.sign(error) * this.sign(x[i]);
        }

        return new double[] {y, error};
    }

    /**
     * Run the SSLMS adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
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
     * Returns the final weights of the LMS adaptive filter
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
