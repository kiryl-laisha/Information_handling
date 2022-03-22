package org.laisha.information_handling.util.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.exception.ProjectException;

import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArithmeticExpressionCalculationImplTest {

    static ArithmeticExpressionCalculationImpl calculation;

    @BeforeAll
    static void setUpClass() {
        calculation = ArithmeticExpressionCalculationImpl.getInstance();
    }

    @AfterAll
    static void tearDownClass() {
        calculation = null;
    }

    @ParameterizedTest
    @DisplayName("Test parses infix expression to postfix expression correctly.")
    @ValueSource(strings = {" -15*(5+-2) "})
    void parseExpressionFromInfixNotationToPostfixNotationPositiveTest(String infixExpression)
            throws ProjectException {

        String actualPostfixExpression =
                calculation.parseExpressionFromInfixNotationToPostfixNotation(infixExpression);
        String expectedPostfixExpression = "15 ~ 5 2 ~ + * ";
        assertEquals(expectedPostfixExpression, actualPostfixExpression);
    }

    @ParameterizedTest
    @DisplayName("Test catches an exception when an infix expression is null or empty " +
            "or contains only whitespace characters.")
    @NullAndEmptySource
    @ValueSource(strings = {"      ",
            "\n\n     ",
            "  \n \t \r "})
    void parseExpressionFromInfixNotationToPostfixNotationFirstNegativeTest(String infixExpression) {

        String expectedExceptionMessage = "Arithmetic expression is null or empty.";
        String actualExceptionMessage = null;
        try {
            calculation.parseExpressionFromInfixNotationToPostfixNotation(infixExpression);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @ParameterizedTest
    @DisplayName("Test catches an exception when an infix expression contains some " +
            "invalid characters.")
    @ValueSource(strings = {"-15*(5+-2)!",
            "  &-15*(5+-2)",
            "{-15*(5+-2)"})
    void parseExpressionFromInfixNotationToPostfixNotationSecondNegativeTest(String infixExpression) {

        String expectedExceptionMessage = "Arithmetic expression contains invalid characters.";
        String actualExceptionMessage = null;
        try {
            calculation.parseExpressionFromInfixNotationToPostfixNotation(infixExpression);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @ParameterizedTest
    @DisplayName("Test calculates the correct postfix expression.")
    @ValueSource(strings = {"15 ~ 5 2 ~ + * "})
    void calculatePostfixNotationExpressionPositiveTest(String postfixExpression) {

        OptionalDouble optionalActual =
                calculation.calculatePostfixNotationExpression(postfixExpression);
        double expected = -45;
        double actual = 0;
        if (optionalActual.isPresent()) {
            actual = optionalActual.getAsDouble();
        }
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test defines empty Optional object when an postfix expression is null or empty " +
            "or contains only whitespace or some invalid characters.")
    @NullAndEmptySource
    @ValueSource(strings = {"      ",
            "\n\n     ",
            "  \n \t \r ",
            "! 15 ~ 5 2 ~ + * ",
            "x 15 ~ 5 2 ~ + * "})
    void calculatePostfixNotationExpressionNegativeTest(String postfixExpression) {

        OptionalDouble optionalActual =
                calculation.calculatePostfixNotationExpression(postfixExpression);
        boolean expected = true;
        boolean actual = optionalActual.isEmpty();
        assertEquals(expected, actual);
    }
}