package com.github.psambit9791.jdsp.misc;

import org.apache.commons.math3.complex.Complex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * <h1>Export</h1>
 * The Export class provides methods for exporting data
 * <p>
 */
public class Export {
    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector and
     * which CSV-separator to use.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator: CSV-separator to be used
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(String[] data, String filePath, String separator, boolean columnVector) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(filePath));
        StringBuilder sb = new StringBuilder();

        if (columnVector) {
            separator = "\n";
        }

        for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
            if (i < data.length - 1) {
                sb.append(data[i]).append(separator);
            }
            else {
                sb.append(data[i]);
            }
        }

        br.write(sb.toString());
        br.close();
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(String[] data, String filePath, boolean columnVector) throws IOException {
        toCSV(data, filePath, ",", columnVector);
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma and data is stored as a column vector.
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(String[] data, String filePath) throws IOException {
        toCSV(data, filePath, "," , true);
    }




    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector and
     * which CSV-separator to use.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator: CSV-separator to be used
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(double[] data, String filePath, String separator, boolean columnVector) throws IOException {
        String[] dataStr = Arrays.stream(data).mapToObj(Double :: toString)
                .toArray(String[]::new);
        toCSV(dataStr, filePath, separator, columnVector);
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(double[] data, String filePath, boolean columnVector) throws IOException {
        toCSV(data, filePath, ",", columnVector);
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma and data is stored as a column vector.
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(double[] data, String filePath) throws IOException {
        toCSV(data, filePath, "," , true);
    }




    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector and
     * which CSV-separator to use.
     * Complex numbers are formatted as: 'real_part + imag_part i'. E.g. complex number Complex(1, -2) --> '1 -2i'
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator: CSV-separator to be used
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(Complex[] data, String filePath, String separator, boolean columnVector) throws IOException {
        String[] dataStr = Arrays.stream(data).map(c -> String.format(Locale.US, "%f %+fi", c.getReal(), c.getImaginary()))
                .toArray(String[]::new);
        toCSV(dataStr, filePath, separator, columnVector);
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma.
     * Complex numbers are formatted as: 'real_part + imag_part i'. E.g. complex number Complex(1, -2) --> '1 -2i'
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param columnVector: set to true if data should be stored as a column vector, false for row vector
     */
    public static void toCSV(Complex[] data, String filePath, boolean columnVector) throws IOException {
        toCSV(data, filePath, ",", columnVector);
    }

    /**
     * Export 'data' to a CSV-file. Specify if the data should be stored as a column vector or a row vector.
     * CSV-separator is by default a comma and data is stored as a column vector.
     * Complex numbers are formatted as: 'real_part + imag_part i'. E.g. complex number Complex(1, -2) --> '1 -2i'
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(Complex[] data, String filePath) throws IOException {
        toCSV(data, filePath, "," , true);
    }



    /**
     * Export 'data' to a CSV-file. Specify which CSV-separator to use.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator CSV-separator to be used
     */
    public static void toCSV(String[][] data, String filePath, String separator) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(filePath));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j < data[0].length - 1) {
                    sb.append(data[i][j]).append(separator);
                }
                else {
                    sb.append(data[i][j]);
                }
            }
            if (i < data.length - 1) {
                sb.append("\n");
            }
        }

        br.write(sb.toString());
        br.close();
    }

    /**
     * Export 'data' to a CSV-file. Default CSV-separator is a comma.
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(String[][] data, String filePath) throws IOException {
        toCSV(data, filePath, ",");
    }





    /**
     * Export 'data' to a CSV-file. Specify which CSV-separator to use.
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator CSV-separator to be used
     */
    public static void toCSV(double[][] data, String filePath, String separator) throws IOException {
        String[][] dataStr = new String[data.length][data[0].length];
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[0].length; c++) {
                dataStr[r][c] =  Double.toString(data[r][c]);
            }
        }

        toCSV(dataStr, filePath, separator);
    }

    /**
     * Export 'data' to a CSV-file. Default CSV-separator is a comma.
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(double[][] data, String filePath) throws IOException {
        toCSV(data, filePath, ",");
    }


    /**
     * Export 'data' to a CSV-file. Specify which CSV-separator to use.
     * Complex numbers are formatted as: 'real_part + imag_part i'. E.g. complex number Complex(1, -2) --> '1 -2i'
     * @param data data to be exported
     * @param filePath path where to store the file
     * @param separator CSV-separator to be used
     */
    public static void toCSV(Complex[][] data, String filePath, String separator) throws IOException {
        String[][] dataStr = new String[data.length][data[0].length];
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[0].length; c++) {
                Complex temp = data[r][c];
                dataStr[r][c] =  String.format(Locale.US, "%f %+fi", temp.getReal(), temp.getImaginary());
            }
        }

        toCSV(dataStr, filePath, separator);
    }

    /**
     * Export 'data' to a CSV-file. Default CSV-separator is a comma.
     * Complex numbers are formatted as: 'real_part + imag_part i'. E.g. complex number Complex(1, -2) --> '1 -2i'
     * @param data data to be exported
     * @param filePath path where to store the file
     */
    public static void toCSV(Complex[][] data, String filePath) throws IOException {
        toCSV(data, filePath, ",");
    }

}
