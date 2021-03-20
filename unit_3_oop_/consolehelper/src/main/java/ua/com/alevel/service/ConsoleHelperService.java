package ua.com.alevel.service;

import java.math.BigDecimal;

public interface ConsoleHelperService {

    int read();

    void write(BigDecimal result);

    void userGreeting();

    BigDecimal[] input();
}
