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
import org.apache.commons.math3.stat.StatUtils;

import java.util.*;

/**
 * <h1>PeakObject</h1>
 * Calculates Peak Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the detect() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class Peak {
    private double[] signal;
    private int[] midpoints;
    private double[] height;
    private int[] plateau_size;
    private double[] width;
    private double[] prominence;
    private int[] distance;
    private double[][] sharpness;

    private double[][] prominenceData;
    private double[][] widthData;

    private double relative_height;

    public Peak(double[] s, int[] m, int[] l, int[] r, String mode) {

        this.signal = s;

        // Peak Information
        this.midpoints = m;

        // Peak Height and Plateau Information
        this.height = new double[m.length];

        //(Equivalent to scipy.signal.find_peaks() plateau_size parameter)
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            if (mode.equals("peak")) {
                this.height[i] = s[this.midpoints[i]];
            }
            else if (mode.equals("trough")) {
                this.height[i] = 0 - s[this.midpoints[i]];
            }
            this.plateau_size[i] = Math.abs(r[i] - l[i] + 1);
        }

        for (int i=0; i<m.length; i++) {
            if (mode.equals("peak")) {
                this.height[i] = s[this.midpoints[i]];
            }
            else if (mode.equals("trough")) {
                this.height[i] = 0 - s[this.midpoints[i]];
            }
            this.plateau_size[i] = Math.abs(r[i] - l[i] + 1);
        }

        // Peak Distance Information (Equivalent to scipy.signal.find_peaks() distance parameter)
        this.distance = this.findPeakDistance(this.midpoints);

        // Peak Sharpness Information (Equivalent to scipy.signal.find_peaks() threshold parameter)
        this.sharpness = this.findPeakSharpness(this.midpoints);

        // Peak Prominence Information (Equivalent to scipy.signal.find_peaks() prominence parameter)
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html

        this.prominenceData = this.findPeakProminence(this.midpoints);
        this.prominence = prominenceData[0];

        // Peak Width Information (Equivalent to scipy.signal.find_peaks() width parameter)
        // Refer to https://docs.scipy.org/doc/scipy/reference/generated/scipy.signal.peak_widths.html
        this.relative_height = 0.5;
        this.widthData = this.findPeakWidth(this.midpoints, this.relative_height);
        this.width = widthData[0];
    }

    public Peak(double[] s, int[] m, int[] l, int[] r, double rel_height, String mode) {

        this.signal = s;

        // Peak Information
        this.midpoints = m;

        // Peak Height and Plateau Information
        this.height = new double[m.length];

        //(Equivalent to scipy.signal.find_peaks() plateau_size parameter)
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            if (mode.equals("peak")) {
                this.height[i] = s[this.midpoints[i]];
            }
            else if (mode.equals("trough")) {
                this.height[i] = 0 - s[this.midpoints[i]];
            }
            this.plateau_size[i] = Math.abs(r[i] - l[i] + 1);
        }

        for (int i=0; i<m.length; i++) {
            if (mode.equals("peak")) {
                this.height[i] = s[this.midpoints[i]];
            }
            else if (mode.equals("trough")) {
                this.height[i] = 0 - s[this.midpoints[i]];
            }
            this.plateau_size[i] = Math.abs(r[i] - l[i] + 1);
        }

        // Peak Distance Information (Equivalent to scipy.signal.find_peaks() distance parameter)
        this.distance = this.findPeakDistance(this.midpoints);

        // Peak Sharpness Information (Equivalent to scipy.signal.find_peaks() threshold parameter)
        this.sharpness = this.findPeakSharpness(this.midpoints);

        // Peak Prominence Information (Equivalent to scipy.signal.find_peaks() prominence parameter)
        // Refer to https://uk.mathworks.com/help/signal/ug/prominence.html

        this.prominenceData = this.findPeakProminence(this.midpoints);
        this.prominence = prominenceData[0];

        // Peak Width Information (Equivalent to scipy.signal.find_peaks() width parameter)
        // Refer to https://docs.scipy.org/doc/scipy/reference/generated/scipy.signal.peak_widths.html
        this.relative_height = rel_height;
        this.widthData = this.findPeakWidth(this.midpoints, this.relative_height);
        this.width = widthData[0];
    }

    /**
     * This method returns the indices of the peaks from this.midpoints
     * @throws java.lang.IllegalArgumentException If argument peaks has a peak value not present in the actual signal peaks
     * @param peaks List of selected peaks
     * @return int[] The corresponding indices from this.midpoints
     */
    private int[] getIndexFromPeak(int[] peaks) {
        int[] indices = new int[peaks.length];
        for (int i=0; i<indices.length; i++) {
            boolean exists = false;
            for (int j=0; j<this.midpoints.length; j++) {
                if (peaks[i] == this.midpoints[j]) {
                    indices[i] = j;
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new IllegalArgumentException("Peaks in argument does not exist in the original peak list");
            }
        }
        return indices;
    }


    /**
     * This method returns the heights of the peaks in the signal
     * @param peaks List of selected peaks
     * @return double[] The list of all the heights of peaks
     */
    public double[] findPeakHeights(int[] peaks) {
        getIndexFromPeak(peaks);
        double[] newHeight = new double[peaks.length];
        for (int i=0; i<peaks.length; i++) {
            newHeight[i] = this.signal[peaks[i]];
        }
        return newHeight;
    }

    /**
     * This method returns the spread of the peak in the signal
     * @param peaks List of selected peaks
     * @return double[] The list of all the spreads of peaks
     */
    public int[] findPlateauSize(int[] peaks) {
        int[] peak_indices = this.getIndexFromPeak(peaks);
        int[] newPS = new int[peaks.length];
        for (int i=0; i<peak_indices.length; i++) {
            newPS[i] = this.plateau_size[peak_indices[i]];
        }
        return newPS;
    }

    /**
     * This method calculates the vertical distance to its neighboring samples
     * (Equivalent to scipy.signal.find_peaks() threshold parameter)
     * @param peaks Peaks for which distance needs to be calculated
     * @return int[][] The vertical distance between the preceding and following samples of peak. 0: Vertical distance from preceding peak, 1: Vertical distance from following peak
     */
    public double[][] findPeakSharpness(int[] peaks) {
        getIndexFromPeak(peaks);
        double[][] sharpness = new double[2][peaks.length];
        for (int i=0; i<peaks.length; i++) {
            sharpness[0][i] = this.signal[peaks[i]] - this.signal[peaks[i]-1];
            sharpness[1][i] = this.signal[peaks[i]] - this.signal[peaks[i]+1];
        }
        return sharpness;
    }

    /**
     * This method calculates the distance between peaks
     * (Equivalent to scipy.signal.find_peaks() distance parameter)
     * @param peaks Peaks for which distance needs to be calculated
     * @return int[] An array of distances between peaks
     */
    public int[] findPeakDistance(int[] peaks) {
        getIndexFromPeak(peaks);
        Arrays.sort(peaks);
        return UtilMethods.diff(peaks);
    }

    /**
     * This method calculates the prominence of the peaks provided as an argument
     * (Equivalent to scipy.signal.find_peaks() prominence parameter)
     * @param peaks Peaks for which prominence needs to be calculated
     * @return double[][] The prominence of the input peaks. 0: Contains the prominence, 1: Contains the Left Bases, 2: Contains the Right Bases
     */
    public double[][] findPeakProminence(int[] peaks) {
        getIndexFromPeak(peaks);
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
                partSignal = UtilMethods.reverse(UtilMethods.convertToPrimitiveDouble(temp));
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
                partSignal = UtilMethods.convertToPrimitiveDouble(temp);
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
     * (Equivalent to scipy.signal.find_peaks() width parameter)
     * @param peaks Peaks for which prominence needs to be calculated
     * @param rel_height the relative height at which the peak width is measured as a percentage of its prominence
     * @throws java.lang.IllegalArgumentException if rel_height is not between 0.0 and 1.0
     * @return double[][] The width of the input peaks. 0: Contains the widths, 1: Contains the Left Intersection Points, 2: Contains the Right Intersection Points
     */
    public double[][] findPeakWidth(int[] peaks, double rel_height) throws IllegalArgumentException {
        getIndexFromPeak(peaks);

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
     * @return int[] The list of all the indices of peaks
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
     * @return double[] The list of all the heights of peaks
     */
    public double[][] getPeakSharpness() {
        return this.sharpness;
    }

    /**
     * This method returns the plateau size of the peaks in the signal
     * @return int[] The list of all the plateau size of peaks
     */
    public int[] getPlateauSize() {
        return this.plateau_size;
    }

    /**
     * This method returns the distance between the detected peaks
     * @return int[] The list of distances between peaks
     */
    public int[] getPeakDistance() {
        return this.distance;
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

    /**
     * This method allows filtering all the peaks by height using both the upper and lower threshold
     * @param lower_threshold The lower threshold of height to check against. Use null to omit.
     * @param upper_threshold The upper threshold of height to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByHeight(Double lower_threshold, Double upper_threshold) {
        return this.filterByHeight(this.midpoints, lower_threshold, upper_threshold);
    }

    /**
     * This method allows filtering the list of peaks by height using both the upper and lower threshold
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param peaks List of peaks to be filtered
     * @param lower_threshold The lower threshold of height to check against. Use null to omit.
     * @param upper_threshold The upper threshold of height to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByHeight(int[] peaks, Double lower_threshold, Double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        double[] height = this.findPeakHeights(peaks);
        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<height.length; i++) {
                if (height[i] >= lower_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<height.length; i++) {
                if (height[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else {
            for (int i=0; i<height.length; i++) {
                if (height[i] >= lower_threshold && height[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method allows filtering the total peaks by plateau size using both the upper and lower threshold
     * @param lower_threshold The lower threshold of plateau size to check against. Use null to omit.
     * @param upper_threshold The upper threshold of plateau size to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByPlateauSize(Double lower_threshold, Double upper_threshold) {
        return this.filterByPlateauSize(this.midpoints, lower_threshold, upper_threshold);
    }

    /**
     * This method allows filtering all the peaks by plateau size using both the upper and lower threshold
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param peaks List of peaks to be filtered
     * @param lower_threshold The lower threshold of plateau size to check against. Use null to omit.
     * @param upper_threshold The upper threshold of plateau size to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByPlateauSize(int[] peaks, Double lower_threshold, Double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        int[] plateau_size = this.findPlateauSize(peaks);
        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<plateau_size.length; i++) {
                if (plateau_size[i] >= lower_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<plateau_size.length; i++) {
                if (plateau_size[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else {
            for (int i=0; i<plateau_size.length; i++) {
                if (plateau_size[i] >= lower_threshold && plateau_size[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method allows filtering all the peaks by prominence using both the upper and lower threshold
     * @param lower_threshold The lower threshold of prominence to check against. Use null to omit.
     * @param upper_threshold The upper threshold of prominence to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByProminence(Double lower_threshold, Double upper_threshold) {
        return this.filterByProminence(this.midpoints, lower_threshold, upper_threshold);
    }

    /**
     * This method allows filtering the list of peaks by prominence using both the upper and lower threshold
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param peaks List of peaks to be filtered
     * @param lower_threshold The lower threshold of prominence to check against. Use null to omit.
     * @param upper_threshold The upper threshold of prominence to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByProminence(int[] peaks, Double lower_threshold, Double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        double[] prominence = this.findPeakProminence(peaks)[0];
        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<prominence.length; i++) {
                if (prominence[i] >= lower_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<prominence.length; i++) {
                if (prominence[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else {
            for (int i=0; i<prominence.length; i++) {
                if (prominence[i] >= lower_threshold && prominence[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method allows filtering all the peaks by width using both the upper and lower threshold
     * @param lower_threshold The lower threshold of width to check against. Use null to omit.
     * @param upper_threshold The upper threshold of width to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByWidth(Double lower_threshold, Double upper_threshold) {
        return this.filterByWidth(this.midpoints, lower_threshold, upper_threshold);
    }

    /**
     * This method allows filtering the list of peaks by width using both the upper and lower threshold
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param peaks List of peaks to be filtered
     * @param lower_threshold The lower threshold of width to check against. Use null to omit.
     * @param upper_threshold The upper threshold of width to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterByWidth(int[] peaks, Double lower_threshold, Double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        double[] width = this.findPeakWidth(peaks, this.relative_height)[0];
        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<width.length; i++) {
                if (width[i] >= lower_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<width.length; i++) {
                if (width[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        else {
            for (int i=0; i<width.length; i++) {
                if (width[i] >= lower_threshold && width[i] <= upper_threshold) {
                    newPeaks.add(peaks[i]);
                }
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method allows filtering all the peaks by distance
     * @param distance The threshold of distance to check against
     * @return int[] The list of filtered peaks
     */
    public int[] filterByPeakDistance(int distance) {
        return this.filterByPeakDistance(this.midpoints, distance);
    }

    /**
     * This method allows filtering the list of peaks by distance
     * @param peaks List of peaks to be filtered
     * @param distance The threshold of distance to check against
     * @return int[] The list of filtered peaks
     */
    public int[] filterByPeakDistance(int[] peaks, int distance) {
        int[] keep = new int[peaks.length];
        Arrays.fill(keep, 1);
        double[] heights = this.findPeakHeights(peaks);
        int[] priority = UtilMethods.argsort(heights, true);
        for (int i=peaks.length-1; i>=0; i--) {
            int j = priority[i];
            if (keep[j] == 0) {
                continue;
            }

            int k = j - 1;
            while (0 <= k && peaks[j] - peaks[k] < distance) {
                keep[k] = 0;
                k--;
            }

            k = j + 1;
            while (k < peaks.length && peaks[k] - peaks[j] < distance) {
                keep[k] = 0;
                k++;
            }
        }
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        for (int i=0; i<keep.length; i++) {
            if(keep[i] == 1) {
                newPeaks.add(peaks[i]);
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }

    /**
     * This method allows filtering all the peaks by sharpness using both the upper and lower threshold
     * @param lower_threshold The lower threshold of sharpness to check against. Use null to omit.
     * @param upper_threshold The upper threshold of sharpness to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterBySharpness(Double lower_threshold, Double upper_threshold) {
        return this.filterBySharpness(this.midpoints, lower_threshold, upper_threshold);
    }

    /**
     * This method allows filtering the list of peaks by sharpness using both the upper and lower threshold
     * @throws java.lang.IllegalArgumentException If both upper and lower threshold is null
     * @param peaks List of peaks to be filtered
     * @param lower_threshold The lower threshold of sharpness to check against. Use null to omit.
     * @param upper_threshold The upper threshold of sharpness to check against. Use null to omit.
     * @return int[] The list of filtered peaks
     */
    public int[] filterBySharpness(int[] peaks, Double lower_threshold, Double upper_threshold) {
        ArrayList<Integer> newPeaks = new ArrayList<Integer>();
        double[][] sharpness = this.findPeakSharpness(peaks);
        int[] keep = new int[peaks.length];
        Arrays.fill(keep, 1);

        if (lower_threshold == null && upper_threshold == null) {
            throw new IllegalArgumentException("All thresholds cannot be null");
        }
        else if (lower_threshold != null && upper_threshold == null) {
            for (int i=0; i<sharpness[0].length; i++) {
                double minVal = Math.min(sharpness[0][i], sharpness[1][i]);
                if (minVal < lower_threshold) {
                    keep[i] = 0;
                }
            }
        }
        else if (lower_threshold == null && upper_threshold != null) {
            for (int i=0; i<sharpness[0].length; i++) {
                double maxVal = Math.max(sharpness[0][i], sharpness[1][i]);
                if (maxVal > upper_threshold) {
                    keep[i] = 0;
                }
            }
        }
        else {
            for (int i=0; i<sharpness[0].length; i++) {
                double minVal = Math.min(sharpness[0][i], sharpness[1][i]);
                if (minVal < lower_threshold) {
                    keep[i] = 0;
                }
            }
            for (int i=0; i<sharpness[0].length; i++) {
                double maxVal = Math.max(sharpness[0][i], sharpness[1][i]);
                if (maxVal > upper_threshold) {
                    keep[i] = 0;
                }
            }
        }
        for (int i=0; i<keep.length; i++) {
            if(keep[i] == 1) {
                newPeaks.add(peaks[i]);
            }
        }
        return UtilMethods.convertToPrimitiveInt(newPeaks);
    }
}