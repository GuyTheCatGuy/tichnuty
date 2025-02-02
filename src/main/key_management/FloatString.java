package main.key_management;

public class FloatString implements Comparable<FloatString>, Cloneable {
    private Float num;
    private String str;

    public FloatString(Float num, String str) {
        this.num = num;
        this.str = str;
    }

    @Override
    public int compareTo(FloatString o) {
        int FloatComparison = this.num.compareTo(o.num);
        if(FloatComparison == 0) {
            return this.str.compareTo(o.str);
        } else {
            return FloatComparison;
        }
    }

    @Override
    public String toString() {
        return "(" + this.num + ", " + this.str + ")";
    }

    // maybe?
    @Override
    public Object clone() {
        try{
            FloatString copy = (FloatString) super.clone();
            return copy;

        } catch (CloneNotSupportedException e){
            return null;
        }
    }
}
