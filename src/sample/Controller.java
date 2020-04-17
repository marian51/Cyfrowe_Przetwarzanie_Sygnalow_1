package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
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

    public double samplingFreq = 16;
    public int selected;

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

    public void SaveProperties(ActionEvent actionEvent) {
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

    public void GenerateSignal () {
        Signal signal = new Signal(generator.Amplitude, generator.Frequency, samplingFreq);
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

        HistChart histChart = new HistChart();
        histChart.loadData(signal);
    }


}
