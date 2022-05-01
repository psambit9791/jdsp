/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.io.WAV;
import com.github.psambit9791.jdsp.speech.Silence;
import com.github.psambit9791.wavfile.WavFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;


public class TestSilence {

    @Test
    public void silenceTestStepLess1() throws IOException, WavFileException {
        int[][] silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 0.25); //iter_steps never less than 0.25 seconds
        s1.detectSilence(objRead);
        silences = s1.getSilence(true);
        Assertions.assertArrayEquals(new int[] {0, 774}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3142, 4032}, silences[1]);
        Assertions.assertArrayEquals(new int[] {5516, 6050}, silences[2]);

        Silence s2 = new Silence(500, -20, 0.5); //iter_steps never less than 0.25 seconds
        s2.detectSilence(objRead);
        silences = s2.getSilence(true);

        Assertions.assertArrayEquals(new int[] {0, 772}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3144, 4028}, silences[1]);
        Assertions.assertArrayEquals(new int[] {5520, 6044}, silences[2]);

        Silence s3 = new Silence(500, -20, 1); //iter_steps never less than 0.25 seconds
        s3.detectSilence(objRead);
        silences = s3.getSilence(true);

        Assertions.assertArrayEquals(new int[] {0, 756}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3168, 4020}, silences[1]);
        Assertions.assertArrayEquals(new int[] {5536, 6036}, silences[2]);
    }

    @Test
    public void silenceTestStepMore1() throws IOException, WavFileException {
        int[][] silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 2.5);
        s1.detectSilence(objRead);
        silences = s1.getSilence(true);

        Assertions.assertArrayEquals(new int[] {0, 700}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3200, 3900}, silences[1]);

        Silence s2 = new Silence(500, -20, 5);
        s2.detectSilence(objRead);
        silences = s2.getSilence(true);

        Assertions.assertArrayEquals(new int[] {0, 500}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3200, 3700}, silences[1]);

        Silence s3 = new Silence(500, -20, 10);
        s3.detectSilence(objRead);
        silences = s3.getSilence(true);

        Assertions.assertArrayEquals(new int[] {0, 500}, silences[0]);
        Assertions.assertArrayEquals(new int[] {3200, 3700}, silences[1]);
    }
}
