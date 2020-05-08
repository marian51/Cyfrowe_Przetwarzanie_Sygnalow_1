package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Signal {
    public double TimeStart;
    public double Time;
    public double Frequency;
    public byte type=0;
    public List<Double> X;
    public List<Double> Y;
    public List<Triplet<Double, Double, Integer>> histogram;

    //zmienne potrzebne do kwantyzacji i rekonstrukcji sygnału
    public int QuantStep;
    public double QuantSamplingFreq;
    public int QuantSamples;
    public List<Double> SamplesX;
    public List<Double> SamplesY;
    public List<Double> QuantizationX;
    public List<Double> QuantizationY;
    public List<Double> QuantXplot;
    public List<Double> QuantYplot;
    public List<Double> ZeroHoldX;
    public List<Double> ZeroHoldY;

    public Signal(double timeStart, double frequency) {
        TimeStart = timeStart;
        Frequency = frequency;
        List<Double> X = new ArrayList<Double>();
        List<Double> Y = new ArrayList<Double>();

    }

    public Signal(Signal signalOne) {
        TimeStart = signalOne.TimeStart;
        Frequency = signalOne.Frequency;
        X = signalOne.X;
        Y = signalOne.Y;
    }

    public void getHistogram(int value) {

        histogram = new ArrayList<Triplet<Double, Double, Integer>>();

        double max = Collections.max(Y);
        double min = Collections.min(Y);
        double range = max - min;
        double interval = range / value;
        double intervalMin, intervalMax;
        int number;

        for (int i = 0; i < value - 1; i++) {
            number=0;
            for (int j = 0; j < Y.size(); j++) {
                double t = Y.get(j);
                if ((t>=min+(interval*i)) && (t<min+(interval*(i+1)))) {
                    number++;
                }
            }
            intervalMin = (double) Math.round( (min+(interval*i))*100 )/100;
            System.out.println("intervalMin = " + intervalMin);
            intervalMax = (double) Math.round( (min+interval*(i+1))*100 )/100;
            System.out.println("intervalMax = " + intervalMax);
            System.out.println("number = " + number + "\n");
            Triplet triplet = new Triplet(intervalMin,intervalMax,number);
            histogram.add(triplet);
        }

        number=0;
        for (int j = 0; j < Y.size(); j++) {
            double t = Y.get(j);
            if ((t>=min+(interval*(value-1))) && (t<=min+(interval*value))) {
                number++;
            }
        }
        intervalMin = (double) Math.round( (min+(interval*(value-1)))*100 )/100;
        intervalMax = (double) Math.round( (min+(interval*value))*100 )/100;
        Triplet triplet = new Triplet(intervalMin,intervalMax,number);
        histogram.add(triplet);

        

    }
}
