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

import com.github.psambit9791.jdsp.misc.Plotting;
import com.github.psambit9791.jdsp.signal.peaks.FindPeak;
import com.github.psambit9791.jdsp.signal.peaks.Peak;
import com.github.psambit9791.jdsp.signal.peaks.Spike;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestFindPeak {

    private final double[] signal = {0.228, 0.423, 0.304, 0.16, 0.241, 0.265, 0.168, 0.038, -0.017, 0.54, 0.234, -0.069,
            -0.094, -0.086, -0.04, 0.007, 0.056, 0.129, 0.112, 0.003, -0.008, 0.051, 0.013, -0.129, -0.221, 0.214,
            0.345, -0.22, -0.289, -0.293};

    private final double[] highResSignal = {0.228, 0.257, 0.287, 0.318, 0.349, 0.378, 0.407, 0.436, 0.434, 0.43, 0.423, 0.417, 0.413, 0.406, 0.399, 0.389, 0.378, 0.362, 0.345, 0.326, 0.304, 0.276, 0.242, 0.215, 0.197, 0.186, 0.18, 0.174, 0.168, 0.163, 0.16, 0.158, 0.158, 0.159, 0.161, 0.169, 0.186, 0.209, 0.226, 0.236, 0.241, 0.246, 0.25, 0.254, 0.256, 0.257, 0.258, 0.259, 0.263, 0.265, 0.265, 0.261, 0.256, 0.251, 0.246, 0.239, 0.226, 0.211, 0.195, 0.18, 0.168, 0.155, 0.141, 0.125, 0.108, 0.093, 0.082, 0.071, 0.06, 0.048, 0.038, 0.032, 0.027, 0.023, 0.014, -0.0, -0.016, -0.028, -0.034, -0.03, -0.017, 0.005, 0.045, 0.103, 0.182, 0.273, 0.362, 0.432, 0.482,0.517, 0.54, 0.554, 0.561, 0.564, 0.556, 0.54, 0.515, 0.473, 0.413, 0.33, 0.234, 0.141, 0.066, 0.012, -0.024, -0.046, -0.057, -0.062, -0.066, -0.067, -0.069, -0.075, -0.08, -0.086, -0.087, -0.087, -0.087, -0.091, -0.094, -0.096, -0.094, -0.091, -0.089, -0.09, -0.091, -0.092, -0.09, -0.088, -0.086, -0.086, -0.086, -0.086, -0.081, -0.076, -0.071, -0.068, -0.065, -0.06, -0.054, -0.047, -0.04, -0.035, -0.032, -0.028, -0.023, -0.017, -0.01, -0.006, -0.004, 0.0, 0.007, 0.012, 0.016, 0.019, 0.02, 0.023, 0.029, 0.036, 0.043, 0.05, 0.056, 0.061, 0.069, 0.079, 0.089, 0.094, 0.099, 0.105, 0.114, 0.122, 0.129, 0.134, 0.137,0.14, 0.142, 0.144, 0.144, 0.139, 0.13, 0.119, 0.112, 0.105, 0.097, 0.086, 0.073, 0.061, 0.049, 0.037, 0.026, 0.015, 0.003, -0.007, -0.013, -0.015, -0.016, -0.019, -0.021, -0.021, -0.018, -0.012, -0.008, -0.004, -0.002, 0.002, 0.01, 0.02, 0.029, 0.034, 0.04, 0.045, 0.051, 0.056, 0.058, 0.059, 0.056, 0.053, 0.048, 0.045, 0.038, 0.027, 0.013, -0.001, -0.011, -0.023, -0.037, -0.055, -0.073, -0.09, -0.105, -0.118, -0.129, -0.138, -0.147, -0.155, -0.161, -0.166, -0.172, -0.184, -0.198, -0.212, -0.221, -0.227, -0.23, -0.226, -0.209, -0.175, -0.12, -0.046, 0.042, 0.133, 0.214, 0.278, 0.326, 0.358, 0.377, 0.388, 0.395, 0.399, 0.395, 0.378, 0.345, 0.29, 0.216, 0.13, 0.036, -0.047, -0.113, -0.162, -0.194, -0.211, -0.22, -0.227, -0.233, -0.239, -0.248, -0.258, -0.267, -0.274, -0.279, -0.283, -0.289, -0.292, -0.291, -0.285, -0.278, -0.276, -0.277, -0.282, -0.288, -0.291, -0.293, -0.295, -0.299, -0.304, -0.307, -0.309, -0.312, -0.317, -0.324, -0.332, -0.337, -0.338, -0.333, -0.326, -0.319, -0.315, -0.309, -0.303, -0.299, -0.296, -0.292, -0.284, -0.277, -0.27, -0.264, -0.258, -0.251, -0.245, -0.238, -0.229, -0.217, -0.208, -0.199, -0.191, -0.181, -0.17, -0.162, -0.156, -0.151, -0.143, -0.135, -0.128, -0.124, -0.123, -0.125, -0.13, -0.133, -0.137, -0.139, -0.143, -0.149, -0.155, -0.159, -0.165, -0.173, -0.182, -0.193, -0.204, -0.216, -0.226, -0.237, -0.251, -0.266, -0.284, -0.299, -0.311, -0.324, -0.338, -0.35, -0.359, -0.365, -0.37, -0.375, -0.378, -0.383, -0.386, -0.384, -0.379, -0.37, -0.363, -0.358, -0.353, -0.344, -0.335, -0.33, -0.333, -0.339, -0.343, -0.347, -0.35, -0.358, -0.37, -0.385, -0.404, -0.422, -0.437, -0.454, -0.476, -0.497, -0.513, -0.525, -0.535, -0.546, -0.559, -0.572, -0.582, -0.594, -0.609, -0.626, -0.643, -0.653, -0.647, -0.623, -0.583, -0.529, -0.459, -0.372, -0.277, -0.191, -0.126, -0.083, -0.056, -0.039, -0.024, -0.01, -0.002, -0.01, -0.036, -0.079, -0.138, -0.213, -0.303, -0.399, -0.489, -0.559, -0.605, -0.63, -0.64, -0.647, -0.655, -0.664, -0.672, -0.678, -0.682, -0.684, -0.688, -0.695, -0.702, -0.704, -0.704, -0.704, -0.704, -0.705, -0.705, -0.702, -0.698, -0.695, -0.694, -0.693, -0.691, -0.685, -0.678, -0.673, -0.671, -0.669, -0.667, -0.661, -0.652, -0.644, -0.639, -0.634, -0.627, -0.616, -0.605, -0.598, -0.594, -0.59, -0.583, -0.572, -0.56, -0.549, -0.54, -0.536, -0.531, -0.523, -0.515, -0.507, -0.5, -0.497, -0.494, -0.488, -0.48, -0.469, -0.459, -0.456, -0.454, -0.449, -0.44, -0.429, -0.422, -0.417, -0.41, -0.403, -0.394, -0.386, -0.382, -0.382, -0.385, -0.389, -0.388, -0.385, -0.388, -0.399, -0.415, -0.429, -0.441, -0.454, -0.469, -0.486, -0.5, -0.51, -0.519, -0.529, -0.541, -0.554, -0.567, -0.573, -0.574, -0.572, -0.57, -0.57, -0.568, -0.562, -0.554, -0.546, -0.54, -0.534, -0.526, -0.516, -0.507, -0.501, -0.5, -0.501, -0.501, -0.499, -0.498, -0.499, -0.507, -0.518, -0.53, -0.541, -0.551, -0.564, -0.579, -0.595, -0.608, -0.617, -0.626, -0.637, -0.652, -0.667, -0.679, -0.685, -0.688, -0.691, -0.694, -0.697, -0.702, -0.709, -0.718, -0.727, -0.735, -0.741, -0.741, -0.73, -0.704, -0.664, -0.607, -0.53, -0.44, -0.352, -0.281, -0.232, -0.203, -0.187, -0.183, -0.183, -0.184, -0.186, -0.194, -0.217, -0.258, -0.316, -0.394, -0.486, -0.578, -0.652, -0.702, -0.73, -0.741, -0.74, -0.735, -0.731, -0.731, -0.73, -0.729, -0.729, -0.731, -0.734, -0.736, -0.735, -0.733, -0.731, -0.729, -0.728, -0.726, -0.723, -0.718, -0.715, -0.716, -0.718, -0.719, -0.715, -0.709, -0.705, -0.703, -0.702, -0.699, -0.691, -0.684, -0.678, -0.675, -0.674, -0.67, -0.661, -0.649, -0.637, -0.628, -0.622, -0.616, -0.608, -0.599, -0.591, -0.586, -0.582, -0.577, -0.568, -0.559, -0.551, -0.545, -0.542, -0.54, -0.535, -0.525, -0.512, -0.503, -0.497, -0.493, -0.486, -0.477, -0.468, -0.461, -0.455, -0.448, -0.442, -0.434, -0.427, -0.422, -0.42, -0.419, -0.416, -0.413, -0.41, -0.408, -0.408, -0.409, -0.411, -0.414, -0.419, -0.424, -0.432, -0.44, -0.449, -0.457, -0.467, -0.48, -0.493, -0.505, -0.515, -0.525, -0.536, -0.548, -0.556, -0.561, -0.562, -0.559, -0.554, -0.549, -0.546, -0.545,-0.541, -0.536, -0.527, -0.519, -0.508, -0.498, -0.487, -0.479, -0.474, -0.472, -0.473, -0.475, -0.474, -0.472, -0.47, -0.469, -0.47, -0.473, -0.479, -0.487, -0.496, -0.508, -0.52, -0.532, -0.541, -0.548, -0.556, -0.569, -0.583, -0.593, -0.6, -0.605, -0.611, -0.618, -0.625, -0.63, -0.631, -0.629, -0.627, -0.628, -0.636, -0.647, -0.654, -0.656, -0.654, -0.646, -0.626, -0.593, -0.544, -0.479, -0.401, -0.315, -0.232, -0.162, -0.109, -0.073, -0.054, -0.052, -0.06,-0.075, -0.095, -0.124, -0.165, -0.219, -0.289, -0.373, -0.466, -0.556, -0.635, -0.697, -0.738, -0.76, -0.765, -0.764, -0.761, -0.76, -0.762, -0.764, -0.763, -0.76, -0.758, -0.757, -0.76, -0.763, -0.761, -0.757, -0.749, -0.742, -0.735, -0.728, -0.721, -0.715, -0.711, -0.709, -0.709, -0.709, -0.705, -0.697, -0.689, -0.684, -0.681, -0.679, -0.675, -0.668, -0.659, -0.651, -0.641, -0.632, -0.623, -0.613, -0.604, -0.595, -0.587, -0.577, -0.566, -0.556, -0.547, -0.542, -0.538, -0.533, -0.527, -0.521, -0.513, -0.508, -0.502, -0.497, -0.492, -0.486, -0.48, -0.474, -0.47, -0.465, -0.459, -0.453, -0.449, -0.447, -0.445, -0.442, -0.436, -0.429, -0.423, -0.419, -0.416, -0.416, -0.415, -0.413, -0.41, -0.41, -0.408, -0.406, -0.404, -0.403, -0.406, -0.415, -0.428, -0.438, -0.447, -0.455, -0.463, -0.472, -0.483, -0.495, -0.506, -0.517, -0.529, -0.542, -0.556, -0.568, -0.575, -0.579, -0.584, -0.59, -0.596, -0.601, -0.603, -0.602, -0.602, -0.601, -0.601, -0.601, -0.596, -0.587, -0.578, -0.568, -0.56, -0.553, -0.546, -0.538, -0.53, -0.525, -0.525, -0.524, -0.522, -0.519, -0.517, -0.52, -0.527, -0.535, -0.544, -0.55, -0.558, -0.568, -0.583, -0.596, -0.607, -0.613, -0.618, -0.626, -0.638, -0.651, -0.661, -0.667, -0.674, -0.681, -0.689, -0.695, -0.698, -0.699, -0.7, -0.705, -0.715, -0.726, -0.736, -0.737, -0.732, -0.72, -0.701, -0.669, -0.618, -0.547, -0.46, -0.362, -0.258, -0.157, -0.068, 0.006, 0.062, 0.098, 0.109, 0.104, 0.09, 0.068, 0.035, -0.015, -0.086, -0.172, -0.269, -0.373, -0.474, -0.562, -0.631, -0.68, -0.708, -0.717, -0.716, -0.714, -0.714, -0.716, -0.717, -0.718, -0.72, -0.722, -0.723, -0.724, -0.725, -0.725, -0.724, -0.724, -0.727, -0.728, -0.729, -0.728, -0.726, -0.725, -0.724, -0.722, -0.723, -0.724, -0.723, -0.722, -0.721, -0.72, -0.717, -0.71, -0.703, -0.698, -0.695, -0.691, -0.684, -0.679, -0.672, -0.666, -0.66, -0.654, -0.647, -0.639, -0.631, -0.625, -0.62, -0.614, -0.606, -0.597, -0.589, -0.547, -0.505, -0.465, -0.422, -0.38, -0.337, -0.297};

    private final double[] simpleSignal ={0.   ,  0.193,  0.381,  0.559,  0.723,  0.869,  0.994,  1.094, 1.169,  1.218,  1.24 ,  1.237,  1.21 ,  1.163,  1.098,  1.02 ,
            0.932,  0.838,  0.744,  0.653,  0.57 ,  0.498,  0.439,  0.396, 0.37 ,  0.362,  0.371,  0.396,  0.435,  0.485,  0.543,  0.605,
            0.668,  0.726,  0.776,  0.814,  0.836,  0.839,  0.82 ,  0.779, 0.713,  0.624,  0.512,  0.379,  0.227,  0.061, -0.117, -0.3  ,
            -0.486, -0.668, -0.843, -1.004, -1.148, -1.272, -1.371, -1.444, -1.49 , -1.507, -1.497, -1.459, -1.398, -1.314, -1.213, -1.098,
            -0.973, -0.843, -0.713, -0.587, -0.469, -0.363, -0.271, -0.196, -0.14 , -0.103, -0.084, -0.083, -0.097, -0.124, -0.161, -0.203,
            -0.248, -0.29 , -0.327, -0.353, -0.365, -0.361, -0.338, -0.295, -0.23 , -0.144, -0.039,  0.085,  0.224,  0.376,  0.535,  0.698,
            0.86 ,  1.016,  1.162,  1.292,  1.404,  1.493,  1.556,  1.592, 1.599,  1.577,  1.528,  1.451,  1.351,  1.229,  1.091,  0.939,
            0.779,  0.616,  0.455,  0.299,  0.153,  0.021, -0.094, -0.19 , -0.265, -0.319, -0.352, -0.365, -0.361, -0.341, -0.309, -0.27 ,
            -0.226, -0.181, -0.141, -0.109, -0.088, -0.081, -0.091, -0.119, -0.166, -0.232, -0.315, -0.414, -0.527, -0.649, -0.778, -0.909,
            -1.036, -1.157, -1.266, -1.358, -1.431, -1.481, -1.505, -1.502, -1.471, -1.411, -1.325, -1.213, -1.079, -0.925, -0.757, -0.578,
            -0.393, -0.208, -0.027,  0.146,  0.305,  0.448,  0.571,  0.672, 0.749,  0.802,  0.832,  0.84 ,  0.827,  0.797,  0.753,  0.698,
            0.637,  0.574,  0.513,  0.458,  0.413,  0.381,  0.364,  0.364, 0.381,  0.415,  0.466,  0.532,  0.611,  0.698,  0.791,  0.885,
            0.976,  1.06 ,  1.133,  1.189,  1.226,  1.242,  1.232,  1.197, 1.135,  1.047,  0.934,  0.799,  0.643,  0.472,  0.288,  0.097,
            -0.097, -0.288, -0.472, -0.643, -0.799, -0.934, -1.047, -1.135, -1.197, -1.232, -1.242, -1.226, -1.189, -1.133, -1.06 , -0.976,
            -0.885, -0.791, -0.698, -0.611, -0.532, -0.466, -0.415, -0.381, -0.364, -0.364, -0.381, -0.413, -0.458, -0.513, -0.574, -0.637,
            -0.698, -0.753, -0.797, -0.827, -0.84 , -0.832, -0.802, -0.749, -0.672, -0.571};
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
    public void maximaTest() {
        int[] result = {1, 5, 9, 17, 21, 26};

        FindPeak fp = new FindPeak(this.signal);
        int[] out = fp.detectRelativeMaxima();

        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void minimaTest() {
        int[] result = {3, 8, 12, 20, 24};

        FindPeak fp = new FindPeak(this.signal);
        int[] out = fp.detectRelativeMinima();

        Assertions.assertArrayEquals(result, out);
    }

    @Test
    public void peakFilterIllegalArgsExceptionTest() throws IOException {
        int[] resultPeaks = {10, 37, 75, 104, 133, 212};
        String expectedMessage = "Peaks in argument does not exist in the original peak list";

        // Detection Tests
        FindPeak fp = new FindPeak(this.simpleSignal);
        Peak out = fp.detectPeaks();
        out.getPeakSharpness();
        out.getWidthData();
        out.getProminenceData();

        // Detection Test for Plateau Size
        Exception exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterByPlateauSize(resultPeaks, 0.0, 1.0);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

        // Detection Test for Height
        exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterByHeight(resultPeaks, 0.0, 1.0);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

        // Detection Test for Prominence
        exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterByProminence(resultPeaks, 0.0, 1.0);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

        // Detection Test for Width
        exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterByWidth(resultPeaks, 0.0, 1.0);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

        // Detection Test for Distance
        exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterByPeakDistance(resultPeaks, 1);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

        // Detection Test for Sharpness
        exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            out.filterBySharpness(resultPeaks, 0.0, 1.0);
        });
        Assertions.assertTrue(exc.getMessage().contains(expectedMessage));

    }

        @Test
    public void peakDetectTest() throws IOException {
        int[] resultPeaks = {10,  37,  75, 104, 133, 171, 197, 232};

        // Detection Tests
        FindPeak fp = new FindPeak(this.simpleSignal);
        Peak out = fp.detectPeaks();
        Assertions.assertTrue(out instanceof Peak);
        Assertions.assertArrayEquals(resultPeaks, out.getPeaks());

        // Height Test
        double[] resultHeight = {01.24 ,  0.839, -0.083,  1.599, -0.081,  0.84 ,  1.242, -0.364};
        double[] outHeight = out.getHeights();
        Assertions.assertArrayEquals(resultHeight, outHeight, 0.001);


        // Height Filtering Test
        int[] resultFilteredPeaks1 = {10, 37, 171, 197};
        int[] resultFilteredPeaks2 = {10, 37, 104, 171, 197};
        int[] resultFilteredPeaks3 = {75, 133, 232};
        int[] filteredPeaks1 = out.filterByHeight(0.5, 1.5);
        int[] filteredPeaks2 = out.filterByHeight(0.0, null);
        int[] filteredPeaks3 = out.filterByHeight(null, 0.0);
        Assertions.assertArrayEquals(resultFilteredPeaks1, filteredPeaks1);
        Assertions.assertArrayEquals(resultFilteredPeaks2, filteredPeaks2);
        Assertions.assertArrayEquals(resultFilteredPeaks3, filteredPeaks3);

        // Plateau Size Test
        int[] resultPlateau = {1, 1, 1, 1, 1, 1, 1, 2};
        int[] outPlateau = out.getPlateauSize();
        Assertions.assertArrayEquals(resultPlateau, outPlateau);

        // Plateau Size Filtering Test
        int[] resultFilteredPS1 = {10,  37,  75, 104, 133, 171, 197};
        int[] resultFilteredPS2 = {232};
        int[] resultFilteredPS3 = {10,  37,  75, 104, 133, 171, 197};
        int[] outFilteredPS1 = out.filterByPlateauSize(0.0, 1.0);
        int[] outFilteredPS2 = out.filterByPlateauSize(2.0, null);
        int[] outFilteredPS3 = out.filterByPlateauSize(null, 1.0);
        Assertions.assertArrayEquals(resultFilteredPS1, outFilteredPS1);
        Assertions.assertArrayEquals(resultFilteredPS2, outFilteredPS2);
        Assertions.assertArrayEquals(resultFilteredPS3, outFilteredPS3);

        // Distance Test
        int[] resultDistance = {27, 38, 29, 29, 38, 26, 35};
        int[] outDistance = out.getPeakDistance();
        Assertions.assertArrayEquals(resultDistance, outDistance);

        // Distance Filtering Test
        int[] resultFilteredDistance1 = {10, 104, 197, 232};
        int[] outFilteredDistance1 = out.filterByPeakDistance(30);
        Assertions.assertArrayEquals(resultFilteredDistance1, outFilteredDistance1);

        // Sharpness Test
        double[][] resultSharpness = {{.022, 0.003, 0.001, 0.007, 0.007, 0.008, 0.016, 0.017},
                {0.003, 0.019, 0.014, 0.022, 0.01 , 0.013, 0.01 , 0}};
        double[][] outSharpness = out.getPeakSharpness();
        for (int i=0; i<resultSharpness.length; i++) {
            Assertions.assertArrayEquals(resultSharpness[i], outSharpness[i], 0.001);
        }

        // Sharpness Filtering Test
        int[] resultFilteredSharp1 = {37,  75, 133, 171, 197};
        int[] resultFilteredSharp2 = {104, 133, 171, 197};
        int[] resultFilteredSharp3 = {10,  37,  75, 104, 133, 171, 197, 232};
        int[] filteredSharp1 = out.filterBySharpness(0.001, 0.02);
        int[] filteredSharp2 = out.filterBySharpness(0.005, null);
        int[] filteredSharp3 = out.filterBySharpness(null, 0.05);
        Assertions.assertArrayEquals(resultFilteredSharp1, filteredSharp1);
        Assertions.assertArrayEquals(resultFilteredSharp2, filteredSharp2);
        Assertions.assertArrayEquals(resultFilteredSharp3, filteredSharp3);


        // Prominence Test
        double[] resultProminence = {1.24 , 0.477, 0.282, 3.104, 0.284, 0.476, 2.484, 0.476};
        double[] outProminence = out.getProminence();
        Assertions.assertArrayEquals(resultProminence, outProminence, 0.001);

        double[][] outPromData = out.getProminenceData();
        double[] resultLeftBase = {0,  25,  57,  57, 123, 150, 150, 218};
        double[] resultRightBase = {57,  57,  84, 150, 150, 182, 218, 244};
        Assertions.assertArrayEquals(resultProminence, outPromData[0], 0.001);
        Assertions.assertArrayEquals(resultLeftBase, outPromData[1], 0.001);
        Assertions.assertArrayEquals(resultRightBase, outPromData[2], 0.001);

        // Prominence Filtering Test
        int[] resultFilteredProm1 = {10,  37, 171, 232};
        int[] resultFilteredProm2 = {10, 104, 197};
        int[] resultFilteredProm3 = {37,  75, 133, 171, 232};
        int[] filteredProm1 = out.filterByProminence(0.4,  1.5);
        int[] filteredProm2 = out.filterByProminence(1.0, null);
        int[] filteredProm3 = out.filterByProminence(null, 1.0);
        Assertions.assertArrayEquals(resultFilteredProm1, filteredProm1);
        Assertions.assertArrayEquals(resultFilteredProm2, filteredProm2);
        Assertions.assertArrayEquals(resultFilteredProm3, filteredProm3);

        // Width Test
        double[] resultWidth = {16.026, 10.282,  8.84 , 26.109,  8.797, 10.249, 45.344, 11.331};
        double[] outWidth = out.getWidth();
        Assertions.assertArrayEquals(resultWidth, outWidth, 0.001);

        double[][] outWidthData = out.getWidthData();
        double[] resultWidthHeight = {0.62 ,  0.6  , -0.224,  0.047, -0.223,  0.602,  0.0, -0.602};
        double[] resultLeftIP = {3.372,  30.927,  70.627,  90.694, 128.067, 166.307, 162.156, 227.114};
        double[] resultRightIP = {19.398,  41.21 ,  79.467, 116.803, 136.864, 176.556, 207.5, 238.444};
        Assertions.assertArrayEquals(resultWidth, outWidthData[0], 0.001);
        Assertions.assertArrayEquals(resultWidthHeight, outWidthData[1], 0.001);
        Assertions.assertArrayEquals(resultLeftIP, outWidthData[2], 0.001);
        Assertions.assertArrayEquals(resultRightIP, outWidthData[3], 0.001);

        // Width Filtering Test
        int[] resultFilteredWidth1 = {37, 171, 232};
        int[] resultFilteredWidth2 = {10,  37, 104, 171, 197, 232};
        int[] resultFilteredWidth3 = {75, 133};
        int[] filteredWidth1 = out.filterByWidth(10.0, 15.0);
        int[] filteredWidth2 = out.filterByWidth(10.0, null);
        int[] filteredWidth3 = out.filterByWidth(null, 10.0);
        Assertions.assertArrayEquals(resultFilteredWidth1, filteredWidth1);
        Assertions.assertArrayEquals(resultFilteredWidth2, filteredWidth2);
        Assertions.assertArrayEquals(resultFilteredWidth3, filteredWidth3);
    }

    @Test
    public void peakDetectHighResTest() throws IOException {
        int[] resultPeaks = {7, 49, 93, 122, 175, 213, 257, 285, 333, 374, 415, 495, 500, 531, 535, 575, 595, 608, 663, 699,
                705, 728,747, 765, 771, 841, 884, 928, 945, 956, 965};

        // Detection Tests
        FindPeak fp = new FindPeak(this.highResSignal);
        Peak out = fp.detectPeaks();
        Assertions.assertTrue(out instanceof Peak);
        Assertions.assertArrayEquals(resultPeaks, out.getPeaks());

        // Height Test
        double[] resultHeight = {0.436,  0.265,  0.564, -0.089,  0.144,  0.059,  0.399, -0.276, -0.123, -0.33 , -0.002,
                -0.382, -0.385, -0.5  , -0.498, -0.183, -0.729, -0.715, -0.408, -0.472, -0.469, -0.627, -0.052, -0.76 ,
                -0.757, -0.403, -0.517,  0.109, -0.714, -0.724, -0.722};
        double[] outHeight = out.getHeights();
        Assertions.assertArrayEquals(resultHeight, outHeight, 0.001);


        // Height Filtering Test
        int[] resultFilteredPeaks1 = {7,  49,  93, 175, 213, 257, 928};
        int[] resultFilteredPeaks2 = {7,  49,  93, 175, 213, 257, 928};
        int[] resultFilteredPeaks3 = {122, 285, 333, 374, 415, 495, 500, 531, 535, 575, 595, 608, 663, 699, 705, 728,
                747, 765, 771, 841, 884, 945, 956, 965};
        int[] filteredPeaks1 = out.filterByHeight(0.02, 1.0);
        int[] filteredPeaks2 = out.filterByHeight(0.0, null);
        int[] filteredPeaks3 = out.filterByHeight(null, 0.0);
        Assertions.assertArrayEquals(resultFilteredPeaks1, filteredPeaks1);
        Assertions.assertArrayEquals(resultFilteredPeaks2, filteredPeaks2);
        Assertions.assertArrayEquals(resultFilteredPeaks3, filteredPeaks3);

        // Plateau Size Test
        int[] resultPlateau = {1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1};
        int[] outPlateau = out.getPlateauSize();
        Assertions.assertArrayEquals(resultPlateau, outPlateau);
        int[] resultPlateau1 = {1, 2, 1, 2, 1};
        int[] outPlateau1 = out.findPlateauSize(new int[]{7, 49, 122, 175, 213});
        Assertions.assertArrayEquals(resultPlateau1, outPlateau1);

        // Plateau Size Filtering Test
        int[] resultFilteredPS1 = {49, 175, 495, 575, 595, 663, 945, 956};
        int[] resultFilteredPS2 = {49, 175, 495, 575, 595, 663, 945, 956};
        int[] resultFilteredPS3 = {7,  93, 122, 213, 257, 285, 333, 374, 415, 500, 531, 535, 608, 699, 705, 728, 747,
                765, 771, 841, 884, 928, 965};
        int[] outFilteredPS1 = out.filterByPlateauSize(2.0, 4.0);
        int[] outFilteredPS2 = out.filterByPlateauSize(2.0, null);
        int[] outFilteredPS3 = out.filterByPlateauSize(null, 1.0);
        Assertions.assertArrayEquals(resultFilteredPS1, outFilteredPS1);
        Assertions.assertArrayEquals(resultFilteredPS2, outFilteredPS2);
        Assertions.assertArrayEquals(resultFilteredPS3, outFilteredPS3);

        // Distance Test
        int[] resultDistance = {42, 44, 29, 53, 38, 44, 28, 48, 41, 41, 80,  5, 31,  4, 40, 20, 13, 55, 36,  6, 23, 19,
                18,  6, 70, 43, 44, 17, 11,  9};
        int[] outDistance = out.getPeakDistance();
        Assertions.assertArrayEquals(resultDistance, outDistance);

        // Distance Filtering Test
        int[] resultFilteredDistance1 = {7,  49,  93, 122, 175, 213, 257, 285, 333, 374, 415, 495, 535, 575, 595, 608,
                663, 705, 728, 747, 771, 841, 884, 928, 945, 965};
        int[] resultFilteredDistance2 = {7,  49,  93, 175, 257, 333, 374, 415, 495, 535, 575, 663, 705, 747, 841, 884,
                928};
        int[] outFilteredDistance1 = out.filterByPeakDistance(12);
        int[] outFilteredDistance2 = out.filterByPeakDistance(40);
        Assertions.assertArrayEquals(resultFilteredDistance1, outFilteredDistance1);
        Assertions.assertArrayEquals(resultFilteredDistance2, outFilteredDistance2);

        // Sharpness Test
        double[][] resultSharpness = {{0.029, 0.002, 0.003, 0.002, 0.002, 0.001, 0.004, 0.002, 0.001, 0.005, 0.008,
                0.004, 0.003, 0.001, 0.001, 0.004, 0.001, 0.003, 0.002, 0.002, 0.001, 0.002, 0.002, 0.001, 0.001, 0.001,
                0.002, 0.011, 0.002, 0.001, 0.002}, {0.002, 0.   , 0.008, 0.001, 0.   , 0.003, 0.004, 0.001, 0.002,
                0.003, 0.008, 0.   , 0.003, 0.001, 0.001, 0.   , 0.   , 0.001, 0.   , 0.001, 0.001, 0.001, 0.008, 0.002,
                0.003, 0.003, 0.003, 0.005, 0.   , 0.   , 0.001}};
        double[][] outSharpness = out.getPeakSharpness();
        for (int i=0; i<resultSharpness.length; i++) {
            Assertions.assertArrayEquals(resultSharpness[i], outSharpness[i], 0.001);
        }

        // Sharpness Filtering Test
        int[] resultFilteredSharp1 = {257, 500, 884};
        int[] resultFilteredSharp2 = {7,  93, 257, 374, 415, 500, 747, 884, 928};
        int[] resultFilteredSharp3 = {49, 122, 175, 213, 257, 285, 333, 495, 500, 531, 535, 575, 595, 608, 663, 699,
                705, 728, 765, 771, 841, 884, 945, 956, 965};
        int[] filteredSharp1 = out.filterBySharpness(0.002, 0.005);
        int[] filteredSharp2 = out.filterBySharpness(0.002, null);
        int[] filteredSharp3 = out.filterBySharpness(null, 0.005);
        Assertions.assertArrayEquals(resultFilteredSharp1, filteredSharp1);
        Assertions.assertArrayEquals(resultFilteredSharp2, filteredSharp2);
        Assertions.assertArrayEquals(resultFilteredSharp3, filteredSharp3);


        // Prominence Test
        double[] resultProminence = {0.208, 0.107, 0.598, 0.003, 0.24 , 0.08 , 0.629, 0.016, 0.215, 0.056, 0.651, 0.323,
                0.004, 0.001, 0.076, 0.558, 0.007, 0.004, 0.248, 0.003, 0.093, 0.004, 0.689, 0.004, 0.006, 0.334, 0.086,
                0.838, 0.003, 0.001, 0.002};
        double[] outProminence = out.getProminence();
        Assertions.assertArrayEquals(resultProminence, outProminence, 0.001);

        double[][] outPromData = out.getProminenceData();
        double[] resultLeftBase = {0,  32,  78, 119, 119, 197, 242, 281, 301, 365, 400, 443, 498, 517, 517, 563, 589,
                589, 589, 684, 684, 726, 589, 762, 762, 762, 864, 762, 943, 955, 960};
        double[] resultRightBase = {78,  78, 762, 125, 242, 242, 762, 301, 400, 400, 762, 562, 562, 532, 562, 589, 599,
                611, 733, 701, 733, 733, 762, 767, 773, 913, 913, 960, 960, 960, 967};
        Assertions.assertArrayEquals(resultProminence, outPromData[0], 0.001);
        Assertions.assertArrayEquals(resultLeftBase, outPromData[1], 0.001);
        Assertions.assertArrayEquals(resultRightBase, outPromData[2], 0.001);

        // Prominence Filtering Test
        int[] resultFilteredProm1 = {7,  93, 175, 333, 495, 575, 663, 841};
        int[] resultFilteredProm2 = {7,  93, 175, 257, 333, 415, 495, 575, 663, 747, 841, 928};
        int[] resultFilteredProm3 = {7,  49,  93, 122, 175, 213, 285, 333, 374, 495, 500, 531, 535, 575, 595, 608, 663,
                699, 705, 728, 765, 771, 841, 884, 945, 956, 965};
        int[] filteredProm1 = out.filterByProminence(0.2, 0.6);
        int[] filteredProm2 = out.filterByProminence(0.2, null);
        int[] filteredProm3 = out.filterByProminence(null, 0.6);
        Assertions.assertArrayEquals(resultFilteredProm1, filteredProm1);
        Assertions.assertArrayEquals(resultFilteredProm2, filteredProm2);
        Assertions.assertArrayEquals(resultFilteredProm3, filteredProm3);

        // Width Test
        double[] resultWidth = {15.233, 19.82 , 14.765,  2.25 , 33.015, 14.671, 15.017,  4.19 , 30.576, 10. , 14.787,
                42.581,  1.333,  1.   , 13.879, 14.984, 5.875,  2.167, 37.336,  2.   , 18.307,  2.125, 15.2  ,  2.333,
                3.   , 53.649, 16.2  , 14.894,  2.5  ,  1.667,  1.5};
        double[] outWidth = out.getWidth();
        Assertions.assertArrayEquals(resultWidth, outWidth, 0.001);

        double[][] outWidthData = out.getWidthData();
        double[] resultWidthHeight = {0.332,  0.212,  0.265, -0.09 ,  0.024,  0.019,  0.085, -0.284, -0.23 , -0.358,
                -0.328, -0.544, -0.387, -0.5  , -0.536, -0.462, -0.732, -0.717, -0.532, -0.474, -0.516, -0.629, -0.396,
                -0.762, -0.76 , -0.57 , -0.56 , -0.31 , -0.716, -0.724, -0.723};
        double[] resultLeftIP = {3.452,  37.147,  84.912, 121.25 , 155.167, 204.9  , 248.467, 283.143, 318.833, 370.   ,
                406.468, 470.611, 499.333, 530.5  , 525.667, 568.756, 591.625, 607.333, 642.3  , 698.25 , 693.318,
                727.   , 740.052, 763.667, 769.   , 803.636, 874.   , 921.5  , 944.25 , 955.5  , 964.5};
        double[] resultRightIP = {18.684,  56.967,  99.677, 123.5  , 188.182, 219.571, 263.484, 287.333, 349.409, 380.   ,
                421.255, 513.192, 500.667, 531.5  , 539.545, 583.739, 597.5  , 609.5  , 679.636, 700.25 , 711.625,
                729.125, 755.253, 766.   , 772.   , 857.286, 890.2  , 936.394, 946.75 , 957.167, 966.};
        Assertions.assertArrayEquals(resultWidth, outWidthData[0], 0.001);
        Assertions.assertArrayEquals(resultWidthHeight, outWidthData[1], 0.001);
        Assertions.assertArrayEquals(resultLeftIP, outWidthData[2], 0.001);
        Assertions.assertArrayEquals(resultRightIP, outWidthData[3], 0.001);

        // Width Filtering Test
        int[] resultFilteredWidth1 = {7,  49,  93, 213, 257, 374, 415, 535, 575, 595, 705, 747, 884, 928};
        int[] resultFilteredWidth2 = {7,  49,  93, 175, 213, 257, 333, 374, 415, 495, 535, 575, 595, 663, 705, 747, 841,
                884, 928};
        int[] resultFilteredWidth3 = {7,  49,  93, 122, 213, 257, 285, 374, 415, 500, 531, 535, 575, 595, 608, 699, 705,
                728, 747, 765, 771, 884, 928, 945, 956, 965};
        int[] filteredWidth1 = out.filterByWidth(5.0, 20.0);
        int[] filteredWidth2 = out.filterByWidth(5.0, null);
        int[] filteredWidth3 = out.filterByWidth(null, 20.0);
        Assertions.assertArrayEquals(resultFilteredWidth1, filteredWidth1);
        Assertions.assertArrayEquals(resultFilteredWidth2, filteredWidth2);
        Assertions.assertArrayEquals(resultFilteredWidth3, filteredWidth3);
    }

    @Test
    public void troughDetectTest() throws IOException{
        int[] resultTroughs = {31,  78, 119, 125, 196, 242, 281, 301, 365, 400, 442, 498, 517, 532, 562, 589, 599, 611, 684,
                701, 726, 733, 762, 767, 773, 864, 913, 943, 954, 960, 967};

        FindPeak fp = new FindPeak(this.highResSignal);
        Peak out = fp.detectTroughs();

        Assertions.assertArrayEquals(resultTroughs, out.getPeaks());
    }

    @Test
    public void peakPlot() throws IOException{
        FindPeak fp = new FindPeak(this.highResSignal);
        Peak out = fp.detectPeaks();

        String outputFileName = "test_outputs/peak_test.png";
        Plotting fig = new Plotting("Peak Detection", "Time", "Signal");
        fig.initialisePlot();
        // Plot all detected peaks
        fig.addSignal("Signal", this.highResSignal, false);
        fig.addPoints("Peaks", out.getPeaks(), out.getHeights(), 'o');

        // Plot Filtered peak points
        int[] filteredPeaks = out.filterByHeight(0.02, 1.0);
        double[] filteredHeights = out.findPeakHeights(filteredPeaks);
        fig.addPoints("Filtered Peaks", filteredPeaks, filteredHeights, '^');

        FindPeak ft = new FindPeak(this.highResSignal);
        Peak out2 = ft.detectTroughs();
        fig.addPoints("Troughs", out2.getPeaks(), out2.getHeights(), '+');

        fig.saveAsPNG(outputFileName);

        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void spikeDetectTest() {
        FindPeak fp = new FindPeak(this.simpleSignal);
        Spike out = fp.getSpikes();

        int[] resultLeftTroughs = {-1, 25, 57, 84, 123, 150, 182, 218};
        int[] resultRightTroughs = {25, 57, 84, 123, 150, 182, 218, 244};
        int[][] outTroughs = out.getAllTroughs();
        Assertions.assertArrayEquals(resultLeftTroughs, outTroughs[0]);
        Assertions.assertArrayEquals(resultRightTroughs, outTroughs[1]);

        // Spike Calculation Test
        double[] resultLeftSpike = {Double.NaN, 0.477, 1.424, 1.964, 0.284, 2.345, 0.878, 0.878};
        double[] resultRightSpike = {0.878, 2.346, 0.282, 1.964, 1.424, 0.476, 2.484, 0.476};
        double[] resultMeanSpike = {0.878, 1.4115, 0.853 , 1.964 , 0.854 , 1.4105, 1.681 , 0.677};
        double[] resultMaxSpike = {0.878, 2.346, 1.424, 1.964, 1.424, 2.345, 2.484, 0.878};
        double[] resultMinSpike = {0.878, 0.477, 0.282, 1.964, 0.284, 0.476, 0.878, 0.476};

        double[] outLeftSpike = out.getLeftSpike();
        double[] outRightSpike = out.getRightSpike();
        double[] outMeanSpike = out.getMeanSpike();
        double[] outMaxSpike = out.getMaxSpike();
        double[] outMinSpike = out.getMinSpike();
        Assertions.assertArrayEquals(resultLeftSpike, outLeftSpike, 0.001);
        Assertions.assertArrayEquals(resultRightSpike, outRightSpike, 0.001);
        Assertions.assertArrayEquals(resultMeanSpike, outMeanSpike, 0.001);
        Assertions.assertArrayEquals(resultMaxSpike, outMaxSpike, 0.001);
        Assertions.assertArrayEquals(resultMinSpike, outMinSpike, 0.001);

        // Spike Filtering Test

        // Left Spike Filter with lower and upper threshold
        int[] resultLeftFilter = {75, 104, 171};
        int[] resultRightFilter = {37, 104, 133, 197};
        int[] resultMeanFilter = {37, 104, 171, 197};
        int[] resultMaxFilter = {37, 75, 104, 133, 171, 197};
        int[] resultMinFilter = {104};

        int[] outLeftFilter = out.filterByProperty(1.0, 4.0, "left");
        int[] outRightFilter = out.filterByProperty(1.0, 4.0, "right");
        int[] outMeanFilter = out.filterByProperty(1.0, 4.0, "mean");
        int[] outMaxFilter = out.filterByProperty(1.0, 4.0, "max");
        int[] outMinFilter = out.filterByProperty(1.0, 4.0, "min");
        Assertions.assertArrayEquals(resultLeftFilter, outLeftFilter);
        Assertions.assertArrayEquals(resultRightFilter, outRightFilter);
        Assertions.assertArrayEquals(resultMeanFilter, outMeanFilter);
        Assertions.assertArrayEquals(resultMaxFilter, outMaxFilter);
        Assertions.assertArrayEquals(resultMinFilter, outMinFilter);

        // Left Spike Filter with lower threshold
        int[] resultLeftFilter1 = {104, 171};
        int[] resultRightFilter1 = {37, 104, 197};
        int[] resultMeanFilter1 = {104, 197};
        int[] resultMaxFilter1 = {37, 104, 171, 197};
        int[] resultMinFilter1 = {104};

        int[] outLeftFilter1 = out.filterByProperty(1.5, null, "left");
        int[] outRightFilter1 = out.filterByProperty(1.5, null, "right");
        int[] outMeanFilter1 = out.filterByProperty(1.5, null, "mean");
        int[] outMaxFilter1 = out.filterByProperty(1.5, null, "max");
        int[] outMinFilter1 = out.filterByProperty(1.5, null, "min");
        Assertions.assertArrayEquals(resultLeftFilter1, outLeftFilter1);
        Assertions.assertArrayEquals(resultRightFilter1, outRightFilter1);
        Assertions.assertArrayEquals(resultMeanFilter1, outMeanFilter1);
        Assertions.assertArrayEquals(resultMaxFilter1, outMaxFilter1);
        Assertions.assertArrayEquals(resultMinFilter1, outMinFilter1);

        // Left Spike Filter with upper threshold
        int[] resultLeftFilter2 = {37, 133, 197, 232};
        int[] resultRightFilter2 = {10, 75, 171, 232};
        int[] resultMeanFilter2 = {10, 75, 133, 232};
        int[] resultMaxFilter2 = {10, 232};
        int[] resultMinFilter2 = {10, 37, 75, 133, 171, 197, 232};

        int[] outLeftFilter2 = out.filterByProperty(null, 1.0, "left");
        int[] outRightFilter2 = out.filterByProperty(null, 1.0, "right");
        int[] outMeanFilter2 = out.filterByProperty(null, 1.0, "mean");
        int[] outMaxFilter2 = out.filterByProperty(null, 1.0, "max");
        int[] outMinFilter2 = out.filterByProperty(null, 1.0, "min");
        Assertions.assertArrayEquals(resultLeftFilter2, outLeftFilter2);
        Assertions.assertArrayEquals(resultRightFilter2, outRightFilter2);
        Assertions.assertArrayEquals(resultMeanFilter2, outMeanFilter2);
        Assertions.assertArrayEquals(resultMaxFilter2, outMaxFilter2);
        Assertions.assertArrayEquals(resultMinFilter2, outMinFilter2);
    }

    @Test
    public void spikeDetectHighResTest() {
        FindPeak fp = new FindPeak(this.highResSignal);
        Spike out = fp.getSpikes();

        int[] resultLeftTroughs = {-1, 31, 78, 119, 125, 196, 242, 281, 301, 365, 400, 442, 498, 517, 532, 562, 589, 599, 611, 684, 701, 726, 733, 762, 767, 773, 864, 913, 943, 954, 960};
        int[] resultRightTroughs = {31, 78, 119, 125, 196, 242, 281, 301, 365, 400, 442, 498, 517, 532, 562, 589, 599, 611, 684, 701, 726, 733, 762, 767, 773, 864, 913, 943, 954, 960, 967};
        int[][] outTroughs = out.getAllTroughs();
        Assertions.assertArrayEquals(resultLeftTroughs, outTroughs[0]);
        Assertions.assertArrayEquals(resultRightTroughs, outTroughs[1]);

        // Spike Calculation Test
        double[] resultLeftSpike = {Double.NaN, 0.107, 0.598, 0.007, 0.236, 0.08 , 0.629, 0.016, 0.215,
                0.056, 0.651, 0.323, 0.004, 0.074, 0.003, 0.558, 0.012, 0.021,
                0.311, 0.09 , 0.006, 0.004, 0.604, 0.005, 0.007, 0.36 , 0.086,
                0.846, 0.003, 0.001, 0.007};
        double[] resultRightSpike = {0.278, 0.299, 0.66 , 0.003, 0.165, 0.289, 0.691, 0.062, 0.263,
                0.323, 0.703, 0.007, 0.189, 0.001, 0.243, 0.558, 0.007, 0.004,
                0.154, 0.003, 0.162, 0.029, 0.713, 0.004, 0.006, 0.2  , 0.22 ,
                0.826, 0.011, 0.005, 0.002};
        double[] resultMeanSpike = {0.278, 0.203 , 0.629 , 0.005 , 0.2005, 0.1845, 0.66  , 0.039 ,
                0.239 , 0.1895, 0.677 , 0.165 , 0.0965, 0.0375, 0.123 , 0.558 ,
                0.0095, 0.0125, 0.2325, 0.0465, 0.084 , 0.0165, 0.6585, 0.0045,
                0.0065, 0.28  , 0.153 , 0.836 , 0.007 , 0.003 , 0.0045};
        double[] resultMaxSpike = {0.278, 0.299, 0.66 , 0.007, 0.236, 0.289, 0.691, 0.062, 0.263,
                0.323, 0.703, 0.323, 0.189, 0.074, 0.243, 0.558, 0.012, 0.021,
                0.311, 0.09 , 0.162, 0.029, 0.713, 0.005, 0.007, 0.36 , 0.22 ,
                0.846, 0.011, 0.005, 0.007};
        double[] resultMinSpike = {0.278, 0.107, 0.598, 0.003, 0.165, 0.08 , 0.629, 0.016, 0.215,
                0.056, 0.651, 0.007, 0.004, 0.001, 0.003, 0.558, 0.007, 0.004,
                0.154, 0.003, 0.006, 0.004, 0.604, 0.004, 0.006, 0.2  , 0.086,
                0.826, 0.003, 0.001, 0.002};

        double[] outLeftSpike = out.getLeftSpike();
        double[] outRightSpike = out.getRightSpike();
        double[] outMeanSpike = out.getMeanSpike();
        double[] outMaxSpike = out.getMaxSpike();
        double[] outMinSpike = out.getMinSpike();
        Assertions.assertArrayEquals(resultLeftSpike, outLeftSpike, 0.001);
        Assertions.assertArrayEquals(resultRightSpike, outRightSpike, 0.001);
        Assertions.assertArrayEquals(resultMeanSpike, outMeanSpike, 0.001);
        Assertions.assertArrayEquals(resultMaxSpike, outMaxSpike, 0.001);
        Assertions.assertArrayEquals(resultMinSpike, outMinSpike, 0.001);

        // Spike Filtering Test

        // Left Spike Filter with lower and upper threshold
        int[] resultLeftFilter = {495, 663, 841};
        int[] resultRightFilter = {7, 49, 213, 333, 374};
        int[] resultMeanFilter = {7, 841};
        int[] resultMaxFilter = {7, 49, 213, 333, 374, 495, 663, 841};
        int[] resultMinFilter = {7};

        int[] outLeftFilter = out.filterByProperty(0.25, 0.5, "left");
        int[] outRightFilter = out.filterByProperty(0.25, 0.5, "right");
        int[] outMeanFilter = out.filterByProperty(0.25, 0.5, "mean");
        int[] outMaxFilter = out.filterByProperty(0.25, 0.5, "max");
        int[] outMinFilter = out.filterByProperty(0.25, 0.5, "min");
        Assertions.assertArrayEquals(resultLeftFilter, outLeftFilter);
        Assertions.assertArrayEquals(resultRightFilter, outRightFilter);
        Assertions.assertArrayEquals(resultMeanFilter, outMeanFilter);
        Assertions.assertArrayEquals(resultMaxFilter, outMaxFilter);
        Assertions.assertArrayEquals(resultMinFilter, outMinFilter);

        // Left Spike Filter with lower threshold
        int[] resultLeftFilter1 = {93, 175, 257, 333, 415, 495, 575, 663, 747, 841, 928};
        int[] resultRightFilter1 = {7, 49, 93, 213, 257, 333, 374, 415, 535, 575, 747, 884, 928};
        int[] resultMeanFilter1 = {7, 49, 93, 175, 257, 333, 415, 575, 663, 747, 841, 928};
        int[] resultMaxFilter1 = {7, 49, 93, 175, 213, 257, 333, 374, 415, 495, 535, 575, 663, 747, 841, 884, 928};
        int[] resultMinFilter1 = {7, 93, 257, 333, 415, 575, 747, 928};

        int[] outLeftFilter1 = out.filterByProperty(0.2, null, "left");
        int[] outRightFilter1 = out.filterByProperty(0.2, null, "right");
        int[] outMeanFilter1 = out.filterByProperty(0.2, null, "mean");
        int[] outMaxFilter1 = out.filterByProperty(0.2, null, "max");
        int[] outMinFilter1 = out.filterByProperty(0.2, null, "min");
        Assertions.assertArrayEquals(resultLeftFilter1, outLeftFilter1);
        Assertions.assertArrayEquals(resultRightFilter1, outRightFilter1);
        Assertions.assertArrayEquals(resultMeanFilter1, outMeanFilter1);
        Assertions.assertArrayEquals(resultMaxFilter1, outMaxFilter1);
        Assertions.assertArrayEquals(resultMinFilter1, outMinFilter1);

        // Left Spike Filter with upper threshold
        int[] resultLeftFilter2 = {122, 213, 285, 374, 500, 531, 535, 595, 608, 699, 705, 728, 765, 771, 884, 945, 956, 965};
        int[] resultRightFilter2 = {122, 285, 495, 531, 595, 608, 699, 728, 765, 771, 945, 956, 965};
        int[] resultMeanFilter2 = {122, 285, 500, 531, 595, 608, 699, 705, 728, 765, 771, 945, 956, 965};
        int[] resultMaxFilter2 = {122, 285, 531, 595, 608, 699, 728, 765, 771, 945, 956, 965};
        int[] resultMinFilter2 = {122, 213, 285, 374, 495, 500, 531, 535, 595, 608, 699, 705, 728, 765, 771, 884, 945, 956, 965};


        int[] outLeftFilter2 = out.filterByProperty(null, 0.1, "left");
        int[] outRightFilter2 = out.filterByProperty(null, 0.1, "right");
        int[] outMeanFilter2 = out.filterByProperty(null, 0.1, "mean");
        int[] outMaxFilter2 = out.filterByProperty(null, 0.1, "max");
        int[] outMinFilter2 = out.filterByProperty(null, 0.1, "min");
        Assertions.assertArrayEquals(resultLeftFilter2, outLeftFilter2);
        Assertions.assertArrayEquals(resultRightFilter2, outRightFilter2);
        Assertions.assertArrayEquals(resultMeanFilter2, outMeanFilter2);
        Assertions.assertArrayEquals(resultMaxFilter2, outMaxFilter2);
        Assertions.assertArrayEquals(resultMinFilter2, outMinFilter2);
    }

    @Test
    public void spikePlot() throws IOException{
        FindPeak fp = new FindPeak(this.highResSignal);
        Spike out = fp.getSpikes();

        int[] peaks = out.getPeaks();

        String outputFileName = "test_outputs/spike_test.png";
        Plotting fig = new Plotting("Spike Detection", "Time", "Signal");
        fig.initialisePlot();
        // Plot all detected peaks
        fig.addSignal("Signal", this.highResSignal, false);
        fig.addPoints("Spikes", peaks, out.findPeakHeights(peaks), 'x');

        // Plot Filtered peak points
        int[] filteredPeaks = out.filterByProperty(0.5, 5.0, "mean");
        double[] filteredHeights = out.findPeakHeights(filteredPeaks);
        fig.addPoints("Filtered Spikes", filteredPeaks, filteredHeights, 'o');

        fig.saveAsPNG(outputFileName);

        boolean fileExists = new File("./"+outputFileName).exists();
        Assertions.assertTrue(fileExists);
    }
}