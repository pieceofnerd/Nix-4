package ua.com.alevel.util;

import java.util.Set;

public class UniqueValuesUtil<T> {

    public static <T> int quantityUniqueValues(Set<T> values) {
        return values.size();
    }
}
