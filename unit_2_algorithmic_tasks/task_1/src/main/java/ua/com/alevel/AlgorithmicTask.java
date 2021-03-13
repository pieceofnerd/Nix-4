package ua.com.alevel;

import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmicTask {

    public static void main(String[] args) {
        DigitalMatcherUtil.digitalMatcherSum();
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

class DigitalMatcherUtil {

    public static void digitalMatcherSum() {
        System.out.print("Finding the sum of numbers in a string\nPlease enter a string: ");

        int sum = 0;
        boolean isDigitInRow = false;
        String string = AlgorithmicTask.userInput();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group());
            isDigitInRow = true;
        }
        if (isDigitInRow) {
            System.out.println("Sum of numbers= " + sum);
        } else {
            System.out.println("This line contains no numbers");
        }
    }

}

