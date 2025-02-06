package data_structures.key_management;

public interface KeyManager<T extends Comparable<T>, E> {

    public default InfiniteKey<T> extractKey(E value) {
       return new InfiniteKey<>(extractRawKey(value));
    }

    public default InfiniteKey<T> extractKey(int infinity) {
        return new InfiniteKey<T>(infinity);
    }

    abstract T extractRawKey(E value);
}
