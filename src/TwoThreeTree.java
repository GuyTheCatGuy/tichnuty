
public class TwoThreeTree<T extends InfiniteKey, E extends HasInfiniteKey>{
    private TwoThreeNode<T, E> root;
    public TwoThreeTree() {
        TwoThreeNode<T, E> root = new TwoThreeNode<>();
        TwoThreeNode<T, E> minusInf = new TwoThreeLeaf<>(-1);
        TwoThreeNode<T, E> plusInf = new TwoThreeLeaf<>(1);
        root.setChildren(minusInf, plusInf, null);
    }
    public void insert(TwoThreeNode<T, E> z) {
        TwoThreeNode<T, E> y = this.root;
        while(!(y.getClass().getSimpleName().equals("TwoThreeLeaf"))) {
            if(z.getKey().compareTo(y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if(z.getKey().compareTo(y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        TwoThreeNode<T, E> x = y.getParent();
        z = x.insertAndSplit(z);

        while(x != this.root) {
            x = x.getParent();
            if(z != null) {
                z = x.insertAndSplit(z);
            } else {
                x.updateKey();
            }
        }

        if(z != null) {
            TwoThreeNode<T, E> w = new TwoThreeNode<>(1);
            w.setChildren(x, z, null);
            this.root = w;
        }
    }

    public void delete(TwoThreeNode<T, E> x) {
        TwoThreeNode<T, E> y = x.getParent();
        if(x == y.getLeft()) {
            y.setChildren(y.getMiddle(), y.getRight(), null);
        } else if(x == y.getMiddle()) {
            y.setChildren(y.getLeft(), y.getRight(), null);

        } else {
            y.setChildren(y.getLeft(), y.getMiddle(), null);
        }
        x.unlink();

        while(y != null) {
            if(y.getMiddle() != null) {
                y.updateKey();
                y = y.getParent();
            } else {
                if(y != this.root) {
                    y = y.borrowOrMerge();
                } else {
                    this.root = y.getLeft();
                    y.getLeft().setParent(null);
                    y.unlink();
                    return;
                }
            }
        }
    }
}
