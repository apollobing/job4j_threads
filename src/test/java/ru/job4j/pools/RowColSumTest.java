package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {
    @Test
    public void whenSumLinearThenGetResult() {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] result = RowColSum.sum(arr);
        Sums[] expected = new Sums[]{new Sums(6, 12), new Sums(15, 15), new Sums(24, 18)};
        assertThat(expected).isEqualTo(result);
    }

    @Test
    public void whenSumAsyncThenGetResult() throws ExecutionException, InterruptedException {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] result = RowColSum.asyncSum(arr);
        Sums[] expected = new Sums[]{new Sums(6, 12), new Sums(15, 15), new Sums(24, 18)};
        assertThat(expected).isEqualTo(result);
    }
}
