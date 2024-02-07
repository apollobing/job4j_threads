package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        do {
            temp = count.get();
        } while (!count.compareAndSet(temp, count.get() + 1));
    }

    public int get() {
        return count.get();
    }
}
