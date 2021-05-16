package ua.com.alevel.service;

import java.util.List;

public interface DateFormatter<Type> {

    List<Type> getAll(String file);

    List<String> convert(List<Type> list);

}
