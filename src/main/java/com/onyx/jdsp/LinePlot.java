package com.onyx.jdsp;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;

public class LinePlot {

    private XYChart figure;
    private XYChartBuilder plot;

    public LinePlot() {
        this.plot = new XYChartBuilder().width(600).height(500).xAxisTitle("X").yAxisTitle("Y");
    }
    public LinePlot(String title) {
        this.plot = new XYChartBuilder().width(600).height(500).title(title).xAxisTitle("X").yAxisTitle("Y");
    }
    public LinePlot(String title, String x_axis, String y_axis) {
        this.plot = new XYChartBuilder().width(600).height(500).title(title).xAxisTitle(x_axis).yAxisTitle(y_axis);
    }
    public LinePlot(int width, int height, String title, String x_axis, String y_axis) {
        this.plot = new XYChartBuilder().width(width).height(height).title(title).xAxisTitle(x_axis).yAxisTitle(y_axis);
    }

    public void initialise_graph() {
        this.figure = this.plot.build();
        this.figure.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        this.figure.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
    }

    public void add_signal(String name, double[] x, double[] y) {
        this.figure.addSeries(name, x, y);
    }

    public void add_signal(String name, double[] y) {
        this.figure.addSeries(name, y);
    }

    public void plot() {
        new SwingWrapper(this.figure).displayChart();
    }

    public void save_as_png(String name) throws IOException {
        BitmapEncoder.saveBitmapWithDPI(this.figure, "./"+name, BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
