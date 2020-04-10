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
        if (time > TimeStart) {
            return Amplitude;
        }
        else if (time.equals(TimeStart)) {
            return 0.5;
        }
        else {
            return 0;
        }
    }

    //10. Impuls jednostkowy
    public double UnitaryImpulse (double time) {
        if (time == TimeStart){
            return 1; //FIXME tu może być A
        }
        else {
            return 0;
        }
    }

    public double getAmplitude() {
        return Amplitude;
    }

    public double getTimeStart() {
        return TimeStart;
    }

    public double getTime() {
        return Time;
    }

    public double getPeriod() {
        return Period;
    }

    public double getTimeJump() {
        return TimeJump;
    }

    public double getProb() {
        return Prob;
    }

    public double getFrequency() {
        return Frequency;
    }

    public void setAmplitude(double amplitude) {
        Amplitude = amplitude;
    }

    public void setTimeStart(double timeStart) {
        TimeStart = timeStart;
    }

    public void setTime(double time) {
        Time = time;
    }

    public void setPeriod(double period) {
        Period = period;
    }

    public void setTimeJump(double timeJump) {
        TimeJump = timeJump;
    }

    public void setProb(double prob) {
        Prob = prob;
    }

    public void setFrequency(double frequency) {
        Frequency = frequency;
    }
}
