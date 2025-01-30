public class SerialNum extends InfiniteKey {
    private final int serial;

    public SerialNum(int serial) {
        super(0);
        this.serial = serial;
    }

    @Override
    public int compareTo(InfiniteKey o) {
        if (o instanceof SerialNum) {
            return Integer.compare(this.serial, ((SerialNum) o).serial);
        }
        return super.compareTo(o);
    }

    @Override
    public String toString() {
        return "(" + this.serial +  ',' + super.infinity + ')';
    }
}
