package com.github.psambit9791.jdsp;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;

/**
 * <h1>Line Plot</h1>
 * The LinePlot class provides method to plot curves on a chart and then either display it or save it as a .png file
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Plotting {

    private XYChart figure;
    private XYChartBuilder plot;

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
    public void initialise_plot() {
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
    public void add_signal(String name, double[] x, double[] y, boolean marker) {
        XYSeries s = this.figure.addSeries(name, x, y);
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
    public void add_signal(String name, double[] y, boolean marker) {
        XYSeries s = this.figure.addSeries(name, y);
        if (!marker) {
            s.setMarker(SeriesMarkers.NONE);
        }
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     */
    public void add_points(String name, double[] x, double[] y) {
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
    public void add_points(String name, int[] x, double[] y) {
        double[] x_new = new double[x.length];
        for (int i=0; i<x.length; i++) {
            x_new[i] = (double)x[i];
        }
        XYSeries s = this.figure.addSeries(name, x_new, y);
        s.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        s.setMarker(SeriesMarkers.CIRCLE);
    }

    /**
     * This method adds points to the plot
     * @param name Name of the curve being added (Used for the legend)
     * @param x Data to be plotted on the X-axis
     * @param y Data to be plotted on the Y-axis
     * @param marker Type of Marker to be used ('x': CROSS, 'o': CIRCLE ,'*': DIAMOND, '+': PLUS, '#': SQUARE, '^': TRIANGLE_UP)
     */
    public void add_points(String name, double[] x, double[] y, char marker) {
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
    public void add_points(String name, int[] x, double[] y, char marker) {
        double[] x_new = new double[x.length];
        for (int i=0; i<x.length; i++) {
            x_new[i] = (double)x[i];
        }
        XYSeries s = this.figure.addSeries(name, x_new, y);
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
     * This method displays the plot
     */
    public void plot() {
        new SwingWrapper(this.figure).displayChart();
    }

    /**
     * This method saves the plot as a .png file
     * @param name Name of the file the plot is to be saved as
     * @throws java.io.IOException if there is any error in saving
     */
    public void save_as_png(String name) throws IOException {
        BitmapEncoder.saveBitmapWithDPI(this.figure, "./"+name, BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
