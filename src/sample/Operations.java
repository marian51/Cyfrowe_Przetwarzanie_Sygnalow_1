package sample;

import java.util.ArrayList;
import java.util.List;

public class Operations {
    public Signal signal;

    public Operations(Signal signal) {
        this.signal = signal;
    }

    public Operations(){

    }

    public static double AverageSignal(List<Double> Ys) {
        double result;
        double sum=0;
        for (int i=0; i<Ys.size(); i++) {
            System.out.println("Wartość: "+Ys.get(i));
            sum+=Ys.get(i);
        }
        result = sum / Ys.size();
        System.out.println("Suma="+sum+", próbki="+Ys.size()+", wynik="+result);
        return result;
    }

    public static double AverageAbsSignal(List<Double> Ys) {
        double result;
        double sum=0;
        for (int i=0; i<Ys.size(); i++) {
            sum+=Math.abs(Ys.get(i));
        }
        result = sum / Ys.size();
        return result;
    }

    public static double AveragePowerSignal(List<Double> Ys) {
        double result;
        double sum=0;
        for (int i=0; i<Ys.size(); i++) {
            sum+=Math.pow(Ys.get(i),2);
        }
        result = sum / Ys.size();
        return result;
    }

    public static double EffectiveValueSignal(List<Double> Ys) {
        double result;
        result = Math.sqrt(AveragePowerSignal(Ys));
        return result;
    }

    public static double VarianceSignal(List<Double> Ys) {
        double result, avg;
        avg = AverageSignal(Ys);
        double sum=0;
        for (int i=0; i<Ys.size(); i++) {
            sum+=Math.pow((Ys.get(i)-avg),2);
        }
        result = sum / Ys.size();
        return result;
    }

    public static void calculateParams(List<Double> Ys) {

    }

    public static List<Double> AdditionSignals(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>();
        for (int i=0; i<Math.max(first.size(),second.size()); i++) {
            result.add(first.get(i)+second.get(i));
        }

        return result;
    }
}
