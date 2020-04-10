package sample;

public class SignalGenerator {
    public double Amplitude;
    public double TimeStart;
    public double Time;
    public double Period;
    public double TimeJump;
    public double Prob;
    public double Frequency;

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
