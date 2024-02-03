package ru.job4j.concurrent;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int size;
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(final int size) {
        this.size = size;
    }

    public synchronized void offer(T value) {
        while (queue.size() == size) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        this.notify();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T el = queue.poll();
        this.notify();
        return el;
    }
}
