package ua.com.alevel.util;

import java.math.BigInteger;

public class ConvertTimeUtil {
    private static final int HOURS_IN_DAY = 24;
    private static final int SECONDS = 60;
    private static final int MINUTES = 60;


    public static long convertMillisecondsToSeconds(BigInteger milliseconds) {
        long sec = Long.parseLong(String.valueOf(milliseconds.divide(new BigInteger(String.valueOf(1000)))));
        return sec % SECONDS;
    }

    public static long convertMillisecondsToMinutes(BigInteger milliseconds) {
        long minutes = Long.parseLong(String.valueOf(milliseconds.divide(new BigInteger(String.valueOf(60_000)))));
        return minutes % MINUTES;
    }

    public static long convertMillisecondsToHour(BigInteger milliseconds) {
        long hours = Long.parseLong(String.valueOf(milliseconds.divide(new BigInteger(String.valueOf(36_00_000)))));
        return hours % HOURS_IN_DAY;
    }

    public static int convertMillisecondsToYear(BigInteger milliseconds) {
        long days = ConvertTimeUtil.convertMillisecondsToDays(milliseconds);
        int j = 1;
        int numberOfYears = 0;
        for (int i = 1; i < days; ) {
            if (DateUtil.getLeapYear(j) == 365) {
                i = i + 365;
            } else {
                i += 366;
            }
            numberOfYears++;
            j++;
        }

        return numberOfYears;
    }

    public static int convertMillisecondsToMonth(BigInteger milliseconds, int year) {
        long days = convertMillisecondsToDays(milliseconds);
        int numberOfMonth = 0;
        int j = 1;
        for (int i = 1; i < days; ) {
            if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
                i += 31;
                numberOfMonth++;
            } else if (j == 4 || j == 6 || j == 9 || j == 11) {
                i += 30;
                numberOfMonth++;
            } else if (j == 2) {
                if (DateUtil.getLeapYear(year) == 365) {
                    i += 28;
                } else {
                    i += 29;
                }
                numberOfMonth++;
            }
            j++;
            if (j == 13) {
                j = 1;
            }
        }
        if (numberOfMonth > 12) {
            numberOfMonth = numberOfMonth % 13;
        }
        return numberOfMonth;
    }

    public static long convertMillisecondsToDays(BigInteger milliseconds) {
        return Long.parseLong(String.valueOf(milliseconds)) / 864_000_00;
    }

    public static int convertMillisecondsToDays(BigInteger milliseconds, int month, int year) {
        long days = Long.parseLong(String.valueOf(milliseconds)) / 864_000_00;
        int numberOfDay = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            numberOfDay = (int) (days % 32);
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            numberOfDay = (int) (days % 31);
        } else if (month == 2) {
            if (DateUtil.getLeapYear(year) == 365) {
                numberOfDay = (int) (days % 29);
            } else {
                numberOfDay = (int) (days % 30);
            }
        }
        return numberOfDay;
    }

    public static long convertYearToMilliseconds(int year) {
        int yearStatus = DateUtil.getLeapYear(year);
        return convertDayToMilliseconds(yearStatus) * year;
    }

    public static long convertMonthToMilliseconds(int numberOfMonth, int year) {
        int sizeOfMonth;
        if (numberOfMonth == 0) {
            return 0;
        }
        sizeOfMonth = DateUtil.getDaysInMonth(numberOfMonth, year);
        return convertDayToMilliseconds(sizeOfMonth);
    }

    public static long convertDayToMilliseconds(int day) {
        return convertCHourToMilliseconds(day) * HOURS_IN_DAY;
    }

    public static long convertCHourToMilliseconds(long hour) {
        return convertMinuteToMilliseconds(hour) * MINUTES;
    }

    public static long convertMinuteToMilliseconds(long minute) {
        return convertSecondToMilliseconds(minute) * SECONDS;
    }

    public static long convertMonth(int numberOfMonth, int year, long dayInVariable) {
        long seconds = 0;
        for (int i = 0; i < numberOfMonth; i++) {
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                seconds += 31 * dayInVariable;
            } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                seconds += 30 * dayInVariable;
            } else if (i == 2) {
                if (DateUtil.getLeapYear(year) == 365) {
                    seconds += 28 * dayInVariable;
                } else {
                    seconds += 29 * dayInVariable;
                }
            }
        }
        return seconds;
    }

    public static long convertYearsToSeconds(int year, int dayInVariable) {
        return dayInVariable * DateUtil.getLeapYear(year) * year;
    }

    public static long convertSecondToMilliseconds(long second) {
        return  (second * 1000);
    }


}
