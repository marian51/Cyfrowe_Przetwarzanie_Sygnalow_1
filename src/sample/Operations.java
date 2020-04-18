package sample;

import java.util.List;

public class Operations {
    public Signal signal;

    public Operations(Signal signal) {
        this.signal = signal;
    }

    public Operations(){

    }

    public static double AverageSignal(List<Double> Xs) {
        double result;
        double sum=0;
        for (int i=0; i<Xs.size(); i++) {
            sum+=Xs.get(i);
        }
        result = sum / Xs.size();
        return result;
    }

    public static double AverageAbsSignal(List<Double> Xs) {
        double result;
        double sum=0;
        for (int i=0; i<Xs.size(); i++) {
            sum+=Math.abs(Xs.get(i));
        }
        result = sum / Xs.size();
        return result;
    }

    public static double AveragePowerSignal(List<Double> Xs) {
        double result;
        double sum=0;
        for (int i=0; i<Xs.size(); i++) {
            sum+=Math.pow(Xs.get(i),2);
        }
        result = sum / Xs.size();
        return result;
    }

    public static double EffectiveValueSignal(List<Double> Xs) {
        double result;
        result = Math.sqrt(AveragePowerSignal(Xs));
        return result;
    }

    public static double VarianceSignal(List<Double> Xs) {
        double result, avg;
        avg = AverageSignal(Xs);
        double sum=0;
        for (int i=0; i<Xs.size(); i++) {
            sum+=Math.pow((Xs.get(i)-avg),2);
        }
        result = sum / Xs.size();
        return result;
    }

    public static void calculateParams() {

    }
}
