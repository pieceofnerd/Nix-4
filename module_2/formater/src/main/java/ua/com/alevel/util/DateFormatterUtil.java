package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.DateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatterUtil {

    private final static Logger logger = LoggerFactory.getLogger(DateFormatterUtil.class.getName());

    public static boolean checkDateFormat(String date) {
        List<Matcher> matchers = matchers(date);
        return matchers.stream()
                .anyMatch(Matcher::matches);
    }

    private static List<Matcher> matchers(String date) {
        Pattern dateFormatWithSlashesDayOnFirstPlace = Pattern.compile("\\d{0,2}/\\d{0,2}/\\d{0,5}");
        Pattern dateFormatWithSlashesYearOnFirstPlace = Pattern.compile("\\d{0,5}/\\d{0,2}/\\d{0,2}");
        Pattern dateFormatWithHyphen = Pattern.compile("\\d{0,2}-\\d{0,2}-\\d{0,5}");

        List<Matcher> matchers = new ArrayList<>();

        matchers.add(dateFormatWithSlashesDayOnFirstPlace.matcher(date));
        matchers.add(dateFormatWithSlashesYearOnFirstPlace.matcher(date));
        matchers.add(dateFormatWithHyphen.matcher(date));

        return matchers;
    }

    public static DateFormat getFormat(String date) {
        int format = 0;
        List<Matcher> matchers = matchers(date);
        for (Matcher m : matchers) {
            if (m.matches()) {
                format = matchers.indexOf(m) + 1;
                break;
            }
        }
        if (format == 0) {
            logger.error("Date has incorrect format");
        }

        switch (format) {
            case 1:
                return DateFormat.DAY_ON_FIRST_PLACE_WITH_SLASHES;
            case 2:
                return DateFormat.YEAR_ON_FIRST_PLACE_WITH_SLASHES;
            case 3:
                return DateFormat.WITH_HYPHEN;
            default:
                return null;
        }
    }


}
