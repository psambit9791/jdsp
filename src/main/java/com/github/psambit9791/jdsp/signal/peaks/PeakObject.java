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
    private double[] width;
    private double[] prominence;

    private double[][] prominenceData;
    private double[][] widthData;

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

        this.prominenceData = this.findPeakProminence(this.midpoints);
        this.prominence = prominenceData[0];

        // Peak Width Information
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html
        this.widthData = this.findPeakWidth(this.midpoints, 0.5);
        this.width = widthData[0];
    }

    /**
     * This method calculates the prominence of the peaks provided as an argument
     * @param peaks Peaks for which prominence needs to be calculated
     * @return double[][] The prominence of the input peaks. 0: Contains the prominence, 1: Contains the Left Bases, 2: Contains the Right Bases
     */
    public double[][] findPeakProminence(int[] peaks) throws IllegalArgumentException {
        double[] prominence = new double[peaks.length];
        double[] left_base = new double[peaks.length];
        double[] right_base = new double[peaks.length];
        for (int i=0; i<peaks.length; i++) {
            double leftProm = 0;
            double rightProm = 0;
            double threshold = this.signal[peaks[i]];

            ArrayList<Double> temp = new ArrayList<Double>();
            // Calculate left prominence
            double[] partSignal = new double[0];
            for (int j=peaks[i]-1; j>=0; j--) {
                temp.add(this.signal[j]);
                if (this.signal[j] > threshold) {
                    break;
                }
                partSignal = UtilMethods.reverse(this.convertToPrimitiveDouble(temp));
            }
            leftProm = threshold - StatUtils.min(partSignal);
            left_base[i] = peaks[i] - (partSignal.length - UtilMethods.argmin(partSignal, true));

            temp.clear();
            // Calculate right prominence
            for (int j=peaks[i]+1; j<this.signal.length; j++) {
                temp.add(this.signal[j]);
                if (this.signal[j] > threshold) {
                    break;
                }
                partSignal = this.convertToPrimitiveDouble(temp);
            }
            rightProm = threshold - StatUtils.min(partSignal);
            right_base[i] = peaks[i] + UtilMethods.argmin(partSignal, false) + 1;
            prominence[i] = Math.min(leftProm, rightProm);
        }

        double[][] promData = new double[3][peaks.length];
        promData[0] = prominence;
        promData[1] = left_base;
        promData[2] = right_base;
        return promData;
    }

    /**
     * This method calculates the width of the peaks provided as an argument
     * @param peaks Peaks for which prominence needs to be calculated
     * @throws java.lang.IllegalArgumentException if rel_height is not between 0.0 and 1.0
     * @return double[][] The width of the input peaks. 0: Contains the widths, 1: Contains the Left Intersection Points, 2: Contains the Right Intersection Points
     */
    public double[][] findPeakWidth(int[] peaks, double rel_height) throws IllegalArgumentException {
        if (rel_height > 1.0 || rel_height < 0.0) {
            throw new IllegalArgumentException("rel_height can be between 0.0 and 1.0");
        }
        double[] width = new double[peaks.length];
        double[][] promData = this.findPeakProminence(peaks);
        double[] prominence = promData[0];
        double[] left_bases = promData[1];
        double[] right_bases = promData[2];

        double[] widthHeight = new double[peaks.length];
        double[] leftIntersectPoint = new double[peaks.length];
        double[] rightIntersectPoint = new double[peaks.length];

        for (int i=0; i<peaks.length; i++) {
            widthHeight[i] = this.signal[peaks[i]] - prominence[i]*rel_height;

            // Calculate left intersection point of half width
            int j = peaks[i];
            while (left_bases[i] < j && widthHeight[i] < this.signal[j]) {
                j--;
            }
            leftIntersectPoint[i] = j;
            if (this.signal[j] < widthHeight[i]) {
                leftIntersectPoint[i] += (widthHeight[i] - this.signal[j])/(this.signal[j+1] - this.signal[j]);
            }

            // Calculate right point of half width
            j = peaks[i];
            while (j < right_bases[i] && widthHeight[i] < this.signal[j]) {
                j++;
            }
            rightIntersectPoint[i] = j;
            if (this.signal[j] < widthHeight[i]) {
                rightIntersectPoint[i] -= (widthHeight[i] - this.signal[j])/(this.signal[j-1] - this.signal[j]);
            }

            width[i] = rightIntersectPoint[i] - leftIntersectPoint[i];
        }

        double[][] wData = new double[4][peaks.length];
        wData[0] = width;
        wData[1] = widthHeight;
        wData[2] = leftIntersectPoint;
        wData[3] = rightIntersectPoint;

        return wData;
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
    public double[] getWidth() { return this.width; }

    /**
     * This method returns the half-width of the peaks in the signal along with other properties
     * @return double[][] The list of all the prominence of peaks with the width height, the left and right bases
     */
    public double[][] getWidthData() { return this.widthData; }

    /**
     * This method returns the prominence of the peaks in the signal
     * @return double[] The list of all the prominence of peaks
     */
    public double[] getProminence() { return this.prominence; }

    /**
     * This method returns the prominence of the peaks in the signal along with other properties
     * @return double[][] The list of all the prominence of peaks and the left and right bases
     */
    public double[][] getProminenceData() { return this.prominenceData; }

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
}