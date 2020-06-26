package com.github.psambit9791.jdsp;

import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.stat.StatUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;


/**
 * <h1>Utility Methods</h1>
 * The UtilMethods class implements different utility functions to help with mathematical operations
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */

public class UtilMethods {

    /**
     * This function returns evenly spaced number over a specified interval such that there are specified number of samples.
     * This is equivalent of the numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.linspace.html">linspace()</a> function.
     * @param start Start value of the sequence
     * @param stop Stop value of the sequence
     * @param samples Number of samples to be generated
     * @param includeEnd Include stop value in the sequence
     * @return double[] Generated sequence
     */
    public static double[] linspace(int start, int stop, int samples, boolean includeEnd) {
        double[] time = new double[samples];
        double T;

        double stopVal = (double) stop;
        double i = start;

        if (includeEnd) {
            T = 1.0/(samples-1);
        }
        else {
            T = 1.0/samples;
            stopVal = stopVal - T;
        }

        int index = 0;
        time[index] = i;

        while (Math.abs(i-stopVal) > 0.00001) {
            i = i + T;
            index++;
            time[index] = i;
        }
        return time;
    }

    // Useful for repeated linspace() required in periodic signal generation
    /**
     * This function returns evenly spaced number over a specified interval such that there are specified number of samples
     * with 'n' iterations of the same sequence. Useful for repeated linspace() required in periodic signal generation.
     * @param start Start value of the sequence
     * @param stop Stop value of the sequence
     * @param samples Number of samples to be generated in each sequence
     * @param repeats Number of time the sequence is concatenated together
     * @return double[] Generated sequence
     */
    public static double[] linspace(int start, int stop, int samples, int repeats) {
        double[] time = new double[samples];
        double T = 1.0/(samples-1);

        double i = start;

        int index = 0;
        time[index] = i;

        while (Math.abs(i-stop) > 0.00001) {
            i = i + T;
            index++;
            time[index] = i;
        }

        double[] out = {};
        for (int j = 0; j<repeats; j++) {
            out = concatenateArray(out, time);
        }
        return out;
    }

    // Same as numpy arange() for double
    /**
     * This function returns evenly spaced number over a specified interval with a specific step in between each element.
     * This is equivalent of the numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.linspace.html">linspace()</a> function.
     * @param start Start value of the sequence
     * @param stop Stop value of the sequence
     * @param step Spacing between elements
     * @return double[] Generated sequence
     */
    public static double[] arange(double start, double stop, double step) {
        if (start > stop) {
            throw new IllegalArgumentException("start cannot be greater than stop");
        }
        int size = (int)((stop-start)/step);
        double[] arr = new double[size];

        double temp = start;
        for (int i=0; i<size; i++){
            arr[i] = temp;
            temp = temp + step;
        }
        return arr;
    }

    // Same as numpy arange() for integer
    /**
     * This function returns evenly spaced number over a specified interval with a specific step in between each element.
     * This is equivalent of the numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.linspace.html">linspace()</a> function.
     * @param start Start value of the sequence
     * @param stop Stop value of the sequence
     * @param step Spacing between elements
     * @return int[] Generated sequence
     */
    public static int[] arange(int start, int stop, int step) {
        if (start > stop) {
            throw new IllegalArgumentException("start cannot be greater than stop");
        }
        int size = (stop - start)/step;
        int[] arr = new int[size];

        int temp = start;
        for (int i=0; i<size; i++){
            arr[i] = temp;
            temp = temp + step;
        }
        return arr;
    }

    // Reverse Array
    /**
     * This function returns the input array after reversing the order of the elements in it.
     * @param arr Array to be reversed
     * @return double[] Reversed array
     */
    public static double[] reverse(double[] arr) {
        double[] inv = new double[arr.length];
        int index = 0;
        for (int i=(arr.length-1); i>=0; i--) {
            inv[index] = arr[i];
            index++;
        }
        return inv;
    }

    /**
     * This function returns the input array after reversing the order of the elements in it.
     * @param arr Array to be reversed
     * @return int[] Reversed array
     */
    public static int[] reverse(int[] arr) {
        int[] inv = new int[arr.length];
        int index = 0;
        for (int i=(arr.length-1); i>=0; i--) {
            inv[index] = arr[i];
            index++;
        }
        return inv;
    }

