package com.github.psambit9791.jdsp.windows;

import java.util.Arrays;

public class BoxCar extends _Window {

    double[] window;
    boolean sym;
    int len;

    public BoxCar(int len, boolean sym) throws IllegalArgumentException {
        this.sym = sym;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    public BoxCar(int len) throws IllegalArgumentException {
        this.sym = true;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    public double[] getWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 1);
        this.window = super.truncate(this.window);
        return this.window;
    }
}
