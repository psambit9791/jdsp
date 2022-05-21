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

package com.github.psambit9791.jdsp.transform;

public class DiscreteSine implements _SineCosine {
    private double[] signal;
    private double[] output = null;
    private int type;

    private Normalization norm;

    public DiscreteSine(double[] s, int type, Normalization norm) throws IllegalArgumentException {
        if ((type <= 0) || (type > 4)) {
            throw new IllegalArgumentException("Type must be between 1 and 4");
        }
        this.signal = s;
        this.type = type;
        this.norm = norm;

    }

    public DiscreteSine(double[] s, Normalization norm) throws IllegalArgumentException {
        this.signal = s;
        this.type = 2;
        this.norm = norm;
    }

    public DiscreteSine(double[] s, int type) throws IllegalArgumentException {
        if ((type <= 0) || (type > 4)) {
            throw new IllegalArgumentException("Type must be between 1 and 4");
        }
        this.signal = s;
        this.type = type;
        this.norm = Normalization.STANDARD;
    }

    public DiscreteSine(double[] s) {
        this.signal = s;
        this.type = 2;
        this.norm = Normalization.STANDARD;
    }

    private double get_scaling_factor(int k) {
        int N = this.signal.length;
        double factor = 0;
        if (this.type == 1) {
            factor = 0.5 * Math.sqrt(2.0/(N+1));
        }
        else if (this.type == 2) {
            if (k == 0) {
                factor = Math.sqrt(1.0/ (4*N));
            }
            else {
                factor = Math.sqrt(1.0/ (2*N));
            }
        }
        else if (this.type == 3 || this.type == 4) {
            factor = 1.0/Math.sqrt(2*N);
        }
        return factor;
    }

    private double[] type1() {
        double[] out = new double[this.signal.length];
        double factor = 1.0;
        for (int k=0; k<out.length; k++) {
            if (this.norm == Normalization.ORTHOGONAL) {
                factor = this.get_scaling_factor(k);
            }
            double sum = 0;
            for (int n = 1; n < this.signal.length - 1; n++) {
                sum += this.signal[n] * Math.sin((Math.PI * (k+1) * (n+1)) / (this.signal.length + 1));
            }
            out[k] = factor * 2 * sum;
        }
        return out;
    }

    private double[] type2() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++){
            double sum = 0;
            for (int n=0; n<this.signal.length; n++) {
                sum += this.signal[n] * Math.sin((Math.PI * (k+1) * (n + 0.5))/this.signal.length);
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k) * 2 * sum;
            }
            else {
                out[k] = 2 * sum;
            }
        }
        return out;
    }

    private double[] type3() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++) {

            double temp0 = Math.pow(-1, k) * this.signal[this.signal.length-1];
            double sum = 0;
            for (int n = 0; n < this.signal.length-1; n++) {
                sum += this.signal[n] * Math.sin((Math.PI * (k+0.5) * (n+1)) / (this.signal.length));
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k) * (temp0 + 2 * sum);
            }
            else {
                out[k] = temp0 + 2 * sum;
            }
        }
        return out;
    }

    private double[] type4() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++){
            double sum = 0;
            for (int n=0; n<this.signal.length; n++) {
                sum += this.signal[n] * Math.sin((Math.PI * (2*k + 1) * (2*n + 1))/(4 * this.signal.length));
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k) * 2 * sum;
            }
            else {
                out[k] = 2 * sum;
            }
        }
        return out;
    }


    public void transform() {
        switch (this.type) {
            case 1:
                this.output = this.type1();
                break;
            case 2:
                this.output = this.type2();
                break;
            case 3:
                this.output = this.type3();
                break;
            case 4:
                this.output = this.type4();
                break;
        }

    }

    public int getSignalLength() {
        return this.signal.length;
    }

    public double[] getMagnitude() throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning result");
        }
        return this.output;
    }
}
