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

import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import static com.github.psambit9791.jdsp.misc.UtilMethods.isSorted;

public class AkimaSpline {

    private PolynomialSplineFunction psf;

    public AkimaSpline(){
        this.psf = null;
    }

    public PolynomialSplineFunction getFunction(){
        if (this.psf == null){
            throw new ExceptionInInitializerError("Execute computeFunction() before returning the function");
        }
        return this.psf;
    }

    public void computeFunction(double[] x, double[] y) {
        if (!isSorted(x, false)) {
            throw new IllegalArgumentException("X-coordinates must be increasing");
        }
        AkimaSplineInterpolator aks = new AkimaSplineInterpolator();
        this.psf = aks.interpolate(x, y);
    }

    public double getValue(double x) {
        if (!this.psf.isValidPoint(x)) {
            throw new IllegalArgumentException("Point has to be within the interpolating range");
        }
        return this.psf.value(x);
    }

    public double[] getValue(double[] x) {
        double[] out = new double[x.length];
        for (int i=0; i<x.length; i++) {
            if (!this.psf.isValidPoint(x[i])) {
                throw new IllegalArgumentException("Point at index " + i + "has to be within the interpolating range");
            }
            out[i] = this.psf.value(x[i]);
        }
        return out;
    }
}
