package ua.com.alevel.util;

import ua.com.alevel.ParserJson;

import java.util.List;
import java.util.stream.Collectors;

public class NameUtil {

    public static String findFirstUniqueName(String file) {
        List<String> names = ParserJson.readJson(file);
        String firstUniqueName = names.get(0);
        while (true) {
            boolean unique = true;
            for (int i = names.indexOf(firstUniqueName) + 1; i < names.size(); i++) {
                if (firstUniqueName.equals(names.get(i))) {
                    int index = names.indexOf(firstUniqueName);
                    names = removeRepeatName(names, names.indexOf(firstUniqueName));
                    firstUniqueName = names.get(index + 1);
                    unique = false;
                    break;
                }
            }
            if (unique) {
                break;
            }
            if (names.indexOf(firstUniqueName) == names.size() - 1) {
                System.out.println("There is no unique elements");
                break;
            }
        }
        return firstUniqueName;
    }

    private static List<String> removeRepeatName(List<String> names, int finalI) {
        return names.stream().filter(n -> !n.equals(names.get(finalI))).collect(Collectors.toList());
    }
}
