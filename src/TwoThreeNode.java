public class TwoThreeNode<T extends InfiniteKey>{
    private T key;
    private TwoThreeNode<T> left;
    private TwoThreeNode<T> middle;
    private TwoThreeNode<T> right;
    private TwoThreeNode<T> parent;


    private TwoThreeNode(T key, TwoThreeNode<T> left, TwoThreeNode<T> middle, TwoThreeNode<T> right,
                         TwoThreeNode<T> parent) {
        this.key = key;
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
    }


    // When children specified - assume is not leaf
    public TwoThreeNode(T key, TwoThreeNode<T> left, TwoThreeNode<T> middle, TwoThreeNode<T> right) {
        this(key, left, middle, right, null);
    }


    //
    public TwoThreeNode(T key, TwoThreeNode<T> parent) {
        this(key, null, null, null, parent);
    }

    public TwoThreeNode(T key) {
        this(key, null, null, null, null);
    }

    public TwoThreeNode<T> search(T key) {
        if(this.getClass().getSimpleName().equals("TwoThreeLeaf")){
            if(this.key.equals(key)) {
                return this;
            } else {
                return null;
            }
        }
        if(key.compareTo(this.left.key) <= 0) {
            return this.left.search(key);
        } else if(key.compareTo(this.middle.key) >= 0) {
            return this.middle.search(key);
        } else {
            return this.right.search(key);
        }
    }

    TwoThreeNode<T> minimum(){
        if(this.getClass().getSimpleName().equals("TwoThreeLeaf")){
            TwoThreeNode<T> x = this.parent.middle;
            if(x.key !="INFINITY") {
                return x;
            } else {
                //ERROR
            }
        }

        return this.left.minimum();
    }





























    public T getKey() {
        return this.key;
    }


    public void setKey(T key) {
        this.key = key;
    }

    public TwoThreeNode<T> getLeft() {
        return this.left;
    }
    public void setLeft(TwoThreeNode<T> left) {
        this.left = left;
    }
    public TwoThreeNode<T> getMiddle() {
        return this.middle;
    }
    public void setMiddle(TwoThreeNode<T> middle) {
        this.middle = middle;
    }
    public TwoThreeNode<T> getRight() {
        return this.right;
    }
    public void setRight(TwoThreeNode<T> right) {
        this.right = right;
    }

    public TwoThreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TwoThreeNode<T> parent) {
        this.parent = parent;
    }
}
