package com.onyx.jdsp.filter;

import com.onyx.jdsp.signal.Convolution;
import com.onyx.jdsp.signal.CrossCorrelation;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

public class Wiener {

    private double[] signal;
    private int windowSize;

    public Wiener(double[] s) {
        if (3 >= s.length) {
            throw new IllegalArgumentException("Signal Length has to be greater than 3.");
        }
        this.signal = s;
        this.windowSize = 3;
    }

    public Wiener(double[] s, int wsize) {
        if (wsize >= s.length) {
            throw new IllegalArgumentException("Window size cannot be greater than or equal to signal length");
        }
        this.signal = s;
        this.windowSize = wsize;
    }

    public double[] wiener_filter() {
        double[] cons = new double[this.windowSize];
        Arrays.fill(cons, 1);

        double[] localMean;
        double[] localVariance;

        // Estimating the local mean
        Convolution c1 = new Convolution(this.signal, cons);
        localMean = c1.convolve("same");
        localMean = MathArrays.scale((1.0/this.windowSize), localMean);

        // Estimating the local variance
        double[] signalSquare = MathArrays.ebeMultiply(this.signal, this.signal);
        double[] meanSquare = MathArrays.ebeMultiply(localMean, localMean);
        CrossCorrelation c2 = new CrossCorrelation(signalSquare, cons);
        localVariance = c2.cross_correlate("same");
        localVariance = MathArrays.scale((1.0/this.windowSize), localVariance);
        localVariance = MathArrays.ebeSubtract(localVariance, meanSquare);

        // Estimating the noise as the Average of the Local Variance of the Signal
        double noiseMean = StatUtils.mean(localVariance);

        double[] res = MathArrays.ebeSubtract(this.signal, localMean);
        double[] temp = new double[localVariance.length];
        for (int i=0; i<temp.length; i++) {
            temp[i] = (1.0 - noiseMean/localVariance[i]);
        }
        res = MathArrays.ebeMultiply(res, temp);
        res = MathArrays.ebeAdd(res, localMean);

        double[] out = new double[localVariance.length];
        for (int i=0; i<out.length; i++) {
            if (localVariance[i] < noiseMean) {
                out[i] = localMean[i];
            }
            else {
                out[i] = res[i];
            }
        }
        return out;
    }
}
