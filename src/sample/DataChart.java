package sample;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

public class DataChart {
    public LineChart<Number,Number> lineChart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;
    public ScatterChart scatterChart;
    public Chart chart;

    public DataChart() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        scatterChart = new ScatterChart(xAxis,yAxis);
    }

    public void loadData (Signal signal, int selected) {

        //xAxis = new NumberAxis();
        xAxis.setLabel("Próbkowanie");

        //yAxis = new NumberAxis();
        yAxis.setLabel("Amplituda");

        XYChart.Series series = new XYChart.Series();

        series.setName("Seria");
        for (int i=0; i<signal.X.size(); i++) {
            series.getData().add(new XYChart.Data(signal.X.get(i), signal.Y.get(i)));
        }

        if (selected != 9 && selected != 10) {
            lineChart = new LineChart<Number, Number>(xAxis,yAxis);
            lineChart.setCreateSymbols(false);
            lineChart.setTitle(Controller.m);

            lineChart.getData().add(series);

            chart = lineChart;
        }
        else {
            //scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
            //scatterChart.setTitle("Wykres też");
            scatterChart.getData().add(series);

            chart = scatterChart;
        }

        Scene scene = new Scene(chart);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();

        stage.setTitle("Wykres");

    }

    public void loadTwice (Signal signal, int select) {

        xAxis.setLabel("Próbkowanie");
        yAxis.setLabel("Amplituda");

        //seria sygnału
        XYChart.Series seriesLine = new XYChart.Series();
        seriesLine.setName("Sygnał");
        for (int i=0; i<signal.X.size(); i++) {
            seriesLine.getData().add(new XYChart.Data(signal.X.get(i), signal.Y.get(i)));
        }

        XYChart.Series seriesDot = new XYChart.Series();
        seriesDot.setName("Próbki");
        for (int i=0; i<signal.SamplesX.size(); i++) {
            seriesDot.getData().add(new XYChart.Data(signal.SamplesX.get(i), signal.SamplesY.get(i)));
        }

        XYChart.Series seriesQuant = new XYChart.Series();
        seriesQuant.setName("Kwantyzacja");
        for (int i=0; i<signal.QuantizationX.size(); i++) {
            seriesQuant.getData().add(new XYChart.Data(signal.QuantizationX.get(i), signal.QuantizationY.get(i)));
        }

        XYChart.Series seriesQuantOther = new XYChart.Series();
        seriesQuantOther.setName("Kwantyzacja");
        for (int i=0; i<signal.QuantXplot.size(); i++) {
            seriesQuantOther.getData().add(new XYChart.Data(signal.QuantXplot.get(i), signal.QuantYplot.get(i)));
        }

        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
        //lineChart.setCreateSymbols(false);
        lineChart.setTitle("Wykres próbkowania");

        lineChart.getData().add(seriesLine);

        switch (select) {
            case 1:
                lineChart.getData().add(seriesDot);
                break;

            case 2:
                lineChart.getData().add(seriesQuant);
                break;

            case 3:
                lineChart.setId("chart3");
                lineChart.setCreateSymbols(false);
                lineChart.getData().add(seriesQuantOther);

                break;

            default:
                break;
        }

        chart = lineChart;

        Scene scene = new Scene(chart);
        scene.getStylesheets().add("chart.css");

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Wykres");

    }

}
