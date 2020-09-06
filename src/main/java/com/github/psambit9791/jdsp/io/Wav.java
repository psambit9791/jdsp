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

import com.github.psambit9791.jdsp.io.WavFile;
import com.github.psambit9791.jdsp.io.WavFileException;
import com.github.psambit9791.jdsp.misc.UtilMethods;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

public class Wav {
    private WavFile wf;
    public Hashtable<String, Long> props;
    private double[][] data;

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

    private int[][] toInt(double[][] a) {
        int[][] out = new int[a.length][a[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j=0; j<out[0].length; j++) {
                out[i][j] = (int)a[i][j];
            }
        }
        return out;
    }

    private long[][] toLong(double[][] a) {
        long[][] out = new long[a.length][a[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j=0; j<out[0].length; j++) {
                out[i][j] = (long)a[i][j];
            }
        }
        return out;
    }

    public void putData(double[][] signal, String filename, String type) throws IOException, WavFileException, IllegalArgumentException {
        signal = UtilMethods.transpose(signal);
        if (type.equals("int")) {
            int[][] sig = this.toInt(signal);
        }
        else if (type.equals("long")) {
            long[][] sig = this.toLong(signal);
        }
        else if (type.equals("double")) {
            double[][] sig = signal;

        }
        else {
            throw new IllegalArgumentException("Type must be int, long or double");
        }
    }
}
