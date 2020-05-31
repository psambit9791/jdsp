package com.github.psambit9791.jdsp.filter;

import com.github.psambit9791.jdsp.UtilMethods;
import com.github.psambit9791.jdsp.signal.Convolution;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;
import java.util.Arrays;

/**
 * <h1>Savitzky–Golay Filter</h1>
 * The Savgol class implements the Savitzky–Golay filter in 4 modes of operation: 'nearest', 'constant', 'mirror', 'wrap'.
 * Reference <a href="https://en.wikipedia.org/wiki/Savitzky%E2%80%93Golay_filter">article</a> for more information on Savitzky–Golay Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Savgol {

    private double[] signal;
    private int windowSize;
    private int polyOrder;
    private double[] output;
    private int deriv;
    private double delta;

    private double[] coeffs;

    /**
     * This constructor initialises the prerequisites required to use Savgol filter.
     * deriv is set to 0 and delta is set to 1
     * @param s Signal to be filtered
     * @param windowSize Size of the filter window/kernel
     * @param polynomialOrder The order of the polynomial used to fit the samples
     */
    public Savgol(double[] s, int windowSize, int polynomialOrder) {
        if (polynomialOrder >= windowSize) {
            throw new IllegalArgumentException("polynomialOrder must be less that windowSize");
        }
        this.signal = s;
        this.windowSize = windowSize;
        this.polyOrder = polynomialOrder;
        this.deriv = 0;
        this.delta = 1;
    }

    /**
     * This constructor initialises the prerequisites required to use Savgol filter.
     * @param s Signal to be filtered
     * @param windowSize Size of the filter window/kernel
     * @param polynomialOrder The order of the polynomial used to fit the samples
     * @param deriv The order of the derivative to compute
     * @param delta The spacing of the samples to which the filter will be applied. Used only if deriv greater than 0
     */
    public Savgol(double[] s, int windowSize, int polynomialOrder, int deriv, double delta) {
        if (polynomialOrder >= windowSize) {
            throw new IllegalArgumentException("polynomialOrder must be less that windowSize");
        }
        this.signal = s;
        this.windowSize = windowSize;
        this.polyOrder = polynomialOrder;
        this.deriv = deriv;
        this.delta = delta;
    }

    /**
     * Compute the coefficients for a 1-d Savitzky-Golay FIR filter based on the parameters provided.
     * @throws java.lang.IllegalArgumentException if window size is even
     * @return the coefficients for a 1-d Savitzky-Golay FIR filter
     */
    public double[] savgol_coeffs() throws IllegalArgumentException {
        int halflen = this.windowSize/2;
        int rem = this.windowSize%2;

        if (rem == 0) {
            throw new IllegalArgumentException("windowSize must be odd");
        }
        double pos = halflen;

        double[] x = UtilMethods.arange(-pos, this.windowSize-pos, 1);
        x = UtilMethods.reverse(x);

        int[] order = UtilMethods.arange(0, polyOrder+1, 1);

        double[][] A = new double[order.length][x.length];
        for (int i = 0; i<order.length; i++) {
            for (int j = 0; j<x.length; j++) {
                A[i][j] = Math.pow(x[j], order[i]);
            }
        }

        double[] y = new double[order.length];
        Arrays.fill(y, 0);

        y[this.deriv] = CombinatoricsUtils.factorial(this.deriv)/(Math.pow(this.delta, this.deriv));
        A = UtilMethods.pseudoInverse(A);
        this.coeffs = MatrixUtils.createRealMatrix(A).operate(y);

        return this.coeffs;
    }

    /**
     * Convolves the 1-d Savitzky-Golay coefficients with the signals in "nearest" mode
     * @return double[] Filtered signal
     */
    public double[] savgol_filter() {
        this.savgol_coeffs();
        Convolution c = new Convolution(this.signal, this.coeffs);
        this.output = c.convolve1d("nearest");
        return this.output;
    }

    /**
     * Convolves the 1-d Savitzky-Golay coefficients with the signals
     * Operates in 4 modes of convolution for filtering: "nearest", "constant", "mirror", "wrap"
     * @param mode Mode of Filter operation
     * @throws java.lang.IllegalArgumentException if mode is not nearest, constant, mirror or wrap
     * @return double[] Filtered signal
     */
    public double[] savgol_filter(String mode) throws IllegalArgumentException {
        if (!mode.equals("nearest") && !mode.equals("constant") && !mode.equals("mirror") && !mode.equals("wrap")) {
            throw new IllegalArgumentException("mode must be mirror, constant, nearest or wrap");
        }
        this.savgol_coeffs();
        Convolution c = new Convolution(this.signal, this.coeffs);
        this.output = c.convolve1d(mode);
        return this.output;
    }
}
