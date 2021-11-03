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
    private double[] window;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Nuttall class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public Nuttall(int len, boolean sym) throws IllegalArgumentException {
        super(len);
        this.sym = sym;
        this.len = len;
        generateWindow();
    }

    /**
     * This constructor initialises the Nuttall class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public Nuttall(int len) throws IllegalArgumentException {
        this(len, true);
    }

    private void generateWindow() {
        double[] w = {0.3635819, 0.4891775, 0.1365995, 0.0106411};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
    }

    /**
     * Generates and returns the Nuttall Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
