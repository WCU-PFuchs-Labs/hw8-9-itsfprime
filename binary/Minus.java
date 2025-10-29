package binary;

public class Minus extends Binop{

    @Override
    public double eval(double left, double right) {
        return left - right;
    }

    @Override
    public String toString(){
        return "-";
    }
}
