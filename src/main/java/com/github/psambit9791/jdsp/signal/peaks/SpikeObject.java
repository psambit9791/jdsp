package com.github.psambit9791.jdsp.signal.peaks;

import java.util.*;

/**
 * <h1>SpikeObject</h1>
 * Calculates Spike Properties and allows filtering based on the properties.
 * This is used by the FindPeak class in the get_spike() method.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class SpikeObject {
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
