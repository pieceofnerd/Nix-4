package ua.com.alevel.controller;

import ua.com.alevel.impl.HeronTriangleArea;
import ua.com.alevel.util.ConsoleHelperUtil;
import ua.com.alevel.util.KnightMoveUtil;
import ua.com.alevel.util.TaskNumber;
import ua.com.alevel.util.UniqueValuesUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ApplicationLevel1Controller {
    private static boolean flag = true;

    public void runApplication() {
        ConsoleHelperUtil.userGreeting();
        while (flag) {
            int option = ConsoleHelperUtil.menu();
            switch (option) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    try {
                        Set<Integer> values = ConsoleHelperUtil.readUniqueSet();
                        int number = UniqueValuesUtil.quantityUniqueValues(values);
                        ConsoleHelperUtil.write(number, TaskNumber.FIRST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    List<String> coordinates = ConsoleHelperUtil.read();
                    boolean isKnightMoves = KnightMoveUtil.isKnightMove(coordinates);
                    ConsoleHelperUtil.write(isKnightMoves, TaskNumber.SECOND);
                    break;
                }
                case 3: {
                    List<Integer> coordinates = ConsoleHelperUtil.readCoordinates();
                    HeronTriangleArea heronTriangleArea = new HeronTriangleArea();
                    double[] sides = heronTriangleArea.calculateSideByPointsOnPlane(coordinates.get(0), coordinates.get(1), coordinates.get(2), coordinates.get(3), coordinates.get(4), coordinates.get(5));
                    double area = heronTriangleArea.area(sides);
                    area = Math.round(area * 100) / 100.0;
                    ConsoleHelperUtil.write(area, TaskNumber.FIRST);
                    break;
                }
            }
        }
    }

}
