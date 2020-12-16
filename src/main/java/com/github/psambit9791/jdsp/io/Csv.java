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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Read and Write CSV Files</h1>
 * This class provides methods; to read the content of CSV files into a HashMap; and to write a HashMap into a CSV file.
 * The HashMap keys are the column names (auto-filled if no column name specified) and the ArrayList is the contents of
 * the column
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Csv {
    private HashMap<String, ArrayList<Object>> content = new HashMap<String, ArrayList<Object>>();
    private enum dataType {DOUBLE, FLOAT, LONG, INTEGER, STRING}
    private char sep;
    private char delimiter;

    /**
     * This constructor initialises the prerequisites required to read and write CSV files.
     * @param separator The character separating the columns
     * @param delimiter The character specifying the end of line for each row
     */
    public Csv(char separator, char delimiter) {
        this.sep = separator;
        this.delimiter = delimiter;
    }

    private void addToHashMap(String key, String[] data, dataType value) {

    }

    public HashMap<String, ArrayList<Object>> readCSV(String filename, String[] colNames) {
        return this.content;
    }

    public HashMap<String, ArrayList<Object>> readCSV(String filename, boolean hasColNames) {
        return this.content;
    }

    public void writeCSV(String filename, HashMap<String, ArrayList<Object>> data) {

    }
}
