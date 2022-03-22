package org.laisha.information_handling.util.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laisha.information_handling.exception.ProjectException;
import org.laisha.information_handling.util.ArithmeticExpressionCalculation;

import java.util.*;

public class ArithmeticExpressionCalculationImpl implements ArithmeticExpressionCalculation {

    private static final Logger logger = LogManager.getLogger();
    private static final ArithmeticExpressionCalculationImpl instance =
            new ArithmeticExpressionCalculationImpl();
    private static final String ARITHMETIC_OPERATOR_REGEX = "[-+*/]";
    private static final String ARITHMETIC_OPERATOR_AND_LIKE_UNARY_MINUS_REGEX =
            ARITHMETIC_OPERATOR_REGEX + "|[~]";//"~" means unary minus
    private static final String ARITHMETIC_OPERATOR_AND_OPENING_BRACKET_REGEX =
            ARITHMETIC_OPERATOR_REGEX + "|[(]";
    private static final String ARITHMETIC_INFIX_EXPRESSION_ELEMENT_REGEX = "[-+*/\\d() ]+";
    private static final String ARITHMETIC_POSTFIX_EXPRESSION_ELEMENT_REGEX = "[-+*/~\\d ]+";
    private static final String ELEMENT_DELIMITER = " ";

    private ArithmeticExpressionCalculationImpl() {
    }

    public static ArithmeticExpressionCalculationImpl getInstance() {
        return instance;
    }

    @Override
    public String parseExpressionFromInfixNotationToPostfixNotation(String expression)
            throws ProjectException {

        validateInfixExpression(expression);
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder postfixNotation = new StringBuilder();
        expression = expression.strip();
        int i = 0;
        while (i < expression.length()) {
            lbl:
            {
                char currentChar = expression.charAt(i);
                if (' ' == currentChar) {
                    break lbl;
                }
                if (Character.isDigit(currentChar)) {
                    int lastIndexOfCurrentNumber = findLastIndexOfCurrentNumber(expression, i);
                    String currentNumberString;
                    if (lastIndexOfCurrentNumber == expression.length() - 1) {
                        currentNumberString = expression.substring(i);
                    } else {
                        currentNumberString = expression.substring(i, lastIndexOfCurrentNumber + 1);
                    }
                    postfixNotation.append(currentNumberString).append(ELEMENT_DELIMITER);
                    i = lastIndexOfCurrentNumber;
                    break lbl;
                }
                if (currentChar == '(') {
                    stack.push(String.valueOf(currentChar));
                    break lbl;
                }
                if (currentChar == ')') {
                    while (!(stack.isEmpty() || stack.peek().equals("("))) {
                        postfixNotation.append(stack.pop()).append(ELEMENT_DELIMITER);
                    }
                    stack.pop();
                    break lbl;
                }
                if (String.valueOf(currentChar).matches(ARITHMETIC_OPERATOR_REGEX)) {
                    if (currentChar == '-' &&
                            (i == 0 || (i > 1 &&
                                    String.valueOf(expression.charAt(i - 1))
                                            .matches(ARITHMETIC_OPERATOR_AND_OPENING_BRACKET_REGEX)))) {
                        currentChar = '~';//like unary minus
                    } else {
                        while (!stack.isEmpty() &&
                                definePriority(currentChar) <=
                                        definePriority(stack.peek().toCharArray()[0])) {
                            postfixNotation.append(stack.pop()).append(ELEMENT_DELIMITER);
                        }
                    }
                    stack.push(String.valueOf(currentChar));
                }
            }
            i++;
        }
        for (String operator : stack) {
            if (operator.matches(ARITHMETIC_OPERATOR_AND_LIKE_UNARY_MINUS_REGEX)) {
                postfixNotation.append(operator).append(ELEMENT_DELIMITER);
            } else {
                throw new ProjectException("Brackets don't match.");
            }
        }
        return postfixNotation.toString();
    }

    @Override
    public OptionalDouble calculatePostfixNotationExpression(String postfixNotation) {

        boolean isValid = validatePostfixExpression(postfixNotation);
        if (!isValid) {
            return OptionalDouble.empty();
        }
        Deque<Double> stack = new ArrayDeque<>();
        postfixNotation = postfixNotation.strip();
        int i = 0;
        while (i < postfixNotation.length()) {
            lbl:
            {
                char currentChar = postfixNotation.charAt(i);
                if (' ' == currentChar) {
                    break lbl;
                }
                if (Character.isDigit(currentChar)) {
                    int lastIndexOfCurrentNumber = findLastIndexOfCurrentNumber(postfixNotation, i);
                    String currentNumberString;
                    if (lastIndexOfCurrentNumber == postfixNotation.length() - 1) {
                        currentNumberString = postfixNotation.substring(i);
                    } else {
                        currentNumberString = postfixNotation.substring(i, lastIndexOfCurrentNumber + 1);
                    }
                    stack.push(Double.parseDouble(currentNumberString));
                    i = lastIndexOfCurrentNumber;
                    break lbl;
                }
                double secondNumber = stack.pop();
                double firstNumber;
                if (String.valueOf(currentChar).matches(ARITHMETIC_OPERATOR_REGEX)) {
                    firstNumber = stack.pop();
                } else {
                    firstNumber = 0;
                }
                stack.push(calculateLocalExpression(currentChar, firstNumber, secondNumber));
            }
            i++;
        }
        return OptionalDouble.of(stack.pop());
    }

    private Double calculateLocalExpression(char currentChar,
                                            double firstNumber,
                                            double secondNumber) {

        double result;
        switch (currentChar) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
            case '~':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
            default:
                result = 0;
        }
        return result;
    }

    private int definePriority(char element) {

        int priorityIndex;
        switch (element) {
            case '(':
                priorityIndex = 0;
                break;
            case '-':
            case '+':
                priorityIndex = 1;
                break;
            case '*':
            case '/':
                priorityIndex = 2;
                break;
            case '~':
                priorityIndex = 3;
                break;
            default:
                priorityIndex = -1;
        }
        return priorityIndex;
    }

    private int findLastIndexOfCurrentNumber(String expression,
                                             int firstIndexOfCurrentNumber) {

        int lastIndexOfCurrentNumber = firstIndexOfCurrentNumber;
        while (lastIndexOfCurrentNumber < expression.length() - 1 &&
                Character.isDigit(expression.charAt(lastIndexOfCurrentNumber + 1))) {
            lastIndexOfCurrentNumber++;
        }
        return lastIndexOfCurrentNumber;
    }

    private void validateInfixExpression(String expression) throws ProjectException {

        if (expression == null || expression.isBlank()) {
            throw new ProjectException("Arithmetic expression is null or empty.");
        }
        if (!expression.matches(ARITHMETIC_INFIX_EXPRESSION_ELEMENT_REGEX)) {
            throw new ProjectException("Arithmetic expression contains invalid characters.");
        }
    }

    private boolean validatePostfixExpression(String expression) {

        if (expression == null || expression.isBlank()) {
            logger.log(Level.WARN, "Postfix expression is null or empty.");
            return false;
        }
        if (!expression.matches(ARITHMETIC_POSTFIX_EXPRESSION_ELEMENT_REGEX)) {
            logger.log(Level.WARN, "Postfix expression contains invalid characters.");
            return false;
        }
        return true;
    }
}
