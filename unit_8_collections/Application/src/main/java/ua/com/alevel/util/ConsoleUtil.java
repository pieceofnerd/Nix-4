package ua.com.alevel.util;


import ua.com.alevel.MathSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConsoleUtil {

    public static String readFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getSize() {
        System.out.print("Please, input number of objects: ");
        int size = (Integer) getNumber();
        if (size < 0) {
            System.out.println("Your value doesn't correct ");
            return getSize();
        }
        return size;
    }

    public static Number getNumber() throws NumberFormatException {
        String input = ConsoleUtil.readFromConsole();
        if (Objects.isNull(input)) {
            System.out.println("Something goes wrong. Please, try again");
            throw new NumberFormatException();
        }
        return Integer.parseInt(input);
    }

    public static <number extends Number & Comparable<number>> void output(MathSet<number> mathSet) {
        System.out.print("\nYour MathSet: ");
        Number[] a = mathSet.toArray();
        if (a.length == 0) {
            System.out.println("empty");
            return;
        }
        for (Number number : a) {
            System.out.print(number + " ");
        }
        System.out.println();
    }

    public static <number extends Number & Comparable<? extends number>> void output(number obj) {
        if (Objects.isNull(obj)) {
            System.out.println("There is no object");
        } else System.out.println("Your result: " + obj);
    }

}
