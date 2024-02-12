package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {
    private final int from;
    private final int to;
    private final T[] array;
    private final T searchFor;

    public ParallelFindIndex(int from, int to, T[] array, T searchFor) {
        this.from = from;
        this.to = to;
        this.array = array;
        this.searchFor = searchFor;
    }

    private int search() {
        int result = -1;
        for (int i = from; i < to; i++) {
            if (searchFor.equals(array[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return search();
        } else {
            int middle = (from + to) / 2;
            RecursiveTask<Integer> left = new ParallelFindIndex<>(from, middle, array, searchFor);
            RecursiveTask<Integer> right = new ParallelFindIndex<>(middle, to, array, searchFor);
            left.fork();
            right.fork();
            return Math.max(left.join(), right.join());
        }
    }

    public static <T> int find(T[] array, T searchFor) {
        RecursiveTask<Integer> task = new ParallelFindIndex<>(0, array.length, array, searchFor);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(task);
    }
}
