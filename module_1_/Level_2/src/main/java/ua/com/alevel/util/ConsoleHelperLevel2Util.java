package ua.com.alevel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelperLevel2Util {
    public static int menu() {
        int menu = -1;
        System.out.print("\n Exit: 0" +
                "\n Check String on valid location '(', ')', '{', '}', '[' and ']',: 1" +
                "\n Enter an option: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String value = bufferedReader.readLine();
            menu = Integer.parseInt(value);
            if (menu < 0 || menu > 1) {
                System.out.println("\nPlease, enter a correct option\n");
                return menu();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("\nPlease, enter a correct option\n");
            return menu();
        }
        return menu;
    }

    public static String read() {
        String source = "";
        System.out.println("Please, input string that you want to check");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            source = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return source;
    }

    public static void write(boolean flag) {
        if (flag)
            System.out.println("Your string is valid");
        else System.out.println("Your string is NOT valid");
    }

}
