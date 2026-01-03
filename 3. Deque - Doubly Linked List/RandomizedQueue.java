import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2]; // unchecked cast is fine
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        return items[index];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity]; // unchecked cast allowed
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] shuffled;
        private int current;

        public RandomizedQueueIterator() {
            shuffled = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                shuffled[i] = items[i];
            }
            StdRandom.shuffle(shuffled);
            current = 0;
        }

        public boolean hasNext() {
            return current < shuffled.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffled[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeue: " + rq.dequeue());
        for (int x : rq) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
