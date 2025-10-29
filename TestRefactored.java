import binary.*;
import java.util.Random;

public class TestRefactored {
    static int numIndepVars = 3;
    static int maxDepth = 5;
    static Random rand = new Random();

    public static void main(String[] args) {
        double[] data = new double[3];
        data[0] = 3.14;
        data[1] = 2.78;
        data[2] = 1.0;

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        NodeFactory nf = new NodeFactory(ops, numIndepVars);

        Node root = nf.getOperator(rand);
        root.addRandomKids(nf, maxDepth, rand);

        String expr = root.toString();
        double result = root.eval(data);

        System.out.println(expr + " = " + String.format("%.2f", result));
    }
}
