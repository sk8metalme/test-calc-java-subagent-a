package com.example.calculator.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    public BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber, String operation) {
        if (firstNumber == null || secondNumber == null || operation == null) {
            throw new IllegalArgumentException("数値と演算子は必須です");
        }

        return switch (operation) {
            case "+" -> firstNumber.add(secondNumber);
            case "-" -> firstNumber.subtract(secondNumber);
            case "*" -> firstNumber.multiply(secondNumber);
            case "/" -> {
                if (secondNumber.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("ゼロで割ることはできません");
                }
                yield firstNumber.divide(secondNumber, 10, RoundingMode.HALF_UP);
            }
            default -> throw new IllegalArgumentException("サポートされていない演算子です: " + operation);
        };
    }

    public BigDecimal calculatePercentage(BigDecimal number) {
        if (number == null) {
            throw new IllegalArgumentException("数値は必須です");
        }
        return number.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateSquareRoot(BigDecimal number) {
        if (number == null) {
            throw new IllegalArgumentException("数値は必須です");
        }
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("負の数の平方根は計算できません");
        }
        return new BigDecimal(Math.sqrt(number.doubleValue()));
    }

    public BigDecimal calculatePower(BigDecimal base, BigDecimal exponent) {
        if (base == null || exponent == null) {
            throw new IllegalArgumentException("底と指数は必須です");
        }
        return new BigDecimal(Math.pow(base.doubleValue(), exponent.doubleValue()));
    }
}
