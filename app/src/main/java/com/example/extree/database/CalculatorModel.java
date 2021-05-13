package com.example.extree.database;

public class CalculatorModel {
    /* Main constructor */
    public CalculatorModel(String expression, String resultString, Double resultDouble) {
        this.expression = expression;
        this.resultString = resultString;
        this.resultDouble = resultDouble;
        this.calculatorID = -1;
    }

    public CalculatorModel(String expression, String resultString, Double resultDouble, Integer calculatorID) {
        this.expression = expression;
        this.resultString = resultString;
        this.resultDouble = resultDouble;
        this.calculatorID = calculatorID;
    }

    /* Getters */

    public String getExpression() {
        return expression;
    }

    public String getResultString() {
        return resultString;
    }

    public Double getResultDouble() {
        return resultDouble;
    }

    public Integer getCalculatorID() {
        return calculatorID;
    }

    /* Only expression, for example - '2+2*3' */
    private final String expression;
    /* Result with expression and '=' */
    private final String resultString;
    /* Result */
    private final Double resultDouble;
    /* ID */
    private final Integer calculatorID;

}
