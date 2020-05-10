package com.github.psambit9791.jdsp.signal;

import java.util.*;

public class FindPeak {

    private double[] signal;
    private ArrayList<Integer> maxima_indices;
    private ArrayList<Integer> minima_indices;
    private int[] peak_indices = null;
    private int[] trough_indices = null;
    private int Fn;

    public FindPeak(double[] s) {
        this.signal = s;
        this.maxima_indices = new ArrayList<Integer>();
        this.minima_indices = new ArrayList<Integer>();
    }

    public FindPeak(double[] s, int Fs) {
        this.signal = s;
        this.maxima_indices = new ArrayList<Integer>();
        this.minima_indices = new ArrayList<Integer>();
        this.Fn = (int) (Fs/2);
    }

    private void reset_indices() {
        this.maxima_indices = new ArrayList<Integer>();
        this.minima_indices = new ArrayList<Integer>();
    }

    public int[] detect_relative_maxima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] < this.signal[i]) && (this.signal[i+1] < this.signal[i])) {
                this.maxima_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.maxima_indices);
    }

    public int[] detect_relative_minima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] > this.signal[i]) && (this.signal[i+1] > this.signal[i])) {
                this.minima_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.minima_indices);
    }

    public int[] detect_peaks() {
        PeakObject p = this.detect(this.signal);
        this.peak_indices = p.getPeaks();
        return this.peak_indices;
    }

    public int[] detect_troughs() {
        double[] reverse_signal = new double[this.signal.length];
        for (int i=0; i<reverse_signal.length; i++) {
            reverse_signal[i] = 0 - this.signal[i];
        }
        PeakObject p = this.detect(reverse_signal);
        this.trough_indices = p.getPeaks();
        return this.trough_indices;
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
        PeakObject pObj = new PeakObject(signal, this.convertToPrimitive(midpoints), this.convertToPrimitive(left_edge), this.convertToPrimitive(right_edge));
        return pObj;
    }

    public SpikeObject get_spike_heights() {
        double[] left_spike = new double[this.peak_indices.length];
        double[] right_spike = new double[this.peak_indices.length];
        double[] avg_spike = new double[this.peak_indices.length];

        if ((this.peak_indices == null) || (this.trough_indices == null)) {
            this.detect_peaks();
            this.detect_troughs();
            //throw new ExceptionInInitializerError("Peaks and Troughs not identified.");
        }
        else {
            int temp = 0;
            for (int i=0; i<this.peak_indices.length; i++) {
                temp = this.getClosest(this.trough_indices, this.peak_indices[i], "min");
                if (temp != -1) {
                    left_spike[i] = Math.abs(this.signal[i] - this.signal[temp]);
                }
                else {
                    left_spike[i] = -1;
                }
                temp = this.getClosest(this.trough_indices, this.peak_indices[i], "max");
                if (temp != -1) {
                    right_spike[i] = Math.abs(this.signal[i] - this.signal[temp]);
                }
                else {
                    right_spike[i] = -1;
                }
            }
        }

        SpikeObject sObj = new SpikeObject(left_spike, right_spike);
        return sObj;
    }

    private int getClosest(int[] arr, int val, String mode) {
        int closest = -1;
        if (mode.equals("min")) {
            int distance = -1000000;
            for (int i=0; i<arr.length; i++) {
                if (((arr[i] - val) > distance) && (arr[i] - val)<0) {
                    distance = arr[i] - val;
                    closest = arr[i];
                }
            }
        }
        else if (mode.equals("max")) {
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

    public SpikeObject get_spike_heights(double[] signal, int[] peaks, int[] troughs) {
        double[] left_spike = new double[peaks.length];
        double[] right_spike = new double[peaks.length];

        int temp = 0;
        for (int i=0; i<peaks.length; i++) {
            temp = this.getClosest(troughs, peaks[i], "min");
            if (temp != -1) {
                left_spike[i] = Math.abs(signal[i] - signal[temp]);
            }
            else {
                left_spike[i] = -1;
            }
            temp = this.getClosest(troughs, peaks[i], "max");
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


// Properties of the spike
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

    public double[] getRightChange() {
        return this.right_spike;
    }
    public double[] getLeftChange() {
        return this.left_spike;
    }
    public double[] getAvgSpikeHeight() {
        for (int k=0; k<this.avg_spike.length; k++) {
            if (this.left_spike[k] < 0) {
                this.avg_spike[k] = this.right_spike[k];
            }
            else if (this.right_spike[k] < 0) {
                this.avg_spike[k] = this.left_spike[k];
            }
            else {
                this.avg_spike[k] = (this.left_spike[k] + this.right_spike[k])/2;
            }
        }
        return this.avg_spike;
    }
    public double[] getMaxSpikeHeight() {
        for (int k=0; k<this.max_spike.length; k++) {
            this.max_spike[k] = Math.max(this.left_spike[k], this.right_spike[k]);
        }
        return this.max_spike;
    }
}


// Properties of the peak
class PeakObject {
    private int[] midpoints;
    private double[] height;
    private int[] plateau_size;

    public PeakObject(double[] s, int[] m, int[] l, int[] r) {

        // Peak Information
        this.midpoints = m;

        // Peak Height Information
        this.height = new double[m.length];
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            this.height[i] = s[this.midpoints[i]];
            this.plateau_size[i] = Math.abs(r[i] - l[i]);
        }
    }

    public int[] getPeaks() {
        return this.midpoints;
    }
    public double[] getHeights() {
        return this.height;
    }
    public int[] getPlateauSize() {
        return this.plateau_size;
    }
}