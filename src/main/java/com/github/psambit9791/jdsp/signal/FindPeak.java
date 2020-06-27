package com.github.psambit9791.jdsp.signal;

import java.util.*;

/**
 * <h1>FindPeak</h1>
 * Detect peaks and extremas (minimas and maximas) in a signal.
 * Reference <a href="https://docs.scipy.org/doc/scipy/reference/signal.html#peak-finding">Scipy Docs on Peak Detection</a> for few of the functionalities provided here.
 * This class provides functions regarding spikes (height and width) and also allows filtering by those properties.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FindPeak {

    private double[] signal;
    private ArrayList<Integer> maxima_indices;
    private ArrayList<Integer> minima_indices;
    private int[] peak_indices = null;
    private int[] trough_indices = null;

    /**
     * This constructor initialises the prerequisites required to use FindPeak.
     * @param s Signal from which peaks need to be detected
     */
    public FindPeak(double[] s) {
        this.signal = s;
        this.maxima_indices = new ArrayList<Integer>();
        this.minima_indices = new ArrayList<Integer>();
    }

    private void reset_indices() {
        this.maxima_indices = new ArrayList<Integer>();
        this.minima_indices = new ArrayList<Integer>();
    }

    /**
     * This method identifies all the maxima within the signal.
     * @return int[] The list of relative maxima identified
     */
    public int[] detect_relative_maxima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] < this.signal[i]) && (this.signal[i+1] < this.signal[i])) {
                this.maxima_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.maxima_indices);
    }

    /**
     * This method identifies all the minima within the signal.
     * @return int[] The list of relative minima identified
     */
    public int[] detect_relative_minima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] > this.signal[i]) && (this.signal[i+1] > this.signal[i])) {
                this.minima_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.minima_indices);
    }

    /**
     * This method identifies all the peaks within the signal.
     * @return PeakObject The list of all the peaks as PeakObject
     */
    public PeakObject detect_peaks() {
        return this.detect(this.signal);
    }

    /**
     * This method identifies all the troughs within the signal.
     * @return PeakObject The list of all the troughs as PeakObject
     */
    public PeakObject detect_troughs() {
        double[] reverse_signal = new double[this.signal.length];
        for (int i=0; i<reverse_signal.length; i++) {
            reverse_signal[i] = 0 - this.signal[i];
        }
        return this.detect(reverse_signal);
    }


    // internal function for detecting peaks
    private PeakObject detect(double[] signal) {
        ArrayList<Integer> midpoints = new ArrayList<Integer>();
        ArrayList<Integer> left_edge = new ArrayList<Integer>();
        ArrayList<Integer> right_edge = new ArrayList<Integer>();
        this.reset_indices();
        int i = 1;
        int i_max = signal.length - 1;
        int i_ahead = 0;

        while (i<i_max) {
            if (signal[i-1] < signal[i]) {
                i_ahead = i + 1;
                while ((i_ahead < i_max) && (signal[i_ahead] == signal[i])) {
                    i_ahead++;
                }

                if (signal[i_ahead] < signal[i]) {
                    left_edge.add(i);
                    right_edge.add(i_ahead-1);
                    midpoints.add((i+i_ahead-1)/2);
                    i = i_ahead;
                }
            }
            i++;
        }
        PeakObject pObj = new PeakObject(signal, this.convertToPrimitive(midpoints));
        return pObj;
    }

    private int getClosest(int[] arr, int val, String mode) {
        int closest = -1;
        if (mode.equals("left")) {
            int distance = -1000000;
            for (int i=0; i<arr.length; i++) {
                if (((arr[i] - val) > distance) && (arr[i] - val)<0) {
                    distance = arr[i] - val;
                    closest = arr[i];
                }
            }
        }
        else if (mode.equals("right")) {
            int distance = 1000000;
            for (int i=arr.length-1; i>=0; i--) {
                if (((arr[i] - val) < distance) && (arr[i] - val)>0) {
                    distance = arr[i] - val;
                    closest = arr[i];
                }
            }
        }
        return closest;
    }

    /**
     * This method identifies all the spikes in the signal.
     * Spikes properties are different from peaks such that, the spike height and width are dependent on their neighbouring troughs.
     * @param signal The signal whose spikes need to be detected
     * @param peaks The peaks that are to be used in this signal
     * @param troughs The troughs that are to be used in this signal
     * @return SpikeObject The list of all the troughs as PeakObject
     */
    public SpikeObject get_spikes(double[] signal, int[] peaks, int[] troughs) {
        double[] left_spike = new double[peaks.length];
        double[] right_spike = new double[peaks.length];

        int temp = 0;
        for (int i=0; i<peaks.length; i++) {
            temp = this.getClosest(troughs, peaks[i], "left");
            if (temp != -1) {
                left_spike[i] = Math.abs(signal[i] - signal[temp]);
            }
            else {
                left_spike[i] = -1;
            }
            temp = this.getClosest(troughs, peaks[i], "right");
            if (temp != -1) {
                right_spike[i] = Math.abs(signal[i] - signal[temp]);
            }
            else {
                right_spike[i] = -1;
            }
        }
        SpikeObject sObj = new SpikeObject(left_spike, right_spike);
        return sObj;
    }

    /**
     * This method identifies all the spikes in the signal.
     * Spikes properties are different from peaks such that, the spike height and width are dependent on their neighbouring troughs.
     * @return SpikeObject The list of all the troughs as PeakObject
     */
    public SpikeObject get_spikes() {
        if ((this.peak_indices == null) || (this.trough_indices == null)) {
            this.detect_peaks();
            this.detect_troughs();
        }
        return this.get_spikes(this.signal, this.peak_indices, this.trough_indices);
    }

    private int[] convertToPrimitive(ArrayList<Integer> l) {
        int[] ret = new int[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i).intValue();
        }
        return ret;
    }

    private ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
        Set<Integer> set = new HashSet<Integer>(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}



