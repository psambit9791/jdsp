package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Nuttall Window</h1>
 * Generates a minimum 4-term Blackman-Harris window according to Nuttall.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Nuttall extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the Nuttall class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Nuttall(int len, boolean sym) throws IllegalArgumentException {
        this.sym = sym;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the Nuttall class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Nuttall(int len) throws IllegalArgumentException {
        this.sym = true;
        this.len = len;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the Nuttall Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.3635819, 0.4891775, 0.1365995, 0.0106411};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
