package binary;

/**
 * Represents a variable like X0, X1, X2, etc.
 */
public class Variable extends Unop {
    private int index;

    public Variable(int index) {
        this.index = index;
    }

    @Override
    public double eval(double[] values) {
        if (index < 0 || index >= values.length) {
            System.err.println("Variable index out of range: X" + index);
            return 0.0;
        }
        return values[index];
    }

    @Override
    public String toString() {
        return "X" + index;
    }
}