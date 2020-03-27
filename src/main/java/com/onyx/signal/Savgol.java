package com.onyx.signal;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Arrays;

public class Savgol {

    private double[] signal;
    private int windowSize;
    private int polyOrder;
    private double[] output;
    private int deriv;
    private double delta;

    private double[] coeffs;

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


    public double[] savgol_coeffs() {
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

    public double[] savgol_filter() {
        this.savgol_coeffs();
        Convolution c = new Convolution(this.signal, this.coeffs);
        this.output = c.convolve1d("nearest");
        return this.output;
    }

    public double[] savgol_filter(String mode) {
        if (!mode.equals("nearest") && !mode.equals("constant") && !mode.equals("mirror") && !mode.equals("wrap")) {
            throw new IllegalArgumentException("mode must be mirror, constant, nearest or wrap");
        }
        this.savgol_coeffs();
        Convolution c = new Convolution(this.signal, this.coeffs);
        this.output = c.convolve1d(mode);
        return this.output;
    }
}
