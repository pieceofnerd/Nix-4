package ua.com.alevel.data.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static boolean checkDate(String date) {
        Pattern briefDateFormatWithSlashes = Pattern.compile("\\d{0,4}/\\d{0,2}/\\d{0,4}");
        Matcher matcher = briefDateFormatWithSlashes.matcher(date);
        if (matcher.matches()) {
            String[] separateDate = date.split("/");
            if (Integer.parseInt(separateDate[1]) > 0 && Integer.parseInt(separateDate[1]) < 13) {
                return Integer.parseInt(separateDate[2]) > 0 && Integer.parseInt(separateDate[2]) < 32;
            }
        }
        return false;
    }

}