/**
 * <h1>SpikeObject</h1>
 * Calculates Spike Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the get_spike() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
class SpikeObject {
    private double[] left_spike;
    private double[] right_spike;
    private double[] avg_spike;
    private double[] max_spike;

    public SpikeObject(double[] l, double[] r) {
        this.left_spike = l;
        this.right_spike = r;
        this.avg_spike = new double[l.length];
        this.max_spike = new double[l.length];
    }

    /**
     * This method returns the height of the peaks with respect to the left trough
     * @return double[] The list of all the right spike heights
     */
    public double[] getLeftChange() {
        return this.left_spike;
    }

    /**
     * This method returns the height of the peaks with respect to the right trough
     * @return double[] The list of all the right spike heights
     */
    public double[] getRightChange() {
        return this.right_spike;
    }

    /**
     * This method returns the height of the peaks as an average of the left and right troughs
     * @return double[] The list of all the spike heights taken as an average of the neighbouring troughs
     */
    public double[] getAvgSpikeHeight() {
        for (int k=0; k<this.avg_spike.length; k++) {
            if (!Double.isNaN(this.left_spike[k]) && !Double.isNaN(this.right_spike[k])) {
                this.avg_spike[k] = (this.left_spike[k] + this.right_spike[k])/2;
            }
            else if (!Double.isNaN(this.left_spike[k])) {
                this.avg_spike[k] = this.left_spike[k];
            }
            else if (!Double.isNaN(this.right_spike[k])) {
                this.avg_spike[k] = this.right_spike[k];
            }
        }
        return this.avg_spike;
    }

    /**
     * This method returns the height of the peaks as the maximum of the left or right troughs
     * @return double[] The list of all the spike heights taken as an maximum between the neighbouring troughs
     */
    public double[] getMaxSpikeHeight() {
        for (int k=0; k<this.max_spike.length; k++) {
            this.max_spike[k] = Math.max(this.left_spike[k], this.right_spike[k]);
        }
        return this.max_spike;
    }
}


/**
 * <h1>PeakObject</h1>
 * Calculates Peak Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the detect() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
class PeakObject {
    private int[] midpoints;
    private double[] height;
    private int[] plateau_size;
    private int[] width;
    private double[] prominence;

    public PeakObject(double[] s, int[] m) {

        // Peak Information
        this.midpoints = m;

        // Peak Height and Plateau Information
        this.height = new double[m.length];
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            this.height[i] = s[this.midpoints[i]];
            this.plateau_size[i] = Math.abs(r[i] - l[i]);
        }

        // Peak Prominence Information
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
    public int[] filterByHeights(double lower_threshold, double upper_threshold) {
        ArrayList<Integer> holder = new ArrayList<Integer>();
        for (int i=0; i<this.height.length; i++) {
            if (this.height[i] >= lower_threshold && this.height[i] <= upper_threshold) {
                holder.add(this.midpoints[i]);
            }
        }
        return this.convertToPrimitive(holder);
    }

    /**
     * This method allows filtering the list of peaks using either the upper or the lower threshold
     * @param threshold The threshold to check against
     * @param mode Can be "upper" or "lower" to select which thresholding to use
     * @return double[] The list of filtered peaks
     */
    public int[] filterByHeights(double threshold, String mode) {
        ArrayList<Integer> holder = new ArrayList<Integer>();
        if (mode.equals("upper")) {
            for (int i=0; i<this.height.length; i++) {
                if (this.height[i] <= threshold) {
                    holder.add(this.midpoints[i]);
                }
            }
        }
        else if (mode.equals("lower")) {
            for (int i=0; i<this.height.length; i++) {
                if (this.height[i] >= threshold) {
                    holder.add(this.midpoints[i]);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Mode must either be lower or upper");
        }
        return this.convertToPrimitive(holder);
    }
}