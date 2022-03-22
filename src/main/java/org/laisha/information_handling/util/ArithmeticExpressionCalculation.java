package org.laisha.information_handling.util;

import org.laisha.information_handling.exception.ProjectException;

import java.util.OptionalDouble;

public interface ArithmeticExpressionCalculation {

    String parseExpressionFromInfixNotationToPostfixNotation(String expression)
            throws ProjectException;

    OptionalDouble calculatePostfixNotationExpression(String postfixNotation);
}
