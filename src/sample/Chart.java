package sample;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Chart {
    public LineChart<Number,Number> lineChart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;

    public void loadData (Signal signal) {

        xAxis = new NumberAxis();
        xAxis.setLabel("Próbkowanie");

        yAxis = new NumberAxis();
        yAxis.setLabel("Amplituda");

        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("Wykres też");

        XYChart.Series series = new XYChart.Series();

        series.setName("Seria");
        for (int i=0; i<signal.X.size(); i++) {
            series.getData().add(new XYChart.Data(signal.X.get(i), signal.Y.get(i)));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();

        stage.setTitle("Wykres");

    }

}
