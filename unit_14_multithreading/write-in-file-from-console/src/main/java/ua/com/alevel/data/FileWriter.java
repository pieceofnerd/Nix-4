package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class FileWriter implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    private final Path path;

    private final Message message;

    public FileWriter(Message message, Path path) {
        this.path = path;
        this.message = message;
    }

    @Override
    public void run() {

        String previousInput = "";

        try (RandomAccessFile output = new RandomAccessFile(path.toFile(), "rw")) {
            while (!message.isThrowOff()) {
                if (!message.getText().equals(previousInput)) {
                    previousInput = message.getText();
                    output.setLength(0L);
                    output.writeBytes(message.getText());
                }
                Thread.sleep(500);
            }
        } catch (FileNotFoundException e) {
            logger.error("Cannot find file {}", path, e);
        } catch (IOException e) {
            logger.error("Failed to write by using RandomAccessFile", e);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted", e);
        }

    }
}
