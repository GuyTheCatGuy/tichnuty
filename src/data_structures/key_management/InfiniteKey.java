package data_structures.key_management;

public class InfiniteKey<T extends Comparable<T>> implements Comparable<InfiniteKey> {
    private final int infinity;
    private final T key;
    public InfiniteKey(int infinity) {
        this.infinity = interpretInfinity(infinity);
        this.key = null;
    }

    public InfiniteKey(T key) {
        this.key = key;
        this.infinity = 0;
    }

    public int getInfinity() {
        return this.infinity;
    }

    public static int interpretInfinity(int infinity) {
        if (infinity > 0) return 1;
        if (infinity < 0) return -1;
        return 0;
    }

    @Override
    public int compareTo(InfiniteKey other) {
        if(other == null) {
            return 1;
        }

        int infinityDiff = this.infinity - other.infinity;
        if (infinityDiff != 0) {
             return infinityDiff;
        } else {
            return this.key.compareTo((T) other.key);
        }
    }

    @Override
    public String toString() {
        if(this.infinity == 1) return "inf";
        if(this.infinity == -1) return "-inf";
        return this.key.toString();
    }

    public boolean countsAsValue() {
        return this.infinity == 0;
    }
}
