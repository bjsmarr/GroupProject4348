
package edu.utdallas.blockingFIFO;


public class BlockingFIFO<T> {
    private final T[] elements;
    private final int capacity;
    private int head;
    private int tail;
    private final Object lock;

    @SuppressWarnings("unchecked")
	public BlockingFIFO(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.lock = new Object();
    }

    public void put(T element) throws InterruptedException {
        synchronized (lock) {
            while (isFull()) {
                lock.wait();
            }
            elements[tail] = element;
            tail = (tail + 1) % capacity;
            lock.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (isEmpty()) {
                lock.wait();
            }
            T element = elements[head];
            head = (head + 1) % capacity;
            lock.notifyAll();
            return element;
        }
    }

    private boolean isFull() {
        return tail - head == capacity;
    }

    private boolean isEmpty() {
        return head == tail;
    }
}