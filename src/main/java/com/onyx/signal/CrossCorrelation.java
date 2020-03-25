package com.onyx.signal;

public class CrossCorrelation {

    private double[] signal;
    private double[] kernel;
    private double[] output;
    private String mode;

    public CrossCorrelation(double[] s, double[] w, String mode) {
        this.signal = s;
        this.kernel = w;
        this.mode = mode;
    }

    public double[] crossCorrelate() {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve(this.mode);
        return this.output;
    }
}
