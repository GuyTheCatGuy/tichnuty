public class TwoThreeLeaf<T extends InfiniteKey<T>, E extends HasInfiniteKey<T>> extends TwoThreeNode<T, E>{
    private E value;
    TwoThreeLeaf(E value) {
        super(value);
        this.value = value;
    }

    TwoThreeLeaf(int infinity) {
        super(infinity);
    }

    public E getValue() {
        return this.value;
    }
}
