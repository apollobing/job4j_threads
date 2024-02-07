package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    void whenIncreaseBy5ThenGet5() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread firstThread = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread secondThread = new Thread(
                () -> {
                    for (int i = 0; i < 2; i++) {
                        casCount.increment();
                    }
                }
        );
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        assertThat(casCount.get()).isEqualTo(5);
    }
}
