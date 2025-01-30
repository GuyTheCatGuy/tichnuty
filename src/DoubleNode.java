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
}
