package com.github.psambit9791.jdsp.signal.peaks;

import java.util.*;

/**
 * <h1>PeakObject</h1>
 * Calculates Peak Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the detect() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class PeakObject {
    private int[] midpoints;
    private double[] height;
    private int[] plateau_size;
    private int[] width;
    private double[] prominence;

    public PeakObject(double[] s, int[] m, int[] l, int[] r, String mode) {

        // Peak Information
        this.midpoints = m;

        // Peak Height and Plateau Information
        this.height = new double[m.length];
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            if (mode.equals("peak")) {
                this.height[i] = s[this.midpoints[i]];
            }
            else if (mode.equals("trough")) {
                this.height[i] = 0 - s[this.midpoints[i]];
            }
            this.plateau_size[i] = Math.abs(r[i] - l[i]);
        }

        // Peak Prominence Information
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html

        // Peak Width Information
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html
    }

    /**
     * This method returns the indices of the signal where the peaks are located
     * @return double[] The list of all the indices of peaks
     */
    public int[] getPeaks() {
        return this.midpoints;
    }

    /**
     * This method returns the heights of the peaks in the signal
     * @return double[] The list of all the heights of peaks
     */
    public double[] getHeights() {
        return this.height;
    }

    /**
     * This method returns the heights of the peaks in the signal
     * @param peaks List of selected peaks
     * @return double[] The list of all the heights of peaks
     */
    public double[] getHeights(int[] peaks) {
        double[] newHeight = new double[peaks.length];
        int[] indices = new int[peaks.length];
        int new_start_point = 0;
        for (int i=0; i<peaks.length; i++) {
            for (int j=new_start_point; j<this.midpoints.length; j++) {
                if (peaks[i] == this.midpoints[j]) {
                    new_start_point = j;
                    indices[i] = j;
                    break;
                }
            }
        }
        for (int i=0; i<indices.length; i++) {
            newHeight[i] = this.height[indices[i]];
        }
        return newHeight;
    }

    /**
     * This method returns the plateau size of the peaks in the signal
     * @return double[] The list of all the plateau size of peaks
     */
    public int[] getPlateauSize() {
        return this.plateau_size;
    }
    /**
     * This method returns the half-width of the peaks in the signal
     * @return double[] The list of all the half width of peaks
     */
    public int[] getWidth() { return this.width; }
    /**
     * This method returns the prominence of the peaks in the signal
     * @return double[] The list of all the prominence of peaks
     */
    public double[] getProminence() { return this.prominence; }

    private int[] convertToPrimitive(ArrayList<Integer> l) {
        int[] ret = new int[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i).intValue();
        }
        return ret;
    }

    /**
     * This method allows filtering the list of peaks using both the upper and lower threshold
     * @param lower_threshold The lower threshold to check against
     * @param upper_threshold The upper threshold to check against
     * @return double[] The list of filtered peaks
     */
    public int[] filterByHeight(double lower_threshold, double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        for (int i=0; i<this.height.length; i++) {
            if (this.height[i] >= lower_threshold && this.height[i] <= upper_threshold) {
                newPeaks.add(this.midpoints[i]);
            }
        }
        return this.convertToPrimitive(newPeaks);
    }

    /**
     * This method allows filtering the list of peaks using either the upper or the lower threshold
     * @param threshold The threshold to check against
     * @param mode Can be "upper" or "lower" to select which thresholding to use
     * @return double[] The list of filtered peaks
     */
    public int[] filterByHeight(double threshold, String mode) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        if (mode.equals("upper")) {
            for (int i=0; i<this.height.length; i++) {
                if (this.height[i] <= threshold) {
                    newPeaks.add(this.midpoints[i]);
                }
            }
        }
        else if (mode.equals("lower")) {
            for (int i=0; i<this.height.length; i++) {
                if (this.height[i] >= threshold) {
                    newPeaks.add(this.midpoints[i]);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Mode must either be lower or upper");
        }
        return this.convertToPrimitive(newPeaks);
    }
}