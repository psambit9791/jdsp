package com.github.psambit9791.jdsp.windows;

/**
 * <h1>Flat Top Window</h1>
 * The Flat top window is used for taking accurate measurements of signal amplitude in the frequency domain, with
 * minimal scalloping error from the center of a frequency bin to its edges, compared to others. This is a 5th-order
 * cosine window, with the 5 terms optimized to make the main lobe maximally flat.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class FlatTop extends _Window{

    double[] window;
    boolean sym;
    int len;

    /**
     * This constructor initialises the FlatTop class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param sym Whether the window is symmetric
     */
    public FlatTop(int len, boolean sym) throws IllegalArgumentException {
        this.len = len;
        this.sym = sym;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * This constructor initialises the FlatTop class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     */
    public FlatTop(int len) throws IllegalArgumentException {
        this.len = len;
        this.sym = true;
        if (lenGuard(len)) {
            throw new IllegalArgumentException("Window Length must be greater than 0");
        }
    }

    /**
     * Generates and returns the FlatTop Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        double[] w = {0.21557895, 0.41663158, 0.277263158, 0.083578947, 0.006947368};
        GeneralCosine gc = new GeneralCosine(this.len, w, this.sym);
        this.window = gc.getWindow();
        return this.window;
    }
}
