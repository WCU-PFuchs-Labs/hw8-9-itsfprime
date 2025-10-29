package binary;

import tabular.*;
import java.util.*;
import java.io.*;

public class Generation {
    private GPTree[] trees;
    private DataSet dataSet;
    private NodeFactory factory;
    private Random rand;
    private int size;

    public Generation(int size, int maxDepth, String fileName) {
        this.size = size;
        dataSet = new DataSet(fileName);
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        int numIndepVars = dataSet.getNumIndependentVariables();
        factory = new NodeFactory(ops, numIndepVars);
        rand = new Random();
        trees = new GPTree[size];

        for (int i = 0; i < size; ++i) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }
    }

    /**
     * Evaluate fitness for each GPTree in the population.
     */
    public void evalAll() {
        for (GPTree t : trees) {
            t.evalFitness(dataSet);
        }
        Arrays.sort(trees);
    }

    /**
     * Return the best GPTree in the population.
     */
    public GPTree getBest() {
        return trees[0];
    }

    /**
     * Print top n trees with their fitness values.
     */
    public void printTop(int n) {
        if (n > trees.length) n = trees.length;
        for (int i = 0; i < n; ++i) {
            System.out.println("Tree " + (i + 1) + ": " + trees[i]);
            System.out.println("Fitness: " + trees[i].getFitness());
        }
    }

    /**
     * Returns population size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns reference to all trees.
     */
    public GPTree[] getTrees() {
        return trees;
    }
}
