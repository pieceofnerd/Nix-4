package ua.com.alevel.data;

import java.io.File;

public class Main {

    private static final String path = "write-in-file-from-console\\src\\main\\resources\\userdata.txt";

    public static void main(String[] args) {
        ConsoleReaderFileWriter consoleReaderFileWriter = new ConsoleReaderFileWriter(new File(path));
        consoleReaderFileWriter.read();
    }

}
