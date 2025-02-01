public class SerialNum implements Comparable<SerialNum> {
    private final int serial;

    public SerialNum(int serial) {
        this.serial = serial;
    }


    @Override
    public String toString() {
        return "" + this.serial;
    }

    @Override
    public int compareTo(SerialNum o) {

        try{
            return this.serial - o.serial;
        }
        catch (Exception e) {
            System.out.println("ERR");
            return 0;
        }
    }
}
