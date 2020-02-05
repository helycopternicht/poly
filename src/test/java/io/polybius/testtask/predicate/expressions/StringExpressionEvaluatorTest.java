package io.polybius.testtask.predicate.expressions;

import com.google.gson.JsonObject;
import io.polybius.testtask.helper.JsonHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringExpressionEvaluatorTest {

    private final StringExpressionEvaluator evaluator = new StringExpressionEvaluator();
    private final JsonHelper helper = new JsonHelper();

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_WhenExpressionIsNull_ShouldThrowException() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        evaluator.evaluate(null, object);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_WhenExpressionIsBlank_ShouldThrowException() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        evaluator.evaluate("", object);
    }

    @Test
    public void evaluate_WhenJsonObjectIsNull_ShouldReturnFalse() {
        boolean actual = evaluator.evaluate("foo>0", null);

        assertFalse(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_WhenExpressionHasNotAnyOperator_ShouldThrowException() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        evaluator.evaluate("expression_without_operator", object);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_WhenExpressionWithMoreThenOneOperator_ShouldThrowException() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        evaluator.evaluate("name=Kate=kate", object);
    }

    @Test
    public void evaluate_WhenJsonObjectHasNotFieldSpecifiedInExpression_ShouldReturnFalse() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        boolean actual = evaluator.evaluate("age>25", object);

        assertFalse(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluate_WhenStringValueUsedInExpressionWithWrongOperator_ShouldThrowException() {
        JsonObject object = helper.getJsonObject("{\"name\": \"Kate\"}");
        evaluator.evaluate("name>Kate", object);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionGOEIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("age>=15", object);

        assertTrue(actual);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionLOEIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("age<=15", object);

        assertTrue(actual);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionGTIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("age>1", object);

        assertTrue(actual);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionLTIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("age<16", object);

        assertTrue(actual);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionEqualsIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("age=15", object);

        assertTrue(actual);
    }

    @Test
    public void evaluate_WhenFieldIsPresentedAndConditionContainsWithStringValueIsCorrect_ShouldReturnTrue() {
        JsonObject object = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15}");
        boolean actual = evaluator.evaluate("name=Bo", object);

        assertTrue(actual);
    }

}