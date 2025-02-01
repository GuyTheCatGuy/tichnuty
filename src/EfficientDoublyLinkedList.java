import data_structures.TwoThreeTree;

public class EfficientDoublyLinkedList<T extends Comparable<T>, E> {

    private TwoThreeTree<T, DoubleNode<E>> tree;
    private KeyManager<T, DoubleNode<E>> keyManager;
    public EfficientDoublyLinkedList(KeyManager<T, DoubleNode<E>> keyManager) {
        this.tree = new TwoThreeTree<>(keyManager);
        this.keyManager = keyManager;
    }

    public void insert(E value) {
        DoubleNode<E> valueNode = new DoubleNode<>(value);
        this.tree.insert(valueNode);
        T key = this.keyManager.extractRawKey(valueNode);
        DoubleNode<E> next = this.tree.tightestUpperBound(key);
        valueNode.connectAfter(next.getPrev());
        valueNode.connectBefore(next);
        this.tree.insert(valueNode);
    }

    public int countValuesInRange(E lower, E upper) {
        return this.tree.countInRange(this.keyManager.extractRawKey(new DoubleNode<>(lower)), this.keyManager.extractRawKey(new DoubleNode<>(upper)));
    }

    public E[] getValuesInRange(E lower, E upper) {
        int count = this.countValuesInRange(lower, upper);
        if (count == 0) {
            return null;
        }

        DoubleNode<E> start = this.tree.tightestUpperBound(this.keyManager.extractRawKey(new DoubleNode<>(lower)));
        Object[] valuesInRange = new Object[count];

        for (int i = 0; i < count && start != null; i++) {
            valuesInRange[i] = start.getValue();
            start = start.getNext();
        }

        try {
            // Runtime check: verify all elements match E type
            for (Object obj : valuesInRange) {
                if (obj != null && !(obj.getClass().isInstance(lower))) {
                    throw new ClassCastException();
                }
            }
            return (E[]) valuesInRange;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
