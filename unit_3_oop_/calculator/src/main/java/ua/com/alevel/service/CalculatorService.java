package ua.com.alevel.service;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface CalculatorService {

    BigDecimal sum(BigDecimal... values);

    BigDecimal multiply(BigDecimal... values);

    BigDecimal subtraction(BigDecimal... values);

    BigDecimal division(BigDecimal... values);

    BigDecimal percentageByNumber(BigDecimal number, BigInteger percent);

    BigDecimal start(int value, BigDecimal... values);
}
