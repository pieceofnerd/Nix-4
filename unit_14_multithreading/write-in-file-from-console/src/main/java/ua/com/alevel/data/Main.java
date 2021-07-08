package ua.com.alevel.data;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        ConsoleReaderFileWriter consoleReaderFileWriter = new ConsoleReaderFileWriter(new File(path));
        consoleReaderFileWriter.read();
    }

}
