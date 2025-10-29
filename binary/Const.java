package binary;

public class Const extends Unop {
    private double value;

    public Const(double d) {
        value = d;
    }

    @Override
    public double eval(double[] values) {
        return value;
    }

    @Override
    public String toString() {return String.format("%.2f", value);}
}