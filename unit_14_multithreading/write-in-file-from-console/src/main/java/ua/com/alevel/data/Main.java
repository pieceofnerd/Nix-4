package ua.com.alevel.data;

import java.nio.file.Path;

public class Main {

    private static final String path = "output.txt";

    public static void main(String[] args) {

        Message message = new Message();

        Thread reader = new Thread(new ConsoleReader(message));
        Thread writer = new Thread(new FileWriter(message, Path.of(path)));

        reader.start();
        writer.start();

    }
}
