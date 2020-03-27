package com.onyx.signal;

import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.Arrays;

public class UtilMethods {

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

        while (i<stopVal) {
            i = i + T;
            index++;
            time[index] = i;
        }
        return time;
    }

    public static double[] linspace(int start, int stop, int samples, int repeats) {
        double[] time = new double[samples];
        double T = 1.0/(samples-1);

        double i = start;

        int index = 0;
        time[index] = i;

        while (i<stop) {
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
    public static double[] reverse(double[] arr) {
        double[] inv = new double[arr.length];
        int index = 0;
        for (int i=(arr.length-1); i>=0; i--) {
            inv[index] = arr[i];
            index++;
        }
        return inv;
    }

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
    public static double[] concatenateArray(double[] arr1, double[] arr2) {
        double[] out = new double[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, out, 0, arr1.length);
        System.arraycopy(arr2, 0, out, arr1.length, arr2.length);
        return out;
    }

    public static int[] concatenateArray(int[] arr1, int[] arr2) {
        int[] out = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, out, 0, arr1.length);
        System.arraycopy(arr2, 0, out, arr1.length, arr2.length);
        return out;
    }

    // Split an array by indices
    public static double[] splitByIndex(double[] arr, int start, int end) {
        double[] out = new double[end-start];
        System.arraycopy(arr, start, out, 0, out.length);
        return out;
    }

    public static int[] splitByIndex(int[] arr, int start, int end) {
        int[] out = new int[end-start];
        System.arraycopy(arr, start, out, 0, out.length);
        return out;
    }

    // Finc pseudo-inverse of a 2D array
    public static double[][] pseudoInverse(double[][] mtrx) {
        RealMatrix M = MatrixUtils.createRealMatrix(mtrx);
        DecompositionSolver solver = new SingularValueDecomposition(M).getSolver();
        return solver.getInverse().getData();
    }

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
}
