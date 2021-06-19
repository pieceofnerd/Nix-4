package ua.com.alevel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Menu {
//    private final static Logger logger = LoggerFactory.getLogger(Menu.class.getName());

    public static int menuFirstLevel() {
        int optionOnFirstLevel;

        System.out.print("\nExit: 0" +
                "\nBook menu: 1" +
                "\nAuthor menu: 2" +
                "\nAdd author to book: 3" +
                "\nPlease, input an option: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = bufferedReader.readLine();
            optionOnFirstLevel = Integer.parseInt(str);
        } catch (IOException | NumberFormatException e) {
//            logger.error("User input incorrect value");
            return menuFirstLevel();
        }
        if (optionOnFirstLevel < 0 || optionOnFirstLevel > 3) {
            System.out.println("\nPlease, enter a correct option");
            return menuFirstLevel();
        }
        return optionOnFirstLevel;
    }
}
