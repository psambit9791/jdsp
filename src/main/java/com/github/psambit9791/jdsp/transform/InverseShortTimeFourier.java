package com.github.psambit9791.jdsp.transform;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.jdsp.windows.Rectangular;
import com.github.psambit9791.jdsp.windows._Window;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

/**
 * <h1>Inverse Short Time Fourier Transform</h1>
 * The InverseShortTimeFourier class applies the inverse short time fourier transform on an input sequence and
 * provides different representations of the reconstructed signal to be returned (real signal, complex signal, ...).
 * <p>
 *
 * @author  Sibo Van Gool
 * @version 1.0
 */
public class InverseShortTimeFourier {
    private final _Fourier[] signal;
    private final int overlap;
    private final int frameLength;
    private final _Window window;
    private Complex[] output;

    /**
     * This constructor initialises the prerequisites required to use InverseShortTimeFourier.
     * @param signal        STFT signal to be converted
     * @param frameLength   length of the frame used in the STFT
     *                          if this is smaller than the Fourier length of signal, then this means that
     *                          the signal was zero-padded in the STFT
     * @param overlap       number of overlap frames used in the STFT
     * @param window        window used in the STFT
     */
    public InverseShortTimeFourier(_Fourier[] signal, int frameLength, int overlap, _Window window) {
        if (signal == null) {
            throw new IllegalArgumentException("Signal can not be null");
        }
        if (frameLength > signal[0].getComplex(false).length) {
            throw new IllegalArgumentException("Frame length can not be larger than signal Fourier length");
        }
        if (overlap >= frameLength) {
            throw new IllegalArgumentException("Overlap must be smaller than frame length");
        }
        if (window == null) {
            throw new IllegalArgumentException("Window can not be null");
        }
        if (window.getWindow().length != frameLength) {
            throw new IllegalArgumentException("Window and frame dimensions must match");
        }
        this.signal = signal;
        this.frameLength = frameLength;
        this.overlap = overlap;
        this.window = window;
    }

    /**
     * This constructor initialises the prerequisites required to use InverseShortTimeFourier.
     * Defaults window to rectangular window (= no windowing effect)
     * @param signal        STFT signal to be converted
     * @param frameLength   length of the frame used in the STFT
     *                          if this is smaller than the Fourier length of signal, then this means that
     *                          the signal was zero-padded in the STFT
     * @param overlap       number of overlap frames used in the STFT
     */
    public InverseShortTimeFourier(_Fourier[] signal, int frameLength, int overlap) {
        this(signal, frameLength, overlap, new Rectangular(frameLength));
    }

    /**
     * This constructor initialises the prerequisites required to use InverseShortTimeFourier.
     * Defaults window to rectangular window (= no windowing effect) and overlap to 50% (= 1/2 of the number of samples
     * in frameLength)
     * @param signal        STFT signal to be converted
     * @param frameLength   length of the frame used in the STFT
     *                          if this is smaller than the Fourier length of signal, then this means that
     *                          the signal was zero-padded in the STFT
     */
    public InverseShortTimeFourier(_Fourier[] signal, int frameLength) {
        this(signal, frameLength, frameLength/2,
                new Rectangular(frameLength));
    }

    /**
     * This constructor initialises the prerequisites required to use InverseShortTimeFourier.
     * Defaults window to rectangular window (= no windowing effect) and overlap to 50% (= 1/2 of the number of samples
     * in frameLength), and frame length to the Fourier length of signal
     * @param signal        STFT signal to be converted
     */
    public InverseShortTimeFourier(_Fourier[] signal) {
        this(signal, signal[0].getComplex(false).length, signal[0].getComplex(false).length/2,
                new Rectangular(signal[0].getComplex(false).length));
    }

