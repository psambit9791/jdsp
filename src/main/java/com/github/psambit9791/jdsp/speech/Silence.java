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

package com.github.psambit9791.jdsp.speech;

import com.github.psambit9791.jdsp.io.WAV;
import com.github.psambit9791.jdsp.misc.UtilMethods;
import com.github.psambit9791.wavfile.WavFileException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;


/**
 * <h1>Silence Detection</h1>
 * The Silence class accepts a WAV object and detects the regions where silence is detected.
 * The start and stop points of silence can be returned as indices or timestamps (in milliseconds). The  indices of the
 * non-silent regions can also be returned.
 * The audio can also be split multiple audio chunks by splitting at the points of silence.
 * This class reflects functionalities from  <a href="https://github.com/jiaaro/pydub/blob/master/pydub/silence.py">Pydub's silence module</a>.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */

public class Silence {

    private int[][] silences;

    private int min_silence_length;
    private int silence_thresh;
    private double iter_steps;

    private float scaling_factor;
    private int total_length;

    private double[][] audio_segment;

    private Hashtable<String, Long> propsOut;

    private int[][] concatenate(int[] new_ind, int[][] existing) {
        int[][] out = new int[existing.length + 1][2];
        System.arraycopy(existing, 0, out, 0, existing.length);
        out[out.length-1] = new_ind;
        return out;
    }

    private double[] flatten(double[][] signal) {
        double[] flat = new double[signal.length*signal[0].length];
        int idx = 0;
        for (int i=0; i< signal.length; i++) {
            for (int j=0; j<signal[0].length; j++) {
                flat[idx] = signal[i][j];
                idx++;
            }
        }
        return flat;
    }

    private int rms(double[] flattened) {
        double rms_val = 0;
        if (flattened.length == 0) {
            return 0;
        }
        for (int i=0; i<flattened.length; i++) {
            rms_val = rms_val + flattened[i]*flattened[i];
        }
        rms_val = Math.sqrt(rms_val/flattened.length);
        return (int)(rms_val);
    }

    private double get_maximum_possible_amplitude(long sample_width) {
        return Math.pow(2, (sample_width * 8))/2.0;
    }

    /**
     * This constructor initialises the prerequisites required to use Silence.
     * @param min_silence_length The minimum length for a segment of silence to be considered
     * @param silence_threshold_db Upper threshold for the loudness to be considered silent in db
     * @param iter_steps Step size for iterating through the audio
     */
    public Silence(int min_silence_length, int silence_threshold_db, double iter_steps) {
        this.min_silence_length = min_silence_length;
        this.silence_thresh = silence_threshold_db;
        this.iter_steps = iter_steps;
    }

    /**
     * This constructor initialises the prerequisites required to use Silence. Steps is set to 1.
     * @param min_silence_length The minimum length for a segment of silence to be considered
     * @param silence_threshold_db Upper threshold for the loudness to be considered silent in db
     */
    public Silence(int min_silence_length, int silence_threshold_db) {
        this.min_silence_length = min_silence_length;
        this.silence_thresh = silence_threshold_db;
        this.iter_steps = 1;
    }

    /**
     * This constructor initialises the prerequisites required to use Silence. The default settings for silence detection
     * are a length of 1000 ms, an upper threshold of -16db and a step size of 1.
     */
    public Silence() {
        this.min_silence_length = 1000;
        this.silence_thresh = -16;
        this.iter_steps = 1;
    }

    /**
     * Set the silence threshold in case the value needs to be changed after object creation.
     * @param silence_threshold_db Upper threshold for the loudness to be considered silent in db
     */
    public void setSilenceThreshold(int silence_threshold_db) {
        this.silence_thresh = silence_threshold_db;
    }

    /**
     * Set the minimum silence length in case the value needs to be changed after object creation.
     * @param min_silence_length The minimum length for a segment of silence to be considered
     */
    public void setMinimumSilenceLength(int min_silence_length) {
        this.min_silence_length = min_silence_length;
    }

    /**
     * Runs the silence detection algorithm
     * @param audio WAV object which holds the audio to be processed
     * @throws WavFileException Inherited from WAV class
     * @throws IOException Inherited from WAV class
     */
    public void detectSilence(WAV audio) throws WavFileException, IOException {
        this.silences = new int[0][2];
        int[] silence_starts = new int[0];

        this.audio_segment = audio.getData("int");
        this.total_length = this.audio_segment.length;
        this.audio_segment = UtilMethods.transpose(this.audio_segment);
        this.propsOut = audio.getProperties();

        int seglen = audio.getDurationInMilliseconds();
        this.scaling_factor = this.propsOut.get("SampleRate")/1000;
        long sample_width = this.propsOut.get("BytesPerSample");
        int channels = this.propsOut.get("Channels").intValue();
        if (seglen < this.min_silence_length) {
            return;
        }
        double threshold = UtilMethods.decibelToRatio(this.silence_thresh) * this.get_maximum_possible_amplitude(sample_width);
        int last_slice_starts = (int)((seglen - this.min_silence_length) * this.scaling_factor);

        this.min_silence_length = (int)(this.min_silence_length * this.scaling_factor);
        int scaled_iter_steps = (int)(this.iter_steps * this.scaling_factor);

        int[] slice_starts = UtilMethods.arange(0, (last_slice_starts+(int)(this.scaling_factor)), scaled_iter_steps);

        if (UtilMethods.integerToBoolean(last_slice_starts%scaled_iter_steps)) {
            slice_starts = UtilMethods.concatenateArray(slice_starts, new int[]{last_slice_starts});
        }

        for (int i=0; i<slice_starts.length; i=i+scaled_iter_steps) {
            double[][] audio_slice = new double[channels][this.min_silence_length];
            for (int j=0; j<channels; j++) {
                audio_slice[j] = UtilMethods.splitByIndex(this.audio_segment[j], slice_starts[i], slice_starts[i]+this.min_silence_length);
            }
            double[] flattened_audio_slice = this.flatten(audio_slice);
            if (this.rms(flattened_audio_slice) <= threshold) {
                silence_starts = UtilMethods.concatenateArray(silence_starts, new int[]{slice_starts[i]});
            }
        }

        if (silence_starts.length == 0) {
            return;
        }

        int current_start = silence_starts[0];
        int previous_i = silence_starts[0];

        boolean continuous;
        boolean has_gap;
        for (int i=1; i<silence_starts.length; i++) {
            continuous  = (silence_starts[i] == previous_i + scaled_iter_steps);
            has_gap = (silence_starts[i] > (previous_i + this.min_silence_length));

            if (!continuous && has_gap) {
                this.silences = this.concatenate(new int[] {current_start, previous_i+this.min_silence_length}, this.silences);
                current_start = silence_starts[i];
            }
            previous_i = silence_starts[i];
        }
        this.silences = this.concatenate(new int[] {current_start, previous_i+this.min_silence_length}, this.silences);
    }

