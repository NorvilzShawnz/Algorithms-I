/* *****************************************************************************
 *  Name: Shawn Norvil
 *  Date: 5/27/2024
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;

        Node(Item item) {
            this.item = item;
            this.next = null;
            this.previous = null;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("");
        }
        Node<Item> newFirst = new Node<>(item);
        if (first == null) {
            first = newFirst;
            last = newFirst;
        }
        else {
            newFirst.next = first;
            first.previous = newFirst;
            first = newFirst;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("");
        }
        Node<Item> newLast = new Node<>(item);
        if (last == null) {
            first = newLast;
            last = newLast;
        }
        else {
            last.next = newLast;
            last = newLast;

        }
        size++;
    }

    // // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("");
        }
        Item removedItem = first.item;
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return removedItem;
        }
        first = first.next;
        size--;
        return removedItem;
    }


    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("");
        }
        Node<Item> current = first;
        Node<Item> previous = null;

        // Traverse to the second to last node
        while (current.next != null) {
            previous = current;
            current = current.next;
        }

        // If there is only one element in the list
        if (previous == null) {
            first = null;
        }
        else {
            // Remove reference to the last node
            previous.next = null;
        }

        // Update last reference
        last = previous;

        size--;
        return current.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Node<Item> current;

        public ArrayIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next; // Move to the next node
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();


        // Use iterator to print all items in the deque
        System.out.println("Items in the deque (front to back):");
        for (Integer item : deque) {
            System.out.println(item);
        }
    }
}
