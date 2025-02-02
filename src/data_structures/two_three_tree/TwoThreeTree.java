package data_structures.two_three_tree;
import data_structures.key_management.*;
import main.TwoThreeTreePrinter;

public class TwoThreeTree<T extends Comparable<T>, E>{
    private final KeyManager<T, E> extractor;
    private TwoThreeNode<T, E> root;


    public TwoThreeTree(KeyManager<T, E> extractor) {
        this(null, null, extractor);

    }

    public TwoThreeTree(E leftSentinelVal, E rightSentinelVal, KeyManager<T, E> extractor){
        this.extractor = extractor;
        TwoThreeNode<T, E> root = new TwoThreeNode<>();
        InfiniteKey<T> plusInf = extractor.extractKey(1), minusInf = extractor.extractKey(-1);

        // was node before
        TwoThreeLeaf<T, E> left = new TwoThreeLeaf<>(minusInf, leftSentinelVal), right = new TwoThreeLeaf<>(plusInf, rightSentinelVal);

        //

        root.setChildren(left , right , null);
        this.root = root;
    }

    // delete
    public void printSize(){
        System.out.println(this.root.getSize());
    }
    //

    public E getMax(){
        TwoThreeLeaf<T, E> maxNode = this.root.getMax();
        if(maxNode != null){
            return maxNode.getValue();
        } else {
            return null;
        }
    }

    public E getMin(){
        TwoThreeLeaf<T, E> minNode = this.root.getMin();
        if(minNode != null){
            return minNode.getValue();
        } else {
            return null;
        }
    }

    public void insert(E value) {
        InfiniteKey<T> key = this.extractor.extractKey(value);
        TwoThreeNode<T, E> leaf = new TwoThreeLeaf<T, E>(key, value);
        this.insert(leaf);
    }


    public int countBefore(T key) {
        return this.root.countLessEquals(new InfiniteKey<>(key));
    }

    public E tightestUpperBound(T key) {
        TwoThreeLeaf<T, E> upper = this.root.tightestUpperBound(new InfiniteKey<>(key));
        return upper.getValue();
    }

    public int countInRange(T start, T end) {
        int beforeStart = this.root.countLessEquals(new InfiniteKey<>(start)),
                beforeEnd = this.root.countLessEquals(new InfiniteKey<>(end));
        System.out.println("before start = " + beforeStart);

        System.out.println("before end = " + beforeEnd);
        boolean found = this.extractor.extractKey(this.tightestUpperBound(start)).compareTo(new InfiniteKey<T>(end)) <= 0;

        if(!found) {
            return 0;
        } else {
            return beforeEnd - beforeStart + 1;
        }
    }

    private void insert(TwoThreeNode<T, E> z) {
        TwoThreeNode<T, E> y = this.root;

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
                x.updateKeyAndSize();
            }
        }

        if(z != null) {
            TwoThreeNode<T, E> w = new TwoThreeNode<T, E>();
            w.setChildren(x, z, null);
            this.root = w;
        }
    }

    public void reassignKey(T oldKey) {
        // get leaf with old key
        TwoThreeLeaf<T, E> leaf = this.root.search(new InfiniteKey<>(oldKey));
        E value = leaf.getValue();
        // delete from tree
        this.delete(leaf);
        // insertion assigns relevant key
        insert(value);
    }


    public E search(T key) {
        TwoThreeLeaf<T, E> x = this.root.search(new InfiniteKey<T>(key));
        if(x != null) {
            return x.getValue();
        } else {
            return null;
        }
    }

    //delete function
    public void print(){
        TwoThreeTreePrinter printer = new TwoThreeTreePrinter();
        printer.printTree(this.root);
    }



// delete function
    public void printMinPath() {

        TwoThreeNode<T, E> y = this.root.getLeft();
        while(! (y instanceof TwoThreeLeaf)) {
            //System.out.println(y.getKey());
            y = y.getLeft();
        }
        TwoThreeNode<T, E> x = y.getParent();
        System.out.println(x.getMiddle().getKey());
        System.out.println("Done, and leftmost is " + x.getLeft().getKey() + '\n');
    }

    public void printMaxPath() {
        //System.out.println("Starting..");
        //System.out.println("root: " + this.root.getKey());

        TwoThreeNode<T, E> y;

        if(this.root.getRight() != null) {
            y = root.getRight();
        } else {
            y = root.getMiddle();
        }

        while(! (y instanceof TwoThreeLeaf)) {
            if(y.getRight() != null) {
                y = y.getRight();
            } else {
                y = y.getMiddle();
            }
        }
    }

    public void delete(T key) {
        TwoThreeLeaf<T,E> leaf = this.root.search(new InfiniteKey<T>(key));



        if(leaf != null) {
            this.delete(leaf);
        }
    }

    public void delete(TwoThreeNode<T, E> x) {

        //System.out.println("boutta delete, this is before:");
        //print();

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
                y.updateKeyAndSize();
                y = y.getParent();
            } else {
                if(y != this.root) {
                    y = y.borrowOrMerge();
                } else {
                    this.root = y.getLeft();
                    y.getLeft().setParent(null);
                    y.unlink();
                    //System.out.println("this is after");
                    //print();
                    return;
                }
            }
        }
        //System.out.println("this is after");
        //print();
    }
}
