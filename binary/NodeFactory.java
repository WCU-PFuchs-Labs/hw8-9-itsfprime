package binary;

import java.util.Random;

public class NodeFactory {
    private int numIndepVars;
    private Binop[] currentOps;

    public NodeFactory(Binop[] b, int numVars) {
        currentOps = b;
        numIndepVars = numVars;
    }

    public Node getOperator(Random rand) {
        int index = rand.nextInt(currentOps.length);
        Binop op = (Binop) currentOps[index].clone();
        return new Node(op, null, null);
    }

    public Node getTerminal(Random rand) {
        int r = rand.nextInt(numIndepVars + 1);
        if (r < numIndepVars) {
            return new Node(new Variable(r));
        } else {
            return new Node(new Const(rand.nextDouble()));
        }
    }

    public int getNumOps() {
        return currentOps.length;
    }

    public int getNumIndepVars() {
        return numIndepVars;
    }
}
