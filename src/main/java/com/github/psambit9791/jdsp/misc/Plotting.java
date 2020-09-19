/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.misc;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;

/**
 * <h1>Line Plot</h1>
 * The LinePlot class provides method to plot curves on a chart and then either display it or save it as a .png file
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.1
 */
public class Plotting {

    private XYChart figure;
    private XYChartBuilder plot;
    private int hlineCount = 1;
    private int vlineCount = 1;

    /**
     * This constructor initialises the parameters required to generate the plot. Marker is automatically set to True.
     */
    public Plotting() {
        this.plot = new XYChartBuilder().width(600).height(500).theme(Styler.ChartTheme.Matlab).xAxisTitle("X").yAxisTitle("Y");
    }

    /**
     * This constructor initialises the parameters required to generate the plot.
     * @param title Title of the plot
     */
    public Plotting(String title) {
        this.plot = new XYChartBuilder().width(600).height(500).theme(Styler.ChartTheme.Matlab).title(title).xAxisTitle("X").yAxisTitle("Y");
    }

    /**
     * This constructor initialises the parameters required to generate the plot.
     * @param title Title of the plot
     * @param x_axis Title of property plotted on the X-axis
     * @param y_axis Title of property plotted on the X-axis
     */
    public Plotting(String title, String x_axis, String y_axis) {
        this.plot = new XYChartBuilder().width(600).height(500).theme(Styler.ChartTheme.Matlab).title(title).xAxisTitle(x_axis).yAxisTitle(y_axis);
    }

    /**
     * This constructor initialises the parameters required to generate the plot.
     * @param width Width of the plot
     * @param height Height of the plot
     * @param title Title of the plot
     * @param x_axis Title of property plotted on the X-axis
     * @param y_axis Title of property plotted on the X-axis
     */
    public Plotting(int width, int height, String title, String x_axis, String y_axis) {
        this.plot = new XYChartBuilder().width(width).height(height).theme(Styler.ChartTheme.Matlab).title(title).xAxisTitle(x_axis).yAxisTitle(y_axis);
    }

    /**
     * This method creates the plot with the provided properties
     */
    public void initialisePlot() {
        this.figure = this.plot.build();
        this.figure.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        this.figure.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
    }

    /**
     * This method adds a curve to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     * @param marker If the marker should be shown at datapoints
     */
    public void addSignal(String name, double[] x, double[] y, boolean marker) {
        XYSeries s = this.figure.addSeries(name, x, y);
        s.setLineWidth(0.75f);
        if (!marker) {
            s.setMarker(SeriesMarkers.NONE);
        }
    }

    /**
     * This method adds a curve to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param y Data to be plotted on the Y-axis
     * @param marker If the marker should be shown at datapoints
     */
    public void addSignal(String name, double[] y, boolean marker) {
        XYSeries s = this.figure.addSeries(name, y);
        s.setLineWidth(0.75f);
        if (!marker) {
            s.setMarker(SeriesMarkers.NONE);
        }
    }

    /**
     * This method adds a curve to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param y Data to be plotted on the Y-axis
     * @param marker If the marker should be shown at datapoints
     * @param line_type Type of Line to be used ("-": SOLID, ".": DOTTED, "--": DASHED, "-.": DASH-DOT)
     */
    public void addSignal(String name, double[] y, boolean marker, String line_type) {
        XYSeries s = this.figure.addSeries(name, y);
        s.setLineWidth(0.5f);
        if (!marker) {
            s.setMarker(SeriesMarkers.NONE);
        }
        if (line_type.equals("-")) {
            s.setLineStyle(SeriesLines.SOLID);
        }
        else if (line_type.equals(".")) {
            s.setLineStyle(SeriesLines.DOT_DOT);
        }
        else if (line_type.equals("--")) {
            s.setLineStyle(SeriesLines.DASH_DASH);
        }
        else if (line_type.equals("-.")) {
            s.setLineStyle(SeriesLines.DASH_DOT);
        }
    }

