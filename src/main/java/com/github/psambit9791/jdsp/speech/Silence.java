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

import java.io.IOException;
import java.util.Hashtable;

public class Silence {

    private int[][] silences;

    private int min_silence_length;
    private int silence_thresh;
    private double iter_steps;

    private float scaling_factor;

    private void concatenate(int[] new_ind) {
        int[][] out = new int[this.silences.length + 1][2];
        System.arraycopy(this.silences, 0, out, 0, this.silences.length);
        out[out.length-1] = new_ind;
        this.silences = out;
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

    public Silence(int min_silence_length, int silence_threshold_db, double iter_steps) {
        this.min_silence_length = min_silence_length;
        this.silence_thresh = silence_threshold_db;
        this.iter_steps = iter_steps;
    }

    public Silence(int min_silence_length, int silence_threshold_db) {
        this.min_silence_length = min_silence_length;
        this.silence_thresh = silence_threshold_db;
        this.iter_steps = 1;
    }

    public Silence() {
        this.min_silence_length = 1000;
        this.silence_thresh = -16;
        this.iter_steps = 1;
    }

    public void setSilenceThreshold(int silence_threshold_db) {
        this.silence_thresh = silence_threshold_db;
    }
    public void setMinimumSilenceLength(int min_silence_length) {
        this.min_silence_length = min_silence_length;
    }

    public void detectSilence(WAV audio) throws WavFileException, IOException {
        this.silences = new int[0][2];
        int[] silence_starts = new int[0];

        double[][] audio_segment = audio.getData("int");
        audio_segment = UtilMethods.transpose(audio_segment);
        Hashtable<String, Long> propsOut = audio.getProperties();

        int seglen = audio.getDurationInMilliseconds();
        this.scaling_factor = propsOut.get("SampleRate")/1000;
        long sample_width = propsOut.get("BytesPerSample");
        int channels = propsOut.get("Channels").intValue();
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
                audio_slice[j] = UtilMethods.splitByIndex(audio_segment[j], slice_starts[i], slice_starts[i]+this.min_silence_length);
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
                this.concatenate(new int[] {current_start, previous_i+this.min_silence_length});
                current_start = silence_starts[i];
            }
            previous_i = silence_starts[i];
        }
        this.concatenate(new int[] {current_start, previous_i+this.min_silence_length});
    }

    public int[][] getSilence(boolean milliseconds) {
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
}
