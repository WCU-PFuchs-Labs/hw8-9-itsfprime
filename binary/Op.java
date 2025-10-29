package binary;

public class Op implements Cloneable {
    @Override
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Op can't clone.");
        }
        return o;
    }
}