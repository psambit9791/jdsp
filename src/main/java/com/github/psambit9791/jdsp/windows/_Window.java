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
public abstract class _Window {
    private boolean extendVal;

    /**
     * Constructor for window with window length 'len'
     * @param len length of the window (number of samples)
     */
    public _Window(int len) {
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Handles window length if it is less than or equal to 0
     * @param length Input length of the window
     * @return boolean False if length more than 0, otherwise True
     */
    public boolean lenGuard(int length) {
        return length <= 0;
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

    /**
     * Apply the window to the input data and return the output.
     * @param input input data
     * @throws NullPointerException if window not initialized
     * @throws IllegalArgumentException if window and input dimensions don't match
     * @return double[] windowed input data
     */
    public double[] applyWindow(double[] input) {
        double[] window = getWindow();
        if (window == null) {
            throw new NullPointerException("Window not initialized");
        }
        if (input.length != window.length) {
            throw new IllegalArgumentException("Input data dimensions and window dimensions don't match");
        }

        double[] out = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            out[i] = input[i]*window[i];
        }
        return out;
    }

    /**
     * Apply the inverse of the window ("undo" the windowing on a signal) to the input data and return the output.
     * @param input input data
     * @throws NullPointerException if window not initialized
     * @throws IllegalArgumentException if window and input dimensions don't match
     * @return double[] windowed input data
     */
    public double[] applyInverseWindow(double[] input) {
        double[] window = getWindow();
        double[] out = new double[input.length];
        // Flag that checks whether the window contained a zero-value. If true, a warning message will be print about
        // irretrievable loss of the signal.
        boolean dataLost = false;

        if (window == null) {
            throw new NullPointerException("Window not initialized");
        }
        if (input.length != window.length) {
            throw new IllegalArgumentException("Input data dimensions and window dimensions don't match");
        }

        for (int i = 0; i < input.length; i++) {
            if (window[i] == 0) {
                dataLost = true;
                continue;
            }
            out[i] = input[i]/window[i];
        }
        if (dataLost) {
            System.err.println("The original window function contained a zero-element, which causes some of the data to be irretrievably lost.");
        }
        return out;
    }
}
