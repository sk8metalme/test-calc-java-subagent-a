package com.example.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void testAddition() {
        BigDecimal result = calculatorService.calculate(
            new BigDecimal("10"), 
            new BigDecimal("5"), 
            "+"
        );
        assertEquals(new BigDecimal("15"), result);
    }

    @Test
    void testSubtraction() {
        BigDecimal result = calculatorService.calculate(
            new BigDecimal("10"), 
            new BigDecimal("3"), 
            "-"
        );
        assertEquals(new BigDecimal("7"), result);
    }

    @Test
    void testMultiplication() {
        BigDecimal result = calculatorService.calculate(
            new BigDecimal("4"), 
            new BigDecimal("5"), 
            "*"
        );
        assertEquals(new BigDecimal("20"), result);
    }

    @Test
    void testDivision() {
        BigDecimal result = calculatorService.calculate(
            new BigDecimal("15"), 
            new BigDecimal("3"), 
            "/"
        );
        assertEquals(new BigDecimal("5.0000000000"), result);
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculatorService.calculate(
                new BigDecimal("10"), 
                new BigDecimal("0"), 
                "/"
            );
        });
    }

    @Test
    void testInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(
                new BigDecimal("10"), 
                new BigDecimal("5"), 
                "invalid"
            );
        });
    }

    @Test
    void testPercentage() {
        BigDecimal result = calculatorService.calculatePercentage(new BigDecimal("50"));
        assertEquals(new BigDecimal("0.5000000000"), result);
    }

    @Test
    void testSquareRoot() {
        BigDecimal result = calculatorService.calculateSquareRoot(new BigDecimal("16"));
        assertEquals(0, new BigDecimal("4.0").compareTo(result));
    }

    @Test
    void testSquareRootNegative() {
        assertThrows(ArithmeticException.class, () -> {
            calculatorService.calculateSquareRoot(new BigDecimal("-1"));
        });
    }

    @Test
    void testPower() {
        BigDecimal result = calculatorService.calculatePower(
            new BigDecimal("2"), 
            new BigDecimal("3")
        );
        assertEquals(0, new BigDecimal("8.0").compareTo(result));
    }
}
