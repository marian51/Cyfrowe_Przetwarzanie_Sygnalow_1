package sample;

import java.util.ArrayList;
import java.util.List;

public class Signal {
    public double TimeStart;
    public double Frequency;
    public double SamplingFreq;
    public List<Double> X;
    public List<Double> Y;

    public Signal(double timeStart, double frequency, double samplingFreq) {
        TimeStart = timeStart;
        Frequency = frequency;
        SamplingFreq = samplingFreq;
        List<Double> X = new ArrayList<Double>();
        List<Double> Y = new ArrayList<Double>();
    }
}
