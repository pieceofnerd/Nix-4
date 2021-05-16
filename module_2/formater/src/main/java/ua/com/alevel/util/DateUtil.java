package ua.com.alevel.util;

public class DateUtil {
    public static String removeFirstZeroInDate(String datePart) {
        if (datePart.isEmpty()) {
            return datePart;
        }
        if (datePart.length() > 1) {
            if (datePart.charAt(0) == '0' && datePart.charAt(1) != '0') {
                char[] temporaryString = datePart.toCharArray();
                char[] stringWithoutFirstZero = new char[temporaryString.length - 1];
                System.arraycopy(temporaryString, 1, stringWithoutFirstZero, 0, temporaryString.length - 1);
                datePart = String.valueOf(stringWithoutFirstZero);
            }
        }
        return datePart;
    }

    public static String addFirstZero(int datePart) {
        if (datePart < 10 && datePart > 0) {
            return "0" +
                    datePart;
        } else return String.valueOf(datePart);
    }

    public static boolean checkMonth(int month) {
        return month <= 12 && month >= 1;
    }

    public static boolean checkDay(int day) {
        return day <= 31 && day >= 1;
    }

}


