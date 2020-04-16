package com.onyx.jdsp.filter;

import com.onyx.jdsp.UtilMethods;
import org.apache.commons.math3.stat.StatUtils;

import java.util.Arrays;

public class Median {

    private double[] signal;
    private int windowSize;

    public Median(double[] s) {
        if (3 >= s.length) {
            throw new IllegalArgumentException("Signal Length has to be greater than 3.");
        }
        this.signal = s;
        this.windowSize = 3;
    }

    public Median(double[] s, int wsize) {
        if (wsize >= s.length) {
            throw new IllegalArgumentException("Window size cannot be greater than or equal to signal length");
        }
        this.signal = s;
        this.windowSize = wsize;
    }

    public double[] median_filter() {
        int paddingSize = (this.windowSize - 1)/2;
        double[] cons = new double[paddingSize];
        double[] newSignal = new double[this.signal.length];
        Arrays.fill(cons, 0);
        double[] paddedSignal = {};
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, cons);
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, this.signal);
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, cons);
        for (int i = 0; i<this.signal.length; i++) {
            newSignal[i] = StatUtils.percentile(paddedSignal, i, this.windowSize, 50);
        }
        return newSignal;
    }

}
