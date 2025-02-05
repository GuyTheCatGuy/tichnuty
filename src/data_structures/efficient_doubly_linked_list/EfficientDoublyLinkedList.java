package data_structures.efficient_doubly_linked_list;
import data_structures.two_three_tree.TwoThreeTree;
import data_structures.key_management.*;

public class EfficientDoublyLinkedList<T extends Comparable<T>, E> {

    private TwoThreeTree<T, DoubleNode<E>> tree;
    private KeyManager<T, DoubleNode<E>> keyManager;

    public EfficientDoublyLinkedList(KeyManager<T, DoubleNode<E>> keyManager) {
        DoubleNode<E> left_sentinel = new DoubleNode<>(), right_sentinel = new DoubleNode<>();
        left_sentinel.connectBefore(right_sentinel);
        this.tree = new TwoThreeTree<>(left_sentinel, right_sentinel, keyManager);
        this.keyManager = keyManager;
    }

    public void insert(E value) {
        DoubleNode<E> valueNode = new DoubleNode<>(value);
        T key = this.keyManager.extractRawKey(valueNode);
        DoubleNode<E> next = this.tree.tightestUpperBound(key);
        this.tree.insert(valueNode);
        valueNode.connectAfter(next.getPrev());
        valueNode.connectBefore(next);
    }

    public int countValuesInRange(T lower, T upper) {
        return this.tree.countInRange(lower, upper);
    }

    public void delete(T key) {
        DoubleNode<E> valueNode = this.tree.search(key);
        valueNode.getPrev().connectBefore(valueNode.getNext());
        this.tree.delete(key);
    }

    public E[] getValuesFromBound(T lower, E[] values) {
        DoubleNode<E> curr = this.tree.tightestUpperBound(lower);
        for(int i = 0; i < values.length && curr != null; i++) {
            values[i] = curr.getValue();
            curr = curr.getNext();
        }
        return values;
    }

    public E[] getValuesInRange(T lower, T upper) {
        int count = this.countValuesInRange(lower, upper);

        // Return an empty array instead of null
        if (count == 0) {
            return (E[]) new Object[0]; // Unsafe, but avoids utils and reflection
        }

        DoubleNode<E> start = this.tree.tightestUpperBound(lower);
        E[] valuesInRange = (E[]) new Object[count];

        for (int i = 0; i < count && start != null; i++) {
            valuesInRange[i] = start.getValue();
            start = start.getNext();
        }

        return valuesInRange;
    }

    public void reassignKey(T oldKey) {
        DoubleNode<E> node = this.tree.search(oldKey);
        boolean res = node.getPrev() == node.getNext();

        node.getPrev().connectBefore(node.getNext());
        this.tree.reassignKey(oldKey);

        T newKey = this.keyManager.extractRawKey(node);
        DoubleNode<E> newNext = this.tree.search(newKey).getNext();

        node.connectAfter(newNext.getPrev());
        node.connectBefore(newNext);
    }

}
