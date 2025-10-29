package tabular;

/**
 * Author: Jeremy Welty
 * Date: 10/5/25
 * Purpose: Holds a y value and an array of x values, has methods to return each.
 */


public class DataRow {

    // add fields here
    private double y;
    private double[] x;

    /**
     * @param y the dependent variable
     * @param x the array of independent variables
     */
    public DataRow(double y, double[] x)
    {
        this.y = y;
        this.x = x;
    }

    /**
     * @return the dependent variable
     */
    public double getDependentVariable() {
        return y;
    }

    /**
     * @return the array of independent variables
     */
    public double[] getIndependentVariables() {
        return x;
    }
}
