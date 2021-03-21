package ua.com.alevel.controller;

import ua.com.alevel.StringReverseUtil;
import ua.com.alevel.view.ConsoleHelperUtil;

public class ApplicationController {

    public String runApplication(int option) {
        switch (option) {
            case 1: {
                String source = ConsoleHelperUtil.read(Option.STRING);
                return StringReverseUtil.reverse(source);
            }
            case 2: {
                String source = ConsoleHelperUtil.read(Option.STRING);
                String dest = ConsoleHelperUtil.read(Option.SUBSTRING);
                return StringReverseUtil.reverse(source, dest);
            }
            case 3: {
                String source = ConsoleHelperUtil.read(Option.STRING);
                String firstIndex = ConsoleHelperUtil.read(Option.INDEX);
                String lastIndex = ConsoleHelperUtil.read(Option.INDEX);
                int firstIndexValue;
                int secondIndexValue;
                try {
                    firstIndexValue = Integer.parseInt(firstIndex);
                    secondIndexValue = Integer.parseInt(lastIndex);
                    if (firstIndexValue > source.length() + 1 || secondIndexValue > source.length() + 1) {
                        System.out.print("Your indexes aren't valid. Input again: ");
                        return runApplication(option);
                    }
                    return StringReverseUtil.reverse(source, firstIndexValue, secondIndexValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return runApplication(option);
                }
            }
            default: return null;
        }

    }
}
