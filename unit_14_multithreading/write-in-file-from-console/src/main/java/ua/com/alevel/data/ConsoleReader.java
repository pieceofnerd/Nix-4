package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class ConsoleReader {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleReader.class);

    private static final String exitWord = "quit";

    private volatile StringBuffer input;

    private volatile boolean exit;


    public void read() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (!(input = bufferedReader.readLine()).equals(exitWord)) {
                this.input.append(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
