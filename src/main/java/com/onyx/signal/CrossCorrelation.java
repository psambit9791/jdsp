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

    private double[] reverseArray(double[] arr) {
        double[] temp = new double[arr.length];
        int iterator = 0;
        for (int i=arr.length-1; i>=0; i--) {
            temp[iterator] = arr[i];
            iterator++;
        }
        return temp;
    }

    public void crossCorrelate() {
        this.kernel = this.reverseArray(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel, this.mode);
        c1.convolve();
        this.output = c1.getOutput();
    }

    public double[] getOutput() {
        return this.output;
    }
}
