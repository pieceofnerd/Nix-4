package ua.com.alevel.db;

import lombok.Getter;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBInMemory {
    private static DBInMemory db;
    @Getter
    private static List<Date> dates;

    public static DBInMemory getInstance() {
        if (db == null) {
            db = new DBInMemory();
        }
        return db;
    }

    private DBInMemory() {
        dates = new ArrayList<>();
    }

    public void createDate(Date date) {
        date.setId(generateId(UUID.randomUUID().toString(), Date.class));
        dates.add(date);
    }

    public List<Date> findAllDates() {
        return dates;
    }


    private boolean isDateExist(String id) {
        return dates.stream()
                .anyMatch(u -> u.getId().equals(id));
    }


    private <C extends BaseEntity> String generateId(String id, Class<C> cClass) {
        if (cClass.isAssignableFrom(Date.class)) {
            if (isDateExist(id)) {
                return generateId(UUID.randomUUID().toString(), Date.class);
            }
        }
        return id;
    }

}
