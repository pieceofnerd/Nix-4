package ua.com.alevel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.impl.DateServiceImpl;
import ua.com.alevel.persistence.type.DateFormatType;
import ua.com.alevel.service.DateService;
import ua.com.alevel.util.DateUtil;

@Getter
@Setter
public class Date extends BaseEntity implements Comparable<Date> {
    private int day;
    private int month;
    private int year;
    private long hour;
    private long minute;
    private long second;

    private static final DateService dateService = new DateServiceImpl();

    public Date(int day, int month, int year, long hour, long minute, long second) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;

    }


    @Override
    public int compareTo(Date o) {
        if (this.getYear() > o.getYear()) {
            return 1;
        } else if (this.getYear() < o.getYear()) {
            return -1;
        } else {
            if (this.getMonth() > o.getYear()) {
                return 1;
            } else if (this.getMonth() < o.getMonth()) {
                return -1;
            } else {
                if (this.getDay() > o.getDay()) {
                    return 1;
                } else if (this.getDay() < o.getDay()) {
                    return -1;
                } else {
                    if (this.getHour() > o.getHour()) {
                        return 1;
                    } else if (this.getHour() < o.getHour()) {
                        return -1;
                    } else if (this.getMinute() > o.getMinute()) {
                        return 1;
                    } else if (this.getMinute() < o.getMinute()) {
                        return -1;
                    } else {
                        return Long.compare(this.getSecond(), o.getSecond());
                    }
                }
            }
        }
    }

    private String convertYearToShortFormat(String year) {
        if (year.length() == 4) {
            char[] yearInCharFormat = {year.charAt(2), year.charAt(3)};
            return String.copyValueOf(yearInCharFormat);
        }
        return year;
    }

    public String toString(DateFormatType type) {
        switch (type) {
            case dd_mm_yy: {

                return
                        day +
                                "/" + month +
                                "/" + convertYearToShortFormat(String.valueOf(year));

            }
            case dd_mmm_yyyy_00_00: {
                String monthFullVersion = dateService.convertMonthFromIntegerToString(month);
                if (monthFullVersion == null) {
                    return "Some errors was happen during algorithm working";
                }
                return day +
                        "/" + monthFullVersion.toLowerCase() +
                        "/" + year +
                        " " + hour +
                        ":" + minute +
                        ":" + second;

            }
            case m_d_yyyy: {
                int fullYear = DateUtil.selectTwoLastDigit(year);
                return month +
                        "/" + day +
                        "/" + fullYear;
            }
            case mmm_d_yy: {
                String monthFullVersion = dateService.convertMonthFromIntegerToString(month);
                if (monthFullVersion == null) {
                    return "Some errors was happen during algorithm working";
                }
                return monthFullVersion.toLowerCase() +
                        "/" + day +
                        "/" + convertYearToShortFormat(String.valueOf(year));

            }
            default: {
                return day +
                        "/" + month +
                        "/" + year +
                        " " + hour +
                        ":" + minute +
                        ":" + second;
            }
        }

    }
}
