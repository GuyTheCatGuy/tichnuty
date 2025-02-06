package data_structures.efficient_doubly_linked_list;
import data_structures.two_three_tree.TwoThreeTree;
import data_structures.key_management.*;
import main.TwoThreeTreePrinter;
import org.w3c.dom.ls.LSOutput;

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


        //System.out.println("inserting " + value + "... ");

        DoubleNode<E> valueNode = new DoubleNode<>(value);
        T key = this.keyManager.extractRawKey(valueNode);
        DoubleNode<E> next = this.tree.tightestUpperBound(key);
        this.tree.insert(valueNode);
        //sentinel should have pointer?
        //System.out.println(next + " - next");
        valueNode.connectAfter(next.getPrev());
        ///////valueNode.connectBefore(next);

        //print();
        //System.out.println("\n");

    }

    public int countValuesInRange(T lower, T upper) {
        return this.tree.countInRange(lower, upper);
    }

    public void delete(T key) {
        DoubleNode<E> valueNode = this.tree.search(key);
        //System.out.println("Deleting " + valueNode);

        //System.out.println("Before deletion: ");
        //this.tree.print();
        //System.out.println();
        ////////valueNode.getPrev().connectBefore(valueNode.getNext());
        valueNode.unlink();
        this.tree.delete(key);
        //System.out.println("After deletion: ");
        //this.tree.print();
        //System.out.println();
    }

/*
    public E[] getValuesInRange(T lower, T upper) {
        int count = this.countValuesInRange(lower, upper);
        if (count == 0) {
            return null;
        }

        DoubleNode<E> start = this.tree.tightestUpperBound(lower);
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
*/

    public E[] getValuesFromBound(T lower, E[] values) {
        DoubleNode<E> curr = this.tree.tightestUpperBound(lower);
        for(int i = 0; i < values.length && curr != null; i++) {
            values[i] = curr.getValue();
            curr = curr.getNext();
        }
        return values;
    }

    public E[] getValuesInRange2(T lower, T upper) {
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


    public void printTree(){
        this.tree.print();
    }

    public void reassignKey(T oldKey) {
        DoubleNode<E> node = this.tree.search(oldKey);
        System.out.println(node + " - trying to find node before reassignment");
        //System.out.println(node);
        System.out.println(node.getPrev());
        boolean res = node.getPrev() == node.getNext();
        System.out.println(res  + " - first infinite list check");

        //node.getPrev().connectBefore(node.getNext()); - instead:
        node.unlink();

        System.out.println("before reass, and key is" + oldKey + ", printing trees:");

        print();



        this.tree.print();
        //this.tree.reassignKey(oldKey);
        this.tree.delete(oldKey);
        print();


        res = node.getPrev() == node.getNext();
        System.out.println(res  + " - second infinite list check");

        T newKey = this.keyManager.extractRawKey(node);
        //successor
        DoubleNode<E> newNext = this.tree.tightestUpperBound(newKey);

        System.out.println(newNext + " plz");

        node.connectAfter(newNext.getPrev());
        //node.connectBefore(newNext);

        res = node.getPrev() == node.getNext();
        System.out.println(res  + " - third infinite list check");
        this.tree.insert(node);
    }

    public void print() {
        int count = -1;
        DoubleNode<E> curr = this.tree.getMin();

        while (curr != null) {
            System.out.print(curr.getValue() + " -> ");
            curr = curr.getNext();
            count++;
        }
        //System.out.println();
        //System.out.println("Total: " + count);
        //System.out.println(curr + " min");

        //System.out.println(this.tree.getMax() + " max");

    }
}