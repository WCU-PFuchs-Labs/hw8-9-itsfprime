package tabular;
/**
 * Author: Jeremy Welty
 * Date: 10/5/25
 * Purpose: Read data from a data file, store data in datarows.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet {

    // add fields here
    private ArrayList<DataRow> data;
    private int numIndepVariables;

    /**
     * @param filename the name of the file to read the data set from
     */
    public DataSet(String filename) {
        data = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            // Read header line (e.g., "quality, acidity, sugar")
            if (!scanner.hasNextLine()) {
                System.err.println("Empty file: " + filename);
                return;
            }

            String headerLine = scanner.nextLine();
            String[] headers = headerLine.split(",");
            numIndepVariables = headers.length - 1; // first column is dependent (y)

            // Read data rows
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue; // skip blank lines

                String[] parts = line.split(",");
                if (parts.length < numIndepVariables + 1) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    // First value is dependent (y)
                    double y = Double.parseDouble(parts[0].trim());

                    // Remaining values are independent (x)
                    double[] xValues = new double[numIndepVariables];
                    for (int i = 0; i < numIndepVariables; i++) {
                        xValues[i] = Double.parseDouble(parts[i + 1].trim());
                    }

                    data.add(new DataRow(y, xValues));

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid numeric row: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }


    /**
     * @return the list of rows
     */
    public ArrayList<DataRow> getRows() {
        return data;
    }

    /**
     * @return the number of independent variables in each row of the data set
     */
    public int getNumIndependentVariables() {
        return numIndepVariables;
    }
}