    /**
     * This method adds a curve to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     * @param marker If the marker should be shown at datapoints
     * @param line_type Type of Line to be used ("-": SOLID, ".": DOTTED, "--": DASHED, "-.": DASH-DOT)
     */
    public void addSignal(String name, double[] x, double[] y, boolean marker, String line_type) {
        XYSeries s = this.figure.addSeries(name, x, y);
        s.setLineWidth(0.75f);
        if (!marker) {
            s.setMarker(SeriesMarkers.NONE);
        }
        if (line_type.equals("-")) {
            s.setLineStyle(SeriesLines.SOLID);
        }
        else if (line_type.equals(".")) {
            s.setLineStyle(SeriesLines.DOT_DOT);
        }
        else if (line_type.equals("--")) {
            s.setLineStyle(SeriesLines.DASH_DASH);
        }
        else if (line_type.equals("-.")) {
            s.setLineStyle(SeriesLines.DASH_DOT);
        }
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     */
    public void addPoints(String name, double[] x, double[] y) {
        XYSeries s = this.figure.addSeries(name, x, y);
        s.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        s.setMarker(SeriesMarkers.CIRCLE);
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     */
    public void addPoints(String name, int[] x, double[] y) {
        double[] x_new = new double[x.length];
        for (int i=0; i<x.length; i++) {
            x_new[i] = (double)x[i];
        }
        this.addPoints(name, x_new, y);
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     * @param marker Type of Marker to be used ('x': CROSS, 'o': CIRCLE, '*': DIAMOND, '+': PLUS, '#': SQUARE, '^': TRIANGLE_UP)
     */
    public void addPoints(String name, double[] x, double[] y, char marker) {
        XYSeries s = this.figure.addSeries(name, x, y);
        s.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        if (marker == 'x') {
            s.setMarker(SeriesMarkers.CROSS);
        }
        else if (marker == 'o') {
            s.setMarker(SeriesMarkers.CIRCLE);
        }
        else if (marker == '*') {
            s.setMarker(SeriesMarkers.DIAMOND);
        }
        else if (marker == '+') {
            s.setMarker(SeriesMarkers.PLUS);
        }
        else if (marker == '#') {
            s.setMarker(SeriesMarkers.SQUARE);
        }
        else if (marker == '^') {
            s.setMarker(SeriesMarkers.TRIANGLE_UP);
        }
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     * @param marker Type of Marker to be used ('x': CROSS, 'o': CIRCLE ,'*': DIAMOND, '+': PLUS, '#': SQUARE, '^': TRIANGLE_UP)
     */
    public void addPoints(String name, int[] x, double[] y, char marker) {
        double[] x_new = new double[x.length];
        for (int i=0; i<x.length; i++) {
            x_new[i] = (double)x[i];
        }
        this.addPoints(name, x_new, y, marker);
    }

    /**
     * This method plot a horizontal line on the graph
     * @param x_min Starting point of the line
     * @param x_max Ending point of the line
     * @param y Position where the line is drawn on y-axis
     */
    public void hline(double x_min, double x_max, double y) {
        double[] x_plot = {x_min, x_max};
        double[] y_plot = {y, y};
        XYSeries s = this.figure.addSeries("Horizontal"+this.hlineCount, x_plot, y_plot);
        this.hlineCount++;
        s.setLineColor(XChartSeriesColors.BLACK);
        s.setMarker(SeriesMarkers.NONE);
        s.setLineStyle(SeriesLines.DASH_DASH);
        s.setShowInLegend(false);
        s.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    }

    /**
     * This method plot a vertical line on the graph
     * @param x Position where the line is drawn on x-axis
     * @param y_min Top point of the line
     * @param y_max Bottom point of the line
     */
    public void vline(double x, double y_min, double y_max) {
        double[] x_plot = {x, x};
        double[] y_plot = {y_min, y_max};
        XYSeries s = this.figure.addSeries("Vertical"+this.vlineCount, x_plot, y_plot);
        this.vlineCount++;
        s.setLineColor(XChartSeriesColors.BLACK);
        s.setMarker(SeriesMarkers.NONE);
        s.setLineStyle(SeriesLines.DASH_DASH);
        s.setShowInLegend(false);
        s.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    }

    /**
     * This method displays the plot
     */
    public void plot() {
        new SwingWrapper(this.figure).displayChart();
    }

    /**
     * This method saves the plot as a .png file
     * @param name Name of the file the plot is to be saved as
     * @throws java.io.IOException if there is any error in saving
     * @throws java.lang.IllegalArgumentException if file extension is not png
     */
    public void saveAsPNG(String name) throws IOException, IllegalArgumentException {
        String extension = name.substring(name.length()-4);
        if (!extension.equals(".png")) {
            throw new IllegalArgumentException("Filename must have PNG exception");
        }
        String filename = name.substring(0, name.length()-4);
        BitmapEncoder.saveBitmapWithDPI(this.figure, filename, BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
