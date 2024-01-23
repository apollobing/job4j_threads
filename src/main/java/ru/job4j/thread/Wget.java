package ru.job4j.thread;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        File file = new File(url.substring(url.lastIndexOf('/') + 1));
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(file)) {
            System.out.println("Downloading file: " + file.getName());
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[512];
            int bytesRead;
            int bytesDownloaded = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                bytesDownloaded += bytesRead;
                output.write(dataBuffer, 0, bytesRead);
                if (bytesDownloaded >= speed) {
                    long duration = System.currentTimeMillis() - startAt;
                    if (duration < 1000) {
                        try {
                            System.out.println("You downloaded " + bytesDownloaded
                                    + " bytes. Wait: " + (1000 - duration) + " ms");
                            Thread.sleep(1000 - duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    bytesDownloaded = 0;
                    startAt = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Download has finished: " + Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Parameters must contain an URL link and a number.");
        }
        try {
            new URL(args[0]).toURI();
            Integer.parseInt(args[1]);
        } catch (MalformedURLException | URISyntaxException | NumberFormatException e) {
            throw new IllegalArgumentException("Check your parameters." + System.lineSeparator()
                    + "First parameter must be correct an URL address." + System.lineSeparator()
                    + "Second parameter must be an integer number.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}