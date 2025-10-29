import binary.*;
import java.util.*;
import java.io.*;

public class TestGeneration {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = input.nextLine();

        int size = 500;
        int maxDepth = 5;

        System.out.println("Creating generation of " + size + " trees...");
        Generation generation = new Generation(size, maxDepth, fileName);

        System.out.println("Evaluating all trees...");
        generation.evalAll();

        System.out.println("Best Tree:");
        GPTree best = generation.getBest();
        System.out.println(best);
        System.out.printf("Best Fitness: %.3f%n", best.getFitness());

        // Print top 10 fitness values
        System.out.println("\nTop Ten Fitness Values:");
        GPTree[] trees = generation.getTrees();
        int limit = Math.min(10, trees.length);
        for (int i = 0; i < limit; i++) {
            System.out.printf("%.2f", trees[i].getFitness());
            if (i < limit - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
