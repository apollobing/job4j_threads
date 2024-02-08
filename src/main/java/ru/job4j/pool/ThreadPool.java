package ru.job4j.pool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(
                            () -> {
                                try {
                                    while (!Thread.currentThread().isInterrupted()) {
                                        tasks.poll().run();
                                    }
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
            );
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Runnable job = () -> System.out.println(Thread.currentThread().getName()
                + " is doing job.");
        for (int i = 0; i < 10; i++) {
            threadPool.work(job);
        }
        Thread.sleep(1000);
        threadPool.shutdown();
    }
}
