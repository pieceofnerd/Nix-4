package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import ua.com.alevel.data.util.ReadWriteUtil;

public class ConsoleReaderFileWriter {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleReaderFileWriter.class);

    private static final String exitWord = "quit";

    private final FileWriter fileWriter;

    private final File file;


    public ConsoleReaderFileWriter(File file) {
        this.file = file;
        this.fileWriter = new FileWriter(file);
        new Thread(fileWriter).start();
    }

    public void read() {

         StringBuilder input = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                if(exit(String.valueOf(input))){
                    fileWriter.setExit(true);
                    break;
                }
                input.append(ReadWriteUtil.read(bufferedReader));
                fileWriter.setCurrentText(String.valueOf(input));
            }

        } catch (FileNotFoundException e) {
            logger.error("Could not find a file {}" + file.getName());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Problem occurs during working with a file");
            throw new RuntimeException(e);
        }

    }

    private boolean exit(String text) {
        return text.contains(exitWord);
    }

}
