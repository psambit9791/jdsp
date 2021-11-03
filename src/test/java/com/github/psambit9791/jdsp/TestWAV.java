/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.io.WAV;
import com.github.psambit9791.wavfile.WavFileException;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class TestWAV {

    @Test
    @Order(1)
    public void createTestOutputDirectory() {
        String dirName = "./test_outputs/";
        File directory = new File(dirName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    @Test
    public void wavTestTemplateInt() throws WavFileException, IOException {
        WAV objRead = new WAV();
        objRead.readTemplate();
        double[][] signal = objRead.getData("int");

        Hashtable<String, Long> propsOut = objRead.getProperties();

        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/sampleInt.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "int", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("int");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i]);
        }
    }

    @Test
    public void wavTestTemplateLong() throws WavFileException, IOException {
        WAV objRead = new WAV();
        objRead.readTemplate();
        double[][] signal = objRead.getData("long");

        Hashtable<String, Long> propsOut = objRead.getProperties();
        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/sampleLong.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "long", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("long");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i]);
        }
    }

    @Test
    public void wavTestTemplateDouble() throws WavFileException, IOException {
        WAV objRead = new WAV();
        objRead.readTemplate();
        double[][] signal = objRead.getData("double");

        Hashtable<String, Long> propsOut = objRead.getProperties();

        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/sampleDouble.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "double", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("double");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i], 0.0001);
        }
    }

    @Test
    public void wavTestCustomInt() throws WavFileException, IOException {
        WAV objRead1 = new WAV();
        String inputFilename = "test_inputs/music.wav";
        objRead1.readWAV(inputFilename);
        Hashtable<String, Long> propsOut = objRead1.getProperties();
        double[][] signal = objRead1.getData("int");

        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/musicInt.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "int", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("int");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i]);
        }
    }

    @Test
    public void wavTestCustomLong() throws WavFileException, IOException {
        WAV objRead1 = new WAV();
        String inputFilename = "test_inputs/music.wav";
        objRead1.readWAV(inputFilename);
        Hashtable<String, Long> propsOut = objRead1.getProperties();
        double[][] signal = objRead1.getData("long");

        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/musicLong.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "long", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("long");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i]);
        }
    }

    @Test
    public void wavTestCustomDouble() throws WavFileException, IOException {
        WAV objRead1 = new WAV();
        String inputFilename = "test_inputs/music.wav";
        objRead1.readWAV(inputFilename);
        Hashtable<String, Long> propsOut = objRead1.getProperties();
        double[][] signal = objRead1.getData("double");

        WAV objWrite = new WAV();
        String outputFileName = "test_outputs/musicDouble.wav";
        objWrite.putData(signal, propsOut.get("SampleRate"), "double", outputFileName);
        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);

        WAV objRead2 = new WAV();
        objRead2.readWAV(outputFileName);
        double[][] signalWritten = objRead2.getData("double");
        double[][] signalT = UtilMethods.transpose(signal);
        double[][] signalWrittenT = UtilMethods.transpose(signalWritten);
        for (int i=0; i<signalT.length; i++) {
            Assertions.assertArrayEquals(signalT[i], signalWrittenT[i], 0.0001);
        }
    }
}
