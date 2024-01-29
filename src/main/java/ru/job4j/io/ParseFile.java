package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized boolean content(Predicate<Character> filter, char character) {
        return filter.test(character);
    }

    public synchronized String getContent() throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            String output = "";
            int data;
            while ((data = input.read()) > 0) {
                if (content(character -> character < 0x80, (char) data)) {
                    output += (char) data;
                }
            }
            return output;
        }
    }
}