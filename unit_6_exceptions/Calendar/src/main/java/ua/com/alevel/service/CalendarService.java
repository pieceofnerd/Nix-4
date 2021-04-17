package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Date;
import ua.com.alevel.persistence.type.CalendarType;

import java.math.BigInteger;
import java.util.List;

public interface CalendarService {
    BigInteger findDifferenceBetweenDates(Date firstDate, Date secondDate, CalendarType type);

    Date addTimeToDate(Date date, long time, CalendarType calendarType);

    Date subtractionTimeFromDate(Date date, long time, CalendarType calendarType);

    CalendarType getCalendarType(int option);

    List<Date> sort(List<Date> dates, boolean increase);
}

