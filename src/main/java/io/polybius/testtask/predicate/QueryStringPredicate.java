package io.polybius.testtask.predicate;

import com.google.gson.JsonObject;
import io.polybius.testtask.predicate.expressions.ExpressionEvaluator;
import io.polybius.testtask.predicate.expressions.StringExpressionEvaluator;
import io.polybius.testtask.predicate.tokens.RegexpTokenizer;
import io.polybius.testtask.predicate.tokens.Tokenizer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class QueryStringPredicate {
    private final Deque<Boolean> valueStack;
    private final Deque<LogicalOperators> operatorStack;
    private final ExpressionEvaluator evaluator;
    private final List<String> tokens;
    private final Tokenizer tokenizer;

    public QueryStringPredicate(String queryString) {
        this.valueStack = new LinkedList<>();
        this.operatorStack = new LinkedList<>();
        this.evaluator = new StringExpressionEvaluator();
        this.tokenizer = new RegexpTokenizer();
        this.tokens = tokenizer.getTokenList(queryString);
    }

    public boolean verify(JsonObject jsonObject) {
        for (String token : tokens) {
            if (!isOperator(token)) {
                valueStack.push(evaluator.evaluate(token, jsonObject));
                continue;
            }

            LogicalOperators currOperator = LogicalOperators.from(token);
            LogicalOperators topOperator = operatorStack.peek();
            if (null == topOperator || topOperator.getPrecedence() < currOperator.getPrecedence()) {
                operatorStack.push(currOperator);
                continue;
            }

            valueStack.push(calculate());
            operatorStack.push(currOperator);
        }

        while (!operatorStack.isEmpty()) {
            valueStack.push(calculate());
        }
        return valueStack.pop();
    }

    private boolean calculate() {
        boolean one = valueStack.pop();
        boolean two = valueStack.pop();
        LogicalOperators operator = operatorStack.pop();

        boolean result = false;
        switch (operator) {
            case AND:
                result = one && two;
                break;
            case OR:
                result = one || two;
                break;
        }
        return result;
    }

    private boolean isOperator(String token) {
        return "&&".equals(token) || "||".equals(token);
    }

    enum LogicalOperators {
        AND(2),
        OR(1);

        private int precedence;

        LogicalOperators(int p) {
            precedence = p;
        }

        public static LogicalOperators from(String strOperator) {
            LogicalOperators result = null;
            switch (strOperator) {
                case "&&":
                    result = AND;
                    break;
                case "||":
                    result = OR;
                    break;
                default:
                    throw new IllegalArgumentException("Operator not found");
            }
            return result;
        }

        public int getPrecedence() {
            return precedence;
        }
    }
}