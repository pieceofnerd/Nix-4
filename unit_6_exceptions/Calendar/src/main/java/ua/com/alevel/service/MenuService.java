package ua.com.alevel.persistence.service;

import ua.com.alevel.persistence.type.CalendarType;

public interface MenuService {
    void mainMenu();

    int chooseOption(int section);

    void calendarTypeMenu();

    void userGreeting();

    void output(String result);

    long readMilliseconds(CalendarType calendarType);

}
