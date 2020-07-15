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
 * <h1>FindPeak</h1>
 * Detect peaks and extremas (minimas and maximas) in a signal.
 * Reference <a href="https://docs.scipy.org/doc/scipy/reference/signal.html#peak-finding">Scipy Docs on Peak Detection</a> for few of the functionalities provided here.
 * This class provides functions regarding spikes and also allows filtering by those properties.
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
    public int[] detectRelativeMaxima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] < this.signal[i]) && (this.signal[i+1] < this.signal[i])) {
                this.maxima_indices.add(i);
            }
        }
        return UtilMethods.convertToPrimitiveInt(this.maxima_indices);
    }

    /**
     * This method identifies all the minima within the signal.
     * @return int[] The list of relative minima identified
     */
    public int[] detectRelativeMinima() {
        this.reset_indices();
        for (int i = 1; i<this.signal.length-1; i++) {
            if ((this.signal[i-1] > this.signal[i]) && (this.signal[i+1] > this.signal[i])) {
                this.minima_indices.add(i);
            }
        }
        return UtilMethods.convertToPrimitiveInt(this.minima_indices);
    }

    /**
     * This method identifies all the peaks within the signal.
     * @return PeakObject The list of all the peaks as PeakObject
     */
    public Peak detectPeaks() {
        Peak p = this.detect(this.signal, "peak");
        this.peak_indices = p.getPeaks();
        return p;
    }

    /**
     * This method identifies all the troughs within the signal.
     * @return PeakObject The list of all the troughs as PeakObject
     */
    public Peak detectTroughs() {
        double[] reverse_signal = new double[this.signal.length];
        for (int i=0; i<reverse_signal.length; i++) {
            reverse_signal[i] = 0 - this.signal[i];
        }
        Peak p = this.detect(reverse_signal, "trough");
        this.trough_indices = p.getPeaks();
        return p;
    }


    // internal function for detecting peaks
    private Peak detect(double[] signal, String mode) {
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
        Peak pObj = new Peak(signal,
                UtilMethods.convertToPrimitiveInt(midpoints),
                UtilMethods.convertToPrimitiveInt(left_edge),
                UtilMethods.convertToPrimitiveInt(right_edge),
                mode);
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
    public Spike getSpikes(double[] signal, int[] peaks, int[] troughs) {
        int[] left_trough = new int[peaks.length];
        int[] right_trough = new int[peaks.length];

        for (int i=0; i<peaks.length; i++) {
            left_trough[i] = this.getClosest(troughs, peaks[i], "left");
            right_trough[i] = this.getClosest(troughs, peaks[i], "right");
        }
        Spike sObj = new Spike(signal, peaks, left_trough, right_trough);
        return sObj;
    }

    /**
     * This method identifies all the spikes in the signal.
     * Spikes properties are different from peaks such that, the spike height and width are dependent on their neighbouring troughs.
     * @return SpikeObject The list of all the troughs as PeakObject
     */
    public Spike getSpikes() {
        if ((this.peak_indices == null) || (this.trough_indices == null)) {
            this.detectPeaks();
            this.detectTroughs();
        }
        return this.getSpikes(this.signal, this.peak_indices, this.trough_indices);
    }

    private ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
        Set<Integer> set = new HashSet<Integer>(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}