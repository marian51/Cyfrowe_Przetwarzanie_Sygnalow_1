package sample;

import java.util.Arrays;
import java.util.Random;

public class SignalGenerator {
    public double Amplitude;
    public double TimeStart;
    public double Time;
    public double Period;
    public double TimeJump;
    public double Prob;
    public double Frequency;
    public double Rate;

    public Random rand = new Random();

    public int selected;

    public SignalGenerator(int s) {
        selected = s;
    }

    //1. Szum o rozkładzie jednostkowym
    public double UnitaryDistributionNoise (double time) {
        return rand.nextDouble() * 2 * Amplitude - Amplitude; //Trzeba odwzorować zakres 0-1 na zakres A - -A
    }

    //2. Szum Gaussowski
    public double GaussianNoise (double time) {
        //double str = Amplitude / 3;
        double x1 = rand.nextDouble();
        double x2 = rand.nextDouble();

        double result = Math.sqrt(-2 * Math.log(x1)) * Math.sin(2 * Math.PI * x2);

        return result;
    }

    //3. Sygnał sinusoidalny
    public double SinusoidalSignal (double time) {
        return Amplitude * Math.sin((2 * Math.PI / Period) * (time - TimeStart));
    }

    //4. Sygnał sinusoidalny wyprostowany jednopołówkowo
    public double HalfStraightSinusoidalSignal (double time) {
        return 0.5 * Amplitude * ( Math.sin((2 * Math.PI / Period) * (time - TimeStart)) + Math.abs(Math.sin((2 * Math.PI / Period) * (time - TimeStart))));
    }

    //5. Sygnał sinusodialny wyprostowany dwupołówkowo
    public double FullStraightSinusoidalSignal (double time) {
        return Amplitude * Math.abs(Math.sin((2 * Math.PI / Period) * (time - TimeStart)));
    }

    //6. Sygnał prostokątny
    public double RectangularSignal (double time) {
        int k = (int)((time - TimeStart) / Period );
        //k = (int)((time / Period) - (TimeStart / Period));
        //k = (int)(1/Frequency);

        if (time >= (k * Period + TimeStart) && time < (Rate * Period + k * Period + TimeStart)) { //FIXME tutaj może być błąd z podawanymi wartościami
            return Amplitude;
        }
        else {
            return 0;
        }
    }

    //7. Sygnał prostokątny symetryczny
    public double SymmetricalRectangularSignal (double time) {
        int k = (int)((time - TimeStart) / Period );

        if (time >= (k * Period + TimeStart) && time < (Rate * Period + k * Period + TimeStart)) { //FIXME tutaj też może być błąd
            return Amplitude;
        }
        else {
            return -Amplitude;
        }
    }

    //8. Sygnał trójkątny
    public double TriangularSignal (double time) {
        int k = (int)((time - TimeStart) / Period );

        if (time >= (k * Period + TimeStart) && time < (Rate * Period + k * Period + TimeStart)) { //FIXME tutaj też może być błąd
            return ((Amplitude / (Rate * Period)) * (time - k * Period - TimeStart));
        }
        else {
            return ((-Amplitude / (Period * (1 - Rate))) * (time - k * Period - TimeStart)) + (Amplitude / (1 - Rate));
        }

    }

    //9. Sygnał jednostkowy
    public double UnitarySignal (Double time) {
        if (time > TimeJump) {
            System.out.println("==== Wartość " + time + " jest większa od " + TimeJump);
            return Amplitude;
        }
        else if (time.equals(TimeJump)) {
            return 0.5;
        }
        else {
            return 0;
        }
    }

    //10. Impuls jednostkowy
    public double UnitaryImpulse (double time) {
        if (time == TimeJump){
            return 1; //FIXME tu może być A
        }
        else {
            return 0;
        }
    }

    //11. Szum impulsowy
    public double NoiseImpulse (double time) {
        double random = rand.nextDouble();

        if (Prob > random) {
            return Amplitude;
        }
        else {
            return 0;
        }
    }

    public double generate(double t) {
        switch (selected) {
            case 0:
                return UnitaryDistributionNoise(t);

            case 1:
                return GaussianNoise(t);

            case 2:
                return SinusoidalSignal(t);

            case 3:
                return HalfStraightSinusoidalSignal(t);

            case 4:
                return FullStraightSinusoidalSignal(t);

            case 5:
                return RectangularSignal(t);

            case 6:
                return SymmetricalRectangularSignal(t);

            case 7:
                return TriangularSignal(t);

            case 8:
                return UnitarySignal(t);

            case 9:
                return UnitaryImpulse(t);

            case 10:
                return NoiseImpulse(t);

            default:
                return 0;
        }
    }

    public String toString () {
        return "Amplituda: " + Amplitude + "\n" + "Czas początkowy: " + TimeStart + "\n" + "Czas trwania: " +
                Time + "\n" + "Okres: " + Period + "\n" + "Czas skoku: " + TimeJump + "\n" + "Prawdopodobieństwo: " + Prob + "\n" + "Częstotliwość: " + Frequency;
    }


}
