package multipleProducersMultipleConsumersNBuffer;

import java.util.Queue;
public class Buffer {

    Queue queue;
    int limit;
    volatile static boolean isFull = false;
    volatile static boolean addedOrder = false;

    public Buffer(int size) {
        queue = new LimitedQueue(size);
        limit = size;
    }

    public synchronized void put(int value) {
        while(isFull) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        queue.add(value);
        System.out.println("Parent " + value + " baked the cake.");

        if(queue.size() == limit) {
            isFull = true;
        }

        notify();
    }

    public synchronized int get(int value) {
        while(queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notify();
        isFull = false;

        int i = (int) queue.peek();

        if(i == value) {
            System.out.println("The child " + i + " ate the cake.");
            return (int)queue.poll();
        } else {
            return -1;
        }
    }
}
