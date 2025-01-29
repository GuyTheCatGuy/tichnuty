public class TwoThreeNode<T extends InfiniteKey, E extends HasInfiniteKey>{
    private InfiniteKey key;
    private TwoThreeNode<T, E> left;
    private TwoThreeNode<T, E> middle;
    private TwoThreeNode<T, E> right;
    private TwoThreeNode<T, E> parent;


    private TwoThreeNode(HasInfiniteKey value, TwoThreeNode<T, E> left, TwoThreeNode<T, E> middle, TwoThreeNode<T, E> right,
                         TwoThreeNode<T, E> parent) {
        if(value != null) {
            this.key = value.getKey();
        } else{
            this.key = null;
        }
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
    }


    public TwoThreeNode(){
        this(null, null, null, null, null);
    }


    public TwoThreeNode(int infinity) {
        this(null, null, null, null, null);
        this.key = new InfiniteKey(infinity);
    }


    public TwoThreeNode(int infinity, TwoThreeNode<T, E> parent) {
        this(null, null, null, null, parent);
        this.key = new InfiniteKey(infinity);
    }

    // When children specified - assume is not leaf
    public TwoThreeNode(HasInfiniteKey value, TwoThreeNode<T, E> left, TwoThreeNode<T, E> middle, TwoThreeNode<T, E> right) {
        this(value, left, middle, right, null);
    }


    //
    public TwoThreeNode(HasInfiniteKey value, TwoThreeNode<T, E> parent) {
        this(value, null, null, null, parent);
    }


    public TwoThreeNode(HasInfiniteKey value) {
        this(value, null, null, null, null);
    }

    public void updateKey(){
        this.key = this.left.key;
        if(this.middle != null) {
            this.key = this.middle.key;
        }
        if(this.right != null) {
            this.key = this.right.key;
        }
    }

    public void setChildren(TwoThreeNode<T, E> left, TwoThreeNode<T, E> middle, TwoThreeNode<T, E> right){
        if(left != null) {
            left.parent = this;
        }
        this.left = left;
        if(middle != null) {
            middle.parent = this;
        }
        this.middle = middle;

        if(this.right != null) {
            right.parent = this;
        }
        this.right = right;

        this.updateKey();
    }


    public TwoThreeNode<T, E> search(T key) {
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



    public TwoThreeNode<T, E> insertAndSplit( TwoThreeNode<T, E> z) {
        TwoThreeNode<T, E> left = this.left, middle = this.middle, right = this.right;
        if(right == null) {
            if(z.key.compareTo(this.left.key) < 0) {
                this.setChildren(z, left, middle);
            } else if (z.key.compareTo(this.middle.key) < 0) {
                this.setChildren(left, z, middle);
            } else {
                this.setChildren(left, middle, z);
            }
            return null;
        }

        TwoThreeNode<T, E> y = new TwoThreeNode<>();

        if(z.key.compareTo(this.left.key) < 0) {
            this.setChildren(z, left, null);
            y.setChildren(middle, right, null);
        } else if(z.key.compareTo(this.middle.key) < 0){
            this.setChildren(left, z, null);
            y.setChildren(middle, right, null);
        } else {
            this.setChildren(left, null, null);
            y.setChildren(right, z, null);
        }
        return y;
    }

    public TwoThreeNode<T, E> borrowOrMerge(){
        TwoThreeNode<T, E> z = this.parent, x;
        if(this == z.left) {
            x = z.middle;
            if(x.right != null) {
                this.setChildren(this.left, x.left, null);
                x.setChildren(x.middle, x.right, null);
            } else {
                x.setChildren(this.left, x.left, null);
                z.setChildren(x, z.right, null);
                this.unlink(); // needed?
            }
            return z;
        }

        if(this == z.middle) {
            x = z.left;
            if(x.right != null) {
                this.setChildren(x.right, this.left, null);
                x.setChildren(x.left, x.middle, null);
            } else {
                x.setChildren(x.left, x.middle, this.left);
                this.unlink();
                z.setChildren(x, z.right, null);
            }
            return z;
        }

        x = z.middle;
        if(x.right != null) {
            this.setChildren(x.right, this.left, null);
            x.setChildren(x.left, x.middle, null);
        } else {
            z.setChildren(z.left, x, null);
            this.unlink();
        }
        return z;
    }

    public void unlink() {

    }

    public InfiniteKey getKey() {
        return this.key;
    }


    public void setKey(T key) {
        this.key = key;
    }

    public TwoThreeNode<T, E> getLeft() {
        return this.left;
    }
    public void setLeft(TwoThreeNode<T, E> left) {
        this.left = left;
    }
    public TwoThreeNode<T, E> getMiddle() {
        return this.middle;
    }
    public void setMiddle(TwoThreeNode<T, E> middle) {
        this.middle = middle;
    }
    public TwoThreeNode<T, E> getRight() {
        return this.right;
    }
    public void setRight(TwoThreeNode<T, E> right) {
        this.right = right;
    }

    public TwoThreeNode<T, E> getParent() {
        return parent;
    }

    public void setParent(TwoThreeNode<T, E> parent) {
        this.parent = parent;
    }
}