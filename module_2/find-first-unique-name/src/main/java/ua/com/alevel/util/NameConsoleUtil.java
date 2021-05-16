package ua.com.alevel.util;

import java.util.List;

public class NameConsoleUtil {
    public static void outputUniqueName(List<String> names, String name) {
        System.out.println("All names are");
        names.forEach(System.out::println);
        System.out.println("--------");
        System.out.println("first unique name is " + name);
    }
}
