/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.misc;

import com.github.psambit9791.jdsp.windows.*;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;


/**
 * <h1>Utility Methods</h1>
 * The UtilMethods class implements different utility functions to help with mathematical operations
 * <p>
 *
 * @author  Sambit Paul
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

        double span = stop - start;

        double stopVal = stop;
        double i = start;

        if (includeEnd) {
            T = span/(samples-1);
        }
        else {
            T = span/samples;
            stopVal = stopVal - T;
        }

        int index = 0;
        time[index] = i;

        for (index=1; index<time.length; index++) {
            i = i + T;
            time[index] = i;
        }
        if (includeEnd) {
            time[time.length-1] = stop;
        }
        return time;
    }

    /**
     * This function returns evenly spaced number over a specified interval such that there are specified number of samples.
     * This is equivalent of the numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.linspace.html">linspace()</a> function.
     * @param start Start value of the sequence
     * @param stop Stop value of the sequence
     * @param samples Number of samples to be generated
     * @param includeEnd Include stop value in the sequence
     * @return double[] Generated sequence
     */
    public static double[] linspace(double start, double stop, int samples, boolean includeEnd) {
        double[] time = new double[samples];
        double T;

        double span = stop - start;

        double stopVal = stop;
        double i = start;

        if (includeEnd) {
            T = span/(samples-1);
        }
        else {
            T = span/samples;
            stopVal = stopVal - T;
        }

        int index = 0;
        time[index] = i;

        for (index=1; index<time.length; index++) {
            i = i + T;
            time[index] = i;
        }
        if (includeEnd) {
            time[time.length-1] = stop;
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
     * @throws java.lang.IllegalArgumentException If start value is greater than stop value
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
     * @throws java.lang.IllegalArgumentException If start value is greater than stop value
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
        for (int i=0; i<inv.length; i++) {
            inv[i] = arr[arr.length-1-i];
        }
        return inv;
    }

    /**
     * This function returns the input array after reversing the order of the elements in it.
     * @param arr Array to be reversed
     * @return Complex[] Reversed array
     */
    public static Complex[] reverse(Complex[] arr) {
        Complex[] inv = new Complex[arr.length];
        for (int i=0; i<inv.length; i++) {
            inv[i] = arr[arr.length-1-i];
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
        for (int i=0; i<inv.length; i++) {
            inv[i] = arr[arr.length-1-i];
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

    /**
     * This function returns the reversal of the input matrix.
     * @param mat Array to be added first
     * @return int[][] Reversed matrix
     */
    public static int[][] reverseMatrix(int[][] mat) {
        int[][] rev = new int[mat.length][mat[0].length];
        for (int i=0; i<mat[0].length; i++){
            rev[i] = UtilMethods.reverse(mat[i]);
        }
        return rev;
    }

    /**
     * This function returns the reversal of the input matrix.
     * @param mat Array to be added first
     * @return double[][] Reversed matrix
     */
    public static double[][] reverseMatrix(double[][] mat) {
        double[][] rev = new double[mat.length][mat[0].length];
        for (int i=0; i<mat.length; i++){
            rev[i] = UtilMethods.reverse(mat[i]);
        }
        return rev;
    }

    // Split an array by indices
    /**
     * This function returns the subset if an array depending on start and stop indices provided.
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
     * This function returns the subset if an array depending on start and stop indices provided.
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

    /**
     * This function returns the subset if an array depending on start and stop indices provided.
     * @param arr Array to be manipulated
     * @param start Start index for split
     * @param end Stop index for split
     * @return int[] Sub-array generated by splitting input array by start and end indices
     */
    public static Complex[] splitByIndex(Complex[] arr, int start, int end) {
        Complex[] out = new Complex[end-start];
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
     * @throws java.lang.IllegalArgumentException If string mode is not "reflect", "constant", "nearest", "mirror" or "wrap"
     * Mode outputs for signal [a b c d]:
     * "reflect" : [d c b a | a b c d | d c b a]
     * "constant" : [0 0 0 0 | a b c d | 0 0 0 0]
     * "nearest" : [a a a a | a b c d | d d d d]
     * "mirror" : [c d c b | a b c d | c b a b]
     * "wrap" : [a b c d | a b c d | a b c d]
     * @return double[][] Pseudo-inverse of the input matrix
     */
    public static double[] padSignal(double[] signal, String mode) {
        double[] newSignal;
        switch (mode) {
            case "reflect": {
                double[] revSig = reverse(signal);
                double[] newSig = {};
                newSig = concatenateArray(newSig, revSig);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, revSig);
                newSignal = newSig;
                break;
            }
            case "constant": {
                double[] cons = new double[signal.length];
                Arrays.fill(cons, 0);
                double[] newSig = {};
                newSig = concatenateArray(newSig, cons);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, cons);
                newSignal = newSig;
                break;
            }
            case "nearest": {
                double[] left = new double[signal.length];
                Arrays.fill(left, signal[0]);
                double[] right = new double[signal.length];
                Arrays.fill(right, signal[signal.length - 1]);

                double[] newSig = {};
                newSig = concatenateArray(newSig, left);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, right);
                newSignal = newSig;
                break;
            }
            case "mirror": {
                double[] temp = splitByIndex(signal, 1, signal.length);
                temp = reverse(temp);
                double[] val = new double[]{temp[1]};
                double[] left = concatenateArray(val, temp);

                temp = splitByIndex(signal, 0, signal.length - 1);
                temp = reverse(temp);
                val = new double[]{temp[temp.length - 2]};
                double[] right = concatenateArray(temp, val);

                double[] newSig = {};
                newSig = concatenateArray(newSig, left);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, right);
                newSignal = newSig;
                break;
            }
            case "wrap": {
                double[] newSig = {};
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, signal);
                newSignal = newSig;
                break;
            }
            default:
                throw new IllegalArgumentException("padSignalforConvolution modes can only be reflect, constant, " +
                        "nearest, mirror, or wrap");
        }
        return newSignal;
    }


    // Different methods of padding a signal
    /**
     * This function returns the input signal by padding it.
     * The output differs based on the mode of operation.
     * @param signal Signal to be padded
     * @param mode The mode in which padding will take place
     * @param length The number of elements by which the signal is extended on either side
     * @throws java.lang.IllegalArgumentException If string mode is not "reflect", "constant", "nearest", "mirror" or "wrap"
     * Mode outputs for signal [a b c d]:
     * "reflect" : [d c b a | a b c d | d c b a]
     * "constant" : [0 0 0 0 | a b c d | 0 0 0 0]
     * "nearest" : [a a a a | a b c d | d d d d]
     * "mirror" : [c d c b | a b c d | c b a b]
     * "wrap" : [a b c d | a b c d | a b c d]
     * @return double[][] Pseudo-inverse of the input matrix
     */
    public static double[] padSignal(double[] signal, String mode, int length) {
        double[] newSignal;
        switch (mode) {
            case "reflect": {
                double[] revSig = reverse(signal);
                double[] newSig = {};
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(revSig, signal.length-length, signal.length));
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(revSig, 0, length));
                newSignal = newSig;
                break;
            }
            case "constant": {
                double[] cons = new double[length];
                Arrays.fill(cons, 0);
                double[] newSig = {};
                newSig = concatenateArray(newSig, cons);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, cons);
                newSignal = newSig;
                break;
            }
            case "nearest": {
                double[] left = new double[length];
                Arrays.fill(left, signal[0]);
                double[] right = new double[length];
                Arrays.fill(right, signal[signal.length - 1]);

                double[] newSig = {};
                newSig = concatenateArray(newSig, left);
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, right);
                newSignal = newSig;
                break;
            }
            case "mirror": {
                double[] temp = splitByIndex(signal, 1, signal.length);
                temp = reverse(temp);
                double[] val = new double[]{temp[1]};
                double[] left = concatenateArray(val, temp);

                temp = splitByIndex(signal, 0, signal.length - 1);
                temp = reverse(temp);
                val = new double[]{temp[temp.length - 2]};
                double[] right = concatenateArray(temp, val);

                double[] newSig = {};
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(left, signal.length-length, signal.length));
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(right, 0, length));
                newSignal = newSig;
                break;
            }
            case "wrap": {
                double[] newSig = {};
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(signal, signal.length-length, signal.length));
                newSig = concatenateArray(newSig, signal);
                newSig = concatenateArray(newSig, UtilMethods.splitByIndex(signal, 0, length));
                newSignal = newSig;
                break;
            }
            default:
                throw new IllegalArgumentException("padSignalforConvolution modes can only be reflect, constant, " +
                        "nearest, mirror, or wrap");
        }
        return newSignal;
    }

    /**
     * Returns a zero padded version of the signal by adding 'padSize' number of zeroes at the end of the signal.
     * E.g. signal [a b c d] with padSize 3 becomes: [a b c d 0 0 0]
     * @param signal signal to be padded
     * @param padSize number of zeroes to add at the end of the signal
     * @return double[] the zero padded signal
     */
    public static double[] zeroPadSignal(double[] signal, int padSize) {
        if (padSize < 0) {
            throw new IllegalArgumentException("Pad size must be a positive integer");
        }
        double[] zeroes = new double[padSize];
        Arrays.fill(zeroes, 0);
        return concatenateArray(signal, zeroes);
    }

    /**
     * Truncate the signal so that it has a final length of 'signalSize'
     * @param signal signal to be truncated
     * @param signalSize final size of the signal after truncating
     * @return double[] truncated signal
     */
    public static double[] truncateSignal(double[] signal, int signalSize) {
        if (signalSize < 0) {
            throw new IllegalArgumentException("Truncation size should be a positive integer");
        }
        if (signalSize > signal.length) {
            throw new IllegalArgumentException("Truncation size should be smaller than signal size");
        }
        return Arrays.copyOfRange(signal, 0, signalSize);
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
     * Implementation of the numpy version of diff()
     * Calculate the first discrete difference in the array
     * @param arr The array to be processed
     * @return int[] The discrete difference array
     */
    public static int[] diff(int[] arr) {
        int[] sig = new int[arr.length-1];
        for (int i=0; i<sig.length; i++) {
            sig[i] = (arr[i+1] - arr[i]);
        }
        return sig;
    }

    /**
     * Implementation of the numpy version of unwrap()
     * Helps to unwrap a given array by changing deltas to values of 2*pi complement
     * It unwraps radian phase p by changing absolute jumps greater than discontinunity to their 2*pi complement
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
     * Java provides a different implementation of modulo than Python.
     * In Java, the result has the sign of the dividend, but in Python, the sign is from the divisor.
     * This method implements the Python version of the modulo operation
     * @param dividend The numerator
     * @param divisor The denominator
     * @return int Result of the modulo operation
     */
    public static int modulo(int dividend, int divisor) {
        return (((dividend % divisor) + divisor) % divisor);
    }

    /**
     * Evenly rounds the number to the given number of decimals
     * @param value Value to be rounded
     * @param decimals Number of decimal places to round to
     * @return double Result of the rounding operation
     */
    public static double round(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }

    /**
     * Evenly rounds the the elements of a double array to the given number of decimals
     * @param arr Value to be rounded
     * @param decimals Number of decimal places to round to
     * @return double The rounded double array
     */
    public static double[] round(double[] arr, int decimals) {
        return Arrays.stream(arr).map(c -> UtilMethods.round(c, decimals)).toArray();
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

    /**
     * Returns the index of the largest element in the input array
     * @param num1 First number for comparison
     * @param num2 Second number for comparison
     * @param epsilon The tolerance against which the check is performed
     * @return boolean Whether they are almost equal or not
     */
    public static boolean almostEquals(double num1, double num2, double epsilon) {
        return Math.abs(num1 - num2) <= epsilon;
    }

    /**
     * Returns the linear interpolation function for a set of X and Y values
     * Reference <a href="https://stackoverflow.com/a/36523685/7153489">Stackoverflow answer</a> helped implement this function.
     * @param x The abscissa (horizontal axis)
     * @param y The ordinate (vertical axis)
     * @return PolynomialSplineFunction The interpolation function
     */
    private static PolynomialSplineFunction linearInterp(double[] x, double[] y) {
        LinearInterpolator li = new LinearInterpolator();
        return li.interpolate(x, y);
    }

    /**
     * Converts an Integer ArrayList to int[] array
     * @param l The Integer ArrayList
     * @return int[] The primitive array
     */
    public static int[] convertToPrimitiveInt(ArrayList<Integer> l) {
        int[] ret = new int[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    public static int[] convertToPrimitiveInt(LinkedList<Integer> l) {
        int[] ret = new int[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    /**
     * Converts an Double ArrayList to double[] array
     * @param l The Double ArrayList
     * @return double[] The primitive array
     */
    public static double[] convertToPrimitiveDouble(ArrayList<Double> l) {
        double[] ret = new double[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    /**
     * Returns the index of the smallest element in the input array
     * @param arr Input array
     * @param reverse If true, checking is done from end to start. Otherwise checked from start to end.
     * @return int The index
     */
    public static int argmin(double[] arr, boolean reverse) {
        double min = StatUtils.min(arr);
        if (reverse) {
            arr = UtilMethods.reverse(arr);
        }
        int index = -1;
        for (int i=0; i<arr.length; i++) {
            if (min == arr[i]) {
                index = i;
                break;
            }
        }
        if (reverse) {
            index = (arr.length - 1) - index;
        }
        return index;
    }

    /**
     * Returns the index of the largest element in the input array
     * @param arr Input array
     * @param reverse If true, checking is done from end to start. Otherwise checked from start to end.
     * @return int The index
     */
    public static int argmax(double[] arr, boolean reverse) {
        double max = StatUtils.max(arr);
        if (reverse) {
            arr = UtilMethods.reverse(arr);
        }
        int index = -1;
        for (int i=0; i<arr.length; i++) {
            if (max == arr[i]) {
                index = i;
                break;
            }
        }
        if (reverse) {
            index = (arr.length - 1) - index;
        }
        return index;
    }

    /**
     * Returns the indices that would sort an array.
     * @param arr The array which is to be sorted
     * @param ascending Whether sorting is to be in ascending or descending order
     * @return int[] Array of indices which sorts the array
     */
    public static int[] argsort(final double[] arr, final boolean ascending) {
        Integer[] indexes = new Integer[arr.length];
        int[] argsArray = new int[arr.length];
        for (int i=0; i<indexes.length; i++) {
            indexes[i] = i;
        }
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer i1, final Integer i2) {
                return (ascending ? 1 : -1) * Double.compare(arr[i1], arr[i2]);
            }
        });
        for (int i=0; i<indexes.length; i++) {
            argsArray[i] = indexes[i];
        }
        return argsArray;
    }

    /**
     * Returns an electrocardiogram as an example for a 1-D signal.
     * @throws java.io.IOException To handle missing Input Output Streams
     * @return double[] The ECG data
     */
    public static double[] electrocardiogram() throws IOException {
        double[] data = new double[108000];
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("ecg.dat");
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String[] line = br.readLine().trim().split(" ");
        for (int i=0; i<line.length; i++) {
            data[i] = Double.parseDouble(line[i]);
        }
        return data;
    }

    /**
     * Returns the absolute value of the matrix.
     * @param m The matrix to be processed
     * @return double[][] The absolute value of the matrix
     */
    public static double[][] absoluteArray(double[][] m) {
        double[][] out = new double [m.length][m[0].length];
        for (int i=0; i<m.length; i++) {
            for (int j=0; j<m[0].length; j++) {
                out[i][j] = Math.abs(m[i][j]);
            }
        }
        return out;
    }

    /**
     * Returns the absolute value of the matrix.
     * @param m The array to be processed
     * @return double[] The absolute value of the array
     */
    public static double[] absoluteArray(double[] m) {
        double[] out = new double [m.length];
        for (int i=0; i<m.length; i++) {
            out[i] = Math.abs(m[i]);
        }
        return out;
    }

    /**
     * Returns the absolute value of the matrix.
     * @param m The array to be processed
     * @return int[] The absolute value of the array
     */
    public static int[] flattenMatrix(int[][] m) {
        int cols = m[0].length;
        int[] out = new int [m.length*cols];
        for (int i=0; i<out.length; i++) {
            out[i] = m[i/cols][i%cols];
        }
        return out;
    }

    /**
     * Returns the absolute value of the matrix.
     * @param m The array to be processed
     * @return double[] The absolute value of the array
     */
    public static double[] flattenMatrix(double[][] m) {
        int cols = m[0].length;
        double[] out = new double [m.length*cols];
        for (int i=0; i<out.length; i++) {
            out[i] = m[i/cols][i%cols];
        }
        return out;
    }

    /**
     * Return the row-array of matrix 'm', at row index 'rowIdx'
     * @param m 2D array (matrix) from which to get the row
     * @param rowIdx row index which you want to extract
     * @return double[] row array located at the row index
     */
    public static double[] getRow(double[][] m, int rowIdx) {
        if (rowIdx < 0) {
            System.err.println("Row index can not be negative");
            return null;
        }
        if (rowIdx >= m.length) {
            System.err.println("Row index is greater than matrix row dimension");
            return null;
        }
        return m[rowIdx];
    }

    /**
     * Return the column-array of matrix 'm', at column index 'colIdx'
     * @param m 2D array (matrix) from which to get the column
     * @param colIdx column index which you want to extract
     * @return double[] column array located at the column index
     */
    public static double[] getColumn(double[][] m, int colIdx) {
        if (colIdx < 0) {
            System.err.println("Column index can not be negative");
            return null;
        }
        if (colIdx >= m[0].length) {
            System.err.println("Column index is greater than matrix column dimension");
            return null;
        }
        double[] column = new double[m.length];
        for (int i = 0; i < column.length; i++){
            column[i] = m[i][colIdx];
        }
        return column;
    }

    /**
     * Returns the transpose of the matrix.
     * @param m The matrix to be transposed
     * @return double[][] The transpose of the matrix
     */
    public static double[][] transpose(double[][] m) {
        RealMatrix m1 = MatrixUtils.createRealMatrix(m);
        return m1.transpose().getData();
    }

    /**
     * Returns the transpose of the matrix.
     * @param m The matrix to be transposed
     * @return double[][] The transpose of the matrix
     */
    public static double[][] transpose(double[] m) {
        double[][] m1 = new double[m.length][1];
        for (int i=0; i<m.length; i++) {
            m1[i][0] = m[i];
        }
        return m1;
    }

    /**
     * Returns the multiplication of 2 matrices
     * @param a Multiplier Matrix
     * @param b Multiplicand Matrix
     * @throws java.lang.ArithmeticException if column size of a not equal to row size of b
     * @return double[][] The result of the matrix multiplication
     */
    public static double[][] matrixMultiply(double[][] a, double[][] b) throws ArithmeticException {
        if (a[0].length != b.length) {
            throw new ArithmeticException("Columns in multiplier must be equal to Rows in Multiplicand");
        }
        RealMatrix m1 = MatrixUtils.createRealMatrix(a);
        RealMatrix m2= MatrixUtils.createRealMatrix(b);
        RealMatrix m = m1.multiply(m2);
        return m.getData();
    }

    /**
     * Returns the multiplication of 2 matrices
     * @param a Addend Matrix 1
     * @param b Addend Matrix 2
     * @throws java.lang.ArithmeticException if column size of a not equal to row size of b
     * @return double[][] The result of the matrix multiplication
     */
    public static double[][] matrixAddition(double[][] a, double[][] b) throws ArithmeticException {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new ArithmeticException("Size of both matrices should be same");
        }
        RealMatrix m1 = MatrixUtils.createRealMatrix(a);
        RealMatrix m2= MatrixUtils.createRealMatrix(b);
        RealMatrix m = m1.add(m2);
        return m.getData();
    }

    /**
     * Returns the subtraction of 2 matrices
     * @param a Minuend Matrix
     * @param b Subtrahend Matrix
     * @throws java.lang.ArithmeticException if column size of a not equal to row size of b
     * @return double[][] The result of the matrix subtraction
     */
    public static double[][] matrixSubtraction(double[][] a, double[][] b) throws ArithmeticException {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new ArithmeticException("Size of both matrices should be same");
        }
        RealMatrix m1 = MatrixUtils.createRealMatrix(a);
        RealMatrix m2= MatrixUtils.createRealMatrix(b);
        RealMatrix m = m1.subtract(m2);
        return m.getData();
    }

    /**
     * Performs scalar arithmetic operation with a specific value on the input array
     * @param arr Array to be processed
     * @param val Value with which arithmetic operation is performed
     * @param action What action needs to be performed. Can be addition, subtraction (2 modes), multiplication, division and raising to power.
     * @throws java.lang.IllegalArgumentException if action is not "add", "sub", "reverse_sub", "mul", "div", "pow"
     * @return double[] The result of the operation
     */
    public static double[] scalarArithmetic(double[] arr, double val, String action) throws IllegalArgumentException {
        if (!action.equals("add") && !action.equals("sub") && !action.equals("mul") && !action.equals("reverse_sub") &&
                !action.equals("div") && !action.equals("pow")) {
            throw new ArithmeticException("action must be 'add', 'sub', 'reverse_sub', 'mul', 'div' or 'pow'");
        }
        double[] out = new double[arr.length];
        switch (action) {
            case "add":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = arr[i] + val;
                }
                break;
            case "sub":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = arr[i] - val;
                }
                break;
            case "reverse_sub":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = val - arr[i];
                }
                break;
            case "mul":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = arr[i] * val;
                }
                break;
            case "div":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = arr[i] / val;
                }
                break;
            default:
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.pow(arr[i], val);
                }
                break;
        }
        return out;
    }

    /**
     * Performs scalar arithmetic operation with a specific value on the input array
     * @param arr Array to be processed
     * @param function What action needs to be performed. Can be addition, subtraction (2 modes), multiplication, division and raising to power.
     * @throws java.lang.IllegalArgumentException if action is not "add", "sub", "reverse_sub", "mul", "div", "pow"
     * @return double[][] The result of the operation
     */
    public static double[] trigonometricArithmetic(double[] arr, String function) throws IllegalArgumentException {
        if (!function.equals("sin") && !function.equals("cos") && !function.equals("tan") && !function.equals("asin") &&
                !function.equals("acos") && !function.equals("atan")) {
            throw new ArithmeticException("action must be 'sin', 'cos', 'tan', 'asin', 'acos' or 'atan'");
        }
        double[] out = new double[arr.length];
        switch (function) {
            case "sin":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.sin(arr[i]);
                }
                break;
            case "cos":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.cos(arr[i]);
                }
                break;
            case "tan":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.tan(arr[i]);
                }
                break;
            case "asin":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.asin(arr[i]);
                }
                break;
            case "acos":
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.acos(arr[i]);
                }
                break;
            default:
                for (int i = 0; i < arr.length; i++) {
                    out[i] = Math.atan(arr[i]);
                }
                break;
        }
        return out;
    }


    /**
     * Checks if input array is sorted in given order
     * @param arr Array to be checked
     * @param descending If array to be checked for descending or ascending order
     * @return boolean The evaluation output
     */
    public static boolean isSorted(double[] arr, boolean descending) {
        if (descending) {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] < arr[i + 1]) {
                    return false;
                }
            }
        }
        else {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Performs linear interpolation to compute value of a point given a list of x and y coordinates
     * @param point Point at which the interpolation value is needed
     * @param x X-coordinates of the data points (must be increasing)
     * @param y Y-coordinates of the data points
     * @return double The evaluation output
     */
    public static double interpolate(double point, double[] x, double[] y) {
        if (!isSorted(x, false)) {
            throw new IllegalArgumentException("X-coordinates must be increasing");
        }
        LinearInterpolator li = new LinearInterpolator();
        PolynomialSplineFunction psf = li.interpolate(x, y);
        return psf.value(point);
    }

    /**
     * Performs linear interpolation to compute values of a list of points given a list of x and y coordinates
     * @param points Points at which the interpolation values are needed
     * @param x X-coordinates of the data points (must be increasing)
     * @param y Y-coordinates of the data points
     * @return double The evaluation output
     */
    public static double[] interpolate(double[] points, double[] x, double[] y) {
        if (!isSorted(x, false)) {
            throw new IllegalArgumentException("X-coordinates must be increasing");
        }
        LinearInterpolator li = new LinearInterpolator();
        PolynomialSplineFunction psf = li.interpolate(x, y);
        double[] out = new double[points.length];
        for (int i=0; i<points.length; i++) {
            out[i] = psf.value(points[i]);
        }
        return out;
    }

    /**
     * Chebyshev Series Evaluation
     * @param x Points at which the Chebyshev series needs to be evaluated
     * @param arr The Chebyshev series coefficients
     * @return double[] The evaluation output
     */
    public static double[] chebyEval(double[] x, double[] arr) {
        double[] b0 = {arr[0]};
        double[] b1 = {0.0};
        double[] b2 = {0.0};

        for (int i=1; i<arr.length; i++) {
            b2 = b1;
            b1 = b0;
            if (b0.length == 1) {
                b0 = UtilMethods.scalarArithmetic(x, b1[0], "mul");
                b0 = UtilMethods.scalarArithmetic(b0, (b2[0] - arr[i]), "sub");
            }
            else if (b1.length > 1 && b2.length == 1) {
                b0 = MathArrays.ebeMultiply(x, b1);
                b0 = UtilMethods.scalarArithmetic(b0, (b2[0] - arr[i]), "sub");
            }
            else {
                b0 = MathArrays.ebeMultiply(x, b1);
                b0 = MathArrays.ebeSubtract(b0, b2);
                b0 = UtilMethods.scalarArithmetic(b0, arr[i], "add");
            }
        }

        return MathArrays.scale(0.5, MathArrays.ebeSubtract(b0, b2));
    }

    /**
     * Chebyshev Series Evaluation
     * @param x Single point at which the Chebyshev series needs to be evaluated
     * @param arr The Chebyshev series coefficients
     * @return double The evaluation output
     */
    public static double chebyEval(double x, double[] arr) {
        double b0 = arr[0];
        double b1 = 0.0;
        double b2 = 0.0;

        for (int i=1; i<arr.length; i++) {
            b2 = b1;
            b1 = b0;
            b0 = x*b1 - b2 + arr[i];
        }

        return 0.5 * (b0 - b2);
    }

    /**
     * Compute the modified Bessel function (0 order) of the first kind
     * @param x Array of all the points to compute for
     * @return double Array of values of the modified Bessel function
     */
    public static double[] i0(double[] x) {
        double[] A = {-4.41534164647933937950E-18, 3.33079451882223809783E-17, -2.43127984654795469359E-16,
                1.71539128555513303061E-15, -1.16853328779934516808E-14, 7.67618549860493561688E-14,
                -4.85644678311192946090E-13, 2.95505266312963983461E-12, -1.72682629144155570723E-11,
                9.67580903537323691224E-11, -5.18979560163526290666E-10, 2.65982372468238665035E-9,
                -1.30002500998624804212E-8, 6.04699502254191894932E-8, -2.67079385394061173391E-7,
                1.11738753912010371815E-6, -4.41673835845875056359E-6, 1.64484480707288970893E-5,
                -5.75419501008210370398E-5, 1.88502885095841655729E-4, -5.76375574538582365885E-4,
                1.63947561694133579842E-3, -4.32430999505057594430E-3, 1.05464603945949983183E-2,
                -2.37374148058994688156E-2, 4.93052842396707084878E-2, -9.49010970480476444210E-2,
                1.71620901522208775349E-1, -3.04682672343198398683E-1, 6.76795274409476084995E-1 };
        double[] B = {-7.23318048787475395456E-18, -4.83050448594418207126E-18, 4.46562142029675999901E-17,
                3.46122286769746109310E-17, -2.82762398051658348494E-16, -3.42548561967721913462E-16,
                1.77256013305652638360E-15, 3.81168066935262242075E-15, -9.55484669882830764870E-15,
                -4.15056934728722208663E-14, 1.54008621752140982691E-14, 3.85277838274214270114E-13,
                7.18012445138366623367E-13, -1.79417853150680611778E-12, -1.32158118404477131188E-11,
                -3.14991652796324136454E-11, 1.18891471078464383424E-11, 4.94060238822496958910E-10,
                3.39623202570838634515E-9, 2.26666899049817806459E-8, 2.04891858946906374183E-7,
                2.89137052083475648297E-6, 6.88975834691682398426E-5, 3.36911647825569408990E-3,
                8.04490411014108831608E-1 };

        double[] out = new double[x.length];
        x = UtilMethods.absoluteArray(x);

        for (int i=0; i<x.length; i++) {
            if (x[i] <= 8) {
                out[i] = Math.exp(x[i]) * UtilMethods.chebyEval(x[i]/2.0 - 2.0, A);
            }
            else {
                out[i] = Math.exp(x[i]) * UtilMethods.chebyEval(32.0/x[i] - 2.0, B) / Math.sqrt(x[i]);
            }
        }

        return out;
    }

    /**
     * Compute the modified Bessel function (0 order) of the first kind
     * @param x Array of all the points to compute for
     * @return double Value of the modified Bessel function
     */
    public static double i0(double x) {
        double[] A = {-4.41534164647933937950E-18, 3.33079451882223809783E-17, -2.43127984654795469359E-16,
                1.71539128555513303061E-15, -1.16853328779934516808E-14, 7.67618549860493561688E-14,
                -4.85644678311192946090E-13, 2.95505266312963983461E-12, -1.72682629144155570723E-11,
                9.67580903537323691224E-11, -5.18979560163526290666E-10, 2.65982372468238665035E-9,
                -1.30002500998624804212E-8, 6.04699502254191894932E-8, -2.67079385394061173391E-7,
                1.11738753912010371815E-6, -4.41673835845875056359E-6, 1.64484480707288970893E-5,
                -5.75419501008210370398E-5, 1.88502885095841655729E-4, -5.76375574538582365885E-4,
                1.63947561694133579842E-3, -4.32430999505057594430E-3, 1.05464603945949983183E-2,
                -2.37374148058994688156E-2, 4.93052842396707084878E-2, -9.49010970480476444210E-2,
                1.71620901522208775349E-1, -3.04682672343198398683E-1, 6.76795274409476084995E-1 };
        double[] B = {-7.23318048787475395456E-18, -4.83050448594418207126E-18, 4.46562142029675999901E-17,
                3.46122286769746109310E-17, -2.82762398051658348494E-16, -3.42548561967721913462E-16,
                1.77256013305652638360E-15, 3.81168066935262242075E-15, -9.55484669882830764870E-15,
                -4.15056934728722208663E-14, 1.54008621752140982691E-14, 3.85277838274214270114E-13,
                7.18012445138366623367E-13, -1.79417853150680611778E-12, -1.32158118404477131188E-11,
                -3.14991652796324136454E-11, 1.18891471078464383424E-11, 4.94060238822496958910E-10,
                3.39623202570838634515E-9, 2.26666899049817806459E-8, 2.04891858946906374183E-7,
                2.89137052083475648297E-6, 6.88975834691682398426E-5, 3.36911647825569408990E-3,
                8.04490411014108831608E-1 };

        double out;
        x = Math.abs(x);

        if (x <= 8) {
            out = Math.exp(x) * UtilMethods.chebyEval(x/2.0 - 2.0, A);
        }
        else {
            out = Math.exp(x) * UtilMethods.chebyEval(32.0/x - 2.0, B) / Math.sqrt(x);
        }
        return out;
    }

    /**
     * Compute the modified Bessel function (0 order) of the first kind, exponentially scaled
     * @param x Single point to compute for
     * @return double Array of values of the exponentially scaled modified Bessel function
     */
    public static double[] i0e(double[] x) {
        double[] A = {-4.41534164647933937950E-18, 3.33079451882223809783E-17, -2.43127984654795469359E-16,
                1.71539128555513303061E-15, -1.16853328779934516808E-14, 7.67618549860493561688E-14,
                -4.85644678311192946090E-13, 2.95505266312963983461E-12, -1.72682629144155570723E-11,
                9.67580903537323691224E-11, -5.18979560163526290666E-10, 2.65982372468238665035E-9,
                -1.30002500998624804212E-8, 6.04699502254191894932E-8, -2.67079385394061173391E-7,
                1.11738753912010371815E-6, -4.41673835845875056359E-6, 1.64484480707288970893E-5,
                -5.75419501008210370398E-5, 1.88502885095841655729E-4, -5.76375574538582365885E-4,
                1.63947561694133579842E-3, -4.32430999505057594430E-3, 1.05464603945949983183E-2,
                -2.37374148058994688156E-2, 4.93052842396707084878E-2, -9.49010970480476444210E-2,
                1.71620901522208775349E-1, -3.04682672343198398683E-1, 6.76795274409476084995E-1 };
        double[] B = {-7.23318048787475395456E-18, -4.83050448594418207126E-18, 4.46562142029675999901E-17,
                3.46122286769746109310E-17, -2.82762398051658348494E-16, -3.42548561967721913462E-16,
                1.77256013305652638360E-15, 3.81168066935262242075E-15, -9.55484669882830764870E-15,
                -4.15056934728722208663E-14, 1.54008621752140982691E-14, 3.85277838274214270114E-13,
                7.18012445138366623367E-13, -1.79417853150680611778E-12, -1.32158118404477131188E-11,
                -3.14991652796324136454E-11, 1.18891471078464383424E-11, 4.94060238822496958910E-10,
                3.39623202570838634515E-9, 2.26666899049817806459E-8, 2.04891858946906374183E-7,
                2.89137052083475648297E-6, 6.88975834691682398426E-5, 3.36911647825569408990E-3,
                8.04490411014108831608E-1 };

        double[] out = new double[x.length];
        x = UtilMethods.absoluteArray(x);

        for (int i=0; i<x.length; i++) {
            if (x[i] <= 8) {
                out[i] = UtilMethods.chebyEval(x[i]/2.0 - 2.0, A);
            }
            else {
                out[i] = UtilMethods.chebyEval(32.0/x[i] - 2.0, B) / Math.sqrt(x[i]);
            }
        }
        return out;
    }

    /**
     * Compute the modified Bessel function (0 order) of the first kind, exponentially scaled
     * @param x Single point to compute for
     * @return double Value of the exponentially scaled modified Bessel function
     */
    public static double i0e(double x) {
        double[] A = {-4.41534164647933937950E-18, 3.33079451882223809783E-17, -2.43127984654795469359E-16,
                1.71539128555513303061E-15, -1.16853328779934516808E-14, 7.67618549860493561688E-14,
                -4.85644678311192946090E-13, 2.95505266312963983461E-12, -1.72682629144155570723E-11,
                9.67580903537323691224E-11, -5.18979560163526290666E-10, 2.65982372468238665035E-9,
                -1.30002500998624804212E-8, 6.04699502254191894932E-8, -2.67079385394061173391E-7,
                1.11738753912010371815E-6, -4.41673835845875056359E-6, 1.64484480707288970893E-5,
                -5.75419501008210370398E-5, 1.88502885095841655729E-4, -5.76375574538582365885E-4,
                1.63947561694133579842E-3, -4.32430999505057594430E-3, 1.05464603945949983183E-2,
                -2.37374148058994688156E-2, 4.93052842396707084878E-2, -9.49010970480476444210E-2,
                1.71620901522208775349E-1, -3.04682672343198398683E-1, 6.76795274409476084995E-1 };
        double[] B = {-7.23318048787475395456E-18, -4.83050448594418207126E-18, 4.46562142029675999901E-17,
                3.46122286769746109310E-17, -2.82762398051658348494E-16, -3.42548561967721913462E-16,
                1.77256013305652638360E-15, 3.81168066935262242075E-15, -9.55484669882830764870E-15,
                -4.15056934728722208663E-14, 1.54008621752140982691E-14, 3.85277838274214270114E-13,
                7.18012445138366623367E-13, -1.79417853150680611778E-12, -1.32158118404477131188E-11,
                -3.14991652796324136454E-11, 1.18891471078464383424E-11, 4.94060238822496958910E-10,
                3.39623202570838634515E-9, 2.26666899049817806459E-8, 2.04891858946906374183E-7,
                2.89137052083475648297E-6, 6.88975834691682398426E-5, 3.36911647825569408990E-3,
                8.04490411014108831608E-1 };

        double out;
        x = Math.abs(x);

        if (x <= 8) {
            out = UtilMethods.chebyEval(x/2.0 - 2.0, A);
        }
        else {
            out = UtilMethods.chebyEval(32.0/x - 2.0, B) / Math.sqrt(x);
        }

        return out;
    }

    /**
     * Compute the normalised sinc function
     * @param x Single point to compute sinc for
     * @return double The computed sinc value
     */
    public static double sinc(double x) {
        double y;
        if (x==0) {
            y = 1.0E-20;
        }
        else {
            y = x;
        }
        y = Math.PI * y;
        return Math.sin(y)/y;
    }

    /**
     * Compute the normalised sinc function
     * @param x Array of point to compute sinc for
     * @return double The computed sinc values for all the inputs
     */
    public static double[] sinc(double[] x) {
        double[] y = new double[x.length];
        for (int i=0; i<y.length; i++) {
            y[i] = UtilMethods.sinc(x[i]);
        }
        return y;
    }

    /**
     * Constructs a Toeplitz matrix from the input vector and returns it
     * @param x First column of the matrix
     * @return double[][] The square Toeplitz matrix
     */
    public static double[][] toeplitz(double[] x) {
        double[][] matrix = new double[x.length][x.length];

        for (int i=0; i<x.length; i++) {
            int index = 0;
            for (int j=i; j<x.length; j++) {
                matrix[i][j] = x[index++];
            }
            index = 0;
            for (int j=i-1; j>=0; j--) {
                matrix[i][j] = x[++index];
            }
        }
        return matrix;
    }

    /**
     * Constructs a Hankel matrix from the input vector and returns it
     * @param c First column of the matrix
     * @return double[][] The square Hankel matrix
     */
    public static double[][] hankel(double[] c) {
        double[][] matrix = new double[c.length][c.length];
        for (double[] row: matrix)
            Arrays.fill(row, 0.0);

        for (int i=0; i<c.length; i++) {
            int index = i;
            for (int j=0; j<c.length-i; j++) {
                matrix[i][j] = c[index++];
            }
        }
        return matrix;
    }

    /**
     * Constructs a Hankel matrix from the input vector and returns it
     * @param c First column of the matrix
     * @param r Last row of the matrix
     * @return double[][] The Hankel matrix with dimensions (r.length x c.length)
     */
    public static double[][] hankel(double[] c, double[] r) {
        double[][] matrix = new double[c.length][r.length];
        if (c[c.length-1] != r[0]) {
            throw new IllegalArgumentException("Last element of c and First element of x must be same");
        }

        double[] temp_r = UtilMethods.splitByIndex(r, 1, r.length);
        double[] vals = UtilMethods.concatenateArray(c, temp_r);

        for (int i=0; i<c.length; i++) {
            int index = i;
            for (int j=0; j<r.length; j++) {
                matrix[i][j] = vals[index++];
            }
        }
        return matrix;
    }

    /**
     * Convert a 1D list of real numbers to a list of Complex numbers
     * @param arr Complex Numbers as a 1D matrix (only real values).
     * @return Complex[] A list of Complex numbers
     */
    public static Complex[] matToComplex(double[] arr) {
        Complex[] out = new Complex[arr.length];
        for (int i=0; i<out.length; i++) {
            out[i] = new Complex(arr[i]);
        }
        return out;
    }

    /**
     * Convert a 2D matrix to a list of Complex numbers
     * @param arr Complex Numbers as a 2D matrix. Dimension 1: Length, Dimension 2: Real part, complex part (optional)
     * @return Complex[] A list of Complex numbers
     */
    public static Complex[] matToComplex(double[][] arr) {
        Complex[] out = new Complex[arr.length];
        for (int i=0; i<out.length; i++) {
            if (arr[i].length == 2) {
                out[i] = new Complex(arr[i][0], arr[i][1]);
            }
            else if (arr[i].length == 1) {
                out[i] = new Complex(arr[i][0]);
            }
            else {
                throw new IllegalArgumentException("Dimension 2 must be of length 1 or 2.");
            }
        }
        return out;
    }

    /**
     * Convert a 3D matrix to a matrix of Complex numbers
     * @param arr Complex Numbers as a 3D matrix. Dimension 1: Rows, Dimension 2: Columns, Dimension 3: Real part, complex part (optional)
     * @return Complex[][] A list of Complex numbers
     */
    public static Complex[][] matToComplex(double[][][] arr) {
        Complex[][] out = new Complex[arr.length][arr[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j = 0; j < out[0].length; j++) {
                if (arr[i][j].length == 2) {
                    out[i][j] = new Complex(arr[i][j][0], arr[i][j][1]);
                } else if (arr[i][j].length == 1) {
                    out[i][j] = new Complex(arr[i][j][0]);
                } else {
                    throw new IllegalArgumentException("Dimension 3 must be of length 1 or 2.");
                }
            }
        }
        return out;
    }

    /**
     * Convert a complex array to a 2D matrix
     * @param arr A list of Complex numbers
     * @return double[][] Complex Numbers as a 2D matrix. Dimension 1: Length, Dimension 2: Real part, complex part
     */
    public static double[][] complexTo2D(Complex[] arr) {
        double[][] out = new double[arr.length][2];
        for (int i=0; i<arr.length; i++) {
            out[i][0] = arr[i].getReal();
            out[i][1] = arr[i].getImaginary();
        }
        return out;
    }

    /**
     * Compute the log of the the input number for a specific base
     * @param y Input number
     * @param base Base value
     * @return double Log of input number with specific base
     */
    public static double log(int y, int base) {
        return Math.log(y)/Math.log(base);
    }

    /**
     * Compute the log of the the input number for a specific base
     * @param y Input number
     * @param base Base value
     * @return double Log of input number with specific base
     */
    public static double log(double y, int base) {
        return Math.log(y)/Math.log(base);
    }

    /**
     * Compute the antilog of the input number for a specific base
     * @param x Input number
     * @param base Base value
     * @return double Antilog of the input number with specific base
     */
    public static double antilog(int x, int base) {
        return Math.pow(base, x);
    }

    /**
     * Compute the antilog of the input number for a specific base
     * @param x Input number
     * @param base Base value
     * @return double Antilog of the input number with specific base
     */
    public static double antilog(double x, int base) {
        return Math.pow(base, x);
    }

    /**
     * Element by Element multiplication of 2 RealMatrix of same shape
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return RealMatrix ebeMutliplication of the 2 matrices
     */
    public static RealMatrix ebeMultiply(RealMatrix m1, RealMatrix m2) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (rowDim != m2.getRowDimension() || colDim != m2.getColumnDimension()) {
            throw new IllegalArgumentException("Dimensions of m1 and m2 matrices must be the same");
        }

        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        for (int i=0; i<rowDim; i++) {
            for (int j=0; j<colDim; j++) {
                out.setEntry(i, j, m1.getEntry(i, j) * m2.getEntry(i, j));
            }
        }
        return out;
    }

    /**
     * Element by Element multiplication of 2 RealMatrix of same shape
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @param type row wise or column wise (can be row or column)
     * @return RealMatrix ebeMutliplication of the 2 matrices
     */
    public static RealMatrix ebeMultiply(RealMatrix m1, RealMatrix m2, String type) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (!type.equals("row") && !type.equals("column")) {
            throw new IllegalArgumentException("Type must be either 'row' or 'column'");
        }
        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        if (type.equals("column")) {
            if (rowDim != m2.getRowDimension() || m2.getColumnDimension() != 1) {
                throw new IllegalArgumentException("Rows of m1 and m2 matrices must be the same and m2 should have only 1 column");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) * m2.getEntry(i, 0));
                }
            }
        }
        if (type.equals("row")) {
            if (colDim != m2.getColumnDimension() || m2.getRowDimension() != 1) {
                throw new IllegalArgumentException("Columns of m1 and m2 matrices must be the same and m2 should have only 1 row");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) * m2.getEntry(0, j));
                }
            }
        }
        return out;
    }

    /**
     * Element by Element division of 2 RealMatrix of same shape
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return RealMatrix ebeDivision of the 2 matrices (m1/m2)
     */
    public static RealMatrix ebeDivide(RealMatrix m1, RealMatrix m2) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (rowDim != m2.getRowDimension() || colDim != m2.getColumnDimension()) {
            throw new IllegalArgumentException("Dimensions of m1 and m2 matrices must be the same");
        }

        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        for (int i=0; i<rowDim; i++) {
            for (int j=0; j<colDim; j++) {
                out.setEntry(i, j, m1.getEntry(i, j) / m2.getEntry(i, j));
            }
        }
        return out;
    }


    /**
     * Element by Element division of 2 RealMatrix
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @param type row wise or column wise (can be row or column)
     * @return RealMatrix ebeDivision of the 2 matrices
     */
    public static RealMatrix ebeDivide(RealMatrix m1, RealMatrix m2, String type) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (!type.equals("row") && !type.equals("column")) {
            throw new IllegalArgumentException("Type must be either 'row' or 'column'");
        }
        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        if (type.equals("column")) {
            if (rowDim != m2.getRowDimension() || m2.getColumnDimension() != 1) {
                throw new IllegalArgumentException("Rows of m1 and m2 matrices must be the same and m2 should have only 1 column");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) / m2.getEntry(i, 0));
                }
            }
        }
        if (type.equals("row")) {
            if (colDim != m2.getColumnDimension() || m2.getRowDimension() != 1) {
                throw new IllegalArgumentException("Columns of m1 and m2 matrices must be the same and m2 should have only 1 row");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) / m2.getEntry(0, j));
                }
            }
        }
        return out;
    }


    /**
     * Element by Element addition of 2 RealMatrix of same shape
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return RealMatrix ebeAddition of the 2 matrices (m1+m2)
     */
    public static RealMatrix ebeAdd(RealMatrix m1, RealMatrix m2) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (rowDim != m2.getRowDimension() || colDim != m2.getColumnDimension()) {
            throw new IllegalArgumentException("Dimensions of m1 and m2 matrices must be the same");
        }

        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        for (int i=0; i<rowDim; i++) {
            for (int j=0; j<colDim; j++) {
                out.setEntry(i, j, m1.getEntry(i, j) + m2.getEntry(i, j));
            }
        }
        return out;
    }


    /**
     * Element by Element addition of 2 RealMatrix
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @param type row wise or column wise (can be row or column)
     * @return RealMatrix ebeAddition of the 2 matrices
     */
    public static RealMatrix ebeAdd(RealMatrix m1, RealMatrix m2, String type) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (!type.equals("row") && !type.equals("column")) {
            throw new IllegalArgumentException("Type must be either 'row' or 'column'");
        }
        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        if (type.equals("column")) {
            if (rowDim != m2.getRowDimension() || m2.getColumnDimension() != 1) {
                throw new IllegalArgumentException("Rows of m1 and m2 matrices must be the same and m2 should have only 1 column");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) + m2.getEntry(i, 0));
                }
            }
        }
        if (type.equals("row")) {
            if (colDim != m2.getColumnDimension() || m2.getRowDimension() != 1) {
                throw new IllegalArgumentException("Columns of m1 and m2 matrices must be the same and m2 should have only 1 row");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) + m2.getEntry(0, j));
                }
            }
        }
        return out;
    }

    /**
     * Element by Element addition of 2 RealMatrix of same shape
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return RealMatrix ebeAddition of the 2 matrices (m1+m2)
     */
    public static RealMatrix ebeSubtract(RealMatrix m1, RealMatrix m2) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (rowDim != m2.getRowDimension() || colDim != m2.getColumnDimension()) {
            throw new IllegalArgumentException("Dimensions of m1 and m2 matrices must be the same");
        }

        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        for (int i=0; i<rowDim; i++) {
            for (int j=0; j<colDim; j++) {
                out.setEntry(i, j, m1.getEntry(i, j) - m2.getEntry(i, j));
            }
        }
        return out;
    }


    /**
     * Element by Element addition of 2 RealMatrix
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @param type row wise or column wise (can be row or column)
     * @return RealMatrix ebeAddition of the 2 matrices
     */
    public static RealMatrix ebeSubtract(RealMatrix m1, RealMatrix m2, String type) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        if (!type.equals("row") && !type.equals("column")) {
            throw new IllegalArgumentException("Type must be either 'row' or 'column'");
        }
        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        if (type.equals("column")) {
            if (rowDim != m2.getRowDimension() || m2.getColumnDimension() != 1) {
                throw new IllegalArgumentException("Rows of m1 and m2 matrices must be the same and m2 should have only 1 column");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) - m2.getEntry(i, 0));
                }
            }
        }
        if (type.equals("row")) {
            if (colDim != m2.getColumnDimension() || m2.getRowDimension() != 1) {
                throw new IllegalArgumentException("Columns of m1 and m2 matrices must be the same and m2 should have only 1 row");
            }
            for (int i=0; i<rowDim; i++) {
                for (int j=0; j<colDim; j++) {
                    out.setEntry(i, j, m1.getEntry(i, j) - m2.getEntry(0, j));
                }
            }
        }
        return out;
    }

    /**
     * Element by Element inversion (x to 1/x) of a RealMatrix
     * @param m1 Matrix to be inverted
     * @return RealMatrix the inverted matrix
     */
    public static RealMatrix ebeInvert(RealMatrix m1) {
        int rowDim = m1.getRowDimension();
        int colDim = m1.getColumnDimension();
        RealMatrix out = MatrixUtils.createRealMatrix(rowDim, colDim);
        for (int i=0; i<rowDim; i++) {
            for (int j=0; j<colDim; j++) {
                out.setEntry(i, j, 1/m1.getEntry(i, j));
            }
        }
        return out;
    }

    /**
     * Dot Product of 2 1-D arrays
     * @param w 1st Vector
     * @param x 2nd Vector
     * @return double Output of the dot product
     */
    public static double dotProduct(double[] w, double[] x) {
        RealVector a = new ArrayRealVector(w, false);
        RealVector b = new ArrayRealVector(x, false);
        return a.dotProduct(b);
    }

    /**
     * Converts decibels to equivalent ratio - in relation to power
     * @param db Value in decibels
     * @return double Ratio in Power
     */
    public static double decibelToRatio(double db) {
        return Math.pow(10, db/20.0);
    }

    /**
     * Converts decibels to equivalent ratio in relation to power or amplitude
     * @param db Value in decibels
     * @param using_amplitude If conversion is in relation to power or amplitude - True if using amplitude
     * @return double Ratio in relation to input type
     */
    public static double decibelToRatio(double db, boolean using_amplitude) {
        double ratio;
        if (using_amplitude) {
            ratio = Math.pow(10, db/20.0);
        }
        else {
            ratio = Math.pow(10, db/10.0);
        }
        return ratio;
    }

    /**
     * Converts ratio (power) to equivalent decibels
     * @param ratio Power Ratio
     * @return Double Decibels
     */
    public static Double ratioToDecibels(double ratio) {
        if (ratio == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return 20 * UtilMethods.log(ratio, 10);
    }

    /**
     * Converts ratio (power or amplitude) to equivalent decibels
     * @param ratio Value in decibels
     * @param using_amplitude If conversion is in relation to power or amplitude - True if using amplitude
     * @return Double Decibels
     */
    public static Double ratioToDecibels(double ratio, boolean using_amplitude) {
        Double decibels = (double) 0;
        if (ratio == 0) {
            decibels = Double.POSITIVE_INFINITY;
        }
        else {
            if (using_amplitude) {
                decibels = 20 * UtilMethods.log(ratio, 10);
            }
            else {
                decibels = 10 * UtilMethods.log(ratio, 10);
            }
        }
        return decibels;
    }

    /**
     * Converts any input integer to boolean. 0 is returned as False, everything else is True.
     * @param value Integer to be converted
     * @return boolean Converted value
     */
    public static boolean integerToBoolean(int value) {
        if (value == 0) {
            return false;
        }
        return true;
    }
}
