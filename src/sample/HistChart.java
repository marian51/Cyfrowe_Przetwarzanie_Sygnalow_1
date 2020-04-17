package sample;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class HistChart {
    public BarChart<Number,Number> barChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;

    public void loadData (Signal signal) {
        signal.getHistogram(10);

        /*System.out.println("Rozmiar histogramu = " + signal.histogram.size());
        for (int i=0; i<signal.histogram.size(); i++) {
            System.out.println(signal.histogram.get(i).toString());
        }*/

        xAxis = new CategoryAxis();
        xAxis.setLabel("Przedziały");

        yAxis = new NumberAxis();
        yAxis.setLabel("Liczba wartości");

        XYChart.Series series = new XYChart.Series();

        series.setName("Histogram");

        for (int i=0; i<signal.histogram.size(); i++) {
            String label = signal.histogram.get(i).getFirst()+", "+signal.histogram.get(i).getSecond();
            series.getData().add(new XYChart.Data(label,signal.histogram.get(i).getThirs()));
            System.out.println("Dodano słupek: "+signal.histogram.get(i).toString());
        }

        barChart = new BarChart(xAxis,yAxis);
        barChart.getData().add(series);

        Scene scene = new Scene(barChart);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }
}
