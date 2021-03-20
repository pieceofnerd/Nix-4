package ua.com.alevel.service.impl;

import ua.com.alevel.service.ConsoleHelperService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class CalculatorConsoleHelper implements ConsoleHelperService {
    @Override
    public int read() {
        int value;
        System.out.print(
                         "\n EXIT: 0 " +
                        "\n Calculate sum of any numbers: 1 " +
                        "\n Calculate multiply of any numbers: 2 " +
                        "\n Calculate subtraction of any numbers: 3 " +
                        "\n Calculate divide of any numbers: 4" +
                        "\n Сalculate a certain percentage of a given number(first parameter is a number, second is percent): 5" +
                        "\n Please, enter a number to select the appropriate operation: ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Integer.parseInt(bufferedReader.readLine());
            if (value < 0 || value > 5) {
                System.out.println("Please input correct number");
                value = read();

            }


        } catch (IOException | NumberFormatException e) {
            System.out.println("Please input correct number");
            value = read();

        }
        return value;
    }

    @Override
    public void write(BigDecimal result) {
        System.out.println("---------------------RESULT--------------------------" +
                "\nYour result is: " + result);
    }

    @Override
    public void userGreeting() {
        System.out.println("Hello! This is a program to provide a handy calculator");
    }

    @Override
    public BigDecimal[] input() {

        System.out.print("Input arguments separate by ',': ");
        String value;
        String[] arguments;
        BigDecimal[] values;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = bufferedReader.readLine();
            value = value.replaceAll(" ", "");
            arguments = value.split(",");
            if (arguments.length < 2) {
                System.out.println("Please, input correct values");
                return input();
            }
            values = new BigDecimal[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                values[i] = new BigDecimal(arguments[i]);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Please, input correct values");
            return input();
        }
        return values;
    }
}
