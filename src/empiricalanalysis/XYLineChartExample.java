/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author shivani bhalchandra
 */
public class XYLineChartExample extends JFrame {

    public void XYLineChartExample(XYSeriesCollection dataset) {

        JPanel chartPanel = createChartPanel(dataset);
        add(chartPanel, BorderLayout.CENTER);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JPanel createChartPanel(XYSeriesCollection li) {
        // creates a line chart object
        // returns the chart panel

        String chartTitle = " Movement Chart";
        String xAxisLabel = "Matrix Size";
        String yAxisLabel = "time in ms";

        XYSeriesCollection dataset = li;
        // System.out.println("tesst count " + dataset.getSeriesCount());

        //XYDataset dataset = createDataset(li);
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XYLineChartExample().setVisible(true);
            }
        });
    }

}
