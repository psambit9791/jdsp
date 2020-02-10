package com.onyx.signal;

import java.util.*;

public class FindPeak {

    private double[] signal;
    private ArrayList<Integer> peak_indices;
    private ArrayList<Integer> trough_indices;
    private ArrayList<Integer> midpoints;
    private ArrayList<Integer> left_edge;
    private ArrayList<Integer> right_edge;
    private int Fn;

    public FindPeak(double[] s) {
        this.signal = s;
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
        this.midpoints = new ArrayList<Integer>();
        this.left_edge = new ArrayList<Integer>();
        this.right_edge = new ArrayList<Integer>();
    }

    public FindPeak(double[] s, int Fs) {
        this.signal = s;
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
        this.midpoints = new ArrayList<Integer>();
        this.left_edge = new ArrayList<Integer>();
        this.right_edge = new ArrayList<Integer>();
        this.Fn = (int) (Fs/2);
    }

    private void reset_indices() {
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
        this.midpoints = new ArrayList<Integer>();
        this.left_edge = new ArrayList<Integer>();
        this.right_edge = new ArrayList<Integer>();
    }

    public int[] detect_relative_maxima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] < this.signal[i]) && (this.signal[i+1] < this.signal[i])) {
                this.peak_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.peak_indices);
    }

    public int[] detect_relative_minima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] > this.signal[i]) && (this.signal[i+1] > this.signal[i])) {
                this.trough_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.trough_indices);
    }

    public PeakObject detect_peaks() {
        this.reset_indices();
        int i = 1;
        int i_max = this.signal.length - 1;
        int i_ahead = 0;

        while (i<i_max) {
            if (this.signal[i-1] < this.signal[i]) {
                i_ahead = i + 1;
                while ((i_ahead < i_max) && (this.signal[i_ahead] == this.signal[i])) {
                    i_ahead++;
                }

                if (this.signal[i_ahead] < this.signal[i]) {
                    this.left_edge.add(i);
                    this.right_edge.add(i_ahead-1);
                    this.midpoints.add((i+i_ahead-1)/2);
                    i = i_ahead;
                }
            }
            i++;
        }
        PeakObject pobj = new PeakObject(this.signal, this.convertToPrimitive(this.midpoints), this.convertToPrimitive(this.left_edge), this.convertToPrimitive(this.right_edge));
        return pobj;
    }


//    public double[] get_peak_heights() {
//
//    }
//
//    public int[] filter_peaks(int[] peak_list, double min_height) {
//        this.reset_indices();
//
//    }

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


class PeakObject {
    private int[] midpoints;
    private int[] left_edge;
    private int[] right_edge;

    private double[] left_height;
    private double[] right_height;
    private double[] avg_height;

    private int[] plateau_size;

    public PeakObject(double[] s, int[] m, int[] l, int[] r) {

        // Peak Information
        this.midpoints = m;
        this.left_edge = l;
        this.right_edge = r;

        // Peak Height Information
        this.left_height = new double[m.length];
        this.right_height = new double[m.length];
        this.avg_height = new double[m.length];
        this.plateau_size = new int[m.length];

        for (int i=0; i<m.length; i++) {
            this.left_height[i] = Math.abs(s[this.midpoints[i]] - s[this.left_edge[i]]);
            this.right_height[i] = Math.abs(s[this.midpoints[i]] - s[this.right_edge[i]]);
            this.avg_height[i] = (this.left_height[i] + this.right_height[i])/2.0;
            this.plateau_size[i] = Math.abs(this.right_edge[i] - this.left_edge[i]);
        }
    }

    public int[] getMidpoints() {
        return this.midpoints;
    }
    public int[] getRightEdges() {
        return this.left_edge;
    }
    public int[] getLeftEdges() {
        return this.right_edge;
    }

    public double[] getLeftPeakHeight() {
        return this.left_height;
    }
    public double[] getRightPeakHeight() {
        return this.right_height;
    }
    public double[] getAvgPeakHeight() {
        return this.avg_height;
    }

    public int[] getPlateauSize() {
        return this.plateau_size;
    }
}