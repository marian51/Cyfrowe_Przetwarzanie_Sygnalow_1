package sample;

import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HistogramView implements Initializable {
    public Pane idPane;
    public Signal signal;
    public Slider idSlider;
    public Pane idPane2;
    public Label idLabel;
    public int value=10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //double v = idSlider.getValue();
        //idLabel.setText(String.valueOf(idSlider.getValue()));


    }

    public void loadData() {
        idPane.getChildren().clear();
        //System.out.println("Metoda");
        //System.out.println("Próba danych; częstotliwość = " + signal.Frequency);
        signal.getHistogram(value);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Przedziały");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Suma wartości");

        XYChart.Series series = new XYChart.Series();
        series.setName("Histogram");

        for (int i=0; i<signal.histogram.size(); i++) {
            String label = signal.histogram.get(i).getFirst()+", "+signal.histogram.get(i).getSecond();
            series.getData().add(new XYChart.Data(label,signal.histogram.get(i).getThirs()));
            System.out.println("Dodano słupek: "+signal.histogram.get(i).toString());
        }

        BarChart<Number,Number> barChart = new BarChart(xAxis,yAxis);
        barChart.getData().add(series);
        barChart.prefWidthProperty().bind(idPane.widthProperty());
        barChart.prefHeightProperty().bind(idPane.heightProperty());

        idPane.getChildren().add(barChart);
    }

    public void getSignal (Signal signal) {
        this.signal = signal;
        loadData();
        idSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            idLabel.setText(Double.toString(newValue.intValue()));
            value = (int)idSlider.getValue();
            loadData();
        });

    }
}
