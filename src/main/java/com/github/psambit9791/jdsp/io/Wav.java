/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.io;

import com.github.psambit9791.wavfile.WavFile;
import com.github.psambit9791.wavfile.WavFileException;
import com.github.psambit9791.jdsp.misc.UtilMethods;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * <h1>Read and Write WAV Files</h1>
 * This class provides methods; to read WAV files including their properties and their content as a 2-D matrix;
 * and to write 2-D matrices into WAV files. For all the matrices, the first dimension is considered the number of frames
 * and the second dimension is considered the number of channels.
 * For example, a matrix denoted as <pre>double[][] signal = new double[16000][2]</pre> translates to a wav file with 2
 * channels and 16000 frames. At a sampling rate of 8kHz, this interprets to 2 seconds of stereo sound.
 *
 * This class is supported largely by the classes provided <a href="http://www.labbookpages.co.uk/audio/javaWavFiles.html">here</a>.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Wav {
    private WavFile wf;
    public Hashtable<String, Long> props;
    private double[][] data;

    /**
     * This reads a sample file from the res folder called "sample.wav"
     * @throws com.github.psambit9791.wavfile.WavFileException if error occurs in WavFile class
     * @throws java.io.IOException if sample file does not exist
     */
    public void readTemplate() throws WavFileException, IOException {
        File f = null;
        try {
            f = new File(getClass().getClassLoader().getResource("sample.wav").getFile());
        }
        catch (NullPointerException e) {
            System.out.println("File Not Found.");
        }
        this.wf = WavFile.openWavFile(f);
        this.computeProperties();
    }

    /**
     * This reads a file from the filepath provided
     * @param filename The path to the file to be read
     * @throws com.github.psambit9791.wavfile.WavFileException if error occurs in WavFile class
     * @throws java.io.IOException if sample file does not exist
     */
    public void readWav(String filename) throws WavFileException, IOException{
        String ext = filename.substring(filename.lastIndexOf(".")+1, filename.length());
        if (!ext.equals("wav")) {
            System.out.println("File is not of WAV format");
        }
        else {
            File f = null;
            try {
                f = new File(filename);
            }
            catch (NullPointerException e) {
                System.out.println("File Not Found.");
            }
            this.wf = WavFile.openWavFile(f);
            this.computeProperties();
        }
    }

    /**
     * Compute properties of the WAV File
     */
    private void computeProperties() {
        int bytesPerSample = (this.wf.getValidBits() + 7)/8;
        this.props = new Hashtable<String, Long>();
        this.props.put("Channels", (long)this.wf.getNumChannels());
        this.props.put("Frames", this.wf.getNumFrames());
        this.props.put("SampleRate", this.wf.getSampleRate());
        this.props.put("BlockAlign", (long)(bytesPerSample*this.wf.getNumChannels()));
        this.props.put("ValidBits", (long)(this.wf.getValidBits()));
        this.props.put("BytesPerSample", (long)bytesPerSample);
    }

    /**
     * Returns the properties of the WAV file
     * @return HashTable A hashtable with the following properties: Channels, Frames, SampleRate, BlockAlign, ValidBits, BytesPerSample
     */
    public Hashtable getProperties() {
        return this.props;
    }


    private double[][] toDouble(int[][] a) {
        double[][] out = new double[a.length][a[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j=0; j<out[0].length; j++) {
                out[i][j] = a[i][j];
            }
        }
        return out;
    }

    private double[][] toDouble(long[][] a) {
        double[][] out = new double[a.length][a[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j=0; j<out[0].length; j++) {
                out[i][j] = a[i][j];
            }
        }
        return out;
    }

    /**
     * Returns the wav file content as a 2-D array
     * @throws java.io.IOException In case any error occurs while reading the frames
     * @throws com.github.psambit9791.wavfile.WavFileException if error occurs in WavFile class
     * @throws java.lang.IllegalArgumentException if type is anything other than "int", "long", "double"
     * @param type Can be "int", "long" and "double"
     *             "int" - Up to 32 bit unsigned
     *             "long" - Up to 64 bit unsigned
     *             "double" - Scales the value between -1 and 1.
     * @return double[][] The content of the wav file
     */
    public double[][] getData(String type) throws IOException, WavFileException, IllegalArgumentException {
        int channels = this.props.get("Channels").intValue();
        int frames = this.props.get("Frames").intValue();
        double[][] signal;
        if (type.equals("int")) {
            int[][] sig = new int[channels][frames];
            wf.readFrames(sig, frames);
            signal = this.toDouble(sig);
        }
        else if (type.equals("long")) {
            long[][] sig = new long[channels][frames];
            wf.readFrames(sig, frames);
            signal = this.toDouble(sig);
        }
        else if (type.equals("double")) {
            double[][] sig = new double[channels][frames];
            wf.readFrames(sig, frames);
            signal = sig;
        }
        else {
            throw new IllegalArgumentException("Type must be int, long or double");
        }
        signal = UtilMethods.transpose(signal);
        return signal;
    }

    /**
     * Puts the 2-D array data into a WAV file. Assumes validBits is 16.
     * @throws com.github.psambit9791.wavfile.WavFileException if error occurs in WavFile class
     * @throws java.io.IOException if there is an issue in writing to the file
     * @throws java.lang.IllegalArgumentException if type is anything other than "int", "long", "double"
     * @param signal The 2-D array that needs to be saved as a wav file
     * @param samplingRate The sampling rate at which the audio was captured
     * @param type Can be "int", "long" and "double". Depends on what the content of the signal parameter is.
     * @param filename The name of the file the signal will be saved as
     */
    public void putData(double[][] signal, long samplingRate, String type, String filename) throws IOException, WavFileException, IllegalArgumentException {
        this.putData(signal, samplingRate, 16, type, filename);
    }

    /**
     * Puts the 2-D array data into a WAV file.
     * @throws com.github.psambit9791.wavfile.WavFileException if error occurs in WavFile class
     * @throws java.io.IOException if there is an issue in writing to the file
     * @throws java.lang.IllegalArgumentException if type is anything other than "int", "long", "double"
     * @param signal The 2-D array that needs to be saved as a wav file
     * @param samplingRate The sampling rate at which the audio was captured
     * @param validBits The number of valid bits used for storing a single sample
     * @param type Can be "int", "long" and "double". Depends on what the content of the signal parameter is.
     * @param filename The name of the file the signal will be saved as
     */
    public void putData(double[][] signal, long samplingRate, int validBits, String type, String filename) throws IOException, WavFileException, IllegalArgumentException {
        signal = UtilMethods.transpose(signal);
        int channels = signal.length;
        long frames = (long)signal[0].length;
        File f = new File(filename);
        this.wf = WavFile.newWavFile(f, channels, frames, validBits, samplingRate);
        if (type.equals("int")) {
            int[][] buffer = new int[channels][100];
            long frameCounter = 0;
            while (frameCounter < frames)
            {
                long remaining = this.wf.getFramesRemaining();
                int toWrite = (remaining > 100) ? 100 : (int) remaining;
                for (int s=0 ; s<toWrite ; s++, frameCounter++)
                {
                    for (int c=0; c<channels; c++) {
                        buffer[c][s] = (int)signal[c][(int)frameCounter];
                    }
                }
                this.wf.writeFrames(buffer, toWrite);
            }
            this.wf.close();
        }
        else if (type.equals("long")) {
            long[][] buffer = new long[channels][100];
            long frameCounter = 0;
            while (frameCounter < frames)
            {
                long remaining = this.wf.getFramesRemaining();
                int toWrite = (remaining > 100) ? 100 : (int) remaining;
                for (int s=0 ; s<toWrite ; s++, frameCounter++)
                {
                    for (int c=0; c<channels; c++) {
                        buffer[c][s] = (long)signal[c][(int)frameCounter];
                    }
                }
                this.wf.writeFrames(buffer, toWrite);
            }
            this.wf.close();
        }
        else if (type.equals("double")) {
            double[][] buffer = new double[channels][100];
            long frameCounter = 0;
            while (frameCounter < frames)
            {
                long remaining = this.wf.getFramesRemaining();
                int toWrite = (remaining > 100) ? 100 : (int) remaining;
                for (int s=0 ; s<toWrite ; s++, frameCounter++)
                {
                    for (int c=0; c<channels; c++) {
                        buffer[c][s] = signal[c][(int)frameCounter];
                    }
                }
                this.wf.writeFrames(buffer, toWrite);
            }
            this.wf.close();

        }
        else {
            throw new IllegalArgumentException("Type must be int, long or double");
        }
    }
}
