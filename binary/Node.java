package binary;

import java.util.Random;

public class Node implements Cloneable {
    private Node left;
    private Node right;
    private Op operation;
    protected int depth;

    public Node(Unop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public Node(Binop operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
        this.depth = 0;
    }

    public double eval(double[] values) {
        if (operation instanceof Unop) {
            return ((Unop) operation).eval(values);
        } else if (operation instanceof Binop) {
            return ((Binop) operation).eval(left.eval(values), right.eval(values));
        } else {
            System.err.println("Error: operation is not a Unop or a Binop!");
            return 0.0;
        }
    }

    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) {
        if (!(operation instanceof Binop)) return; // Unop does nothing

        if (depth >= maxDepth) {
            left = nf.getTerminal(rand);
            left.depth = depth + 1;
            right = nf.getTerminal(rand);
            right.depth = depth + 1;
            return;
        }

        int totalChoices = nf.getNumOps() + nf.getNumIndepVars() + 1;

        // Left child
        if (rand.nextInt(totalChoices) < nf.getNumOps()) {
            left = nf.getOperator(rand);
            left.depth = depth + 1;
            left.addRandomKids(nf, maxDepth, rand);
        } else {
            left = nf.getTerminal(rand);
            left.depth = depth + 1;
        }

        // Right child
        if (rand.nextInt(totalChoices) < nf.getNumOps()) {
            right = nf.getOperator(rand);
            right.depth = depth + 1;
            right.addRandomKids(nf, maxDepth, rand);
        } else {
            right = nf.getTerminal(rand);
            right.depth = depth + 1;
        }
    }

    @Override
    public String toString() {
        if (operation instanceof Unop) {
            return operation.toString();
        } else if (operation instanceof Binop) {
            return "(" + left.toString() + " " + operation.toString() + " " + right.toString() + ")";
        } else {
            return "<?>"; // Unknown op
        }
    }

    @Override
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Node can't clone.");
        }
        Node b = (Node) o;
        if (left != null) b.left = (Node) left.clone();
        if (right != null) b.right = (Node) right.clone();
        if (operation != null) b.operation = (Op) operation.clone();
        return b;
    }

    /**
     * Preorder traversal: visit self, then left, then right
     */
    public void traverse(Collector c) {
        c.collect(this);
        if (left != null) left.traverse(c);
        if (right != null) right.traverse(c);
    }

    /**
     * Swap this node's left child with trunk's left child
     */
    public void swapLeft(Node trunk) {
        Node temp = this.left;
        this.left = trunk.left;
        trunk.left = temp;
    }

    /**
     * Swap this node's right child with trunk's right child
     */
    public void swapRight(Node trunk) {
        Node temp = this.right;
        this.right = trunk.right;
        trunk.right = temp;
    }

    /**
     * Return true if this node is a leaf (Unop)
     */
    public boolean isLeaf() {
        return (operation instanceof Unop);
    }
}
