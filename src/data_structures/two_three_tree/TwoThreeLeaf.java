package data_structures.two_three_tree;
import data_structures.key_management.*;

public class TwoThreeLeaf<T extends Comparable<T>, E > extends TwoThreeNode<T, E>{
    private E value;

    public TwoThreeLeaf(InfiniteKey<T> key, E value) {
        super(key);
        this.value = value;

        if(key.countsAsValue()) {
            this.size = 1;
        } else {
            this.size = 0;
        }

    }


    public TwoThreeLeaf(InfiniteKey<T> key) {
        this(key, null);
    }

    public E getValue() {
        return this.value;
    }
}
