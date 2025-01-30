public class Person extends HasInfiniteKey<SerialNum> {
    private final SerialNum serialNum;
    private final String name;

    public Person(int serial, String name) {
        super(new SerialNum(serial));
        this.serialNum = new SerialNum(serial);
        this.name = name;
    }

    @Override
    public InfiniteKey getKey() {
        return serialNum;
    }

    @Override
    public String toString() {
        return "Person{" + "serialNum=" + serialNum + ", name='" + name + "'" + '}';
    }
}
