package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static List<String> read(String file) {
        List<String> data = new ArrayList<>();
        try (InputStreamReader isReader =
                     new InputStreamReader(
                             Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
             BufferedReader br = new BufferedReader(isReader)) {
            String line;
            while ((line = br.readLine()) != null)
                data.add(line);
        } catch (IOException e) {
            logger.error("Problems occurs during reading file {}", file);
            throw new RuntimeException();
        }
        return data;

    }
}
