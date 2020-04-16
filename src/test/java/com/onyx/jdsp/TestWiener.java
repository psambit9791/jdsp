package com.onyx.jdsp;

import com.onyx.jdsp.filter.Wiener;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class TestWiener {

    @Test
    public void wienerTest() {
        double[] signal = {3.0, 4.0, 2.0, 1.0, 5.0, 6.0, 0.0, 4.0, 5.0};
        int wsize = 5;
        double[] result = {1.8, 2.0, 3.0, 3.6, 3.336, 3.883, 3.685, 3.367, 2.385};

        Wiener wf = new Wiener(signal, wsize);
        double[] out = wf.wiener_filter();
        assertArrayEquals(out, result, 0.001);
    }
}
