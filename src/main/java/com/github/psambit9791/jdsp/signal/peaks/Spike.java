/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal.peaks;

import com.github.psambit9791.jdsp.misc.UtilMethods;

import java.util.*;

/**
 * <h1>SpikeObject</h1>
 * Calculates Spike Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the get_spike() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class Spike {
    private double[] signal;
    private int[] peaks;
    private int[] left_trough;
    private int[] right_trough;
    private double[] left_spike;
    private double[] right_spike;
    private double[] mean_spike;
    private double[] max_spike;
    private double[] min_spike;

    public Spike(double[] signal, int[] peaks, int[] left, int[] right) {
        this.signal = signal;
        this.peaks = peaks;
        this.left_trough = left;
        this.right_trough = right;
        this.left_spike = this.calculateLeftSpikeHeight(this.peaks, this.left_trough);
        this.right_spike = this.calculateRightSpikeHeight(this.peaks, this.right_trough);
        this.mean_spike = this.calculateMeanSpikeHeight(this.left_spike, this.right_spike);
        this.max_spike = this.calculateMaxSpikeHeight(this.left_spike, this.right_spike);
        this.min_spike = this.calculateMinSpikeHeight(this.left_spike, this.right_spike);
    }

    /**
     * This method returns indices of the left and right troughs identified
     * @return int[][] Returns the left and right troughs. 0: Left Trough indices, 1: Right Trough indices
     */
    public int[][] getAllTroughs() {
        int[][] troughData = new int[2][this.peaks.length];
        troughData[0] = this.left_trough;
        troughData[1] = this.right_trough;
        return troughData;
    }

    /**
     * This method calculates the height of the spike with respect to the left trough
     * @param peaks List of all the identified peaks
     * @param left_trough List of the troughs on left of given peaks
     * @return double[] The list of all the right spike heights
     */
    public double[] calculateLeftSpikeHeight(int[] peaks, int[] left_trough) {
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
     * @param peaks List of all the identified peaks
     * @param right_trough List of the troughs on right of given peaks
     * @return double[] The list of all the right spike heights
     */
    public double[] calculateRightSpikeHeight(int[] peaks, int[] right_trough) {
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
     * This method returns the height of the peaks as a mean of the left and right troughs. If either value is NaN, only other value is considered
     * @param left_spike List of left spike heights of peaks
     * @param right_spike List of right spike heights of peaks
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
     * This method returns the height of the peaks as the maximum of the left or right troughs. If either value is NaN, other value is considered
     * @param left_spike List of left spike heights of peaks
     * @param right_spike List of right spike heights of peaks
     * @return double[] The list of all the spike heights taken as an maximum between the neighbouring troughs
     */
    public double[] calculateMaxSpikeHeight(double[] left_spike, double[] right_spike) {
        double[] max_spike = new double[left_spike.length];
        for (int k=0; k<max_spike.length; k++) {
            if (Double.isNaN(left_spike[k])) {
                max_spike[k] = right_spike[k];
            }
            else if (Double.isNaN(right_spike[k])) {
                max_spike[k] = left_spike[k];
            }
            else {
                max_spike[k] = Math.max(left_spike[k], right_spike[k]);
            }
        }
        return max_spike;
    }

    /**
     * This method returns the height of the peaks as the minimum of the left or right troughs. If either value is NaN, other value is considered
     * @param left_spike List of left spike heights of peaks
     * @param right_spike List of right spike heights of peaks
     * @return double[] The list of all the spike heights taken as an minimum between the neighbouring troughs
     */
    public double[] calculateMinSpikeHeight(double[] left_spike, double[] right_spike) {
        double[] max_spike = new double[left_spike.length];
        for (int k=0; k<max_spike.length; k++) {
            if (Double.isNaN(left_spike[k])) {
                max_spike[k] = right_spike[k];
            }
            else if (Double.isNaN(right_spike[k])) {
                max_spike[k] = left_spike[k];
            }
            else {
                max_spike[k] = Math.min(left_spike[k], right_spike[k]);
            }
        }
        return max_spike;
    }

    /**
     * This method returns the indices of the signal where the peaks are located
     * @return int[] The list of all the indices of peaks
     */
    public int[] getPeaks() {
        return this.peaks;
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

    /**
     * This method returns the minimum spike height of the peaks in the signal
     * @return double[] The list of all the minimum spike heights
     */
    public double[] getMinSpike() {
        return this.min_spike;
    }

    /**
     * This method returns the peaks with matching spike property
     * @throws java.lang.IllegalArgumentException if spikeType is incorrect. Can be left, right, mean, max and min.
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param lower_threshold The lower threshold of sharpness to check against
     * @param upper_threshold The upper threshold of sharpness to check against
     * @param spikeType What kind of spike to be compared against. Options are 'left', 'right', 'mean', 'max', 'min'
     * @return double[] The list of all the maximum spike heights
     */
    public int[] filterByProperty(Double lower_threshold, Double upper_threshold, String spikeType) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        double[] spikeList;
        if (spikeType.equals("left")) {
            spikeList = this.left_spike;
        }
        else if (spikeType.equals("right")) {
            spikeList = this.right_spike;
        }
        else if (spikeType.equals("mean")) {
            spikeList = this.mean_spike;
        }
        else if (spikeType.equals("max")) {
            spikeList = this.max_spike;
        }
        else if (spikeType.equals("min")) {
            spikeList = this.min_spike;
        }
        else {
            throw new IllegalArgumentException("spikeType Can only be 'left', 'right', 'mean' and 'max'");
        }

        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<spikeList.length; i++) {
                if (Double.isNaN(spikeList[i])) {
                    continue;
                }
                if (spikeList[i] > lower_threshold) {
                    newPeaks.add(this.peaks[i]);
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<spikeList.length; i++) {
                if (Double.isNaN(spikeList[i])) {
                    continue;
                }
                if (spikeList[i] < upper_threshold) {
                    newPeaks.add(this.peaks[i]);
                }
            }
        }
        else {
            for (int i=0; i<spikeList.length; i++) {
                if (Double.isNaN(spikeList[i])) {
                    continue;
                }
                if (spikeList[i] > lower_threshold && spikeList[i] < upper_threshold) {
                    newPeaks.add(this.peaks[i]);
                }
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method returns the heights of the peaks in the signal
     * @param peaks List of selected peaks
     * @return double[] The list of all the heights of peaks
     */
    public double[] findPeakHeights(int[] peaks) {
        double[] newHeight = new double[peaks.length];
        for (int i=0; i<peaks.length; i++) {
            newHeight[i] = this.signal[peaks[i]];
        }
        return newHeight;
    }
}
