package ua.com.alevel.controller;

import ua.com.alevel.db.DBInMemory;
import ua.com.alevel.impl.CalendarServiceImpl;
import ua.com.alevel.impl.DateServiceImpl;
import ua.com.alevel.impl.MenuServiceImpl;
import ua.com.alevel.persistence.entity.Date;
import ua.com.alevel.persistence.type.CalendarType;
import ua.com.alevel.service.CalendarService;
import ua.com.alevel.service.DateService;
import ua.com.alevel.service.MenuService;
import ua.com.alevel.util.ConsoleUtil;

import java.math.BigInteger;
import java.util.List;
import java.util.Queue;

public class DifferenceBetweenDatesController {
    private static final int DATA_FORMAT_CHOOSE_OPTION = 2;
    private static final int CALENDAR_TYPE_CHOOSE_OPTION = 3;
    private static final int QUANTITY_DATES = 2;
    private static final DBInMemory db = DBInMemory.getInstance();
    MenuService menuService;
    DateService dateService;
    CalendarService calendarService;

    public DifferenceBetweenDatesController() {
        menuService = new MenuServiceImpl();
        dateService = new DateServiceImpl();
        calendarService = new CalendarServiceImpl();
    }

    public void differenceBetweenDates() {
        dateService.dateFormatMenu();
        int optionDataFormatTypeMenu = menuService.chooseOption(DATA_FORMAT_CHOOSE_OPTION);

        System.out.println("Finding difference between two dates:");
        ConsoleUtil.takeDatesFromUser(QUANTITY_DATES, optionDataFormatTypeMenu);
        menuService.calendarTypeMenu();
        int optionCalendarTypeMenu = menuService.chooseOption(CALENDAR_TYPE_CHOOSE_OPTION);
        CalendarType calendarType = calendarService.getCalendarType(optionCalendarTypeMenu);

        List<Date> dates = db.findAllDates();
        BigInteger difference = calendarService.findDifferenceBetweenDates(dates.get(0), dates.get(1), calendarType);
        dates.clear();
        if (difference.equals(-1)) {
            differenceBetweenDates();
            return;
        }
        menuService.output(String.valueOf(difference));

    }

}
