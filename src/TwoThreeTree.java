
public class TwoThreeTree<T extends Comparable<T> & Infinite >{
    private TwoThreeNode<T> root;
    public TwoThreeTree() {
        this.root = new TwoThreeNode<T>(null, false, null);
    }
}
