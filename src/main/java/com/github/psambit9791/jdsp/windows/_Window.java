package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

abstract class _Window {

    boolean extendVal;

    /**
     * Handles window length if it is less than or equal to 0
     * @param length Input length of the window
     */
    public boolean lenGuard(int length) {
        if (length > 0) {
            return false;
        }
        return true;
    }

    /**
     * Increases window length by if required
     * @param length Length of the window
     * @param sym Determines if window is symmetric
     */
    public int extend(int length, boolean sym) {
        if (!sym) {
            this.extendVal = true;
            return length+1;
        }
        else {
            this.extendVal = false;
            return length;
        }
    }

    /**
     * Removes the last element if required
     * @param arr The array to be truncated
     */
    public double[] truncate(double[] arr) {
        if (this.extendVal) {
            return UtilMethods.splitByIndex(arr, 0, arr.length-1);
        }
        else {
            return arr;
        }
    }

    public abstract double[] getWindow();
}
