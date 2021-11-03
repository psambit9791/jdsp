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

import com.github.psambit9791.jdsp.io.CSV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestCsv {

    private HashMap<String, ArrayList<Object>> result = new HashMap<String, ArrayList<Object>>();

    public void generateResultHashMap(String[] colNames) {
        for (int i=0; i<colNames.length; i++) {
            this.result.put(colNames[i], new ArrayList<>());
        }
        String[][] temp = {
                {"1562682658.557", "1562682658.638", "1562682658.718", "1562682658.798", "1562682658.878", "1562682658.959", "1562682659.038", "1562682659.118", "1562682659.199", "1562682659.278"},
                {"0.0", "0.08100008964538574", "0.16100001335144043", "0.24100017547607422", "0.3210000991821289", "0.40200018882751465", "0.4810001850128174", "0.5610001087188721", "0.6419999599456787", "0.7210001945495605"},
                {"0.07336", "0.07562", "0.0733", "0.07977", "0.07739", "0.07367", "0.07343", "0.07928", "0.07758", "0.07532"},
                {"-0.88678", "-0.89294", "-0.8924", "-0.89648", "-0.89032", "-0.89166", "-0.8963", "-0.89648", "-0.89014", "-0.89172"},
                {"0.51074", "0.51404", "0.51874", "0.51483", "0.5119", "0.51154", "0.51544", "0.51398", "0.5141", "0.51428"}
        };
        for (int i=0; i<colNames.length; i++) {
            for (int j=0; j<temp[i].length; j++) {
                this.result.get(colNames[i]).add(temp[i][j]);
            }
        }
    }

    @Test
    @Order(1)
    public void createTestInputDirectory() {
        String dirName = "./test_inputs/";
        File directory = new File(dirName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    @Test
    @Order(2)
    public void createTestOutputDirectory() {
        String dirName = "./test_outputs/";
        File directory = new File(dirName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    @Test
    public void csvReadTest1() throws IOException {
        this.generateResultHashMap(new String[]{"t", "diff", "x", "y", "z"});
        CSV readObj = new CSV(',');
        String inputFilename = "test_inputs/sheet_withcolumn.csv";
        HashMap<String, ArrayList<Object>> out = readObj.readCSV(inputFilename, true);
        Assertions.assertTrue(out.equals(this.result));
    }

    @Test
    public void csvReadTest2() throws IOException {
        this.generateResultHashMap(new String[]{"X0", "X1", "X2", "X3", "X4"});
        CSV readObj = new CSV(',');
        String inputFilename = "test_inputs/sheet_nocolumn.csv";
        HashMap<String, ArrayList<Object>> out = readObj.readCSV(inputFilename, false);
        Assertions.assertTrue(out.equals(this.result));
    }

    @Test
    public void csvReadCustomColumnsTest3() throws IOException {
        this.generateResultHashMap(new String[]{"Time", "Difference", "X-Axis", "Y-Axis", "Z-Axis"});
        CSV readObj = new CSV(',');
        String inputFilename = "test_inputs/sheet_withcolumn.csv";
        HashMap<String, ArrayList<Object>> out = readObj.readCSV(inputFilename, new String[]{"Time", "Difference", "X-Axis", "Y-Axis", "Z-Axis"}, true);
        Assertions.assertTrue(out.equals(this.result));
    }

    @Test
    public void csvReadCustomColumnsTest4() throws IOException {
        this.generateResultHashMap(new String[]{"Time", "Difference", "X-Axis", "Y-Axis", "Z-Axis"});
        CSV readObj = new CSV(',');
        String inputFilename = "test_inputs/sheet_nocolumn.csv";
        HashMap<String, ArrayList<Object>> out = readObj.readCSV(inputFilename, new String[]{"Time", "Difference", "X-Axis", "Y-Axis", "Z-Axis"}, false);
        Assertions.assertTrue(out.equals(this.result));
    }

    @Test
    public void csvWrite() throws IOException {
        this.generateResultHashMap(new String[]{"t", "diff", "x", "y", "z"});
        String outputFilename = "test_outputs/sheet.csv";
        CSV writeObj = new CSV(',');
        writeObj.writeCSV(outputFilename, this.result);
        boolean fileExists = new File("./"+outputFilename).exists();
        Assertions.assertTrue(fileExists);
    }
}
