package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.filter.Wiener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWiener {

    @Test
    public void wienerTest1() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wsize = 5;
        double[] result = {1.8, 2.0, 3.0, 3.6, 3.336, 3.883, 3.685, 3.367, 2.385};

        Wiener wf = new Wiener(signal, wsize);
        double[] out = wf.wiener_filter();
        Assertions.assertArrayEquals(result, out, 0.001);
    }

    @Test
    public void wienerTest2() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        double[] result = {2.333, 3.0, 2.333, 2.667, 4.164, 4.679, 2.09, 3.164, 3.328};

        Wiener wf = new Wiener(signal);
        double[] out = wf.wiener_filter();
        Assertions.assertArrayEquals(result, out, 0.001);
    }
}
