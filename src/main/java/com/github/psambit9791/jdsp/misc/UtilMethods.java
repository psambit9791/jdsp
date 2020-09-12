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

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.stat.StatUtils;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


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

        double span = stop - start;

        double stopVal = (double) stop;
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

        double stopVal = (double) stop;
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
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        Double d = value;
        String temp = df.format(d);
        return Double.parseDouble(temp);
    }

    /**
     * Evenly rounds the the elements of a double array to the given number of decimals
     * @param arr Value to be rounded
     * @param decimals Number of decimal places to round to
     * @return double The rounded double array
     */
    public static double[] round(double[] arr, int decimals) {
        double[] rounded = new double[arr.length];
        String dPlaces = "#.";
        for (int i=0; i<decimals; i++) {
            dPlaces = dPlaces + "#";
        }

        DecimalFormat df = new DecimalFormat(dPlaces);
            df.setRoundingMode(RoundingMode.HALF_EVEN);
        for (int i=0; i<rounded.length; i++) {
            Double d = arr[i];
            rounded[i] = Double.parseDouble(df.format(d));
        }
        return rounded;
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
        PolynomialSplineFunction psf = li.interpolate(x, y);
        return psf;
    }

    /**
     * Converts an Integer ArrayList to int[] array
     * @param l The Integer ArrayList
     * @return int[] The primitive array
     */
    public static int[] convertToPrimitiveInt(ArrayList<Integer> l) {
        int[] ret = new int[l.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = l.get(i).intValue();
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
            ret[i] = l.get(i).doubleValue();
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
     * Returns the transpose of the matrix.
     * @param m The matrix to be transposed
     * @return double[][] The transpose of the matrix
     */
    public static double[][] transpose(double[][] m) {
        RealMatrix m1 = MatrixUtils.createRealMatrix(m);
        double[][] m_t = m1.transpose().getData();
        return m_t;
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
     * Performs scalar arithmetic operation with a specific value on the input array
     * @param arr Array to be processed
     * @param val Value with which arithmetic operation is performed
     * @param action What action needs to be performed. Can be addition, subtraction, multiplication and division.
     * @throws java.lang.IllegalArgumentException if action is not "add", "sub", "mul", "div"
     * @return double[][] The result of the operation
     */
    public static double[] scalarArithmetic(double[] arr, double val, String action) throws IllegalArgumentException {
        if (!action.equals("add") && !action.equals("sub") && !action.equals("mul") && !action.equals("div")) {
            throw new ArithmeticException("action must be 'add', 'sub', 'mul', or 'div'");
        }
        double[] out = new double[arr.length];
        if (action.equals("add")) {
            for (int i=0; i<arr.length; i++) {
                out[i] = arr[i] + val;
            }
        }
        else if (action.equals("sub")) {
            for (int i=0; i<arr.length; i++) {
                out[i] = arr[i] - val;
            }
        }
        else if (action.equals("mul")) {
            for (int i=0; i<arr.length; i++) {
                out[i] = arr[i] * val;
            }
        }
        else {
            for (int i=0; i<arr.length; i++) {
                out[i] = arr[i] / val;
            }
        }
        return out;
    }
}
