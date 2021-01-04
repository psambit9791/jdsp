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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
//    private enum dataType {DOUBLE, FLOAT, LONG, INTEGER, STRING}
    private String sep;
    private String[] keyList;
//    private dataType[] colTypes;

//    /**
//     * This constructor initialises the prerequisites required to read and write CSV files.
//     * @param separator The character separating the columns
//     * @param columnDatatypes The datatype of the content of each column
//     */
//    public Csv(char separator, dataType[] columnDatatypes) {
//        this.sep = separator+"";
//        this.colTypes = columnDatatypes;
//    }

    /**
     * This constructor initialises the prerequisites required to read and write CSV files.
     * @param separator The character separating the columns
     */
    public Csv(char separator) {
        this.sep = separator+"";
    }

    /**
     * This function initialises the HashMap with the column names as the keys.
     * @param colNames The columns names in the CSV which will act as keys for the HashMap
     */
    private void initialiseHashMap(String[] colNames) {
        this.keyList = colNames;
        for (int i=0; i<this.keyList.length; i++) {
            this.content.put(this.keyList[i], new ArrayList<>());
        }
    }

    /**
     * Adds each item in the row to their corresponding keys in the HashMap
     * @param data Array of items in the row split by separator
     */
    private void addRecordToHashmap(String[] data) {
        for (int i=0; i<this.keyList.length; i++) {
            this.content.get(this.keyList[i]).add(data[i]);
        }
    }

    /**
     * The function takes the path of the file, the columns names and if the CSV file has column names in first row to
     * generate the HashMap. The column names wither override the existing column names or are used in stead of random names.
     * @param pathToCsv The path to the CSV file to be read
     * @param colNames Custom names of the columns to be used as keys
     * @param hasColNames If the first row of the CSV has column names. If column names are provided, then it is overridden with colNames
     * @return HashMap A hashmap which contains the column names as keys and the contents of each column as an ArrayList
     * @throws java.io.IOException If error occurs during file read
     */
    public HashMap<String, ArrayList<Object>> readCSV(String pathToCsv, String[] colNames, boolean hasColNames) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        if (hasColNames) {
            csvReader.readLine();
        }
        String row = "";
        this.initialiseHashMap(colNames);
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(this.sep);
            this.addRecordToHashmap(data);
        }
        return this.content;
    }

    /**
     * The function takes the path of the file and if the CSV file has column names in first row to generate the HashMap.
     * If there are no column names, then column names are auto-generated.
     * @param pathToCsv The path to the CSV file to be read
     * @param hasColNames If the first row of the CSV has column names. If column names are not present, X0 to XN are issued as standard names.
     * @return HashMap A hashmap which contains the column names as keys and the contents of each column as an ArrayList
     * @throws java.io.IOException If error occurs during file read
     */
    public HashMap<String, ArrayList<Object>> readCSV(String pathToCsv, boolean hasColNames) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        if (!hasColNames) {
            BufferedReader test = new BufferedReader(new FileReader(pathToCsv));
            int totalCols = test.readLine().split(this.sep).length;
            String[] colNames = new String[totalCols];
            for (int i=0; i<totalCols; i++) {
                colNames[i] = "X"+i;
            }
            this.initialiseHashMap(colNames);
        }
        else {
            String[] colNames = csvReader.readLine().split(this.sep);
            this.initialiseHashMap(colNames);
        }
        String row = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(this.sep);
            this.addRecordToHashmap(data);
        }
        return this.content;
    }

    /**
     * The function takes the path of the file and a HashMap and writes it to the file. They keys are used as column names
     * and the contents of the keys (ArrayLists) are rendered as items in the columns.
     * @param pathToCsv The path to the CSV file to be written to
     * @param data A hashmap which contains the column names as keys and the contents of each column as an ArrayList
     * @throws java.io.IOException If error occurs during file write
     * @throws java.lang.IllegalArgumentException If the number of value for all the keys is not same
     */
    public void writeCSV(String pathToCsv, HashMap<String, ArrayList<Object>> data) throws IOException, IllegalArgumentException {
        FileWriter csvWriter = new FileWriter(pathToCsv);
        Set<String> setOfString = data.keySet();
        String[] keyList = new String[setOfString.size()];

        int index = 0;
        for (String str : setOfString)
            keyList[index++] = str;

        int[] row_length = new int[keyList.length];
        for (int i=0; i<keyList.length; i++) {
            row_length[i] = data.get(keyList[i]).size();
        }
        boolean flag = true;
        int base_length = row_length[0];
        for (int i=0; i<row_length.length; i++) {
            flag = (base_length == row_length[i]) && flag;
            if (!flag) {
                throw new IllegalArgumentException("Columns have different number of items. Please check the Hashmap being used");
            }
        }

        for (int i=0; i<keyList.length; i++) {
            csvWriter.append(keyList[i]);
            if (i==keyList.length-1) {
                csvWriter.append("\n");
            }
            else {
                csvWriter.append(",");
            }
        }

        for (int i=0; i<base_length; i++) {
            for (int j=0; j<keyList.length; j++) {
                csvWriter.append(data.get(keyList[j]).get(i).toString());
                if (j==keyList.length-1) {
                    csvWriter.append("\n");
                }
                else {
                    csvWriter.append(",");
                }
            }
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
