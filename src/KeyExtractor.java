public interface KeyExtractor<T extends Comparable<T>, E> {

    public default InfiniteKey<T> extractKey(E value) {
       return new InfiniteKey<>(extractKeyAttributes(value));
    }

    public default InfiniteKey<T> extractKey(int infinity) {
        return new InfiniteKey<T>(infinity);
    }

    abstract T extractKeyAttributes(E value);
}
