package ua.com.alevel.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParserUtil {

    public static String readFile(String file) {
        Path fileName = Path.of(file);
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
