package ua.com.alevel.util;

import java.util.List;

public class ConsoleUtil {
    public static void outDate(List<String> dates, List<String> datesWithFormat) {
        System.out.println("Date Formatter:\n" +
                "Source dates\t Result Date Format" +
                "\n------------------------------------");
        for (int i = 0; i < dates.size(); i++) {
            System.out.println(dates.get(i) + "\t\t " + datesWithFormat.get(i));
        }
    }
}
