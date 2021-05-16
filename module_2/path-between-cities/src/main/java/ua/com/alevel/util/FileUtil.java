package ua.com.alevel.util;

import org.apache.commons.io.FileUtils;
import ua.com.alevel.ParserJson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    public static int getSize(String file) {
        String[] data = readFile(file);
        if (Objects.isNull(data)) {
            System.out.println("File is incorrect");
            return 0;
        }
        try {
            return Integer.parseInt(data[0]);
        } catch (ClassCastException c) {
            System.out.println("Incorrect behavior");
            throw new ClassCastException();
        }
    }

    public static String[] readFile(String file) {
        String res = ParserJson.readFile(file);
        if (Objects.nonNull(res))
            return res.split("\\n");
        else return null;
    }

    public static void writeFile(String file, String value) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(value);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeFile(String file, List<String> value) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (String s : value) {
                fileWriter.write(s+"\n");
            }
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
