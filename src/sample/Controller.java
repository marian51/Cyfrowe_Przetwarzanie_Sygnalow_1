package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
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

    SignalGenerator generator = new SignalGenerator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    }
}
