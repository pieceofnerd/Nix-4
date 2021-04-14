package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.impl.DateServiceImpl;
import ua.com.alevel.persistence.entity.Date;
import ua.com.alevel.service.DateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleUtil {
    private final static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class.getName());
    private final static DateService dateService = new DateServiceImpl();

    public static String readFromConsole() {
        String input;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("Troubles with bufferReader");
            throw new RuntimeException("Troubles with bufferReader");
        }
        return input;
    }

    public static int getQuantityFromUser() {
        System.out.print("Please, input quantity of dates that you want to sort: ");
        String source = ConsoleUtil.readFromConsole();
        int quantity;
        try {
            quantity = Integer.parseInt(source);
            if (quantity < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease, notice, quantity of elements must be a number bigger 0");
            return getQuantityFromUser();
        }
        return quantity;
    }

    public static boolean isSortDatesByIncrease() {
        System.out.print("Enter 0 to sort by increase\n" +
                "Enter 1 to sort by decrease: ");
        String source = readFromConsole();
        boolean isSortedByIncrease;
        try {
            int value = (Integer.parseInt(source));
            if (value < 0 || value > 1) {
                throw new NumberFormatException();
            }
            isSortedByIncrease = (value == 0);
        } catch (NumberFormatException e) {
            System.out.println("\n Please, notice, you need to enter value in bounds of [0-1]");
            return isSortDatesByIncrease();
        }
        return isSortedByIncrease;
    }

    public static void takeDatesFromUser(int quantity, int format) {
        boolean isDateFormatCorrect = false;
        String dateInput = null;
        for (int i = 0; i < quantity; i++) {
            while (!isDateFormatCorrect) {
                dateInput = dateService.inputDate();
                isDateFormatCorrect = dateService.isDateFormatRight(dateInput,format);
            }
            isDateFormatCorrect = false;
            dateService.createDate(dateInput);
        }
    }

}
