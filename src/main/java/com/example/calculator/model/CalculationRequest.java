package com.example.calculator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class CalculationRequest {
    
    @NotNull(message = "最初の数値は必須です")
    @DecimalMin(value = "-999999999.9999999999", message = "数値は-999999999.9999999999以上である必要があります")
    @DecimalMax(value = "999999999.9999999999", message = "数値は999999999.9999999999以下である必要があります")
    private BigDecimal firstNumber;
    
    @NotNull(message = "2番目の数値は必須です")
    @DecimalMin(value = "-999999999.9999999999", message = "数値は-999999999.9999999999以上である必要があります")
    @DecimalMax(value = "999999999.9999999999", message = "数値は999999999.9999999999以下である必要があります")
    private BigDecimal secondNumber;
    
    @NotNull(message = "演算子は必須です")
    private String operation;
    
    private String displayValue = "0";
    private String previousValue = "";
    private String currentOperation = "";
    private boolean shouldResetDisplay = false;

    // デフォルトコンストラクタ
    public CalculationRequest() {}

    // コンストラクタ
    public CalculationRequest(BigDecimal firstNumber, BigDecimal secondNumber, String operation) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
    }

    // Getters and Setters
    public BigDecimal getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(BigDecimal firstNumber) {
        this.firstNumber = firstNumber;
    }

    public BigDecimal getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(BigDecimal secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(String currentOperation) {
        this.currentOperation = currentOperation;
    }

    public boolean isShouldResetDisplay() {
        return shouldResetDisplay;
    }

    public void setShouldResetDisplay(boolean shouldResetDisplay) {
        this.shouldResetDisplay = shouldResetDisplay;
    }
}
