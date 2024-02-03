package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    void whenAdd5AndPoll5ThenGet01234() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(5);
        List<Integer> nums = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        simpleBlockingQueue.offer(i);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        nums.add(simpleBlockingQueue.poll());
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(nums).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    void whenDoesNotAddThenConsumerWaitAndCantConsume() {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(5);
        List<Integer> nums = new ArrayList<>();
        Thread consumer = new Thread(
                () -> nums.add(simpleBlockingQueue.poll())
        );
        consumer.start();
        assertThat(nums.size()).isEqualTo(0);
    }
}