    /**
     * Return the periods of silence as indices for the input audio.
     * @return int[][] An integer array containing a list of start and stop points representing the silent segments
     */
    public int[][] getSilence() {
        return this.getSilence(false);
    }

    /**
     * Return the periods of silence as indices or timestamps for the input audio.
     * @param milliseconds If True, returns timestamps. Otherwise, returns indices
     * @return int[][] An integer array containing a list of start and stop points representing the silent segments
     */
    public int[][] getSilence(boolean milliseconds) throws ExceptionInInitializerError {
        if (this.silences == null) {
            throw new ExceptionInInitializerError("Execute detectSilence() function before returning result");
        }
        if (milliseconds) {
            int[][] silenceMS = new int[silences.length][2];
            for (int i=0; i<silenceMS.length; i++) {
                silenceMS[i][0] = (int)(this.silences[i][0]/this.scaling_factor);
                silenceMS[i][1] = (int)(this.silences[i][1]/this.scaling_factor);
            }
            return silenceMS;
        }
        else {
            return this.silences;
        }
    }

    /**
     * Return the periods of non-silence as indices for the input audio.
     * @return int[][] An integer array containing a list of start and stop points representing the non-silent segments
     */
    public int[][] getNonSilent() throws ExceptionInInitializerError {
        return this.getNonSilent(false);
    }

    /**
     * Return the periods of non-silence as indices or timestamps for the input audio.
     * @param milliseconds If True, returns timestamps. Otherwise, returns indices
     * @return int[][] An integer array containing a list of start and stop points representing the non-silent segments
     */
    public int[][] getNonSilent(boolean milliseconds) throws ExceptionInInitializerError {
        if (this.silences == null) {
            throw new ExceptionInInitializerError("Execute detectSilence() function before returning result");
        }
        int[][] sil = this.getSilence();
        int[][] non_sil = new int[0][2];

        for (int i=0; i<sil.length+1; i++) {
            int base_start;
            int sil_start;

            if (i == 0) {
                base_start = 0;
                sil_start = sil[i][0];
            } else if (i == sil.length) {
                base_start = sil[i-1][1];
                sil_start = this.total_length - 1;
            }
            else {
                base_start = sil[i-1][1];
                sil_start = sil[i][0];
            }

            if (base_start == sil_start) {
                continue;
            }
            else {
                non_sil = this.concatenate(new int[] {base_start, sil_start}, non_sil);
            }
        }

        if (milliseconds) {
            int[][] nonSilenceMS = new int[non_sil.length][2];
            for (int i=0; i<non_sil.length; i++) {
                nonSilenceMS[i][0] = (int)(non_sil[i][0]/this.scaling_factor);
                nonSilenceMS[i][1] = (int)(non_sil[i][1]/this.scaling_factor);
            }
            return nonSilenceMS;
        }
        else {
            return non_sil;
        }
    }

    /**
     * Splits the input audio file by the non-silent segments and saves each non-silent segment as an individual audio file.
     * For N non-silent segments in the audio, N new audio files are created.
     * @param saveDir The directory where the split audio segments are saved. Files are saved as sil1.wav, sil2.wav ... till silN.wav.
     * @throws IOException Inherited from WAV class
     * @throws WavFileException Inherited from WAV class
     * @throws ExceptionInInitializerError Raised if executed before detectSilence() is executed
     * @throws NullPointerException Raised if the input directory does not exist
     */
    public void splitBySilence(String saveDir) throws IOException, WavFileException, ExceptionInInitializerError, NullPointerException {
        if (this.silences == null) {
            throw new ExceptionInInitializerError("Execute detectSilence() function before returning result");
        }
        File directory = new File(saveDir);
        if (! directory.exists()){
            throw new NullPointerException("Provided directory to save files not found.");
        }
        double[][] tempData = new double[2][0];

        int[][] non_sil = this.getNonSilent();
        for (int i=0; i<non_sil.length; i++) {
            String outputFileName = saveDir + "sil" + (i + 1) + ".wav";
            for (int j=0; j<this.audio_segment.length; j++) {
                tempData[j] = UtilMethods.splitByIndex(this.audio_segment[j], non_sil[i][0], non_sil[i][1]);
            }
            WAV objWrite = new WAV();
            objWrite.putData(UtilMethods.transpose(tempData), propsOut.get("SampleRate"), this.propsOut.get("ValidBits").intValue(),"int", outputFileName);
        }
    }
}
