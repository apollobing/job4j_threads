package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelFindIndexTest {
    @Test
    public void whenFindStringThenGetIndex() {
        String[] arr = new String[]{"Java", "Spring", "Maven"};
        int result = ParallelFindIndex.find(arr, "Maven");
        int expected = 2;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFindDoubleThenGetIndex() {
        Double[] arr = new Double[]{2.5, 3.7, 614.89, 0.001};
        int result = ParallelFindIndex.find(arr, 3.7);
        int expected = 1;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenLinearSearchThenGetIndex() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        int result = ParallelFindIndex.find(arr, 7);
        int expected = 6;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenParallelSearchThenGetIndex() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
        int result = ParallelFindIndex.find(arr, 15);
        int expected = 14;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFindStringThenCanNotGetIndex() {
        String[] arr = new String[]{"IDE", "Idea", "SQL"};
        int result = ParallelFindIndex.find(arr, "DB");
        int expected = -1;
        assertThat(result).isEqualTo(expected);
    }
}
