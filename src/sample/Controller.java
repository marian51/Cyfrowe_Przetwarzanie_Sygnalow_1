package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField idAmplitude;
    public TextField idTimeStart;
    public TextField idTime;
    public TextField idPeriod;
    public TextField idTimeJump;
    public TextField idProb;
    public TextField idFrequency;
    public TextField idRate;
    public ComboBox idCombo;

    public Signal signal;

    public double samplingFreq = 16;
    public int selected;
    public TextField idAvg;
    public TextField idAvgAbs;
    public TextField idEffective;
    public TextField idVariance;
    public TextField idPower;

    SignalGenerator generator = new SignalGenerator(selected);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> list = FXCollections.observableArrayList(
                "1. Szum o rozkładzie jednostkowym",
                "2. Szum Gaussowski",
                "3. sygnał sinusoidalny",
                "4. Sygnał sinusoidalny wyprostowany jednopołówkowo",
                "5. Sygnał sinusoidalny wyprosotwany dwupołówkowo",
                "6. Sygnał prostokątny",
                "7. Sygnał prostokątny symetryczny",
                "8. Sygnał trójkątny",
                "9. Sygnał jednostkowy",
                "10. Impuls jednostkowy",
                "11. Szum impulsowy"
        );

        idCombo.setItems(list);

    }

    public void SaveProperties(ActionEvent actionEvent) throws IOException {
        generator.Amplitude = Double.parseDouble(idAmplitude.textProperty().get());
        generator.TimeStart = Double.parseDouble(idTimeStart.textProperty().get());
        generator.Time = Double.parseDouble(idTime.textProperty().get());
        generator.Period = Double.parseDouble(idPeriod.textProperty().get());
        generator.TimeJump = Double.parseDouble(idTimeJump.textProperty().get());
        generator.Prob = Double.parseDouble(idProb.textProperty().get());
        generator.Frequency = Double.parseDouble(idFrequency.textProperty().get());
        generator.Rate = Double.parseDouble(idRate.textProperty().get());

        System.out.println(generator.toString());

        GenerateSignal();


    }

    public void SetSignal(ActionEvent actionEvent) {
        selected = idCombo.getSelectionModel().selectedIndexProperty().get();
        generator.selected = selected;
        System.out.println("Wybrana opcja: " + generator.selected); //FIXME konsola
    }

    public void GenerateSignal () throws IOException {
        signal = new Signal(generator.TimeStart, generator.Time, generator.Frequency, samplingFreq);
        signal.X = new ArrayList<>();
        signal.Y = new ArrayList<>();
        //System.out.println("generator.TimeStart = " + generator.TimeStart);
        //System.out.println("generator.TimeStart+4 = " + generator.TimeStart+4);
        //System.out.println("1/samplingFreq = " + 1/samplingFreq);
        for (Double i = generator.TimeStart; i <= generator.TimeStart + generator.Time; i += 1/generator.Frequency) {
            //System.out.println("====>" + i);
            signal.X.add(i);
            signal.Y.add(generator.generate(i));
        }

        for (int i=0; i<signal.X.size(); i++) {
            System.out.println(signal.X.get(i) + " " + signal.Y.get(i));
        }


        DataChart dataChart = new DataChart();
        dataChart.loadData(signal, generator.selected);

        //HistChart histChart = new HistChart();
        //histChart.loadData(signal);

        loadHistogram();

        calculateParams(signal.Y);
    }

    public void loadHistogram() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HistogramView.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("HistogramView.fxml"));
        Parent root = (Parent) loader.load();
        HistogramView histogramView = loader.getController();
        histogramView.getSignal(signal);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Wykres hustogramu");
        stage.setScene(scene);
        stage.show();
    }

    public void calculateParams(List<Double> Xs) {
        idAvg.textProperty().setValue(Double.toString(Math.round(Operations.AverageSignal(Xs)*100.0)/100.0));
        idAvgAbs.textProperty().setValue(Double.toString(Math.round(Operations.AverageAbsSignal(Xs)*100.0)/100.0));
        idEffective.textProperty().setValue(Double.toString(Math.round(Operations.EffectiveValueSignal(Xs)*1000.0)/1000.0));
        idVariance.textProperty().setValue(Double.toString(Math.round(Operations.VarianceSignal(Xs)*100.0)/100.0));
        idPower.textProperty().setValue(Double.toString(Math.round(Operations.AveragePowerSignal(Xs)*100.0)/100.0));
    }




}
