package binary;

/**
 * Base class for unary operations (like Const and Variable).
 */
public abstract class Unop extends Op {
     public abstract double eval(double[] values);
}