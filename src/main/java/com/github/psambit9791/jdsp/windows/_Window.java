package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h1>_Window Class</h1>
 * This is an abstract class providing specific methods and abstract methods required to build any Window object.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
abstract class _Window {

    boolean extendVal;

    /**
     * Handles window length if it is less than or equal to 0
     * @param length Input length of the window
     * @return boolean False if length more than 0, otherwise True
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
     * @return int Returns the updated window length
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
     * @return double[] Returns the truncated window
     */
    public double[] truncate(double[] arr) {
        if (this.extendVal) {
            return UtilMethods.splitByIndex(arr, 0, arr.length-1);
        }
        else {
            return arr;
        }
    }

    /**
     * This method computes the window and returns it
     * @return double[] The generated window
     */
    public abstract double[] getWindow();
}
