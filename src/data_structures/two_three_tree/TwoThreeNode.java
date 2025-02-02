package data_structures.two_three_tree;
import data_structures.key_management.*;

public class TwoThreeNode<T extends Comparable<T>, E>{
    protected InfiniteKey<T> key;
    private TwoThreeNode<T, E> left;
    private TwoThreeNode<T, E> middle;
    private TwoThreeNode<T, E> right;
    private TwoThreeNode<T, E> parent;
    int size;

    public int getSize(){
        return this.size;
    }

    /*
    private data_structures.two_three_tree.TwoThreeNode(data_structures.key_management.InfiniteKey<T> key, data_structures.two_three_tree.TwoThreeNode<T, E> left, data_structures.two_three_tree.TwoThreeNode<T, E> middle, data_structures.two_three_tree.TwoThreeNode<T, E> right,
                         data_structures.two_three_tree.TwoThreeNode<T, E> parent) {
        this.key = key;
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
    }
*/

    public TwoThreeNode(){
        //this(null, null, null, null, null);
        this(null);
    }


    public TwoThreeNode(InfiniteKey<T> key) {
        //this(key, null, null, null, null);
        this.key = key;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = null;
        this.size = 0;
    }




    /* delete
    public data_structures.two_three_tree.TwoThreeNode(data_structures.key_management.InfiniteKey<T> key, data_structures.two_three_tree.TwoThreeNode<T, E> left, data_structures.two_three_tree.TwoThreeNode<T, E> middle, data_structures.two_three_tree.TwoThreeNode<T, E> right) {

        this(key, left, middle, right, null);
    }
    */

    public void updateKeyAndSize(){
        int size = 0;
        this.key = this.left.key;
        size += this.left.size;
        if(this.middle != null) {
            this.key = this.middle.key;
            size += this.middle.size;
        }
        if(this.right != null) {
            this.key = this.right.key;
            size += this.right.size;
        }
        this.size = size;
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

        if(right != null) {
            right.parent = this;
        }
        this.right = right;
        this.updateKeyAndSize();
    }


    public TwoThreeLeaf<T, E> search(InfiniteKey<T> key) {
        //arbitrary choice between upper and lower bound
        TwoThreeLeaf<T, E> res = this.tightestUpperBound(key);
        if(res.key.compareTo(key) == 0) {
            return res;
        } else {
            return null;
        }
    }


   private TwoThreeNode<T, E> currentUpperBound(InfiniteKey<T> key) {
        if(this instanceof TwoThreeLeaf){
            return this;
        }

       if(key.compareTo(this.left.key) <= 0) {
           return this.left.currentUpperBound(key);
       } else if(key.compareTo(this.middle.key) <= 0) {
           return this.middle.currentUpperBound(key);
       } else {
           return this.right.currentUpperBound(key);
       }

   }

   public int countLessEquals(InfiniteKey<T> key){
        if(this instanceof TwoThreeLeaf){
            if(this.key.compareTo(key) == 0) {
                return 1;
            } else {
                return 0;
            }
        }



       if(key.compareTo(this.left.key) <= 0) {
           return this.left.countLessEquals(key);
       } else if(key.compareTo(this.middle.key) <= 0) {
           return this.left.size + this.middle.countLessEquals(key);
       } else {
           return this.left.size + this.middle.size + this.right.countLessEquals(key);
       }
   }



    public TwoThreeLeaf<T, E> tightestUpperBound(InfiniteKey<T> key) {
        if(this instanceof TwoThreeLeaf){
            return (TwoThreeLeaf<T, E>) this;
        }


        //return this.currentUpperBound(key).tightestUpperBound(key);
        return this.currentUpperBound(key).tightestUpperBound(key);

    }

    public TwoThreeNode<T, E> insertAndSplit( TwoThreeNode<T, E> z) {
        TwoThreeNode<T, E> left = this.left, middle = this.middle, right = this.right;
        if (right == null) {
            if (z.key.compareTo(this.left.key) < 0) {
                this.setChildren(z, left, middle);
            } else if (z.key.compareTo(this.middle.key) < 0) {
                this.setChildren(left, z, middle);
            } else {
                this.setChildren(left, middle, z);
            }
            return null;
        }

        TwoThreeNode<T, E> y = new TwoThreeNode<>();

        if (z.key.compareTo(this.left.key) < 0) {
            this.setChildren(z, left, null);
            y.setChildren(middle, right, null);
        } else if (z.key.compareTo(this.middle.key) < 0) {
            this.setChildren(left, z, null);
            y.setChildren(middle, right, null);
        } else if(z.key.compareTo(this.right.key) < 0){
            this.setChildren(left, middle, null);
            y.setChildren(z, right, null);
        }else {
            this.setChildren(left, middle, null);
            y.setChildren(right, z, null);
        }
        return y;
    }
/*
    public data_structures.two_three_tree.TwoThreeLeaf<T, E> getInfimum(data_structures.key_management.InfiniteKey<T> key){
        if(this.getClass() == data_structures.two_three_tree.TwoThreeLeaf.class){

        }

        if(this.key.compareTo(key) == 0) {
            return this.getMax();
        } else if(this.key.compareTo(key) > 0) {

        }
    }


    private data_structures.two_three_tree.TwoThreeNode<T, E> getSameLevelLeft() {
        if(this.parent == null) {
            return null;
        }

        if(this == this.parent.right) {
            return this.parent.middle;
        } else if(this == this.parent.middle) {
            return this.parent.left;
        }


    }
*/
    private TwoThreeNode<T, E> getRightMost() {
        if(this.right == null) {
            return this.middle;
        } else{
            return this.right;
        }
    }

/*
    public data_structures.two_three_tree.TwoThreeLeaf<T, E> getSupremum(data_structures.key_management.InfiniteKey<T> key){

        if(this.key.compareTo(key) == 0) {
            return this.getMax();
        }


    }
*/
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
                this.unlink(); // no
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
                this.unlink(); // no
                z.setChildren(x, z.right, null);
            }
            return z;
        }

        x = z.middle;
        if(x.right != null) {
            this.setChildren(x.right, this.left, null);
            x.setChildren(x.left, x.middle, null);
        } else {
            x.setChildren(x.left, x.middle, this.left);
            z.setChildren(z.left, x, null);
            this.unlink(); // no
        }
        return z;
    }

    public TwoThreeLeaf<T, E> getMin(){
        if(this.getClass() == TwoThreeLeaf.class) {
            TwoThreeLeaf<T, E> min;
            if(this.getKey().getInfinity() == -1) {
                min = (TwoThreeLeaf<T, E>) this.parent.middle;
                if(min.getKey().getInfinity() == 1) {
                    return null;
                }
            } else {
                min = (TwoThreeLeaf<T, E>) this;
            }
            return min;
        }
        return this.left.getMin();
    }

    public TwoThreeLeaf<T, E> getMax(){
        if(this.getClass() == TwoThreeLeaf.class) {
            TwoThreeLeaf<T, E> max;
            if(this.getKey().getInfinity() == 1) {
                if(this == this.parent.right) {
                    max = (TwoThreeLeaf<T, E>) this.parent.middle;
                } else {
                    max = (TwoThreeLeaf<T, E>) this.parent.left;
                }

                if(max.getKey().getInfinity() == -1) {
                    return null;
                }
            } else {
                max = (TwoThreeLeaf<T, E>) this;
            }
            return max;
        }

        return this.getRightMost().getMax();
    }

    public void unlink() {

    }


    public InfiniteKey getKey() {
        return this.key;
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