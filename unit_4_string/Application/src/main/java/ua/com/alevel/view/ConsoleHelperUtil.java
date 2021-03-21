package ua.com.alevel.view;

import ua.com.alevel.controller.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelperUtil {

    public static void output(String string) {
        System.out.println("Result: " + string);
    }

    public static void userGreeting() {
        System.out.println("Hello! This is a program to reverse your strings");
    }

    public static String read(Option option) {
        switch (option) {
            case STRING: {
                System.out.print("Please, enter a string: ");
                return read();
            }
            case SUBSTRING: {
                System.out.print("Please, enter a substring: ");
                return read();
            }
            case INDEX: {
                System.out.print("Please, enter an index: ");
                int index = readIndex();
                return String.valueOf(index);
            }
            default:
                return read(option);
        }
    }

    public static String read() {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return read();
        }
        return string;
    }

    public static int readIndex() {
        int value;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Integer.parseInt(bufferedReader.readLine());
            if (value < 1) {
                System.out.print("Your indexes aren't valid. Input again: ");
                return readIndex();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.print("Your indexes aren't valid. Input again: ");
            return readIndex();
        }
        return value;
    }

    public static int menu() {
        System.out.print(
                "\nReverse words: 1" +
                        "\nReverse substring: 2" +
                        "\nReverse string within two indexes: 3" +
                        "\nExit: 0" +
                        "\nSelect option by appropriate number: ");
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
