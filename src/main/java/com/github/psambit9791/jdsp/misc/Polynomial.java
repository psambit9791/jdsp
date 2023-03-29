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

package com.github.psambit9791.jdsp.misc;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class Polynomial {

    /**
     * Fit a polynomial of 'deg' degree to an array of point.
     * @param x The abscissa (horizontal axis)
     * @param y The ordinate (vertical axis)
     * @param degree Degree of the fitting polynomial
     * @return double[] Polynomial coefficients with the highest power first
     */
    public static double[] polyfit(double[] x, double[] y, int degree) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y must be of same length");
        }
        PolynomialCurveFitter pcf = PolynomialCurveFitter.create(degree);
        ArrayList<WeightedObservedPoint> points = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            points.add(new WeightedObservedPoint(1, x[i], y[i]));
        }
        return UtilMethods.reverse(pcf.fit(points));
    }

    /**
     * Evaluate a polynomial at specific values
     * @param coefficients polynomial coefficients (including coefficients equal to zero) from the highest degree to the constant term
     * @param x A list of number at which the polynomial is evaluated
     * @return double[] The evaluation result for the input list x
     */
    public static double[] polyval(double[] coefficients, double[] x) {
        int degree = coefficients.length - 1;
        double[] y = new double[x.length];
        Arrays.fill(y, 0.0);
        for (int i=0; i<x.length; i++) {
            for (int j=0; j<coefficients.length; j++) {
                y[i] = y[i] + coefficients[j] * Math.pow(x[i], degree-j);
            }
        }
        return y;
    }

    /**
     * Evaluate a polynomial at a specific value
     * @param coefficients polynomial coefficients (including coefficients equal to zero) from the highest degree to the constant term
     * @param x A number at which the polynomial is evaluated
     * @return double The evaluation result for the input x
     */
    public static double polyval(double[] coefficients, double x) {
        int degree = coefficients.length - 1;
        double y = 0.0;
        for (int j=0; j<coefficients.length; j++) {
            y = y + coefficients[j] * Math.pow(x, degree-j);
        }
        return y;
    }

    /**
     * Returns the derivative of the specified order of a polynomial.
     * @param p Polynomial Coefficients with the highest power first
     * @param m Order of derivative
     * @return double[] Derivative of the polynomial coefficients
     */
    public static double[] polyder(double[] p, int m) {
        if (m > p.length || m < 0) {
            throw new IllegalArgumentException("m should be greater than 0 and less than number of polynomial coefficients");
        }
        double[] p_holder = p;
        double[] der;
        for (int i=0; i<m; i++) {
            der = new double[p_holder.length-1];
            for (int j=0; j<der.length; j++) {
                der[j] = p_holder[j] * (p_holder.length-j-1);
            }
            p_holder = der;
        }
        return p_holder;
    }
}
