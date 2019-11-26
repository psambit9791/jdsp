package com.onyx.test;

import com.onyx.signal.Detrend;

import java.util.Arrays;

public class DetrendTest {
    private static void displayArray(double[] arr) {
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+", ");
        }
        System.out.println();
    }

    private static void detrendTest() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        final double[] result_d1 =  {0.0, 0.0, 0.0, 0.0, 0.0};
        final double[] result_d2 = {-2.0, -1.0, 0.0, 1.0, 2.0};

        Detrend d1 = new Detrend(original, "linear");
        d1.detrendSignal();
        double[] out = d1.getDetrendedSignal();
        if (!Arrays.equals(out,result_d1)) {
            System.out.println("Linear Detrend not implemented properly");
        }

        Detrend d2 = new Detrend(original, "constant");
        d2.detrendSignal();
        out = d2.getDetrendedSignal();
        if (!Arrays.equals(out,result_d2)) {
            System.out.println("Constant Detrend not implemented properly");
        }
    }

    public static void main(String args[]) {
        detrendTest();
    }
}
