package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
    public Signal signalOne;
    public Signal signalTwo;
    public Signal signalThree;

    public double samplingFreq = 16;
    public int selected;
    public TextField idAvg;
    public TextField idAvgAbs;
    public TextField idEffective;
    public TextField idVariance;
    public TextField idPower;
    public Button idButtonSave;
    public Button idButtonLoad;
    public AnchorPane idLeftPane;
    public Pane idPane;
    public ChoiceBox idChoice;
    public static String m;

    //konstrukcja i rekonstrukcja sygnału
    public TextField idQuantStep;
    public TextField idQuantSample;
    public TextField idQuantCount;
    public Button btnAC;
    public Button btnCA;
    public ChoiceBox idRecoChoice;
    public TextField idMSE;
    public TextField idSNR;
    public TextField idMD;

    SignalGenerator generator = new SignalGenerator(selected);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> list = FXCollections.observableArrayList(
                "1. Szum o rozkładzie jednostkowym",
                "2. Szum Gaussowski",
                "3. Sygnał sinusoidalny",
                "4. Sygnał sinusoidalny wyprostowany jednopołówkowo",
                "5. Sygnał sinusoidalny wyprosotwany dwupołówkowo",
                "6. Sygnał prostokątny",
                "7. Sygnał prostokątny symetryczny",
                "8. Sygnał trójkątny",
                "9. Sygnał jednostkowy",
                "10. Impuls jednostkowy",
                "11. Szum impulsowy"
        );

        ObservableList<String> choiceList = FXCollections.observableArrayList(
                "Dodawanie",
                "Odejmowanie",
                "Mnożenie",
                "Dzielenie"
        );

        ObservableList<String> choiceReconstruction = FXCollections.observableArrayList(
                "Ekstrapolacja zerowego rzędu",
                "Interpolacja pierwszego rzędu",
                "Rekonstrukcja w oparciu o funkcję sinc"
        );

        idCombo.setItems(list);
        idChoice.setItems(choiceList);
        idRecoChoice.setItems(choiceReconstruction);

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
    }

    public void GenerateSignal () throws IOException {
        signal = new Signal(generator.TimeStart, generator.Frequency);
        signal.Time = generator.Time;
        signal.X = new ArrayList<>();
        signal.Y = new ArrayList<>();
        for (Double i = generator.TimeStart; i <= generator.TimeStart + generator.Time; i += 1/generator.Frequency) {
            signal.X.add(i);
            signal.Y.add(generator.generate(i));
        }

        for (int i=0; i<signal.X.size(); i++) {
            System.out.println(signal.X.get(i) + " " + signal.Y.get(i));
        }

        DataChart dataChart = new DataChart();
        dataChart.loadData(signal, generator.selected);

        loadHistogram(signal);

        calculateParams(signal.Y);
    }

    public void loadHistogram(Signal signal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HistogramView.fxml"));
        Parent root = (Parent) loader.load();
        HistogramView histogramView = loader.getController();
        histogramView.getSignal(signal);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Wykres histogramu");
        stage.setScene(scene);
        stage.show();
    }

    public void calculateParams(List<Double> Ys) {
        idAvg.textProperty().setValue(Double.toString(Math.round(Operations.AverageSignal(Ys)*100.0)/100.0));
        idAvgAbs.textProperty().setValue(Double.toString(Math.round(Operations.AverageAbsSignal(Ys)*100.0)/100.0));
        idEffective.textProperty().setValue(Double.toString(Math.round(Operations.EffectiveValueSignal(Ys)*1000.0)/1000.0));
        idVariance.textProperty().setValue(Double.toString(Math.round(Operations.VarianceSignal(Ys)*100.0)/100.0));
        idPower.textProperty().setValue(Double.toString(Math.round(Operations.AveragePowerSignal(Ys)*100.0)/100.0));
    }

    public void calculateErrors(List<Double> Ys, List<Double> Yr) {
        idMSE.textProperty().setValue(Double.toString(Math.round(Operations.MeanSquaredError(Ys,Yr)*100.0)/100.0));
        idSNR.textProperty().setValue(Double.toString(Math.round(Operations.SignalToNoiseRatio(Ys,Yr)*100.0)/100.0));
        idMD.textProperty().setValue(Double.toString(Math.round(Operations.MaximumDifference(Ys,Yr)*100.0)/100.0));
    }

    // TODO obliczanie parametrów kwantyzacji/rekonstrukcji

    public void saveSignalToFile(String filePath, Signal signal) throws IOException {
        filePath="results\\"+filePath;
        String filePathBin = filePath + "1.dat";
        String filePathTxt = filePath + "1.txt";

        //zapis pliku binarnego
        File f = new File(filePathBin);
        for(int i=2; f.exists(); i++) {
            f = new File(String.format(filePath+"%d.dat",i));
        }
        FileOutputStream file = new FileOutputStream(f);

        BufferedOutputStream buff = new BufferedOutputStream(file);
        DataOutputStream data = new DataOutputStream(buff);

        data.writeDouble(Math.round(signal.TimeStart*100.0)/100.0);
        data.writeDouble(Math.round(signal.Frequency*100.0)/100.0);
        data.writeDouble(Math.round(signal.type*100.0)/100.0);
        data.writeDouble(Math.round(signal.Y.size()*100.0)/100.0);
        for (Double i : signal.Y) {
            data.writeDouble(Math.round(i*100.0)/100.0);
        }
        data.close();
        buff.flush();
        file.close();

        //zapis pliku tekstowego
        File g = new File(filePathTxt);
        for(int i=2; g.exists(); i++) {
            g = new File(String.format(filePath+"%d.txt",i));
        }
        PrintWriter fileWriter = new PrintWriter(g);

        fileWriter.println("Time start:" + signal.TimeStart);
        fileWriter.println("Frequency: " + signal.Frequency);
        fileWriter.println("Type: " + signal.type);
        fileWriter.println("Size: " + signal.Y.size());
        for (int i=0; i<signal.Y.size(); i++) {
            fileWriter.println(String.valueOf(Math.round(signal.Y.get(i)*100.0)/100.0));
        }
        fileWriter.close();
    }

    public void btnSave(ActionEvent actionEvent) throws IOException {
        String filePath = String.valueOf(idCombo.getSelectionModel().selectedItemProperty().getValue());
        saveSignalToFile(filePath,signal);
    }

    public void btnLoad(ActionEvent actionEvent) throws IOException {
        loadSignalFromFile(1);
    }

    public void btnLoadTwo(ActionEvent actionEvent) throws IOException {
        loadSignalFromFile(2);
    }

    public void loadSignalFromFile(int signal) throws IOException {
        List<Double> readData = new ArrayList<>();
        List<Double> newY = new ArrayList<>();
        List<Double> newX = new ArrayList<>();

        //okno dialogowe do wybierania pliku
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        FileInputStream openedFile = new FileInputStream(selectedFile);
        BufferedInputStream reader = new BufferedInputStream(openedFile);
        DataInputStream dataInputStream = new DataInputStream(openedFile);

        int ch;

        try {
            while (true) {
                Double data = dataInputStream.readDouble();
                System.out.println(data);
                readData.add(data);
            }
        }
        catch (EOFException ignore) {
        }

        double signalTimeStart = readData.get(0);
        double signalFrequency = readData.get(1);
        int size = readData.get(3).intValue();

        for (int i=0; i<size; i++) {
            newY.add(readData.get(i+4));
        }

        System.out.println("________");
        for (double i=0; i<newY.size(); i++) {
            newX.add(signalTimeStart+i/signalFrequency);
        }

        if(signal==1) {
            signalOne = new Signal(signalTimeStart,signalFrequency);
            signalOne.Y = newY;
            signalOne.X = newX;

            DataChart dataChart = new DataChart();
            dataChart.loadData(signalOne, 1); //FIXME wybór rodzaju wykresu

            loadHistogram(signalOne);
        }
        else {
            signalTwo = new Signal(signalTimeStart,signalFrequency);
            signalTwo.Y = newY;
            signalTwo.X = newX;

            DataChart dataChart = new DataChart();
            dataChart.loadData(signalTwo, 1); //FIXME wybór rodzaju wykresu

            loadHistogram(signalTwo);
        }
    }


    public void compute(ActionEvent actionEvent) throws IOException {
        String selectedOperation = idChoice.valueProperty().getValue().toString();
        m = selectedOperation;
        System.out.println("Wybrana operacja: "+ selectedOperation);

        List<Double> computeResult = new ArrayList<>();
        computeResult = Operations.computeSignals(signalOne.Y,signalTwo.Y,selectedOperation);

        System.out.println("________");
        for (Double d : computeResult) {
            System.out.println(d);
        }

        signalThree = new Signal(signalOne);
        signalThree.Y=computeResult;

        DataChart dataChart = new DataChart();
        dataChart.loadData(signalThree, 1); //FIXME wybór rodzaju wykresu

        loadHistogram(signalThree);

        String filePath = "wynik_"+selectedOperation;
        saveSignalToFile(filePath,signalThree);

        calculateParams(signalThree.Y);
    }

    public void conversionAC(ActionEvent actionEvent) {
        signal.QuantSamplingFreq = Double.parseDouble(idQuantSample.textProperty().get());
        signal.SamplesX = new ArrayList<>();
        signal.SamplesY = new ArrayList<>();
        signal.QuantizationX = new ArrayList<>();
        signal.QuantizationY = new ArrayList<>();

        signal.ZeroHoldX = new ArrayList<>();
        signal.ZeroHoldY = new ArrayList<>();

        signal.QuantXplot = new ArrayList<>();
        signal.QuantYplot = new ArrayList<>();

        signal.SincY = new ArrayList<>();
        signal.SincX = new ArrayList<>();

        //tworzenie współrzędnych wykresu próbkowania
        for (Double i = generator.TimeStart; i <= generator.TimeStart + generator.Time; i += 1/signal.QuantSamplingFreq) {
            signal.SamplesX.add(i);
            signal.SamplesY.add(generator.generate(i));
            System.out.println(i+"\t\t"+generator.generate(i));
        }

        DataChart dataChart = new DataChart();
        dataChart.loadTwice(signal, 1);

        //obliczanie wartości kwantyzacji
        double maxY = Collections.max(signal.SamplesY);
        double minY = Collections.min(signal.SamplesY);
        double range = signal.SamplesX.get(1)-signal.SamplesX.get(0);

        double step = (maxY-minY)/(Math.pow(2.0, (signal.QuantSamplingFreq-1)));

        for (int i=0; i<signal.SamplesY.size(); i++) {
            double value = signal.SamplesY.get(i);
            signal.QuantizationY.add(minY+(Math.round((value-minY)/step)*step));
            signal.QuantizationX.add(signal.SamplesX.get(i)-(range/2));             //przesunięcie
            //System.out.println("X = " + signal.QuantizationX.get(i) + ", Y = " + signal.QuantizationY.get(i)+"\n");
        }

        double g = range/100;

        for (int i=0; i<signal.QuantizationX.size(); i++) {
            for (double j=signal.QuantizationX.get(i); j<signal.QuantizationX.get(i)+range; j=j+g) {
                signal.QuantXplot.add(j);
                signal.QuantYplot.add(signal.QuantizationY.get(i));
            }
        }

        DataChart dataChart2 = new DataChart();
        dataChart2.loadTwice(signal, 3);

    }

    public void conversionCA(ActionEvent actionEvent) {

        int selectedOption = idRecoChoice.getSelectionModel().selectedIndexProperty().get();
        System.out.println("Wybrana opcja rekonstrukcji: " + selectedOption);
        List<Double> tempY = new ArrayList<>();
        switch (selectedOption) {
            case 0:
            {

                //Ekstrapolacja zerowego rzędu (ZeroHold)
                double range = signal.SamplesX.get(1)-signal.SamplesX.get(0);
                double g = range/100;

                for (int i=0; i<signal.SamplesX.size(); i++) {
                    for (double j=signal.SamplesX.get(i); j<signal.SamplesX.get(i)+range; j=j+g) {
                        signal.ZeroHoldX.add(j);
                        tempY.add(signal.QuantizationY.get(i));
                        signal.ZeroHoldY.add(signal.SamplesY.get(i));
                    }
                }

                DataChart dataChart = new DataChart();
                dataChart.loadTwice(signal, 4);

                for (int i=0; i<tempY.size();i++) {
                    System.out.println(tempY.get(i));
                }

                calculateErrors(tempY,signal.ZeroHoldY);
            }
            break;

            case 1:
            {
                //Interpolacja pierwszego rzędu
                DataChart dataChart = new DataChart();
                dataChart.loadTwice(signal, 5);

                calculateErrors(signal.QuantizationY,signal.SamplesY);
            }
            break;

            case 2:
            {
                //rekonstrukcja w oparciu o sinc
                signal.calculateSincRecon();
                DataChart dataChart = new DataChart();
                dataChart.loadTwice(signal, 6);

                calculateErrors(signal.Y,signal.SincY);
            }
            break;

            default:
                break;
        }

    }
}
