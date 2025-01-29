public abstract class InfiniteKey implements Comparable<InfiniteKey> {
    private final int infinity;
    public InfiniteKey(int infinity) {
        switch (infinity){
            case -1:
                this.infinity = -1;
                break;

            case 1:
                this.infinity = 1;
                break;

            default:
                this.infinity = 0;
                break;
        }
    }

    @Override
    public int compareTo(InfiniteKey other) {
        return this.infinity - other.infinity;
    }
}
