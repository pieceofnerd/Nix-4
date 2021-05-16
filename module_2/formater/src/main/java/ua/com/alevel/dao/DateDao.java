package ua.com.alevel.dao;

import ua.com.alevel.entity.Date;
import ua.com.alevel.ParserJson;


import java.util.List;

public class DateDao implements BaseDao<Date> {

    @Override
    public List<String> getAll(String file) {
        return ParserJson.readJson(file);
    }

}
