package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.impl.AuthorServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadParametersUtil {
    private final static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class.getName());

    public static String readParams() {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("BufferReader works incorrect way");
            throw new RuntimeException("BufferReader works incorrect way");
        }
        if (string.isBlank()) {
            System.out.print("\nYour input is empty!" +
                    "\nPlease try again: ");
            return readParams();
        }
        return string;
    }
}
