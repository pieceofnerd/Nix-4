package ua.com.alevel.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleHelperUtil {
    public static void userGreeting() {
        System.out.println("Hello! This is a program to fulfil different tasks");
    }

    public static int menu() {
        System.out.print("\n Exit: 0" +
                "\n 1 Task: find the number of unique values: 1" +
                "\n 2 Task: A knight's move on chessboard: 2" +
                "\n 3 Task: Find an area of triangle: 3" +
                "\n Please, enter a value: ");

        int select;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            select = Integer.parseInt(bufferedReader.readLine());
            if (select < 0 || select > 3) {
                System.out.println("\nPlease enter a correct option");
                return menu();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("\nPlease enter a correct option");
            return menu();
        }
        return select;
    }

    public static <T> Set<T> readUniqueSet() throws IOException {

        System.out.println("Please, input values separated by ',': ");
        String line;
        Set<T> values = new HashSet<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            line = bufferedReader.readLine();
            String lineWithoutDoubleSpaces = line.replaceAll(" +", "");
            String[] separateValues = lineWithoutDoubleSpaces.split(",");
            for (String separateValue : separateValues) {
                values.add((T) separateValue);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter correct numbers");
            return readUniqueSet();
        }
        return values;
    }

    public static List<String> read() {
        System.out.println("Please, input the coordinates of knight and coordinates where you want to move: ");
        String row = null;
        List<String> coordinates = new ArrayList<>(4);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 2; i++) {
            System.out.print("Input " + (i + 1) + " coordinate, for example c2: ");
            try {
                row = bufferedReader.readLine();
                row = row.replaceAll(" +", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pattern pattern = Pattern.compile("^[a-h]\\d");
            assert row != null;
            Matcher matcher = pattern.matcher(row);
            if (!matcher.matches()) {
                System.out.println("\nThere is wrong coordinates\n");
                return read();
            }
            Character[] values = {row.charAt(0), row.charAt(1)};
            for (Character c : values) {
                coordinates.add(String.valueOf(c));
            }
        }
        return coordinates;
    }

    public static List<Integer> readCoordinates() {
        System.out.println("Please, Input the coordinates of triangle's side");
        List<Integer> coordinates = new ArrayList<>(4);
        String value;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 3; i++) {
            System.out.print("Input " + (i + 1) + " coordinate, for example -1, 5: ");
            try {
                value = bufferedReader.readLine();
                value = value.replaceAll(" +", "");
            } catch (IOException | NumberFormatException e) {
                System.out.println("\nThere is wrong parameters\n");
                return readCoordinates();
            }
            try {
                String[] temporaryValuesArray = value.split(",");
                Integer[] points = {Integer.parseInt(String.valueOf(temporaryValuesArray[0].charAt(0))), Integer.parseInt(String.valueOf(temporaryValuesArray[1].charAt(0)))};
                coordinates.addAll(Arrays.asList(points).subList(0, 2));
            } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("\nThere is wrong parameters\n");
                return readCoordinates();
            }
        }
        return coordinates;
    }

    public static <T> void write(T value, TaskNumber taskNumber) {
        switch (taskNumber) {
            case FIRST:
                System.out.println("Your result is " + value.toString());
                break;
            case SECOND: {
                if ((boolean) value) {
                    System.out.println("You can move the knight");
                } else System.out.println("You can't move the knight. Please choose other location");
                break;
            }
        }
    }


}
