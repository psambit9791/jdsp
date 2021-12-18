package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.windows.Rectangular;
import com.github.psambit9791.jdsp.windows._Window;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

/**
 * <h1>Short Time Fourier Transform</h1>
 * The ShortTimeFourier class applies the short time fourier transform on the input signal and
 * provides different representations of the output to be returned (spectrogram, complex value, magnitudes, phases...)
 * and if the output should be mirrored or not-mirrored.
 * <p>
 *
 * @author  Sibo Van Gool
 * @version 1.0
 */
public class ShortTimeFourier {
    private double[] signal;
    private _Fourier[] output = null;
    private final double Fs;
    private final int frameLength;
    private final int fourierLength;
    private final int overlap;
    private final _Window window;

    /**
     * Compute the Short-Time Fourier Transform for a time signal, with windowing.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param fourierLength Number of samples used in the Fourier analysis of each frame
     *                          If the value is greater than frameLength, frame gets zero padded
     * @param window        Windowing function to perform on each STFT frame
     * @param Fs            Sampling frequency of the signal (in Hz)
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, int fourierLength, _Window window, double Fs) {
        if (signal == null) {
            throw new IllegalArgumentException("Signal can not be null");
        }
        if (frameLength < 1) {
            throw new IllegalArgumentException("Frame length must be greater than 0");
        }
        if (overlap >= frameLength) {
            throw new IllegalArgumentException("Overlap size should be smaller than the frame length");
        }
        if (fourierLength < frameLength) {
            throw new IllegalArgumentException("Fourier length should be equal to or greater than the frame length");
        }
        if (window == null) {
            throw new IllegalArgumentException("Window can not be null");
        }
        this.signal = signal;
        this.frameLength = frameLength;
        this.overlap = overlap;
        this.fourierLength = fourierLength;
        this.window = window;
        this.Fs = Fs;
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal, with windowing.
     * Defaults sampling frequency to 1.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param fourierLength Number of samples used in the Fourier analysis of each frame
     *                          If the value is greater than frameLength, frame gets zero padded
     * @param window        Windowing function to perform on each STFT frame
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, int fourierLength, _Window window) {
        this(signal, frameLength, overlap, fourierLength, window, 1);
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal.
     * Defaults window to rectangular window (= no windowing) and sampling frequency to 1.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param fourierLength Number of samples used in the Fourier analysis of each frame
     *                          If the value is greater than frameLength, frame gets zero padded
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, int fourierLength) {
        this(signal, frameLength, overlap, fourierLength, new Rectangular(frameLength), 1);
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal, with windowing.
     * Defaults fourier length to frameLength and sampling frequency to 1.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     * @param window        Windowing function to perform on each STFT frame
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap, _Window window) {
        this(signal, frameLength, overlap, frameLength, window, 1);
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal.
     * Defaults window to rectangular window (= no windowing), fourier length to frameLength and sampling frequency to 1.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     * @param overlap       Number of samples that overlap between frames
     */
    public ShortTimeFourier(double[] signal, int frameLength, int overlap) {
        this(signal, frameLength, overlap, frameLength, new Rectangular(frameLength), 1);
    }

    /**
     * Compute the Short-Time Fourier Transform for a time signal.
     * Defaults overlap to 50% (half of the samples of frameLength), window to rectangular window (= no windowing),
     * fourier length to frameLength and sampling frequency to 1.
     *
     * @param signal        Signal for which to compute the STFT
     * @param frameLength   Number of samples that each FFT-frame should have
     */
    public ShortTimeFourier(double[] signal, int frameLength) {
        this(signal, frameLength, frameLength/2, frameLength, new Rectangular(frameLength), 1);
    }


    /**
     * Calculate the STFT output
     */
    public void transform() {
        int cols = (this.signal.length - frameLength) / (frameLength - overlap) + 1;
        this.output = new _Fourier[cols];

        int R = 0;  // Initialize frame counter
        for (int m = 0; R < cols; m += (frameLength - overlap)) {
            double[] frame = Arrays.copyOfRange(this.signal, m, m + frameLength);

            // Apply windowing
            frame = window.applyWindow(frame);

            // Perform zero padding
            if (this.fourierLength > this.frameLength) {
                frame = UtilMethods.zeroPadSignal(frame, this.fourierLength-this.frameLength);
            }

            // Calculate Fourier transform
            _Fourier dft;
            if (frame.length > 200) {
                dft = new FastFourier(frame);
            }
            else {
                dft = new DiscreteFourier(frame);
            }
            dft.transform();

            // Fill in the output
            this.output[R] = dft;

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

    public _Fourier[] getOutput() {
        checkOutput();
        return output;
    }

    /**
     * Returns the full complex matrix of the STFT
     * @param onlyPositive set to True if non-mirrored output is required
     * @return Complex[][] STFT result matrix; row = frequency frame; column = time frame
     */
    public Complex[][] getComplex(boolean onlyPositive) {
        checkOutput();

        Complex[][] result = new Complex[this.output[0].getComplex(onlyPositive).length][this.output.length];

        // Fill in the output
        for (int c = 0; c < this.output.length; c++) {
            _Fourier dft = this.output[c];
            for (int r = 0; r < result.length; r++) {
                result[r][c] = dft.getComplex(onlyPositive)[r];
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
        checkOutput();

        double[] axis = new double[this.output[0].getComplex(onlyPositive).length];

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
        checkOutput();

        double[] axis = new double[this.output.length];
        for (int i = 0; i < axis.length; i++) {
            axis[i] = i*(this.frameLength - overlap)/this.Fs;
        }
        return axis;
    }

    /**
     * Checks whether the STFT has been calculated yet
     * @throws ExceptionInInitializerError if result hasn't been calculated yet
     */
    private void checkOutput() {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute stft() function before returning result");
        }
    }
}

