package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVUtil {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    public static List<String[]> parse(List<String> csvLines) {
        List<String[]> csvData = new ArrayList<>();

        for (String line : csvLines) {
            String[] data = line.split(",");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].substring(1, data[i].length() - 1);
            }
            csvData.add(data);
        }
        return csvData;
    }

    public static void outputElement(String element) {
        if (Objects.nonNull(element)) {
            logger.info("Element is: " + element);
        } else {
            logger.warn("Incorrect parameters were inputted");
        }
    }
}
