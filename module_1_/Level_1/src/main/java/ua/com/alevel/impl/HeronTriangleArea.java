package ua.com.alevel.impl;

import ua.com.alevel.service.TriangleArea;

public class HeronTriangleArea implements TriangleArea {

    @Override
    public double area(double... sides) {
        double halfPerimeter = 0;
        double multiplySidesHalfPerimeter = 1;
        for (Double d : sides) {
            halfPerimeter += d / 2;
        }
        for (Double d : sides) {
            multiplySidesHalfPerimeter *= halfPerimeter - d;
        }
        return Math.sqrt(halfPerimeter * multiplySidesHalfPerimeter);
    }

    @Override
    public double[] calculateSideByPointsOnPlane(int x1, int y1, int x2, int y2, int x3, int y3) {
        double[] sides = new double[3];
        sides[0] = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow(y1 - y2, 2));
        sides[1] = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow(y2 - y3, 2));
        sides[2] = Math.sqrt(Math.pow((x3 - x1), 2) + Math.pow(y3 - y1, 2));
        return sides;
    }


}
