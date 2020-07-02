package com.github.psambit9791.jdsp.signal.peaks;

import java.util.*;

/**
 * <h1>SpikeObject</h1>
 * Calculates Spike Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the get_spike() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class SpikeObject {
    private double[] signal;
    private int[] peaks;
    private int[] left_trough;
    private int[] right_trough;
    private double[] left_spike;
    private double[] right_spike;
    private double[] mean_spike;
    private double[] max_spike;

    public SpikeObject(double[] signal, int[] peaks, int[] left, int[] right) {
        this.signal = signal;
        this.peaks = peaks;
        this.left_trough = left;
        this.right_trough = right;
        this.left_spike = this.calculateLeftSpike(this.peaks, this.left_trough);
        this.right_spike = this.calculateRightSpike(this.peaks, this.right_trough);
        this.mean_spike = this.calculateMeanSpikeHeight(this.left_spike, this.right_spike);
        this.max_spike = this.calculateMaxSpikeHeight(this.left_spike, this.right_spike);
    }

    /**
     * This method calculates the height of the spike with respect to the left trough
     * @return double[] The list of all the right spike heights
     */
    public double[] calculateLeftSpike(int[] peaks, int[] left_trough) {
        double[] left_spike = new double[peaks.length];
        for (int i=0; i<peaks.length; i++) {
            if (left_trough[i] != -1) {
                left_spike[i] = Math.abs(this.signal[peaks[i]] - this.signal[left_trough[i]]);
            }
            else {
                left_spike[i] = Double.NaN;
            }
        }
        return left_spike;
    }

    /**
     * This method calculates the height of the spike with respect to the left trough
     * @return double[] The list of all the right spike heights
     */
    public double[] calculateRightSpike(int[] peaks, int[] right_trough) {
        double[] right_spike = new double[peaks.length];
        for (int i=0; i<this.peaks.length; i++) {
            if (right_trough[i] != -1) {
                right_spike[i] = Math.abs(this.signal[peaks[i]] - this.signal[right_trough[i]]);
            }
            else {
                right_spike[i] = Double.NaN;
            }
        }
        return right_spike;
    }

    /**
     * This method returns the height of the peaks as a mean of the left and right troughs
     * @return double[] The list of all the spike heights taken as an average of the neighbouring troughs
     */
    public double[] calculateMeanSpikeHeight(double[] left_spike, double[] right_spike) {
        double[] mean_spike = new double[left_spike.length];
        for (int k=0; k<mean_spike.length; k++) {
            if (!Double.isNaN(left_spike[k]) && !Double.isNaN(right_spike[k])) {
                mean_spike[k] = (left_spike[k] + right_spike[k])/2;
            }
            else if (!Double.isNaN(left_spike[k]) && Double.isNaN(right_spike[k])) {
                mean_spike[k] = left_spike[k];
            }
            else if (!Double.isNaN(right_spike[k]) && Double.isNaN(left_spike[k])) {
                mean_spike[k] = right_spike[k];
            }
        }
        return mean_spike;
    }

    /**
     * This method returns the height of the peaks as the maximum of the left or right troughs
     * @return double[] The list of all the spike heights taken as an maximum between the neighbouring troughs
     */
    public double[] calculateMaxSpikeHeight(double[] left_spike, double[] right_spike) {
        double[] max_spike = new double[left_spike.length];
        for (int k=0; k<max_spike.length; k++) {
            max_spike[k] = Math.max(left_spike[k], right_spike[k]);
        }
        return max_spike;
    }

    /**
     * This method returns the left spike height of the peaks in the signal
     * @return double[] The list of all the left spike heights
     */
    public double[] getLeftSpike() {
        return this.left_spike;
    }

    /**
     * This method returns the right spike height of the peaks in the signal
     * @return double[] The list of all the right spike heights
     */
    public double[] getRightSpike() {
        return this.right_spike;
    }

    /**
     * This method returns the average spike height of the peaks in the signal
     * @return double[] The list of all the mean spike heights
     */
    public double[] getMeanSpike() {
        return this.mean_spike;
    }

    /**
     * This method returns the maximum spike height of the peaks in the signal
     * @return double[] The list of all the maximum spike heights
     */
    public double[] getMaxSpike() {
        return this.max_spike;
    }
}
