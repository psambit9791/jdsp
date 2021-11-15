package com.github.psambit9791.jdsp.filter.adaptive;

import java.util.Arrays;

/**
 * <h1>Normalized Least-mean-squares (NLMS) adaptive filter</h1>
 * The NLMS adaptive filter is a filter that adapts its filter weights to get an input signal x to match a desired output
 * signal (= the output of the filter). It does this by trying to minimize the squared error between the desired signal
 * and the filter output signal.
 *
 * It is very similar to the LMS-filter, with the difference that the learning rate gets automatically adjusted according
 * to the input signal's power.
 *
 * @author SiboVanGool
 * @version 1.0
 */
public class NLMSFilter {
    private final double learningRate;  // Learning rate (= step size)
    private double[] weights;           // Weights of the filter
    private double[] error;             // Error of the filter
    private double[] output;            // Filtered output

    /**
     * Dictates how the filter weights initialization should be done:
     *      RANDOM: filter weights get an initial random value ranging from 0 to 1
     *      ZEROS: filter weights get initial value 0
     */
    public enum WeightsFillMethod {
        RANDOM,
        ZEROS
    }

    /**
     * This constructor initialises the prerequisites required for the NLMS adaptive filter.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2
     * @param weights initialized weights (size = number of taps of the filter)
     */
    public NLMSFilter(double learningRate, double[] weights) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must be non-null and with a length greater than 0");
        }
        if (learningRate < 0 || learningRate > 2) {
            System.err.println("Keep the learning rate between 0 and 2 to avoid diverging results");
        }
        this.learningRate = learningRate;
        this.weights = weights;
    }

    /**
     * This constructor initialises the prerequisites required for the NLMS adaptive filter.
     * @param learningRate also known as step size. Determines how fast the adaptive filter changes its filter weights.
     *                     If it is too slow, the filter may have bad performance. If it is too high, the filter will
     *                     be unstable. A correct learning rate is dependent on the power of the input signal
     *                      For a stable filter, the learning rate should be:
     *                          0 ≤ learningRate ≤ 2
     * @param length length (number of taps) of the filter
     * @param fillMethod determines how the weights should be initialized
     */
    public NLMSFilter(double learningRate, int length, WeightsFillMethod fillMethod) {
        if (learningRate < 0 || learningRate > 2) {
            System.err.println("Keep the learning rate between 0 and 2 to avoid diverging results");
        }
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
        this.learningRate = learningRate;
    }

    /**
     * Adapt weights according one desired value and its input, for a certain k-sample of x.
     * @param desired desired value for a sample 'k' in the input signal
     * @param x array of input samples, starting at index 'k - N' until index 'k', with 'N' being the filter length.
     * @return double[] with first element the filter output 'y' and second element the filter error 'e'
     */
    private double[] adaptWeights(double desired, double[] x) {
        double y = 0;
        double power_x = 0;
        double regTerm = 2.2204460492503131E-16;  // Regularization term (for when power_x is zero) - term taken from MATLAB: https://nl.mathworks.com/help/dsp/ref/dsp.lmsfilter-system-object.html#bsfxw0_-6
        // Calculate output and power in x
        for (int i = 0; i < x.length; i++) {
            y += x[x.length - 1 - i] * weights[i];
            power_x += Math.pow(x[i], 2);
        }

        // Calculate error
        double error = desired - y;

        // Update filter coefficients
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = this.weights[i] + this.learningRate/(regTerm + power_x) * error * x[x.length - 1 - i];
        }

        return new double[] {y, error};
    }

    /**
     * Run the NLMS adaptive filter algorithm. This will iterate over the input signal x and adapt the filter weights to
     * match the desired signal.
     * @param desired desired signal that you want after filtering of x
     * @param x input signal that you want to filter with the NLMS adaptive filter to achieve the desired signal
     */
    public void run(double[] desired, double[] x) {
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

            double[] out = adaptWeights(desired[i], x_subset);
            this.output[i] = out[0];
            this.error[i] = out[1];
        }
    }

    /**
     * Returns the final weights of the NLMS adaptive filter
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
            throw new ExceptionInInitializerError("Execute run() function before returning result");
        }
    }
}
