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

package com.github.psambit9791.jdsp.splines;

import static com.github.psambit9791.jdsp.misc.UtilMethods.isSorted;

/**
 * <h1>B-Spline</h1>
 * The BSpline class implements the B-spline interpolation method. The B-spline creates a piecewise polynomial function
 * of degree n-1 which is then used to fit with the original signal using the control points.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class BSpline {

    private int order;
    private double xMin;
    private double xMax;
    private umontreal.ssj.functionfit.BSpline psf;

    /**
     * This constructor initialises the prerequisites required to create a B-Spline of the specific order
     * @param order The order of the B-Spline to be constructed
     */
    public BSpline(int order) {
        if (order < 2 || order > 5) {
            throw new IllegalArgumentException("Order must be between 2 and 5");
        }
        this.order = order;
        this.psf = null;
    }

    /**
     * Returns the polynomial spline function after it has been computed using computeFunction() method
     * @return umontreal.ssj.functionfit.BSpline The B-Spline function
     */
    public umontreal.ssj.functionfit.BSpline getFunction() {
        if (this.psf == null){
            throw new ExceptionInInitializerError("Execute computeFunction() before calling this function");
        }
        return this.psf;
    }

    /**
     * Given the X-coordinates and Y-coordinates, this function computes the B-Spline for the specified order.
     * @param x The X-coordinates
     * @param y The Y-coordinates
     */
    public void computeFunction(double[] x, double[] y) {
        if (!isSorted(x, false)) {
            throw new IllegalArgumentException("X-coordinates must be increasing");
        }
        this.xMin = x[0];
        this.xMax = x[x.length - 1];
        this.psf = new umontreal.ssj.functionfit.BSpline(x, y, this.order);
    }

    /**
     * Checks if an X-coordinate value is valid for the spline function
     * @param val An X-coordinate value
     * @return boolean If the provided value is within the limits of the knots.
     */
    private boolean isValidPoint(double val) {
        return (val <= this.xMax) && (val >= this.xMin);
    }

    /**
     * Computes the Y-coordinate for the provided X-coordinate if the X-coordinate is valid.
     * @param x An X-cordinate
     * @return double The Y-coordinate computed using the spline and the X-coordinate
     */
    public double getValue(double x) {
        if (this.psf == null){
            throw new ExceptionInInitializerError("Execute computeFunction() before calling this function");
        }
        if (!this.isValidPoint(x)) {
            throw new IllegalArgumentException("Point has to be within the interpolating range");
        }
        return this.psf.evaluate(x);
    }

    /**
     * Computes the Y-coordinate for the provided list of X-coordinates if the X-coordinates are valid.
     * @param x List of X-coordinates
     * @return double[] List of Y-coordinate computed using the spline and the list of X-coordinates
     */
    public double[] getValue(double[] x) {
        if (this.psf == null){
            throw new ExceptionInInitializerError("Execute computeFunction() before calling this function");
        }
        double[] out = new double[x.length];
        for (int i=0; i<x.length; i++) {
            if (!this.isValidPoint(x[i])) {
                throw new IllegalArgumentException("Point at index " + i + "has to be within the interpolating range");
            }
            out[i] = this.psf.evaluate(x[i]);
        }
        return out;
    }
}
