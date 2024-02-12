package ru.job4j.pools;

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

    @Override
    protected Integer compute() {
        int result = -1;
        if (to - from <= 10) {
            for (int i = from; i < to; i++) {
                if (searchFor.equals(array[i])) {
                    result = i;
                }
            }
        } else {
            int middle = (from + to) / 2;
            RecursiveTask<Integer> left = new ParallelFindIndex<>(from, middle, array, searchFor);
            RecursiveTask<Integer> right = new ParallelFindIndex<>(middle, to, array, searchFor);
            left.fork();
            right.fork();
            result = Math.max(left.join(), right.join());
        }
        return result;
    }
}
