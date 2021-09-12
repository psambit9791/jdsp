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

public class BSpline {

    private int order;
    private double xMin;
    private double xMax;
    private umontreal.ssj.functionfit.BSpline psf;

    public BSpline(int order) {
        this.order = order;
        this.psf = null;
    }

    public umontreal.ssj.functionfit.BSpline getFunction() {
        if (this.psf == null){
            throw new ExceptionInInitializerError("Execute computeFunction() before returning the function");
        }
        return this.psf;
    }

    public void computeFunction(double[] x, double[] y) {
        if (!isSorted(x, false)) {
            throw new IllegalArgumentException("X-coordinates must be increasing");
        }
        this.xMin = x[0];
        this.xMax = x[x.length - 1];
//        this.psf = umontreal.ssj.functionfit.BSpline.createInterpBSpline(x, y, this.order);
        this.psf = new umontreal.ssj.functionfit.BSpline(x, y, this.order);
    }

    private boolean isValidPoint(double val) {
        return (val <= this.xMax) && (val >= this.xMin);
    }

    public double getValue(double x) {
        if (!this.isValidPoint(x)) {
            throw new IllegalArgumentException("Point has to be within the interpolating range");
        }
        return this.psf.evaluate(x);
    }

    public double[] getValue(double[] x) {
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
