package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.windows.Rectangular;
import com.github.psambit9791.jdsp.windows._Window;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

public class ShortTimeFourier {
    private double[] signal;
    private Complex[][] output = null;
    private final double Fs;
    private final int frameLength;
    private final int overlap;
    private final _Window window;

    /**
     * Compute the Short-Time Fourier Transform for a time signal, with windowing.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param Fs            Sampling frequency of the signal
     * @param window        Windowing function to perform on each STFT frame
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, double Fs, _Window window) {
        this.signal = signal;
        this.frameLength = frameLength;
        this.overlap = overlap;
        this.Fs = Fs;
        this.window = window;
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal, with windowing.
     *
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, _Window window) {
        this(signal, frameLength, overlap, 1, window);
    }

    /**
     * Defaults window as a rectangular window (= no windowing performed)
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param Fs            Sampling frequency of the signal
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, double Fs) {
        this(signal, frameLength, overlap, Fs, new Rectangular(frameLength));
    }

    /**
     * Defaults Fs to 1 and as window a rectangular window (= no windowing performed)
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap) {
        this(signal, frameLength, overlap, 1, new Rectangular(frameLength));
    }

    /**
     * Defaults Fs to 1 and as window a rectangular window (= no windowing performed) and 50% overlap
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     */
    public ShortTimeFourier(double[] signal, int frameLength) {
        this(signal, frameLength, frameLength/2, 1, new Rectangular(frameLength));
    }


    /**
     * Calculate the STFT output
     */
    public void stft() {
        if (overlap >= frameLength) {
            throw new IllegalArgumentException("Overlap size should be smaller than frame length size");
        }

        int cols = (this.signal.length - frameLength) / (frameLength - overlap) + 1;
        this.output = new Complex[frameLength][cols];

        int R = 0;  // Initialize frame counter
        for (int m = 0; R < this.output[0].length; m += (frameLength - overlap)) {
            double[] frame = Arrays.copyOfRange(this.signal, m, m + frameLength);
            frame = window.applyWindow(frame);
            // TODO: zero-padding
            DiscreteFourier dft = new DiscreteFourier(frame);       // TODO: better to use FFT once implemented
            dft.dft();
            Complex[] dftOut = dft.getComplex(false);

            // Fill in the output
            for (int i = 0; i < this.output.length; i++) {
                this.output[i][R] = dftOut[i];
            }

            R++;
        }
    }

    /**
     * Returns the spectrogram matrix of the STFT (= squared magnitude)
     * @param onlyPositive set to True if non-mirrored output is required
     * @return double[][] spectrogram (squared magnitude) of the STFT; row = frequency frame; column = time frame
     */
    public double[][] spectrogram(boolean onlyPositive) {
        double[][] resultMag = getMagnitude(onlyPositive);
        double[][] result = new double[resultMag.length][resultMag[0].length];

        // Fill in the output
        for (int c = 0; c < resultMag[0].length; c++) {
            for (int r = 0; r < resultMag.length; r++) {
                result[r][c] = Math.pow(resultMag[r][c], 2);    // Squared magnitude of the output
            }
        }
        return result;
    }

    /**
     * Returns the magnitude matrix of the STFT
     * @param onlyPositive set to True if non-mirrored output is required
     * @return double[][] magnitude matrix of the STFT; row = frequency frame; column = time frame
     */
    public double[][] getMagnitude(boolean onlyPositive) {
        Complex[][] resultComplex = getComplex(onlyPositive);
        double[][] result = new double[resultComplex.length][resultComplex[0].length];

        // Fill in the output
        for (int c = 0; c < resultComplex[0].length; c++) {
            for (int r = 0; r < resultComplex.length; r++) {
                result[r][c] = resultComplex[r][c].abs();
            }
        }
        return result;
    }

    /**
     * Returns the phase matrix of the STFT (in radians)
     * @param onlyPositive set to True if non-mirrored output is required
     * @return double[][] phase matrix (radians) of the STFT; row = frequency frame; column = time frame
     */
    public double[][] getPhaseRad(boolean onlyPositive) {
        Complex[][] resultComplex = getComplex(onlyPositive);
        double[][] result = new double[resultComplex.length][resultComplex[0].length];

        // Fill in the output
        for (int c = 0; c < resultComplex[0].length; c++) {
            for (int r = 0; r < resultComplex.length; r++) {
                result[r][c] = resultComplex[r][c].getArgument();
            }
        }
        return result;
    }

    /**
     * Returns the phase matrix of the STFT (in degrees)
     * @param onlyPositive set to True if non-mirrored output is required
     * @return double[][] phase matrix (degrees) of the STFT; row = frequency frame; column = time frame
     */
    public double[][] getPhaseDeg(boolean onlyPositive) {
        Complex[][] resultComplex = getComplex(onlyPositive);
        double[][] result = new double[resultComplex.length][resultComplex[0].length];

        // Fill in the output
        for (int c = 0; c < resultComplex[0].length; c++) {
            for (int r = 0; r < resultComplex.length; r++) {
                result[r][c] = Math.toDegrees(resultComplex[r][c].getArgument());
            }
        }
        return result;
    }

    /**
     * Returns the full complex matrix of the STFT
     * @param onlyPositive set to True if non-mirrored output is required
     * @return Complex[][] STFT result matrix; row = frequency frame; column = time frame
     */
    public Complex[][] getComplex(boolean onlyPositive) {
        if (this.output == null) {
            stft();
        }

        Complex[][] result;

        if (onlyPositive) {
            int numBins = this.output.length / 2 + 1;
            result = new Complex[numBins][this.output[0].length];
        }
        else {
            result = new Complex[this.output.length][this.output[0].length];
        }

        // Fill in the output
        for (int c = 0; c < result[0].length; c++) {
            for (int r = 0; r < result.length; r++) {
                result[r][c] = this.output[r][c];
            }
        }
        return result;
    }

    /**
     * Returns the frequency axis of the STFT
     * @param onlyPositive set to True if non-mirrored output is required
     * @return double[] array of all the time frames
     */
    public double[] getFrequencyAxis(boolean onlyPositive) {
        if (this.output == null) {
            throw new ExceptionInInitializerError("No STFT calculated yet");
        }

        double[] axis;
        if (onlyPositive) {
            int numBins = this.output.length / 2 + 1;
            axis = new double[numBins];
        }
        else {
            axis = new double[this.output.length];
        }

        for (int i = 0; i < axis.length; i++) {
            axis[i] = i*this.Fs/this.frameLength;
        }

        return axis;
    }

    /**
     * Returns the time axis of the STFT
     * @return double[] array of all the frequency frames
     */
    public double[] getTimeAxis() {
        if (this.output == null) {
            throw new ExceptionInInitializerError("No STFT calculated yet");
        }

        double[] axis = new double[this.output[0].length];
        for (int i = 0; i < axis.length; i++) {
            axis[i] = i*(this.frameLength - overlap)/this.Fs;
        }
        return axis;
    }
}

