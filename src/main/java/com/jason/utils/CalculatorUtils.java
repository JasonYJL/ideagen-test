package com.jason.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorUtils {
    private static final String EXPRESSION_REGEX = "\\([\\d.+\\-*\\/ ]*\\)";
    private static final String[] OPERATORS = {"*", "/", "+", "-"};

    public static double calculate(String sum) {
        // using regex to retrieve equation within bracket.
        Pattern pattern = Pattern.compile(EXPRESSION_REGEX);
        Matcher matcher = pattern.matcher(sum);

        String expression = sum;

        boolean checkIfRegexMatch = matcher.find();
        if (checkIfRegexMatch) {
            String group = matcher.group();

            // to remove "(" and ")" from the extracted equation
            expression = group.substring(2, group.lastIndexOf(")") - 1);
        }

        String value = CalculatorUtils.expressionProcess(expression);

        if (checkIfRegexMatch) {
            return calculate(sum.replaceFirst(EXPRESSION_REGEX, value));
        }

        return Double.parseDouble(value);
    }

    /**
     *
     * @param equation extracted math equation which required to be executed,
     * @return calculated value in String data type.
     */
    public static String expressionProcess(String equation) {
        for (String s : OPERATORS) {
            // using operator to build regex for expression extraction
            String operatorRegex = String.format("[\\d.]* \\%s [\\d.]*", s);
            Pattern operatorPattern = Pattern.compile(operatorRegex);
            Matcher operatorMatcher = operatorPattern.matcher(equation);

            if (operatorMatcher.find()) {
                BigDecimal answer = operatorLogic(operatorMatcher.group().split(" "));
                equation = equation.replaceFirst(operatorRegex, answer.toString());
                if (equation.length() == 1) {
                    break;
                }
                return expressionProcess(equation);
            }
        }
        return equation;
    }

    /**
     * Used to calculate value from passing expression array.
     * @param expression Eg 1 + 1 became {"1", "+", "1"}
     * @return BigDecimal value calculated from expression passed in.
     */
    private static BigDecimal operatorLogic(String... expression) {
        BigDecimal firstNum = new BigDecimal(expression[0]);
        BigDecimal secondNum = new BigDecimal(expression[2]);

        return switch (expression[1]) {
            case "*" -> firstNum.multiply(secondNum);
            case "/" -> firstNum.divide(secondNum);
            case "+" -> firstNum.add(secondNum);
            default -> firstNum.subtract(secondNum);
        };
    }
}
