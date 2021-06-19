package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.util.ReadWriteUtil;

import java.io.File;

public class FileWriter implements Runnable {

    private String previousText;

    private String currentText;

    private boolean exit;

    private final File file;

    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    public FileWriter(File file) {
        this.file = file;
        this.exit = false;
        this.currentText = "";
        this.previousText = "";
    }


    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(1000);
                write();
            } catch (InterruptedException e) {
                logger.warn(" thread {} was interrupted", Thread.currentThread().getName());
                break;
            }
        }
    }

    private void write() {
        if (!previousText.equals(currentText)) {
            ReadWriteUtil.write(file, currentText);
            this.previousText = currentText;
        }
    }

}
