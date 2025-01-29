public class InfiniteKey implements Comparable<InfiniteKey> {
    private final int infinity;

    public InfiniteKey(int infinity) {
        this.infinity = interpretInfinity(infinity);
    }



    public static int interpretInfinity(int infinity) {
        if (infinity > 0) return 1;
        if (infinity < 0) return -1;
        return 0;
    }

    @Override
    public int compareTo(InfiniteKey other) {
        return this.infinity - other.infinity;
    }
}
