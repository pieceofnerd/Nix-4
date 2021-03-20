package ua.com.alevel.service.impl;

import ua.com.alevel.service.CalculatorService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static java.math.BigInteger.ZERO;

@Deprecated
public class DivisionbyZeroCalculatorVersion implements CalculatorService {
    @Override
    public BigDecimal sum(BigDecimal... values) {
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal value : values) {
            sum = sum.add(value);
        }
        return sum;
    }

    @Override
    public BigDecimal multiply(BigDecimal... values) {
        BigDecimal multiply = new BigDecimal(1);
        for (BigDecimal value : values) {
            multiply = multiply.multiply(value);
        }
        return multiply;
    }

    @Override
    public BigDecimal subtraction(BigDecimal... values) {
        BigDecimal subtraction = new BigDecimal(String.valueOf(values[0]));
        for (int i = 1; i < values.length; i++) {
            subtraction = subtraction.subtract(values[i]);
        }
        return subtraction;
    }

    @Override
    public BigDecimal division(BigDecimal... values) {
        BigDecimal division = new BigDecimal(String.valueOf(values[0]));
        BigDecimal zero = new BigDecimal(0);
        for (int i = 1; i < values.length; i++) {
            if (values[i].equals(zero)) {
                System.out.print("You result is infinity: ");
                return null;
            }
            division = division.divide(values[i], RoundingMode.HALF_UP);
        }
        return division;
    }

    @Override
    public BigDecimal percentageByNumber(BigDecimal number, BigInteger percent) {
        BigDecimal result;
        if (percent.compareTo(ZERO) > 0) {
            result = number.multiply(new BigDecimal(percent)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        } else throw new RuntimeException("Percent should be a positive number");
        return result;
    }

    @Override
    public BigDecimal start(int value, BigDecimal... values) {
        BigDecimal result = null;
        switch (value) {
            case 1: {
                result = sum(values);
                break;
            }
            case 2: {
                result = multiply(values);
                break;
            }
            case 3: {
                result = subtraction(values);
                break;
            }
            case 4: {
                result = division(values);
                break;
            }
            //case 5: percentageByNumber(values)
        }
        return result;
    }
}
