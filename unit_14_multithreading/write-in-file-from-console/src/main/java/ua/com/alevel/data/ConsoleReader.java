package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConsoleReader implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleReader.class);

    private static final String EXIT_WORD = "quit";

    private final Message message;

    public ConsoleReader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (!(input = bufferedReader.readLine()).equalsIgnoreCase(EXIT_WORD)) {
                message.setText(input);
            }
        } catch (IOException e) {
            logger.error("Problem occurred during bufferReader work", e);
        }
        message.makeThrowOff();
    }
}
