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

import java.io.File;
import java.io.IOException;


public class TestSilence {

    @Test
    @Order(1)
    public void createTestOutputDirectory() {
        String dirName = "./test_outputs/";
        File directory = new File(dirName);
        if (! directory.exists()){
            directory.mkdir();
        }
        String subdirName = "./test_outputs/non_silences/";
        File subdirectory = new File(subdirName);
        if (! subdirectory.exists()){
            subdirectory.mkdir();
        }
    }

    @Test
    public void silenceTestMilliSecStepLess1() throws IOException, WavFileException {
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
    public void silenceTestIndexStepLess1() throws IOException, WavFileException {
        int[][] silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 0.25); //iter_steps never less than 0.25 seconds
        s1.detectSilence(objRead);
        silences = s1.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 24768}, silences[0]);
        Assertions.assertArrayEquals(new int[] {100544, 129024}, silences[1]);
        Assertions.assertArrayEquals(new int[] {176512, 193600}, silences[2]);

        Silence s2 = new Silence(500, -20, 0.5); //iter_steps never less than 0.25 seconds
        s2.detectSilence(objRead);
        silences = s2.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 24704}, silences[0]);
        Assertions.assertArrayEquals(new int[] {100608, 128896}, silences[1]);
        Assertions.assertArrayEquals(new int[] {176640, 193408}, silences[2]);

        Silence s3 = new Silence(500, -20, 1); //iter_steps never less than 0.25 seconds
        s3.detectSilence(objRead);
        silences = s3.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 24192}, silences[0]);
        Assertions.assertArrayEquals(new int[] {101376, 128640}, silences[1]);
        Assertions.assertArrayEquals(new int[] {177152, 193152}, silences[2]);
    }


    @Test
    public void nonSilenceTestMilliSecStepLess1() throws IOException, WavFileException {
        int[][] non_silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 0.25); //iter_steps never less than 0.25 seconds
        s1.detectSilence(objRead);
        non_silences = s1.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {774, 3142}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {4032, 5516}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {6050, 10009}, non_silences[2]);

        Silence s2 = new Silence(500, -20, 0.5); //iter_steps never less than 0.25 seconds
        s2.detectSilence(objRead);
        non_silences = s2.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {772, 3144}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {4028, 5520}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {6044, 10009}, non_silences[2]);

        Silence s3 = new Silence(500, -20, 1); //iter_steps never less than 0.25 seconds
        s3.detectSilence(objRead);
        non_silences = s3.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {756, 3168}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {4020, 5536}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {6036, 10009}, non_silences[2]);
    }

    @Test
    public void nonSilenceTestIndexStepLess1() throws IOException, WavFileException {
        int[][] non_silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 0.25); //iter_steps never less than 0.25 seconds
        s1.detectSilence(objRead);
        non_silences = s1.getNonSilent(false);
//        for (int i=0; i<non_silences.length; i++) {
//            System.out.println(Arrays.toString(non_silences[i]));
//        }
//        System.out.println();
        Assertions.assertArrayEquals(new int[] {24768, 100544}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {129024, 176512}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {193600, 320302}, non_silences[2]);

        Silence s2 = new Silence(500, -20, 0.5); //iter_steps never less than 0.25 seconds
        s2.detectSilence(objRead);
        non_silences = s2.getNonSilent();
        Assertions.assertArrayEquals(new int[] {24704, 100608}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {128896, 176640}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {193408, 320302}, non_silences[2]);

        Silence s3 = new Silence(500, -20, 1); //iter_steps never less than 0.25 seconds
        s3.detectSilence(objRead);
        non_silences = s3.getNonSilent();
        Assertions.assertArrayEquals(new int[] {24192, 101376}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {128640, 177152}, non_silences[1]);
        Assertions.assertArrayEquals(new int[] {193152, 320302}, non_silences[2]);
    }

    @Test
    public void silenceTestMilliSecStepMore1() throws IOException, WavFileException {
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

    @Test
    public void nonSilenceTestMilliSecStepMore1() throws IOException, WavFileException {
        int[][] non_silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 2.5);
        s1.detectSilence(objRead);
        non_silences = s1.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {700, 3200}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {3900, 10009}, non_silences[1]);

        Silence s2 = new Silence(500, -20, 5);
        s2.detectSilence(objRead);
        non_silences = s2.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {500, 3200}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {3700, 10009}, non_silences[1]);

        Silence s3 = new Silence(500, -20, 10);
        s3.detectSilence(objRead);
        non_silences = s3.getNonSilent(true);
        Assertions.assertArrayEquals(new int[] {500, 3200}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {3700, 10009}, non_silences[1]);
    }

    @Test
    public void silenceTestIndexStepMore1() throws IOException, WavFileException {
        int[][] silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 2.5);
        s1.detectSilence(objRead);
        silences = s1.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 22400}, silences[0]);
        Assertions.assertArrayEquals(new int[] {102400, 124800}, silences[1]);

        Silence s2 = new Silence(500, -20, 5);
        s2.detectSilence(objRead);
        silences = s2.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 16000}, silences[0]);
        Assertions.assertArrayEquals(new int[] {102400, 118400}, silences[1]);

        Silence s3 = new Silence(500, -20, 10);
        s3.detectSilence(objRead);
        silences = s3.getSilence();
        Assertions.assertArrayEquals(new int[] {0, 16000}, silences[0]);
        Assertions.assertArrayEquals(new int[] {102400, 118400}, silences[1]);
    }

    @Test
    public void nonSilenceTestIndexStepMore1() throws IOException, WavFileException {
        int[][] non_silences;
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        Silence s1 = new Silence(500, -20, 2.5);
        s1.detectSilence(objRead);
        non_silences = s1.getNonSilent();
        Assertions.assertArrayEquals(new int[] {22400, 102400}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {124800, 320302}, non_silences[1]);

        Silence s2 = new Silence(500, -20, 5);
        s2.detectSilence(objRead);
        non_silences = s2.getNonSilent();
        Assertions.assertArrayEquals(new int[] {16000, 102400}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {118400, 320302}, non_silences[1]);

        Silence s3 = new Silence(500, -20, 10);
        s3.detectSilence(objRead);
        non_silences = s3.getNonSilent();
        Assertions.assertArrayEquals(new int[] {16000, 102400}, non_silences[0]);
        Assertions.assertArrayEquals(new int[] {118400, 320302}, non_silences[1]);
    }

    @Test
    @Order(3)
    public void testSplitBySilence() throws IOException, WavFileException {
        WAV objRead = new WAV();
        objRead.readWAV("test_inputs/silence.wav");

        String saveDirectory = "./test_outputs/non_silences/";

        Silence s1 = new Silence(500, -20, 2.5);
        s1.detectSilence(objRead);
        s1.splitBySilence(saveDirectory);

        boolean fileExists;

        fileExists = new File("./"+saveDirectory + "sil1.wav").exists();
        Assertions.assertTrue(fileExists);
        fileExists = new File("./"+saveDirectory + "sil2.wav").exists();
        Assertions.assertTrue(fileExists);
    }
}