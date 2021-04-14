package ua.com.alevel.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.persistence.type.CalendarType;
import ua.com.alevel.service.MenuService;
import ua.com.alevel.util.ConsoleUtil;

public class MenuServiceImpl implements MenuService {
    private final static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class.getName());

    @Override
    public void mainMenu() {
        System.out.println("\nExit: 0\n" +
                "Find difference between dates: 1\n" +
                "Add time to date: 2\n" +
                "Subtract time from date: 3\n" +
                "Compare list of dates: 4\n");
    }

    @Override
    public int chooseOption(int section) {
        System.out.print("Please enter an option number: ");
        int option;
        try {
            option = Integer.parseInt(ConsoleUtil.readFromConsole());
        } catch (NumberFormatException e) {
            logger.error("Incorrect Number");
            System.out.println("\nYou entered an incorrect value. Please try again\n");
            return chooseOption(section);
        }
        switch (section) {
            case 1: {
                if (!checkBoundsOfUserInput(0, 4, option))
                    return chooseOption(section);
                break;
            }
            case 2: {
                if (!checkBoundsOfUserInput(1, 4, option))
                    return chooseOption(section);
                break;
            }
            case 3: {
                if (!checkBoundsOfUserInput(1, 6, option))
                    return chooseOption(section);
                break;
            }
        }
        return option;
    }

    @Override
    public void calendarTypeMenu() {
        System.out.println("\nChoose type of time format: \n" +
                "Milliseconds: 1\n" +
                "Seconds: 2\n" +
                "Minutes: 3\n" +
                "Hours: 4\n" +
                "Years: 5\n" +
                "Centuries: 6\n");
    }

    @Override
    public void userGreeting() {
        System.out.println("Hello dear user! Thank you for using our program");
    }

    @Override
    public void output(String result) {
        System.out.println("\nYour result is: " + result);
    }

    @Override
    public long readMilliseconds(CalendarType calendarType) {
        System.out.print("Please input number of " + String.valueOf(calendarType).toLowerCase() + ": ");
        String source = ConsoleUtil.readFromConsole();
        try {
            long millisecond = Long.parseLong(source);
            if (millisecond < 0 || millisecond > 10000000000000l) {
                throw new NumberFormatException();
            }
            return millisecond;
        } catch (NumberFormatException e) {
            logger.error("Incorrect Number");
            System.out.println("\nincorrect format of time. Enter lower value  Please try again\n");
            return readMilliseconds(calendarType);
        }

    }

    private boolean checkBoundsOfUserInput(int first, int second, int option) {
        if (option < first || option > second) {
            logger.error("Incorrect Number");
            System.out.println("\nYour option must be in bound[" + first + "-" + second + "]. Please try again\n");
            return false;
        } else return true;
    }
}
