public class HasInfiniteKey<T> {
    private InfiniteKey key;
    public HasInfiniteKey(InfiniteKey key) {
        this.key = key;
    }
    public InfiniteKey getKey() {
        return this.key;
    }
    public void setKey(InfiniteKey key) {
        this.key = key;
    }
}
