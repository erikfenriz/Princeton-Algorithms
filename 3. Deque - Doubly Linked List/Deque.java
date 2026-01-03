import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;

        if (size == 0) {
            head = newNode;
            tail = newNode;
            newNode.next = null;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        if (size == 0) {
            head = newNode;
            tail = newNode;
            newNode.prev = null;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item removedItem = head.item;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removedItem;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item removedItem = tail.item;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return removedItem;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.addLast(2);
        d.addFirst(0);
        System.out.println("Size: " + d.size());
        for (int x : d) {
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println("Remove first: " + d.removeFirst());
        System.out.println("Remove last: " + d.removeLast());
    }
}
