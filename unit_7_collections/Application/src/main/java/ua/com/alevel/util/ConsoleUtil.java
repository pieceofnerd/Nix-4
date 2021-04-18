package ua.com.alevel.util;

import ua.com.alevel.impl.OrderList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConsoleUtil {

    public static String readFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getSize() {
        System.out.print("Please, input number of objects: ");
        int size = getNumber();
        if (size < 0) {
            System.out.println("Your value doesn't correct ");
            return getSize();
        }
        return size;
    }

    public static int getNumber() throws NumberFormatException {
        String input = ConsoleUtil.readFromConsole();
        if (Objects.isNull(input)) {
            System.out.println("Something goes wrong. Please, try again");
            throw new NumberFormatException();
        }
        return Integer.parseInt(input);
    }

    public static <Type extends Comparable<? super Type>> void output(OrderList<Type> orderList) {
        if (orderList.isEmpty()) {
            System.out.println("List is empty");
            return;
        }
        System.out.println("Your order list: ");
        orderList.forEach(System.out::println);
    }

    public static <Type extends Comparable<? super Type>> void output(Type obj) {
        if (Objects.isNull(obj)) {
            System.out.println("There is no object");
        } else System.out.println("Your result: " + obj);
    }

}
