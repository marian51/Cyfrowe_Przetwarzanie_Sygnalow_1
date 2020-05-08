package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Operations {
    public Signal signal;
    public String selected;

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

    public static List<Double> AdditionSignals(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>();
        for (int i=0; i<first.size(); i++) {
            result.add(first.get(i)+second.get(i));
        }

        return result;
    }

    public static List<Double> SubstractionSignals(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>();
        for (int i=0; i<first.size(); i++) {
            result.add(first.get(i)-second.get(i));
        }

        return result;
    }

    public static List<Double> MultiplicationSignals(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>();
        for (int i=0; i<first.size(); i++) {
            result.add(first.get(i)*second.get(i));
        }

        return result;
    }

    public static List<Double> DivisionSignals(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>();
        for (int i=0; i<first.size(); i++) {
            if(second.get(i)!=0) {
                result.add(first.get(i)/second.get(i));
            }
            else {
                result.add(Math.max(first.get(i),second.get(i)));    //FIXME problem dzielenia przez 0
            }
        }
        return result;
    }

    public static List<Double> computeSignals(List<Double> first, List<Double> second, String choice) {
        switch (choice) {
            case "Dodawanie":
                return AdditionSignals(first,second);

            case "Odejmowanie":
                return SubstractionSignals(first,second);

            case "Mnożenie":
                return MultiplicationSignals(first,second);

            case "Dzielenie":
                return DivisionSignals(first,second);

            default:
                return null;
        }

    }

    //miary z drugiego zadania
    public static double MeanSquaredError(List<Double> Ysampled, List<Double> Yrecon) {
        double sum = 0;
        double result;
        int N = Ysampled.size();

        for (int i=0; i<N; i++) {
            sum += Math.pow((Ysampled.get(i)-Yrecon.get(i)),2.0);
        }

        result = sum/N;
        return result;
    }

    public static double SignalToNoiseRatio(List<Double> Ysampled, List<Double> Yrecon) {
        double sum1 = 0;
        double sum2 = 0;
        double result;
        int N = Ysampled.size();

        for (int i=0; i<N; i++) {
            sum1 += Math.pow(Ysampled.get(i),2.0);
            sum2 += Math.pow((Ysampled.get(i)-Yrecon.get(i)),2.0);
        }

        result = 10 * Math.log10(sum1/sum2);

        return result;
    }

    public static double MaximumDifference(List<Double> Ysampled, List<Double> Yrecon) {
        int N = Ysampled.size();
        List<Double> temp = new ArrayList<>();
        double result;

        for (int i=0; i<N; i++) {
            temp.add(Math.abs(Ysampled.get(i)-Yrecon.get(i)));
        }

        result = Collections.max(temp);
        return result;
    }
}
