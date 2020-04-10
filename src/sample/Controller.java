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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void SaveProperties(ActionEvent actionEvent) {
    }
}
