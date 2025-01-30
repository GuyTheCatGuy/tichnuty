
public class TwoThreeTree<T extends Comparable<T>, E extends HasInfiniteKey<T>, P extends KeyExtractor<T, E>>{
    private final P extractor;
    private TwoThreeNode<T, E> root;


    public TwoThreeTree(P extractor) {
        this.extractor = extractor;

        TwoThreeNode<T, E> root = new TwoThreeNode<>();
        InfiniteKey<T> plusInf = extractor.extractKey(1), minusInf = extractor.extractKey(-1);
        TwoThreeNode<T, E> plusInf = new TwoThreeNode<>();


        TwoThreeNode<T, E> minusInf = new TwoThreeLeaf<>(-1);
        TwoThreeNode<T, E> plusInf = new TwoThreeLeaf<>(1);
        root.setChildren(minusInf, plusInf, null);

        this.root = root;
        // delete
        System.out.println(root.getLeft() == null);
        // delete
    }



    public void insert(E value) {
        InfiniteKey<T> key = this.extractor.extractKey(value);
        InfiniteKeyValue<T, E> keyValue = new InfiniteKeyValue<>(key, value);
        TwoThreeNode<T, E> leaf = new TwoThreeLeaf<>(value);
        this.insert(leaf);
    }
    public void insert(TwoThreeNode<T, E> z) {
        TwoThreeNode<T, E> y = this.root;
        // delete
        System.out.println(y.getLeft() == null);
        // delete
        while(!(y instanceof TwoThreeLeaf)) {
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

    public E search(T key) {
        TwoThreeLeaf<T, E> x = this.root.search(key);
        if(x != null) {
            return x.getValue();
        } else {
            System.out.println("Nothing found");
            return null;
        }
    }


    public void printMinPath() {
        System.out.println("Starting..");
        System.out.println("root: " + this.root.getKey());
        TwoThreeNode<T, E> y = this.root.getLeft();
        while(! (y instanceof TwoThreeLeaf)) {
            System.out.println(y.getKey());
            y = y.getLeft();
        }
        TwoThreeNode<T, E> x = y.getParent();
        System.out.println(x.getMiddle().getKey());
        System.out.println("Done, and leftmost is " + x.getLeft().getKey() + '\n');
    }

    public void printMaxPath() {
        System.out.println("Starting..");
        System.out.println("root: " + this.root.getKey());

        TwoThreeNode<T, E> y;

        if(this.root.getRight() != null) {
            y = root.getRight();
        } else {
            y = root.getMiddle();
        }

        while(! (y instanceof TwoThreeLeaf)) {
            System.out.println(y.getKey());
            if(y.getRight() != null) {
                y = y.getRight();
            } else {
                y = y.getMiddle();
            }
        }
        TwoThreeNode<T, E> x = y.getParent();
        if(x.getRight() != null) {
            System.out.println(x.getMiddle().getKey());
            System.out.println("Done, and rightmost is " + x.getRight().getKey() + '\n');
        } else {
            System.out.println(x.getLeft().getKey());
            System.out.println("Done, and rightmost is " + x.getMiddle().getKey() + '\n');
        }
    }

    public void delete(T key) {
        System.out.println(this.search(key) + " will be deleted");
        this.delete(this.root.search(key));
    }

    public void delete(E value) {
        try {
            this.delete((T) value.getKey());
        } catch(ClassCastException e) {
            return;
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
