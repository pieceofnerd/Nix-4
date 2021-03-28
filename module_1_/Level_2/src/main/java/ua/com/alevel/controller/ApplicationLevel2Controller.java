package ua.com.alevel.controller;

import ua.com.alevel.util.ConsoleHelperLevel2Util;
import ua.com.alevel.util.StringCheckUtil;

public class ApplicationLevel2Controller {
    public void runApplication() {

        String source = ConsoleHelperLevel2Util.read();
        boolean flag = StringCheckUtil.isStringValid(source);
        ConsoleHelperLevel2Util.write(flag);
    }
}
