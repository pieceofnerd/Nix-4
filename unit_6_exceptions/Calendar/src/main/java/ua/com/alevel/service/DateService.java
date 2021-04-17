package ua.com.alevel.service;

public interface DateService {
    void createDate(String date);

    String inputDate();

    int selectDateFormat();

    boolean isDateFormatRight(String date, int option);

    int convertMonthFromStringToInteger(String month);

    void dateFormatMenu();

    String convertMonthFromIntegerToString(Integer month);
}
