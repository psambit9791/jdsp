package com.github.psambit9791.jdsp.signal.peaks;

import com.github.psambit9791.jdsp.UtilMethods;
import org.apache.commons.math3.stat.StatUtils;

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
    private double[] signal;
    private int[] midpoints;
    private double[] height;
    private int[] plateau_size;
    private int[] width;
    private double[] prominence;

    public PeakObject(double[] s, int[] m, int[] l, int[] r, String mode) {

        this.signal = s;

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

        this.prominence = new double[m.length];

        for (int i=0; i<m.length; i++) {
            double leftProm = 0;
            double rightProm = 0;
            double threshold = this.signal[this.midpoints[i]];

            ArrayList<Double> temp = new ArrayList<Double>();
            // Calculate left prominence
            for (int j=this.midpoints[i]-1; j>=0; j--) {
                temp.add(this.signal[j]);
                if (this.signal[j] > threshold) {
                    break;
                }
                double[] partSignal = UtilMethods.reverse(this.convertToPrimitiveDouble(temp));
                leftProm = threshold - StatUtils.min(partSignal);
            }

            temp.clear();
            // Calculate right prominence
            for (int j=this.midpoints[i]+1; j<this.signal.length; j++) {
                temp.add(this.signal[j]);
                if (this.signal[j] > threshold) {
                    break;
                }
                double[] partSignal = this.convertToPrimitiveDouble(temp);
                rightProm = threshold - StatUtils.min(partSignal);
            }
            this.prominence[i] = Math.min(leftProm, rightProm);
        }


        // Peak Width Information
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html
    }

    private int[] getIndexFromPeak(int[] peaks) {
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
        return indices;
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
        for (int i=0; i<peaks.length; i++) {
            newHeight[i] = this.signal[peaks[i]];
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

    private double[] convertToPrimitiveDouble(ArrayList<Double> l) {
        double[] ret = new double[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i).doubleValue();
        }
        return ret;
    }

    /**
     * This method allows filtering the list of peaks by height using both the upper and lower threshold
     * @param lower_threshold The lower threshold of height to check against
     * @param upper_threshold The upper threshold of height to check against
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
     * This method allows filtering the list of peaks by height using either the upper or the lower threshold
     * @param threshold The threshold of height to check against
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

    /**
     * This method allows filtering the list of peaks by width using both the upper and lower threshold
     * @param lower_threshold The lower threshold of width to check against
     * @param upper_threshold The upper threshold of width to check against
     * @return double[] The list of filtered peaks
     */
    public int[] filterByWidth(double lower_threshold, double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        for (int i=0; i<this.width.length; i++) {
            if (this.width[i] >= lower_threshold && this.width[i] <= upper_threshold) {
                newPeaks.add(this.midpoints[i]);
            }
        }
        return this.convertToPrimitive(newPeaks);
    }

    /**
     * This method allows filtering the list of peaks by width using either the upper or the lower threshold
     * @param threshold The threshold of width to check against
     * @param mode Can be "upper" or "lower" to select which thresholding to use
     * @return double[] The list of filtered peaks
     */
    public int[] filterByWidth(double threshold, String mode) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        if (mode.equals("upper")) {
            for (int i=0; i<this.width.length; i++) {
                if (this.width[i] <= threshold) {
                    newPeaks.add(this.midpoints[i]);
                }
            }
        }
        else if (mode.equals("lower")) {
            for (int i=0; i<this.width.length; i++) {
                if (this.width[i] >= threshold) {
                    newPeaks.add(this.midpoints[i]);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Mode must either be lower or upper");
        }
        return this.convertToPrimitive(newPeaks);
    }

    /**
     * This method allows filtering the list of peaks by prominence using both the upper and lower threshold
     * @param lower_threshold The lower threshold of prominence to check against
     * @param upper_threshold The upper threshold of prominence to check against
     * @return double[] The list of filtered peaks
     */
    public int[] filterByProminence(double lower_threshold, double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        for (int i=0; i<this.prominence.length; i++) {
            if (this.prominence[i] >= lower_threshold && this.prominence[i] <= upper_threshold) {
                newPeaks.add(this.midpoints[i]);
            }
        }
        return this.convertToPrimitive(newPeaks);
    }

    /**
     * This method allows filtering the list of peaks by prominence using either the upper or the lower threshold
     * @param threshold The threshold of prominence to check against
     * @param mode Can be "upper" or "lower" to select which thresholding to use
     * @return double[] The list of filtered peaks
     */
    public int[] filterByProminence(double threshold, String mode) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        if (mode.equals("upper")) {
            for (int i=0; i<this.prominence.length; i++) {
                if (this.prominence[i] <= threshold) {
                    newPeaks.add(this.midpoints[i]);
                }
            }
        }
        else if (mode.equals("lower")) {
            for (int i=0; i<this.prominence.length; i++) {
                if (this.prominence[i] >= threshold) {
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