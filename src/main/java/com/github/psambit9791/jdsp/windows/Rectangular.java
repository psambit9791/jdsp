package com.github.psambit9791.jdsp.windows;

import java.util.Arrays;

/**
 * <h1>Rectangular Window</h1>
 * Generates a rectangular window.
 * <p>
 *
 * @author  Sibo Van Gool
 * @version 1.0
 */
public class Rectangular extends _Window {
    private double[] window;
    private final int len;
    private final boolean sym;

    /**
     * This constructor initialises the Rectangular class. This window has no real effect on the data, but serves
     * more as a dummy window.
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Rectangular(int len, boolean sym) {
        super(len);
        this.len = len;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Rectangular class. This window has no real effect on the data, but serves
     * more as a dummy window. Symmetricity is set to True.
     * @param len Length of the window
     */
    public Rectangular(int len) {
        this(len, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 1);
    }

    @Override
    public double[] getWindow() {
        return this.window;
    }
}
