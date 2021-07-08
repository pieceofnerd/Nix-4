package ua.com.alevel.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.util.ReadWriteUtil;

import java.io.File;

public class FileWriter implements Runnable {

    private volatile String input;

    private volatile boolean exit;

    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);


    public void setCurrentText(String input) {
        this.input=input;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        try(RandomAccessFile rfa = new RandomAccessFile(file, "rw")){
            while(!exit){
                if(!previousText.equals(currentText)){

                }
            }
        }
    }


}
