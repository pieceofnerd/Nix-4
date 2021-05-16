package ua.com.alevel.controller;

import ua.com.alevel.ParserJson;
import ua.com.alevel.entity.Date;
import ua.com.alevel.impl.DateFormatterImpl;
import ua.com.alevel.service.DateFormatter;
import ua.com.alevel.util.ConsoleUtil;

import java.util.List;

public class DateFormatterController {
    private static final String file = "formater/src/main/resources/date.json";

    public void dateFormatterController() {
        DateFormatter<Date> dateDateFormatter = new DateFormatterImpl();
        List<String> dates = dateDateFormatter.convert(dateDateFormatter.getAll(file));
        ConsoleUtil.outDate(ParserJson.readJson(file), dates);
    }


}
