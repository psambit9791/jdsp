package com.onyx.signal;

import java.util.*;

public class FindPeak {

    private double[] signal;
    private ArrayList<Integer> peak_indices;
    private ArrayList<Integer> trough_indices;
    private int Fn;

    public FindPeak(double[] s) {
        this.signal = s;
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
    }

    public FindPeak(double[] s, int Fs) {
        this.signal = s;
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
        this.Fn = (int) (Fs/2);
    }

    private void reset_indices() {
        this.peak_indices = new ArrayList<Integer>();
        this.trough_indices = new ArrayList<Integer>();
    }

    public int[] detect_peaks() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] < this.signal[i]) && (this.signal[i+1] < this.signal[i])) {
                this.peak_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.peak_indices);
    }

    public int[] detect_troughs() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] > this.signal[i]) && (this.signal[i+1] > this.signal[i])) {
                this.peak_indices.add(i);
            }
        }
        return this.convertToPrimitive(this.peak_indices);
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

    private ArrayList removeDuplicates(ArrayList<Integer> list) {
        Set<Integer> set = new HashSet<Integer>(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
