/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author kaustubhspande
 */
public class LineChart extends Application{
    
    @Override public void start(Stage stage) {
        EmpiricalAnalysis ea = new EmpiricalAnalysis();
        stage.setTitle("Empirical Analysis Chart");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        //creating the chart
        final javafx.scene.chart.LineChart<Number,Number> lineChart = 
                new javafx.scene.chart.LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Empirical Analysis Chart");
        //defining a series
        XYChart.Series series = new XYChart.Series();
       
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 20));
        series.getData().add(new XYChart.Data(3, 23));
        series.getData().add(new XYChart.Data(4, 20));
        series.getData().add(new XYChart.Data(5, 23));
        series.getData().add(new XYChart.Data(6, 20));
        series.getData().add(new XYChart.Data(7, 23));
        series.getData().add(new XYChart.Data(8, 20));
        series.getData().add(new XYChart.Data(9, 20));
        series.getData().add(new XYChart.Data(10, 20));
        series.getData().add(new XYChart.Data(11, 23));
        series.getData().add(new XYChart.Data(12, 20));
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }

        
}
