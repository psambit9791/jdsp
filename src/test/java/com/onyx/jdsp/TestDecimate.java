package com.onyx.jdsp;

import com.onyx.jdsp.signal.Decimate;
import com.onyx.jdsp.signal.GeneratePeriodic;
import org.apache.commons.math3.util.MathArrays;
import org.junit.Test;

import javax.smartcardio.ATR;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestDecimate {

    private int Fs = 100;
    private double[] signal;
    private GeneratePeriodic gp = new GeneratePeriodic(this.Fs);

    @Test
    public void DecimateWithoutZeroPhaseTest() {
        double[] result = {0.   ,  0.003,  0.111,  0.481, -0.015, -0.452,  0.616, -0.435, 0.016,  0.446, -0.758,  0.785,
                -0.509,  0.027,  0.486, -0.841, 0.904, -0.645,  0.151,  0.406, -0.828,  0.964, -0.761,  0.29 , 0.287};
        int factor = 4;

        double[] sin1 = this.gp.generateSineWave(10);
        double[] sin2 = this.gp.generateSineWave(35);
        this.signal = MathArrays.ebeAdd(sin1, sin2);

        Decimate d = new Decimate(this.signal, this.Fs, false);
        double[] out = d.decimate(factor);

        assertArrayEquals(out, result, 0.001);
    }

    @Test
    public void DecimateWithZeroPhaseTest() {
        double[] result = {0.43 ,  0.451, -0.863,  0.923, -0.639,  0.116,  0.455, -0.869, 0.979, -0.744,  0.247,  0.337,
                -0.802,  0.984, -0.82 ,  0.366, 0.217, -0.724,  0.977, -0.891,  0.502,  0.036, -0.508,  0.671, -0.05};
        int factor = 4;

        double[] sin1 = this.gp.generateSineWave(10);
        double[] sin2 = this.gp.generateSineWave(20);
        this.signal = MathArrays.ebeAdd(sin1, sin2);

        Decimate d = new Decimate(this.signal, this.Fs, true);
        double[] out = d.decimate(factor);

        double[] outTemp = UtilMethods.splitByIndex(out, 1, out.length);
        double[] resultTemp = UtilMethods.splitByIndex(result, 1, result.length);

        // For this use-case, testing is done in all samples except first, because the first
        // point is inconsistent between Scipy and jDSP due to difference in implementation of filters
        assertArrayEquals(outTemp, resultTemp, 0.05);
    }
}
