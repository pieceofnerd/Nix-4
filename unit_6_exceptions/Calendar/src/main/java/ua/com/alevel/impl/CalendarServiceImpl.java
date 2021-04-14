package ua.com.alevel.persistence.impl;

import ua.com.alevel.persistence.entity.Date;
import ua.com.alevel.persistence.type.CalendarType;
import ua.com.alevel.service.CalendarService;
import ua.com.alevel.util.ConvertTimeUtil;
import ua.com.alevel.util.DateUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class CalendarServiceImpl implements CalendarService {

    @Override
    public BigInteger findDifferenceBetweenDates(Date firstDate, Date secondDate, CalendarType type) {
        Date interval;
        List<Date> dates = new ArrayList<>(2);
        dates.add(firstDate);
        dates.add(secondDate);

        dates = sort(dates, false);
        firstDate = dates.get(0);
        secondDate = dates.get(1);

        int year = firstDate.getYear() - secondDate.getYear();
        int month = firstDate.getMonth() - secondDate.getMonth();
        int day = firstDate.getDay() - secondDate.getDay();
        long hour = firstDate.getHour() - secondDate.getHour();
        long minute = firstDate.getMinute() - secondDate.getMinute();
        long second = firstDate.getSecond() - secondDate.getSecond();

        interval = new Date(day, month, year, hour, minute, second);
        interval = dateCorrect(interval);
        if (interval.getYear() < 0) {
            System.out.println("Difference cannot be negative");
            return new BigInteger("-1");
        }
        checkDate(interval);

        switch (type) {
            case MILLISECOND:
                long yearInMilliseconds = ConvertTimeUtil.convertYearToMilliseconds(interval.getYear());
                long monthInMilliseconds = ConvertTimeUtil.convertMonthToMilliseconds(interval.getMonth(), interval.getYear());
                long dayInMilliseconds = ConvertTimeUtil.convertDayToMilliseconds(interval.getDay());
                long hourInMilliseconds = ConvertTimeUtil.convertCHourToMilliseconds(interval.getHour());
                long minuteInMilliseconds = ConvertTimeUtil.convertMinuteToMilliseconds(interval.getMinute());
                long secondInMilliseconds = ConvertTimeUtil.convertSecondToMilliseconds(interval.getSecond());
                return BigInteger.valueOf(yearInMilliseconds + dayInMilliseconds + monthInMilliseconds + hourInMilliseconds + minuteInMilliseconds + secondInMilliseconds);

            case SECOND:
                return BigInteger.valueOf(interval.getSecond() + interval.getMinute() * 60 + interval.getHour() * 3600 + interval.getDay() * 86400 +
                        ConvertTimeUtil.convertMonth(interval.getMonth(), interval.getYear(), 86400) + ConvertTimeUtil.convertYearsToSeconds(year, 86400));
            case MINUTE:
                return BigInteger.valueOf(interval.getSecond() / 60 + interval.getMinute() + interval.getHour()*60 + interval.getDay() * 1440 +
                        ConvertTimeUtil.convertMonth(interval.getMonth(), interval.getYear(), 1440) + ConvertTimeUtil.convertYearsToSeconds(year, 1440));
            case HOUR:
                return BigInteger.valueOf(interval.getHour() + interval.getDay() * 24 + ConvertTimeUtil.convertMonth(interval.getMonth(), interval.getYear(), 24) +
                        ConvertTimeUtil.convertYearsToSeconds(year, 24));
            case YEAR:
                return BigInteger.valueOf(interval.getYear());
            case CENTURY:
                return BigInteger.valueOf(interval.getYear() / 100);
            default:
                return (new BigInteger(String.valueOf(-1)));
        }


    }

    @Override
    public CalendarType getCalendarType(int option) {
        CalendarType[] calendarTypes = CalendarType.values();
        for (CalendarType calendarType : calendarTypes) {
            if (calendarType.getCalendarType() == option) {
                return calendarType;
            }
        }
        return null;
    }

    @Override
    public Date addTimeToDate(Date date, long time, CalendarType calendarType) throws RuntimeException {
        switch (calendarType) {
            case MILLISECOND: {
                BigInteger dateInMilliseconds = convertDateToMillisecond(date);
                BigInteger sumOfMilliseconds = dateInMilliseconds.add(BigInteger.valueOf(time));
                return convertMillisecondsToDate(sumOfMilliseconds);
            }
            case SECOND: {
                date.setSecond(date.getSecond() + time);
                checkDate(date);
                return date;
            }
            case MINUTE: {
                date.setMinute(date.getMinute() + time);
                checkDate(date);
                return date;
            }
            case HOUR: {
                date.setHour(date.getHour() + time);
                checkDate(date);
                return date;
            }
            case YEAR: {
                try {
                    date.setYear((int) (date.getYear() + time));
                    return date;
                } catch (NumberFormatException e) {
                    System.out.println("\nYou entered too many hours value\n");
                    return null;
                }
            }
            case CENTURY: {
                try {
                    date.setYear((int) (date.getYear() + (time * 100)));
                    return date;
                } catch (NumberFormatException e) {
                    System.out.println("\nYou entered too many hours value\n");
                    return null;
                }
            }
            default:
                return null;
        }
    }

    @Override
    public Date subtractionTimeFromDate(Date date, long time, CalendarType calendarType) {
        switch (calendarType) {
            case MILLISECOND: {
                BigInteger dateInMilliseconds = convertDateToMillisecond(date);
                BigInteger subtractionOfMilliseconds = dateInMilliseconds.subtract(BigInteger.valueOf(time));
                return convertMillisecondsToDate(subtractionOfMilliseconds);

            }
            case SECOND: {
                date.setSecond(date.getSecond() - time);
                break;
            }
            case MINUTE: {
                date.setMinute(date.getMinute() - time);
                break;
            }
            case HOUR: {
                date.setHour(date.getHour() - time);
                break;
            }
            case YEAR: {
                try {
                    date.setYear((int) (date.getYear() - time));
                } catch (NumberFormatException e) {
                    System.out.println("\nYou entered too many hours value\n");
                    return null;
                }
                break;
            }
            case CENTURY: {
                try {
                    date.setYear((int) (date.getYear() - (time * 100)));
                } catch (NumberFormatException e) {
                    System.out.println("\nYou entered too many hours value\n");
                    return null;
                }
                break;
            }
            default:
                return null;

        }
        checkDate(date);
        date = dateCorrect(date);
        if (date.getYear() < 0) {
            date = null;
        }
        return date;

    }

    @Override
    public List<Date> sort(List<Date> dates, boolean increase) {
        if (increase)
            return dates.stream().sorted().collect(Collectors.toList());
        else {
            List<Date> sortedDates = dates.stream().sorted().collect(Collectors.toList());
            Collections.reverse(sortedDates);
            return sortedDates;

        }
    }

    private Date dateCorrect(Date date) {

        if (date.getSecond() < 0) {
            long minutesInSeconds = ((date.getMinute() * 60) + date.getSecond());
            long deltaMinutes = minutesInSeconds / 60;
            int deltaSeconds = (int) (-minutesInSeconds % 60);
            if (deltaSeconds < 0) {
                deltaSeconds = -deltaSeconds;
            }
            date.setSecond(deltaSeconds);
            date.setMinute(deltaMinutes);
        }

        if (date.getMinute() < 0) {
            long hourInMinutes = ((date.getHour() * 60) + date.getMinute());
            long deltaHour = hourInMinutes / 60;
            int deltaMinutes = (int) (-hourInMinutes % 60);
            if (deltaMinutes < 0) {
                deltaMinutes = -deltaMinutes;
            }
            date.setMinute(deltaMinutes);
            date.setHour(deltaHour);
        }

        if (date.getHour() < 0) {
            long dayInHours = ((date.getDay() * 24) + date.getHour());
            int deltaDay = (int) (dayInHours / 24);
            int deltaHour = (int) (-dayInHours % 24);
            if (deltaHour < 0) {
                deltaHour = -deltaHour;
            }
            date.setHour(deltaHour);
            date.setDay(deltaDay);
        }

        if (date.getDay() < 1) {
            long MonthInDays = ((date.getMonth() * DateUtil.getDaysInMonth(date.getMonth(), date.getYear())) + date.getDay());
            int deltaMonth = (int) (MonthInDays / DateUtil.getDaysInMonth(date.getMonth(), date.getYear()));
            int deltaDay = (int) (-MonthInDays % 60);
            if (deltaDay < 0) {
                deltaDay = -deltaDay;
            }
            date.setDay(deltaDay);
            date.setMonth(deltaMonth);
        }

        if (date.getMonth() < 1) {
            long yearsInMonths = (date.getYear() * 12 + date.getMonth());
            int deltaYear = (int) yearsInMonths / 12;
            int deltaMonth = (int) (-yearsInMonths % 12);
            if (deltaMonth < 0) {
                deltaMonth = -deltaMonth;
            }
            date.setMonth(deltaMonth);
            date.setYear(deltaYear);
        }

        if (date.getMonth() < 1) {
            int year = date.getYear() - date.getMonth();
            if (year < 0) {
                System.out.println("Year cannot be less than 0. Your request was incorrect");
            }
        }
        if (date.getYear() < 0) {
            System.out.println("Year cannot be less than 0. Your request was incorrect");
        }
        return date;
    }

    private void checkDate(Date date) {
        if (date.getSecond() > 59) {
            double deltaMinute = (date.getSecond() / 60.0);
            int deltaSecond = (int) date.getSecond() % 60;
            date.setMinute(date.getMinute() + (long) deltaMinute);
            date.setSecond(deltaSecond);
        }

        if (date.getMinute() > 59) {
            double deltaHour = (date.getMinute() / 60.0);
            int deltaMinute = (int) date.getMinute() % 60;
            date.setHour(date.getHour() + (long) deltaHour);
            date.setMinute(deltaMinute);
        }


        if (date.getHour() > 23) {
            double deltaDay = date.getHour() / 24.0;
            int deltaHour = (int) date.getHour() % 24;
            date.setDay((date.getDay() + (int) deltaDay));
            date.setHour(deltaHour);
        }

        if (date.getDay() > DateUtil.getDaysInMonth(date.getMonth(), date.getYear())) {
            double deltaMonth = date.getDay() / (double) DateUtil.getDaysInMonth(date.getMonth(), date.getYear());
            int deltaDay = date.getDay() % DateUtil.getDaysInMonth(date.getMonth(), date.getYear());
            date.setMonth(date.getMonth() + (int) deltaMonth);
            date.setDay(deltaDay);
        }

        if (date.getMonth() > 12) {
            double deltaYear = (date.getMonth() / 12.0);
            int deltaMonth = date.getMonth() % 12;
            date.setYear(date.getYear() + (int) deltaYear);
            date.setMonth(deltaMonth);
        }

    }

    private BigInteger convertDateToMillisecond(Date date) {
        long second = date.getSecond();
        long minute = date.getMinute();
        long hour = date.getHour();
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();


        long milliseconds = ConvertTimeUtil.convertSecondToMilliseconds(second)
                + ConvertTimeUtil.convertMinuteToMilliseconds(minute)
                + ConvertTimeUtil.convertCHourToMilliseconds(hour)
                + ConvertTimeUtil.convertDayToMilliseconds(day)
                + ConvertTimeUtil.convertMonthToMilliseconds(month, year)
                + ConvertTimeUtil.convertYearToMilliseconds(year);

        return BigInteger.valueOf(milliseconds);
    }

    private Date convertMillisecondsToDate(BigInteger milliseconds) {
        int year = ConvertTimeUtil.convertMillisecondsToYear(milliseconds);
        int month = ConvertTimeUtil.convertMillisecondsToMonth(milliseconds, year);
        int day = ConvertTimeUtil.convertMillisecondsToDays(milliseconds, month, year);
        long hour = ConvertTimeUtil.convertMillisecondsToHour(milliseconds);
        long minute = ConvertTimeUtil.convertMillisecondsToMinutes(milliseconds);
        long second = ConvertTimeUtil.convertMillisecondsToSeconds(milliseconds);
        return new Date(day, month, year, hour, minute, second);
    }
}
