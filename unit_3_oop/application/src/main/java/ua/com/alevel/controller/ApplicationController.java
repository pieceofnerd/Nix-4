package ua.com.alevel.controller;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.service.CalculatorService;
import ua.com.alevel.service.ConsoleHelperService;
import ua.com.alevel.service.factory.CalculatorServiceFactory;
import ua.com.alevel.service.factory.ConsoleHelperFactory;

import java.math.BigDecimal;

public class ApplicationController {
    ConsoleHelperService consoleHelperService = ConsoleHelperFactory.getInstance().getConsoleHelpService();
    CalculatorService calculatorService = CalculatorServiceFactory.getInstance().getCalculatorService();
    @Getter
    @Setter
    private static boolean flag = true;

    public void runApplication() {
        consoleHelperService.userGreeting();
        while (flag) {
            int value = consoleHelperService.read();
            if (value == 0) {
                flag = false;
                return;
            }
            BigDecimal[] arguments = consoleHelperService.input();
            BigDecimal result = calculatorService.start(value, arguments);
            consoleHelperService.write(result);
        }
    }
}
