package ua.com.alevel.util;

import java.util.Objects;

public class MenuUtil {

    public static void mainMenu() {
        System.out.print("EXIT: 0\n" +
                "Deal with numbers: 1\n" +
                "Deal with customer type: 2\n");
    }

    public static void orderListMenu() {

        System.out.print("\nEXIT: 0\n" +
                "add collection: 1\n" +
                "add an element: 2\n" +
                "get an element: 3\n" +
                "get index of an element: 4\n" +
                "get lastIndex of an element: 5\n" +
                "remove an element: 6\n" +
                "set a new value: 7\n" +
                "get sublist: 8\n" +
                "clean:9\n");
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
