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
    public List<Double> SincX;
    public List<Double> SincY;

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

    public double Sinc(double t) {
        if(Math.abs(t) < 0 + 0.000001) {
            return 1.0;
        }
        else {
            return ((Math.sin(Math.PI*t))/(Math.PI*t));
        }
    }

    public void calculateSincRecon() {
        SincX = new ArrayList<>();
        SincY = new ArrayList<>();
        int points = (int)(Frequency*Time);
        double interval = 1/Frequency;
        double tempp = 0;

        for (double t=TimeStart; t<Time; t+=1/Frequency) {
            double value = 0;

            for (int n=0; n<points; n++) {
                value += Y.get(n) * Sinc(t/interval-n);
            }

            SincY.add(value);
            SincX.add(t);
        }

        /*for (int i=0; i<SincX.size(); i++) {
            System.out.println(SincX.get(i));
        }
        System.out.println("______");
        for (int i=0; i<SincY.size(); i++) {
            System.out.println(SincY.get(i));
        }*/

        /*for (int i=0; i<points; i++) {
            double y = 0;
            for (int n = 0; n<QuantizationX.size(); n++) {
                if (QuantizationX.get(n) > (i * interval)) {
                    break;
                }
                y = QuantizationY.get(n);
            }

            for (int j=0; j<QuantizationX.size(); j++) {
                tempp = QuantizationY.get(j);
                tempp += y * Sinc((QuantizationX.get(j)/interval-i));

                //SincX.add(QuantizationX.get(j));
            }
            SincY.add(tempp);
        }

        for (int k=0; k<QuantizationX.size(); k++) {

        }

        for (int i=0; i<SincX.size(); i++) {
            System.out.println("SincX = "+SincX.get(i)+", SincY = "+SincY.get(i));
        }*/
    }
}
