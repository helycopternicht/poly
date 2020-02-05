package io.polybius.testtask.predicate.expressions;

import com.google.gson.JsonObject;

public interface ExpressionEvaluator {
    boolean evaluate(String expression, JsonObject jsonObject);
}
