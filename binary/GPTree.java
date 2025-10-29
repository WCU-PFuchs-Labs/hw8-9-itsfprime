package binary;

import tabular.*;
import java.util.*;

public class GPTree implements Collector, Comparable<GPTree>, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }

    GPTree() {
        root = null;
    }

    /**
     * @param - node The node to be collected.
     */
    @Override
    public void collect(Node node) {
        // Add node to crossNodes if it is not a leaf node
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }
    }



    // DO NOT EDIT code below for Homework 8.
    // If you are doing the challenge mentioned in
    // the comments above the crossover method
    // then you should create a second crossover
    // method above this comment with a slightly
    // different name that handles all types
    // of crossover.


    /**
     * This initializes the crossNodes field and
     * calls the root Node's traverse method on this
     * so that this can collect the Binop Nodes.
     */
    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }

    /**
     * This returns a String with all of the binop Strings
     * separated by semicolons
     */
    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }


    /**
     * this implements left child to left child
     * and right child to right child crossover.
     * Challenge: additionally implement left to
     * right child and right to left child crossover.
     */
    public void crossover(GPTree tree, Random rand) {
        // find the points for crossover
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();
        // get the connection points
        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);


        if(left) {
            thisTrunk.swapLeft(treeTrunk);

        } else {
            thisTrunk.swapRight(treeTrunk);
        }

    }



    public String toString() {
        return root.toString();
    }

    public double eval(double[] data) {
        return root.eval(data);
    }



    // ===================== ADDED FOR CHECKPOINT 1 =====================


    /**
     * Evaluates fitness over all rows of a DataSet.
     */
    public void evalFitness(DataSet dataSet) {
        double sum = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double predicted = eval(row.getIndependentVariables());
            double actual = row.getDependentVariable();
            double diff = predicted - actual;
            sum += diff * diff;
        }
        fitness = sum;
    }

    /**
     * Returns the most recently computed fitness value.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Compares GPTrees by fitness value.
     */
    @Override
    public int compareTo(GPTree t) {
        if (this.fitness < t.fitness) return -1;
        if (this.fitness > t.fitness) return 1;
        return 0;
    }

    /**
     * Equality is defined as equal fitness.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof GPTree)) return false;
        GPTree t = (GPTree) o;
        return this.compareTo(t) == 0;
    }

    /**
     * Deep clone of the GPTree.
     */
    @Override
    public Object clone() {
        try {
            GPTree copy = (GPTree) super.clone();
            copy.root = (Node) root.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
