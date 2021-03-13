package ua.com.alevel;

import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmicTask {

    public static void main(String[] args) {
        LettersMatcherUtil.letterMatcherSortAndQuantity();
    }

    public static String userInput() {
        @NonNull
        String string = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

}


class LettersMatcherUtil {

    public static void letterMatcherSortAndQuantity() {
        System.out.print("\nSorted selection of all Latin / Cyrillic characters with the number of occurrences in a line\nPlease enter a string: ");

        Map<Character, Integer> letters = new TreeMap<>();
        String string = AlgorithmicTask.userInput();
        Pattern pattern = Pattern.compile("[A-zА-я]");
        Matcher matcher = pattern.matcher(string);
        boolean isLetterInRow = false;

        while (matcher.find()) {
            isLetterInRow = true;
            if (letters.containsKey(matcher.group().charAt(0))) {
                int value = letters.get(matcher.group().charAt(0));
                letters.put(matcher.group().charAt(0), value + 1);
            } else
                letters.put(matcher.group().charAt(0), 1);
        }
        if (isLetterInRow) {
            System.out.println("Your selection is: ");
            letters.forEach((letter, quantity) -> System.out.println("\t" + letter + " - " + quantity));
        } else System.out.println("There are no letters in your rows");

    }

}
