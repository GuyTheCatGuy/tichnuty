package data_structures.efficient_doubly_linked_list;

public class DoubleNode<T> {
    private DoubleNode<T> prev;
    private DoubleNode<T> next;
    private T value;

    public DoubleNode(T value, DoubleNode<T> prev, DoubleNode<T> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public DoubleNode() {
        this(null, null, null);
    }

    public void connectBefore(DoubleNode<T> other) {
        this.next = other;
        if(other != null) {
            other.prev = this;
        }
    }

    public void connectAfter(DoubleNode<T> other) {
        this.prev = other;
        if(other != null) {
            other.next = this;
        }
    }

    public DoubleNode(T value) {
        this(value, null, null);
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DoubleNode<T> getPrev() {
        return this.prev;
    }

    public void setPrev(DoubleNode<T> prev) {
        this.prev = prev;
    }

    public DoubleNode<T> getNext() {
        return this.next;
    }

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    public void unlink() {
        if(this.prev != null) {
            this.prev.next = this.next;
        }
        if(this.next != null) {
            this.next.prev = this.prev;
        }
    }

    @Override
    public String toString(){
        if(value != null) {
            return this.value.toString();
        } else {
            return "nullValue";
        }
    }
}
