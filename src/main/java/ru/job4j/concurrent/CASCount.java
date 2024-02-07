package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer temp;
        do {
            temp = count.get();
        } while (!count.compareAndSet(temp, count.get() + 1));
    }

    public int get() {
        return count.get();
    }
}
