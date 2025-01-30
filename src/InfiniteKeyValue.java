// supports only -inf, +inf, value=null
public class InfiniteKeyValue<T extends Comparable<T>, E> {
    private InfiniteKey<T> key;
    private E value;

    protected InfiniteKeyValue (InfiniteKey<T> key, E value) {
        this.key = key;
        this.value = value;
    }

    public InfiniteKeyValue (InfiniteKey<T> key) {
        this(key, null);
    }

    public InfiniteKey<T> getKey () {
        return this.key;
    }

    protected E getValue() {
        return this.value;
    }
}
