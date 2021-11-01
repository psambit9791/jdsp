package com.github.psambit9791.jdsp;

import com.github.psambit9791.jdsp.misc.Export;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestExport {
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
    public void testExportDouble1D() throws IOException {
        double[] result = {1.23456, 2, -3, 4, 5e4};
        String filePath1 = "test_outputs/Export double 1D column.csv";
        Export.toCSV(result, filePath1);
        boolean fileExists = new File(filePath1).exists();
        Assertions.assertTrue(fileExists);

        String filePath2 = "test_outputs/Export double 1D row.csv";
        Export.toCSV(result, filePath2, false);
        fileExists = new File(filePath2).exists();
        Assertions.assertTrue(fileExists);

        String filePath3 = "test_outputs/Export double 1D separator.csv";
        Export.toCSV(result, filePath3, ";", false);
        fileExists = new File(filePath3).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testExportDouble2D() throws IOException {
        double[][] result = {{1.23456, 2}, {-3, 4}, {5e4, 6}};
        String filePath1 = "test_outputs/Export double 2D.csv";
        Export.toCSV(result, filePath1);
        boolean fileExists = new File(filePath1).exists();
        Assertions.assertTrue(fileExists);

        String filePath2 = "test_outputs/Export double 2D separator.csv";
        Export.toCSV(result, filePath2, ";");
        fileExists = new File(filePath2).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testExportComplex1D() throws IOException {
        Complex[] result = {new Complex(1, -2.345), new Complex(1e2, 3.1415),
                new Complex(-4, 5)};
        String filePath1 = "test_outputs/Export Complex 1D column.csv";
        Export.toCSV(result, filePath1);
        boolean fileExists = new File(filePath1).exists();
        Assertions.assertTrue(fileExists);

        String filePath2 = "test_outputs/Export Complex 1D row.csv";
        Export.toCSV(result, filePath2, false);
        fileExists = new File(filePath2).exists();
        Assertions.assertTrue(fileExists);

        String filePath3 = "test_outputs/Export Complex 1D separator.csv";
        Export.toCSV(result, filePath3, ";", false);
        fileExists = new File(filePath3).exists();
        Assertions.assertTrue(fileExists);
    }

    @Test
    public void testExportComplex2D() throws IOException {
        Complex[][] result = {{new Complex(1, -2.345), new Complex(1e2, 3.1415)},
                {new Complex(-4, 5), new Complex(6, 7)},
                {new Complex(8, 9), new Complex(10, 11)}};
        String filePath1 = "test_outputs/Export Complex 2D.csv";
        Export.toCSV(result, filePath1);
        boolean fileExists = new File(filePath1).exists();
        Assertions.assertTrue(fileExists);

        String filePath2 = "test_outputs/Export Complex 2D separator.csv";
        Export.toCSV(result, filePath2, ";");
        fileExists = new File(filePath2).exists();
        Assertions.assertTrue(fileExists);
    }
}