    /**
     * This function performs the inverse discrete fourier transform on the input sequence
     */
    public void transform() {
        int signalLength = (int)Math.floor((this.signal.length - 1) * (frameLength - this.overlap) + frameLength);
        this.output = new Complex[signalLength];

        int[] averageDivisor = new int[signalLength];   // Stores output average divisor
        Arrays.fill(averageDivisor, 1);
        int arrPastePosition = 0;

        // Flag that checks whether the window contained a zero-value. If true, a warning message will be print about
        // irretrievable loss of the signal.
        boolean dataLost = false;

        for (_Fourier dtft : this.signal) {
            double[][] seq = UtilMethods.complexTo2D(dtft.getComplex(false));
            _InverseFourier idft;
            double logval = Math.log(seq.length)/Math.log(2);
            if (logval == (int)(logval)) {
                idft = new InverseFastFourier(UtilMethods.matToComplex(seq), false);
            }
            else {
                idft = new InverseDiscreteFourier(seq, false);
            }
            idft.transform();
            Complex[] idft_result = idft.getComplex();

            // Fill in output signal
            for (int i = 0; i < this.frameLength; i++) {    // Last elements of idft_result are zeroes from zero-padding, so only parse until this.frameLength
                double windowVal = this.window.getWindow()[i];
                double real = idft_result[i].getReal();
                double imaginary = idft_result[i].getImaginary();

                // Inverse the windowing effect
                if (windowVal != 0) {
                    real = real / windowVal;
                }
                else {
                    dataLost = true;
                }

                // Do a summation of output-values, for overlapping frames on the same output-element
                if (this.output[i + arrPastePosition] != null) {
                    averageDivisor[i + arrPastePosition] += 1;
                    real = real + this.output[i + arrPastePosition].getReal();
                    imaginary = imaginary + this.output[i + arrPastePosition].getImaginary();
                }

                this.output[i + arrPastePosition] = new Complex(real, imaginary);
            }
            arrPastePosition += frameLength - overlap;
        }

        // Overlapping frames sum their value on the same output-element, which needs to be averaged in this step
        for (int i = 0; i < averageDivisor.length; i++) {
            if (averageDivisor[i]  > 1) {
                double real = this.output[i].getReal()/averageDivisor[i];
                double imaginary = this.output[i].getImaginary()/averageDivisor[i];
                this.output[i] = new Complex(real, imaginary);
            }
        }

        if (dataLost) {
            System.err.println("The original window function contained a zero-element, which causes some of the data to be irretrievably lost.");
        }
    }

    /**
     * This method returns the complex value of the generated time signal as a Complex array.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return Complex[] The inverse STFT time signal
     */
    public Complex[] getComplex() throws ExceptionInInitializerError {
        checkOutput();
        return this.output;
    }

    /**
     * This method returns the complex value of the generated time signal as a 2D matrix.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return double[][] The inverse STFT time signal; first array column = real part; second array column = imaginary part
     */
    public double[][] getComplex2D() throws ExceptionInInitializerError {
        return UtilMethods.complexTo2D(getComplex());
    }

    /**
     * This method returns the real part of the generated time signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return double[] The real part of the inverse STFT time signal
     */
    public double[] getReal() throws ExceptionInInitializerError {
        checkOutput();
        return Arrays.stream(this.output).mapToDouble(Complex :: getReal).toArray();
    }

    /**
     * This method returns the imaginary part of the generated time signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return double[] The imaginary part of the inverse STFT time signal
     */
    public double[] getImaginary() throws ExceptionInInitializerError {
        checkOutput();
        return Arrays.stream(this.output).mapToDouble(Complex :: getImaginary).toArray();
    }

    /**
     * This method returns the magnitude of the generated time signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return double[] The magnitude of the inverse STFT time signal
     */
    public double[] getMagnitude() throws ExceptionInInitializerError {
        checkOutput();
        return Arrays.stream(this.output).mapToDouble(Complex :: abs).toArray();
    }

    /**
     * This method returns the phase of the generated time signal.
     * @throws java.lang.ExceptionInInitializerError if called before executing istft() method
     * @return double[] The phase of the inverse STFT time signal
     */
    public double[] getPhase() throws ExceptionInInitializerError {
        checkOutput();
        return Arrays.stream(this.output).mapToDouble(Complex :: getArgument).toArray();
    }

    /**
     * Checks whether the ISTFT has been calculated yet
     * @throws ExceptionInInitializerError if result hasn't been calculated yet
     */
    private void checkOutput() throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute istft() function before returning result");
        }
    }
}
