package ua.com.alevel.persistence.type;

import lombok.Getter;

public enum CalendarType {
    MILLISECOND(1),
    SECOND(2),
    MINUTE(3),
    HOUR(4),
    YEAR(5),
    CENTURY(6);
    @Getter
    private final int calendarType;

    CalendarType(int calendarType) {
        this.calendarType = calendarType;
    }
}
