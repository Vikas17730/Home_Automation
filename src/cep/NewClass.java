/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class NewClass extends JFrame {
    
    private static XYSeries series1 = new XYSeries("Hum");
    
    public NewClass(int x, double y) {
        super("XY Line Chart Example with JFreechart");
        JPanel chartPanel = createChartPanel(x,y);
        add(chartPanel, BorderLayout.CENTER);
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private JPanel createChartPanel(int x, double y) {
    String chartTitle = "Objects Movement Chart";
    String xAxisLabel = "X";
    String yAxisLabel = "Y";
 
    XYDataset dataset = createDataset(x,y);
 
    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
            xAxisLabel, yAxisLabel, dataset);
 
    return new ChartPanel(chart);
}
 
    private XYDataset createDataset(int x,double y) {
    XYSeriesCollection dataset = new XYSeriesCollection();
    series1.add(x,y);
 
   /* series2.add(2.0, 1.0);
    series2.add(2.5, 2.4);
    series2.add(3.2, 1.2);
    series2.add(3.9, 2.8);
    series2.add(4.6, 3.0);
 
    series3.add(1.2, 4.0);
    series3.add(2.5, 4.4);
    series3.add(3.8, 4.2);
    series3.add(4.3, 3.8);
    series3.add(4.5, 4.0);
 */
    dataset.addSeries(series1);
   // dataset.addSeries(series2);
    //dataset.addSeries(series3);
 
    return dataset;
}
}
