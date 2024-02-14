package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenSumLinearThenGetResult() {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> result = new ArrayList<>();
        for (RolColSum.Sums el : RolColSum.sum(arr)) {
            result.add(el.getRowSum());
            result.add(el.getColSum());
        }
        List<Integer> expected = List.of(6, 12, 15, 15, 24, 18);
        assertThat(expected).containsExactlyElementsOf(result);
    }

    @Test
    public void whenSumAsyncThenGetResult() throws ExecutionException, InterruptedException {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> result = new ArrayList<>();
        for (RolColSum.Sums el : RolColSum.asyncSum(arr)) {
            result.add(el.getRowSum());
            result.add(el.getColSum());
        }
        List<Integer> expected = List.of(6, 12, 15, 15, 24, 18);
        assertThat(expected).containsExactlyElementsOf(result);
    }
}