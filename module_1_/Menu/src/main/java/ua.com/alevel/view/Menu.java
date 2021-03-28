package ua.com.alevel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    public static int menu() {
        System.out.print("\n Exit: 0" +
                "\n LEVEL 1: 1" +
                "\n LEVEL 2: 2" +
                "\n LEVEL 3: 3" +
                "\n Please, enter a value: ");
        int select;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            select = Integer.parseInt(bufferedReader.readLine());
            if (select < 0 || select > 3) {
                System.out.println("\nPlease enter a correct option");
                return menu();
            }
            if (select == 0) {
                System.exit(0);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("\nPlease enter a correct option");
            return menu();
        }
        return select;
    }
}
