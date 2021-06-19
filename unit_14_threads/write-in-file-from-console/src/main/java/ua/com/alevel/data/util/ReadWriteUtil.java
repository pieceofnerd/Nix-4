package ua.com.alevel.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReadWriteUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReadWriteUtil.class);

    public static String read(BufferedReader bufferedReader) {
        try {
            System.out.print("Please, enter text: ");
            return bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("Problem occurs during working with file");
            throw new RuntimeException(e);
        }
    }


    public static void write(File file, String text) {
        try (RandomAccessFile rfa = new RandomAccessFile(file, "rws")) {
            rfa.write(text.getBytes(StandardCharsets.UTF_8), 0, text.length());
        } catch (FileNotFoundException e) {
            logger.error("Cannot find file {}", file.getName());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Problem occurs during working with file");
            throw new RuntimeException(e);
        }
    }

}
