package sample;

public class Triplet<T, U, V> {

    private T first;
    private U second;
    private V thirs;

    public Triplet(T first, U second, V thirs) {
        this.first = first;
        this.second = second;
        this.thirs = thirs;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThirs() {
        return thirs;
    }

    public String toString () {
        return "f="+getFirst()+", s="+getSecond()+", t="+getThirs();
    }
}