    // Concatenate 2 arrays
    /**
     * This function returns the concatenation of the 2 input arrays.
     * @param arr1 Array to be added first
     * @param arr2 Array to be added second
     * @return double[] Concatenated array
     */
    public static double[] concatenateArray(double[] arr1, double[] arr2) {
        double[] out = new double[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, out, 0, arr1.length);
        System.arraycopy(arr2, 0, out, arr1.length, arr2.length);
        return out;
    }

    /**
     * This function returns the concatenation of the 2 input arrays.
     * @param arr1 Array to be added first
     * @param arr2 Array to be added second
     * @return int[] Concatenated array
     */
    public static int[] concatenateArray(int[] arr1, int[] arr2) {
        int[] out = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, out, 0, arr1.length);
        System.arraycopy(arr2, 0, out, arr1.length, arr2.length);
        return out;
    }

    // Split an array by indices
    /**
     * This function returns the concatenation of the 2 input arrays.
     * @param arr Array to be manipulated
     * @param start Start index for split
     * @param end Stop index for split
     * @return double[] Sub-array generated by splitting input array by start and end indices
     */
    public static double[] splitByIndex(double[] arr, int start, int end) {
        double[] out = new double[end-start];
        System.arraycopy(arr, start, out, 0, out.length);
        return out;
    }

    /**
     * This function returns the concatenation of the 2 input arrays.
     * @param arr Array to be manipulated
     * @param start Start index for split
     * @param end Stop index for split
     * @return int[] Sub-array generated by splitting input array by start and end indices
     */
    public static int[] splitByIndex(int[] arr, int start, int end) {
        int[] out = new int[end-start];
        System.arraycopy(arr, start, out, 0, out.length);
        return out;
    }

    // Find pseudo-inverse of a 2D array
    /**
     * This function returns the pseudo-inverse of a 2D matrix.
     * @param mtrx 2D matrix
     * @return double[][] Pseudo-inverse of the input matrix
     */
    public static double[][] pseudoInverse(double[][] mtrx) {
        RealMatrix M = MatrixUtils.createRealMatrix(mtrx);
        DecompositionSolver solver = new SingularValueDecomposition(M).getSolver();
        return solver.getInverse().getData();
    }

    // Different methods of padding a signal
    /**
     * This function returns the input signal by padding it.
     * The output differs based on the mode of operation.
     * @param signal Signal to be padded
     * @param mode The mode in which padding will take place
     * Mode outputs for signal [a b c d]:
     * "reflect" : [d c b a | a b c d | d c b a]
     * "constant" : [0 0 0 0 | a b c d | 0 0 0 0]
     * "nearest" : [a a a a | a b c d | d d d d]
     * "mirror" : [c d c b | a b c d | c b a b]
     * "wrap" : [a b c d | a b c d | a b c d]
     * @return double[][] Pseudo-inverse of the input matrix
     */
    public static double[] padSignal(double[] signal, String mode) {
        double[] newSignal = {};
        if (mode.equals("reflect")) {
            double[] revSig = reverse(signal);
            double[] newSig = {};
            newSig = concatenateArray(newSig, revSig);
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, revSig);
            newSignal = newSig;
        }
        else if (mode.equals("constant")) {
            double[] cons = new double[signal.length];
            Arrays.fill(cons, 0);
            double[] newSig = {};
            newSig = concatenateArray(newSig, cons);
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, cons);
            newSignal = newSig;
        }
        else if (mode.equals("nearest")) {
            double[] left = new double[signal.length];
            Arrays.fill(left, signal[0]);
            double[] right = new double[signal.length];
            Arrays.fill(right, signal[signal.length-1]);

            double[] newSig = {};
            newSig = concatenateArray(newSig, left);
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, right);
            newSignal = newSig;
        }
        else if (mode.equals("mirror")) {
            double[] temp = splitByIndex(signal, 1, signal.length);
            temp = reverse(temp);
            double[] val = new double[]{temp[1]};
            double[] left = concatenateArray(val, temp);

            temp = splitByIndex(signal, 0, signal.length-1);
            temp = reverse(temp);
            val = new double[]{temp[temp.length - 2]};
            double[] right = concatenateArray(temp, val);

            double[] newSig = {};
            newSig = concatenateArray(newSig, left);
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, right);
            newSignal = newSig;
        }
        else if (mode.equals("wrap")) {
            double[] newSig = {};
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, signal);
            newSig = concatenateArray(newSig, signal);
            newSignal = newSig;
        }
        else {
            throw new IllegalArgumentException("padSignalforConvolution modes can only be reflect, constant, " +
                    "nearest, mirror, wrap or interp (default)");
        }
        return newSignal;
    }

    /**
     * Implementation of the numpy version of diff()
     * Calculate the first discrete difference in the array
     * @param arr The array to be processed
     * @return double[] The discrete difference array
     */
    public static double[] diff(double[] arr) {
        double[] sig = new double[arr.length-1];
        for (int i=0; i<sig.length; i++) {
            sig[i] = (arr[i+1] - arr[i]);
        }
        return sig;
    }

    /**
     * Implementation of the numpy version of unwrap()
     * Helps to unwrap a given array by changing deltas to values of 2*pi complement
     * It unwraps radian phase p by changing absolute jumps greater than discont to their 2*pi complement along the given axis
     * @param arr The array to be unwrapped
     * @return double[] The unwrapped array
     */
    public static double[] unwrap(double[] arr) {
        double[] diff = UtilMethods.diff(arr);
        double[] out = new double[arr.length];
        for (int i=0; i<diff.length; i++) {
            diff[i] = UtilMethods.modulo(diff[i]+Math.PI, 2*Math.PI) - Math.PI;
        }
        out[0] = arr[0];
        for (int i=0; i<diff.length; i++){
            out[i+1] = out[i] + diff[i];
        }
        return out;
    }

    /**
     * Java provides a different implementation of modulo than Python.
     * In Java, the result has the sign of the dividend, but in Python, the sign is from the divisor.
     * This method implements the Python version of the modulo operation
     * @param dividend The numerator
     * @param divisor The denominator
     * @return double Result of the modulo operation
     */
    public static double modulo(double dividend, double divisor) {
        return (((dividend % divisor) + divisor) % divisor);
    }

    /**
     * Evenly rounds the number to the given number of decimals
     * @param value Value to be rounded
     * @param decimals Number of decimal places to round to
     * @return double Result of the rounding operation
     */
    public static double round(double value, int decimals) {
        String dPlaces = "#.";
        for (int i=0; i<decimals; i++) {
            dPlaces = dPlaces + "#";
        }

        DecimalFormat df = new DecimalFormat(dPlaces);
        df.setRoundingMode(RoundingMode.CEILING);
        Double d = value;
        String temp = df.format(d);
        return Double.parseDouble(temp);
    }

    /**
     * Scales the input array between the new limits provided in the arguments
     * @param arr Array to be modified
     * @param min_value Minimum value for the rescaling range
     * @param max_value Maximum value for the rescaling range
     * @return double[] Rescaled array
     */
    public static double[] rescale(double[] arr, int min_value, int max_value) {
        double[] newArr = new double[arr.length];
        double minArr = StatUtils.min(arr);
        double maxArr = StatUtils.max(arr);
        for (int i=0; i<arr.length; i++) {
            newArr[i] = ((arr[i] - minArr)/(maxArr - minArr))*(max_value - min_value) + min_value;
        }
        return newArr;
    }

    /**
     * Standardizes the input array between the range 0 and 1
     * @param arr Array to be modified
     * @return double[] Standardized array
     */
    public static double[] standardize(double[] arr) {
        double[] newArr = new double[arr.length];
        double minArr = StatUtils.min(arr);
        double maxArr = StatUtils.max(arr);
        for (int i=0; i<arr.length; i++) {
            newArr[i] = ((arr[i] - minArr)/(maxArr - minArr));
        }
        return newArr;
    }

    /**
     * Normalizes the input array with the mean and standard deviation
     * @param arr Array to be modified
     * @return double[] Standardized array
     */
    public static double[] normalize(double[] arr) {
        return StatUtils.normalize(arr);
    }

    /**
     * Zero-Centres the input array
     * @param arr Array to be modified
     * @return double[] Standardized array
     */
    public static double[] zeroCenter(double[] arr) {
        double[] newArr = new double[arr.length];
        double mean = StatUtils.mean(arr);
        for (int i=0; i<arr.length; i++) {
            newArr[i] = arr[i] - mean;
        }
        return newArr;
    }
}
