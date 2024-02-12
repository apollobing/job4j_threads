package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindIndex<T> {
    public int find(T[] array, T searchFor) {
        RecursiveTask<Integer> task = new ParallelFindIndex<>(0, array.length, array, searchFor);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(task);
    }
}
