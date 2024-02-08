package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(
                () -> {
                    String subject = String.format(
                            "Notification %s to email %s.", user.getUsername(), user.getEmail()
                    );
                    String body = String.format("Add a new event to %s", user.getUsername());
                    send(subject, body, user.getEmail());
                }
        );
    }

    public void send(String subject, String body, String email) {
        System.out.printf(
                "Email with subject: \"%s\"%s"
                        + "and body: \"%s\"%s"
                        + "has been sent to: \"%s\"%s",
                subject, System.lineSeparator(),
                body, System.lineSeparator(),
                email, System.lineSeparator()
        );
    }

    public void close() {
        pool.shutdown();
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user = new User("johny", "johny@supamail.com");
        User user1 = new User("kate", "kate@supamail.com");
        emailNotification.emailTo(user);
        emailNotification.emailTo(user1);
        emailNotification.close();
        while (!emailNotification.pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
