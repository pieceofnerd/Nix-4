package ua.com.alevel.service;

public interface TriangleArea {

    double area(double... sides);

    double[] calculateSideByPointsOnPlane(int x1, int y1, int x2, int y2, int x3, int y3);

}
