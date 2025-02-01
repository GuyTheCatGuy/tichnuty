public class Person {
    private SerialNum serialNum;
    private final String name;

    public Person(int serial, String name) {
        this.serialNum = new SerialNum(serial);
        this.name = name;
    }

    public SerialNum getKey() {
        return serialNum;
    }

    public void setKey(SerialNum key) {
        this.serialNum = key;
    }

    @Override
    public String toString() {
        return "Person{" + "serialNum=" + serialNum + ", name='" + name + "'" + '}';
    }
}
