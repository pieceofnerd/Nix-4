package ua.com.alevel.persistence.type;

import lombok.Getter;

public enum MonthType {

    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12),

    jan(1),
    feb(2),
    mar(3),
    apr(4),
    may(5),
    jun(6),
    jul(7),
    aug(8),
    sep(9),
    oct(10),
    nov(11),
    dec(12);

    @Getter
    private int monthFormat;

    MonthType(int i) {
        this.monthFormat = i;
    }

}
