package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    private static final int LEAP_YEAR = 366;
    private static final int NON_LEAP_YEAR = 365;
    private static final int BRIEF_FEBRUARY_SIZE = 28;
    private static final int FULL_FEBRUARY_SIZE = 29;
    private static final int THIRTY_DAYS = 30;
    private static final int THIRTY_ONE_DAYS = 31;


    private final static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class.getName());

    public static boolean checkDateFormat(String date) {
        List<Matcher> matchers = matchers(date);
        boolean isDateFormatCorrect = matchers.stream()
                .anyMatch(Matcher::matches);
        return isDateFormatCorrect;
    }

    private static List<Matcher> matchers(String date) {
        Pattern briefDateFormatWithSlashes = Pattern.compile("\\d{0,2}/\\d{0,2}/\\d{0,5}");
        Pattern fullDateFormatWithSlashes = Pattern.compile("\\d{0,2}/\\d{0,2}/\\d{0,5}\\s+\\d{1,2}:\\d{1,2}:?\\d{0,2}");
        Pattern fullDateFormatWithSpaces = Pattern.compile("\\d{0,2}\\s+\\w{0,9}\\s+\\d{0,5}\\s+\\d{1,2}:\\d{1,2}:?\\d{0,2}");
        Pattern briefDateFormatWithSpaces = Pattern.compile("\\d{0,2}\\s+\\w{0,9}\\s+\\d{0,5}");
        Pattern briefDateFormatWithSpacesMonthOnFirstPlace = Pattern.compile("\\w{0,9}\\s+\\d{0,2}\\s+\\d{0,5}");
        Pattern fullDateFormatWithSpacesMonthOnFirstPlace = Pattern.compile("\\w{0,9}\\s+\\d{0,2}\\s+\\d{0,5}\\s+\\d{1,2}:\\d{1,2}:?\\d{0,2}");
        Pattern briefFormatWithSpacesOnlyYear = Pattern.compile("\\d{4,5}\\s+\\d{1,2}:\\d{1,2}:?\\d{0,2}");

        List<Matcher> matchers = new ArrayList<>();
        matchers.add(briefDateFormatWithSlashes.matcher(date));
        matchers.add(fullDateFormatWithSlashes.matcher(date));
        matchers.add(fullDateFormatWithSpaces.matcher(date));
        matchers.add(briefDateFormatWithSpaces.matcher(date));
        matchers.add(briefDateFormatWithSpacesMonthOnFirstPlace.matcher(date));
        matchers.add(fullDateFormatWithSpacesMonthOnFirstPlace.matcher(date));
        matchers.add(briefFormatWithSpacesOnlyYear.matcher(date));
        return matchers;

    }

    public static int getFormat(String date) {
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
        return format;
    }

    public static int getDaysInMonth(int numberOfMonth, int year) {
        int sizeOfMonth = 0;
        if (numberOfMonth == 2) {
            if (year % 4 == 0) {
                if (year % 100 == 0 && year % 400 != 0) {
                    sizeOfMonth = BRIEF_FEBRUARY_SIZE;
                } else sizeOfMonth = FULL_FEBRUARY_SIZE;
            } else sizeOfMonth = BRIEF_FEBRUARY_SIZE;
        } else if (numberOfMonth == 1 || numberOfMonth == 3 || numberOfMonth == 5 || numberOfMonth == 7 || numberOfMonth == 8 || numberOfMonth == 10 || numberOfMonth == 12) {
            sizeOfMonth = THIRTY_ONE_DAYS;
        } else sizeOfMonth = THIRTY_DAYS;
        return sizeOfMonth;
    }

    public static int getLeapYear(int year) {
        int yearStatus;
        if (year % 4 == 0) {
            if (year % 100 == 0 && year % 400 != 0) {
                yearStatus = NON_LEAP_YEAR;
            } else {
                yearStatus = LEAP_YEAR;
            }
        } else yearStatus = NON_LEAP_YEAR;
        return yearStatus;
    }

    public static int selectTwoLastDigit(int year) {
        if (year < 3) {
            return year;
        }
        String[] yearWithTwoCharacter = "\\d{1}".split(String.valueOf(year));
        if (yearWithTwoCharacter.length < 2) {
            System.out.println("\nCannot convert year format\n");
            return year;
        }
        String firstDigit = yearWithTwoCharacter[yearWithTwoCharacter.length - 2];
        String lastDigit = yearWithTwoCharacter[yearWithTwoCharacter.length - 1];
        String[] digit = new String[2];
        digit[0] = firstDigit;
        digit[1] = lastDigit;
        String result = String.join("", digit);
        return Integer.parseInt(result);
    }
}
