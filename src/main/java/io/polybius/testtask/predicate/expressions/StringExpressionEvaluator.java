package io.polybius.testtask.predicate.expressions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class StringExpressionEvaluator implements ExpressionEvaluator {

    private static final int COUNT_OF_OPERANDS = 2;
    private static final String[] COMPARISON_OPERATORS = {"<=", ">=", "=", ">", "<"};

    @Override
    public boolean evaluate(String expression, JsonObject jsonObject) {
        if (null == expression || expression.isEmpty()) {
            throw new IllegalArgumentException("Expression can not be empty");
        }

        if (null == jsonObject || jsonObject.isJsonNull()) {
            return false;
        }

        String operator = extractOperator(expression);
        String[] values = extractValues(expression);
        if (values.length != COUNT_OF_OPERANDS) {
            throw new IllegalArgumentException("Invalid count of operands: " + expression);
        }

        String left = values[0];
        String right = values[1];
        if (!jsonObject.has(left)) {
            return false;
        }

        if (!isNumber(right) && !"=".equals(operator)) {
            throw new IllegalArgumentException("For string values only use contains logic (=)");
        }

        JsonElement jsonElement = jsonObject.get(left);
        boolean result = false;
        switch (operator) {
            case ">=": {
                result = jsonElement.getAsDouble() >= Double.parseDouble(right);
                break;
            }
            case "<=": {
                result = jsonElement.getAsDouble() <= Double.parseDouble(right);
                break;
            }
            case ">": {
                result = jsonElement.getAsDouble() > Double.parseDouble(right);
                break;
            }
            case "<": {
                result = jsonElement.getAsDouble() < Double.parseDouble(right);
                break;
            }
            case "=": {
                result = isNumber(jsonElement.getAsString())
                        ? jsonElement.getAsDouble() == Double.parseDouble(right)
                        : jsonElement.getAsString().toLowerCase().contains(right.toLowerCase());
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }

        return result;
    }

    private String extractOperator(String expression) {
        for (String s : COMPARISON_OPERATORS) {
            if (expression.contains(s)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown operator in expression: " + expression);
    }

    private String[] extractValues(String expression) {
        String regex = String.join("|", COMPARISON_OPERATORS);
        return expression.split(regex);
    }

    private boolean isNumber(String value) {
        if (null == value) {
            return false;
        }
        return value.matches("-?\\d+(\\.\\d+)?");
    }
}
