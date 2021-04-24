package ua.com.alevel.util;

import java.util.Objects;

public class MenuUtil {


    public static void orderListMenu() {
        System.out.print("\nEXIT: 0\n" +
                "add an element: 1\n" +
                "add an array of elements: 2\n" +
                "join a MathSet: 3\n" +
                "sort Desc: 4\n" +
                "sort Asc: 5\n" +
                "get element by index: 6\n" +
                "get min element: 7\n" +
                "get max element: 8\n" +
                "get average :9\n" +
                "get median: 10\n");
    }

    public static int chooseOption(int minValue, int maxValue) {
        System.out.print("Please enter an option number: ");
        int option;
        try {
            option = Integer.parseInt(Objects.requireNonNull(ConsoleUtil.readFromConsole()));
            if (option < minValue || option > maxValue) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("\nYou entered an incorrect value. Please try again\n");
            return chooseOption(minValue, maxValue);
        }
        return option;
    }
}
