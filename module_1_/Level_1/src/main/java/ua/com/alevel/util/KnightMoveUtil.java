package ua.com.alevel.util;

import java.util.List;

public class KnightMoveUtil {

    public static boolean isKnightMove(List<String> coordinates) {
        int x1, x2, y1, y2;
        boolean flag;
        x1 = coordinates.get(0).charAt(0);
        x2 = coordinates.get(2).charAt(0);
        y1 = Integer.parseInt(coordinates.get(1));
        y2 = Integer.parseInt(coordinates.get(3));

        flag = (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 1) || (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 2);
        return flag;
    }
}
