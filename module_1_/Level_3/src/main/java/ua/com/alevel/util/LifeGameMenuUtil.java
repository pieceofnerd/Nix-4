package ua.com.alevel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LifeGameMenuUtil {
    public static int menu() {
        int menu;
        System.out.print("Exit: 0" +
                "\nPlay Game of Life: 1" +
                "\nEnter an option: ");
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
}
