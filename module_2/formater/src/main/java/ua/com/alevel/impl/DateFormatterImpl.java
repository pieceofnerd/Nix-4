package ua.com.alevel.impl;

import ua.com.alevel.dao.DateDao;
import ua.com.alevel.entity.Date;
import ua.com.alevel.entity.DateFormat;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.service.DateFormatter;
import ua.com.alevel.util.DateFormatterUtil;
import ua.com.alevel.util.DateUtil;


import java.util.*;

public class DateFormatterImpl implements DateFormatter<Date> {
    private static final BaseDao<Date> dateDao = new DateDao();

    @Override
    public List<String> convert(List<Date> list) {
        List<String> convertList = new ArrayList<>();
        list.forEach(l -> {
            StringJoiner joiner = new StringJoiner("");
            joiner.add(String.valueOf(l.getYear()));
            joiner.add(DateUtil.addFirstZero(l.getMonth()));
            joiner.add(DateUtil.addFirstZero(l.getDay()));
            convertList.add(joiner.toString());
        });
        return convertList;
    }

    @Override
    public List<Date> getAll(String file) {
        List<String> source = dateDao.getAll(file);
        List<Date> dates = new ArrayList<>();

        source.removeIf(s -> {
            boolean formatCorrect = DateFormatterUtil.checkDateFormat(s);
            return !formatCorrect;
        });

        for (String s : source) {
            DateFormat format = DateFormatterUtil.getFormat(s);
            if (Objects.nonNull(format)) {
                switch (format) {
                    case WITH_HYPHEN: {
                        String[] date = s.split("-");
                        try {
                            date[0] = DateUtil.removeFirstZeroInDate(date[0]);
                            date[1] = DateUtil.removeFirstZeroInDate(date[1]);
                            if (DateUtil.checkMonth(Integer.parseInt(date[0])) && DateUtil.checkDay(Integer.parseInt(date[1])))
                                dates.add(new Date(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])));
                        } catch (ClassCastException e) {
                            System.out.println("Incorrect format of date");
                        }
                        break;
                    }
                    case DAY_ON_FIRST_PLACE_WITH_SLASHES: {
                        String[] date = s.split("/");
                        try {
                            date[1] = DateUtil.removeFirstZeroInDate(date[1]);
                            date[0] = DateUtil.removeFirstZeroInDate(date[0]);
                            if (DateUtil.checkMonth(Integer.parseInt(date[1])) && DateUtil.checkDay(Integer.parseInt(date[0])))
                                dates.add(new Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));
                        } catch (ClassCastException e) {
                            System.out.println("Incorrect format of date");
                        }
                        break;
                    }
                    case YEAR_ON_FIRST_PLACE_WITH_SLASHES: {
                        String[] date = s.split("/");
                        try {
                            date[1] = DateUtil.removeFirstZeroInDate(date[1]);
                            date[2] = DateUtil.removeFirstZeroInDate(date[2]);
                            if (DateUtil.checkMonth(Integer.parseInt(date[1])) && DateUtil.checkDay(Integer.parseInt(date[2])))
                                dates.add(new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
                        } catch (ClassCastException e) {
                            System.out.println("Incorrect format of date");
                        }
                        break;
                    }
                }
            }
        }
        return dates;
    }

}
