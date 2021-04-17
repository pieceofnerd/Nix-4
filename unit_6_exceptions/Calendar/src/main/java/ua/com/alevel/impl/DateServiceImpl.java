package ua.com.alevel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.db.DBInMemory;
import ua.com.alevel.persistence.entity.Date;
import ua.com.alevel.persistence.type.MonthType;
import ua.com.alevel.service.DateService;
import ua.com.alevel.util.ConsoleUtil;
import ua.com.alevel.util.DateUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateServiceImpl implements DateService {
    private final static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class.getName());
    private final static DBInMemory db = DBInMemory.getInstance();

    @Override
    public String inputDate() {
        System.out.print("Please, enter a date: ");
        String date = ConsoleUtil.readFromConsole();
        boolean isDateFormatCorrect = DateUtil.checkDateFormat(date);
        if (!isDateFormatCorrect) {
            logger.error("Date format was set incorrect");
            System.out.println("\nYou entered date in incorrect format. Please, try again\n");
            return inputDate();
        }
        return date;
    }

    @Override
    public void createDate(String dateInStringFormat) {
        int day;
        int month;
        int year;
        int hour;
        int minute;
        int second;

        int format = DateUtil.getFormat(dateInStringFormat);
        String[] parts = divideDateOnParts(dateInStringFormat);
        replaceEmptinessByDefaultValue(parts);
        String[] dateFormat = makeDatePartsCorrectFormat(parts, format);
        switch (format) {
            case 1:
            case 2:
            case 3:
            case 4: {
                try {
                    day = Integer.parseInt(dateFormat[0]);
                    if (!isMonthWrittenLikeNumber(dateFormat[1])) {
                        month = convertMonthFromStringToInteger(dateFormat[1]);
                    } else
                        month = Integer.parseInt(dateFormat[1]);
                    dateFormat[2] = convertYearToFullFormat(dateFormat[2]);
                    year = Integer.parseInt(dateFormat[2]);
                    hour = Integer.parseInt(dateFormat[3]);
                    minute = Integer.parseInt(dateFormat[4]);
                    second = Integer.parseInt(dateFormat[5]);
                    Date date = new Date(day, month, year, hour, minute, second);
                    db.createDate(date);
                } catch (NumberFormatException e) {
                    logger.error("Incorrect Number");
                    System.out.println("\nincorrect format\n");
                }
                break;
            }
            case 5:
            case 6: {
                try {
                    if (!isMonthWrittenLikeNumber(dateFormat[0])) {
                        month = convertMonthFromStringToInteger(dateFormat[0]);
                    } else
                        month = Integer.parseInt(dateFormat[0]);
                    day = Integer.parseInt(dateFormat[1]);
                    dateFormat[2] = convertYearToFullFormat(dateFormat[2]);
                    year = Integer.parseInt(dateFormat[2]);
                    hour = Integer.parseInt(dateFormat[3]);
                    minute = Integer.parseInt(dateFormat[4]);
                    second = Integer.parseInt(dateFormat[5]);
                    Date date = new Date(day, month, year, hour, minute, second);
                    db.createDate(date);
                } catch (NumberFormatException e) {
                    logger.error("Incorrect Number");
                    System.out.println("\nincorrect format\n");
                }
                break;
            }
            case 7: {
                try {
                    year = Integer.parseInt(dateFormat[0]);
                    hour = Integer.parseInt(dateFormat[1]);
                    minute = Integer.parseInt(dateFormat[2]);
                    second = Integer.parseInt(dateFormat[3]);
                    Date date = new Date(1, 1, year, hour, minute, second);
                    db.createDate(date);
                } catch (NumberFormatException e) {
                    logger.error("Incorrect Number");
                    System.out.println("\nincorrect format\n");
                }
                break;
            }
        }


    }

    @Override
    public void dateFormatMenu() {
        System.out.print("\nChoosing date format\n" +
                "dd/mm/yy: 1\n" +
                "m/d/yyyy: 2\n" +
                "mmm-d-yy: 3\n" +
                "dd-mmm-yyyy 00:00: 4\n");
    }

    @Override
    public int selectDateFormat() {
        System.out.print("Please, enter a date format: ");
        String output = ConsoleUtil.readFromConsole();
        int option;
        try {
            option = Integer.parseInt(output);
            if (option < 1 || option > 4) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect date format");
            System.out.println("\nDate format option must be a number from menu. Please try again\n");
            return selectDateFormat();
        }
        return option;
    }

    @Override
    public boolean isDateFormatRight(String date, int option) {
        CheckDateFormat checkDateFormat = new CheckDateFormat();
        boolean flag = true;
        int format = DateUtil.getFormat(date);
        if (option == 2) {
            format = 6;
        }
        String[] parts = divideDateOnParts(date);
        switch (format) {
            case 1:
            case 2:
            case 3:
            case 4: {
                String[] dateFormat = makeDatePartsCorrectFormat(parts, format);
                if (format > 2 && format < 6) {
                    dateFormat[1] = String.valueOf(convertMonthFromStringToInteger(dateFormat[1]));
                }
                boolean isDayCorrect = checkDateFormat.checkDay(dateFormat[0], dateFormat[1], dateFormat[2]);
                boolean isMonthCorrect = checkDateFormat.checkMonth(dateFormat[1]);
                boolean isYearCorrect = checkDateFormat.checkYear(dateFormat[2]);
                boolean isHourCorrect = checkDateFormat.checkHour(dateFormat[3]);
                boolean isMinutesCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[4]);
                boolean isSecondsCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[5]);
                if (!isDayCorrect || !isMonthCorrect || !isYearCorrect || !isHourCorrect || !isMinutesCorrects || !isSecondsCorrects) {
                    flag = false;
                }
                break;
            }
            case 5:
            case 6: {

                String[] dateFormat = makeDatePartsCorrectFormat(parts, format);
                if (format > 2 && format < 6) {
                    dateFormat[0] = String.valueOf(convertMonthFromStringToInteger(dateFormat[0]));
                }

                boolean isMonthCorrect = checkDateFormat.checkMonth(dateFormat[0]);
                boolean isDayCorrect = checkDateFormat.checkDay(dateFormat[1], dateFormat[0], dateFormat[3]);
                boolean isYearCorrect = checkDateFormat.checkYear(dateFormat[2]);
                boolean isHourCorrect = checkDateFormat.checkHour(dateFormat[3]);
                boolean isMinutesCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[4]);
                boolean isSecondsCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[5]);
                if (!isDayCorrect || !isMonthCorrect || !isYearCorrect || !isHourCorrect || !isMinutesCorrects || !isSecondsCorrects) {
                    flag = false;
                }
                break;
            }
            case 7: {
                String[] dateFormat = makeDatePartsCorrectFormat(parts, format);
                boolean isYearCorrect = checkDateFormat.checkYear(dateFormat[0]);
                boolean isHourCorrect = checkDateFormat.checkHour(dateFormat[1]);
                boolean isMinutesCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[2]);
                boolean isSecondsCorrects = checkDateFormat.checkMinutesAndSeconds(dateFormat[3]);
                if (!isYearCorrect || !isHourCorrect || !isMinutesCorrects || !isSecondsCorrects) {
                    flag = false;
                }
                break;
            }


        }
        return flag;
    }

    @Override
    public int convertMonthFromStringToInteger(String month) {
        MonthType[] months = MonthType.values();
        for (MonthType m : months) {
            if (month.toUpperCase().equals(String.valueOf(m))) {
                return m.getMonthFormat();
            }
        }
        return 0;
    }

    @Override
    public String convertMonthFromIntegerToString(Integer month) {
        MonthType[] months = MonthType.values();
        for (MonthType m : months) {
            if (month == m.getMonthFormat()) {
                return String.valueOf(m);
            }
        }
        System.out.println("No month matches");
        return null;
    }

    private void removeFirstZeroInDate(String[] parts) {
        for (int i = 0; i < parts.length; ++i) {
            if (parts[i].isEmpty()) {
                continue;
            }
            if (parts[i].length() > 1) {
                if (parts[i].charAt(0) == '0' && parts[i].charAt(1) != '0') {
                    char[] temporaryString = parts[i].toCharArray();
                    char[] stringWithoutFirstZero = new char[temporaryString.length - 1];
                    System.arraycopy(temporaryString, 1, stringWithoutFirstZero, 0, temporaryString.length - 1);
                    parts[i] = String.valueOf(stringWithoutFirstZero);
                }
            }
        }
    }

    private String[] initialiseDateTime(String[] parts, int option) {
        String[] dateWithFullTime;
        if (option == 7) {
            dateWithFullTime = new String[4];
        } else {
            dateWithFullTime = new String[6];
        }
        System.arraycopy(parts, 0, dateWithFullTime, 0, parts.length);
        if (parts.length < dateWithFullTime.length) {
            for (int i = parts.length; i < dateWithFullTime.length; i++) {
                dateWithFullTime[i] = "0";
            }

        }
        if (option > 0 && option < 5 && parts.length < 2) {
            dateWithFullTime[1] = String.valueOf(1);
        } else if (option >= 5 && option < 7 && parts.length < 2) {
            dateWithFullTime[0] = String.valueOf(1);
        }
        return dateWithFullTime;
    }

    private String[] divideDateOnParts(String date) {
        String[] prepareParts = date.split("[/:\\s+]");
        if (date.equals("//")) {
            return new String[]{"", "", ""};
        }
        String[] parts = new String[prepareParts.length + 1];
        if (date.charAt(date.length() - 1) == '/') {
            System.arraycopy(prepareParts, 0, parts, 0, prepareParts.length);
            parts[prepareParts.length] = "";
        } else parts = prepareParts;
        return parts;
    }

    private String[] makeDatePartsCorrectFormat(String[] parts, int option) {
        removeFirstZeroInDate(parts);
        return initialiseDateTime(parts, option);
    }

    private void replaceEmptinessByDefaultValue(String[] parts) {

        if (parts[0].isEmpty()) {
            parts[0] = "1";
        }
        if (parts[1].isEmpty()) {
            parts[1] = "1";
        }
        if (parts[2].isEmpty()) {
            parts[2] = "2021";
        }
    }

    private String convertYearToFullFormat(String year) {
        char[] fullYear = {'0', '0', '0', '0'};
        char[] standardYear = {'2', '0', '0', '0'};
        if (year.length() < 3) {
            for (int i = 1; i < 5; i++) {
                if (i <= year.length())
                    fullYear[4 - i] = year.charAt(year.length() - i);
                else
                    fullYear[4 - i] = standardYear[4 - i];
            }
            return String.valueOf(fullYear);
        }

        return year;
    }

    private boolean isMonthWrittenLikeNumber(String month) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(month);
        return matcher.matches();
    }

    static class CheckDateFormat {
        private boolean checkDay(String numberDay, String numberMonth, String numberYear) {

            if (numberDay.isEmpty()) {
                return true;
            }
            try {
                int month = Integer.parseInt(numberMonth);
                if (month == 0) {
                    System.out.println("You wrote incorrect month");
                }
                int year = Integer.parseInt(numberYear);
                int day = Integer.parseInt(numberDay);
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    return checkDayAccordingToMonth(day, 31);
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    return checkDayAccordingToMonth(day, 30);
                } else if (month == 2 && DateUtil.getLeapYear(year) == 365) {
                    return checkDayAccordingToMonth(day, 28);
                } else if (month == 2 && DateUtil.getLeapYear(year) == 366) {
                    return checkDayAccordingToMonth(day, 29);
                } else return false;
            } catch (NumberFormatException e) {
                logger.error("Incorrect date was written");
                System.out.println("There is incorrect format of date. Please, try again");
                return false;
            }

        }

        private boolean checkMonth(String month) {
            if (month.isEmpty()) {
                return true;
            }
            String monthToUpperCase = month.toUpperCase();
            MonthType[] months = MonthType.values();
            for (MonthType m : months) {
                if (String.valueOf(m).equals(monthToUpperCase)) {
                    return true;
                }
            }
            try {
                if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
                    logger.error("Incorrect month");
                    System.out.println("Month must be in bound [1-12]. Please, try again");
                    return false;
                } else return true;
            } catch (NumberFormatException e) {
                logger.error("Incorrect date was written");
                System.out.println("There is incorrect format of date. Please, try again");
                return false;
            }
        }

        private boolean checkYear(String year) {
            if (year.isEmpty()) {
                return true;
            }
            try {
                if (Integer.parseInt(year) < 0) {
                    logger.error("Incorrect year");
                    System.out.println("Year must be more than 0. Please, try again");
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                logger.error("Incorrect date was written");
                System.out.println("There is incorrect format of date. Please, try again");
                return false;
            }
        }

        private boolean checkHour(String hour) {

            try {
                if (Integer.parseInt(hour) < 0 || Integer.parseInt(hour) > 23) {
                    logger.error("Incorrect hour");
                    System.out.println("Hour must be in bound [0-23]. Please, try again");
                    return false;
                } else return true;
            } catch (NumberFormatException e) {
                logger.error("Incorrect date was written");
                System.out.println("There is incorrect format of date. Please, try again");
                return false;
            }
        }

        private boolean checkMinutesAndSeconds(String minute) {
            try {
                if (Integer.parseInt(minute) < 0 || Integer.parseInt(minute) > 59) {
                    logger.error("Incorrect value");
                    System.out.println("Minutes/Seconds  must be in bound [0-59]. Please, try again");
                    return false;
                } else return true;
            } catch (NumberFormatException e) {
                logger.error("Incorrect date was written");
                System.out.println("There is incorrect format of date. Please, try again");
                return false;
            }
        }

        private boolean checkDayAccordingToMonth(int day, int maxDayValue) {
            if (day < 1 || day > maxDayValue) {
                logger.error("Incorrect day");
                System.out.println("Day must be in bound [1-" + maxDayValue + "]. Please, try again");
                return false;
            } else return true;
        }
    }
}
