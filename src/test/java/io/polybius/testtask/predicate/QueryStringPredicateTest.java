package io.polybius.testtask.predicate;

import com.google.gson.JsonObject;
import io.polybius.testtask.helper.JsonHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryStringPredicateTest {

    private final static JsonHelper helper = new JsonHelper();

    @Test
    public void verify_WhenSimpleConditionIsCorrect_ShouldReturnTrue() {
        JsonObject jsonObject = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15,\"height\":120,\"frendsCount\":20}");
        QueryStringPredicate predicate = new QueryStringPredicate("height>100");

        boolean actual = predicate.verify(jsonObject);

        assertTrue(actual);
    }

    @Test
    public void verify_WhenSimpleConditionIsNotCorrect_ShouldReturnFalse() {
        JsonObject jsonObject = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15,\"height\":120,\"frendsCount\":20}");
        QueryStringPredicate predicate = new QueryStringPredicate("height<100");

        boolean actual = predicate.verify(jsonObject);

        assertFalse(actual);
    }

    @Test
    public void verify_WhenConditionWithLogicalOperatorsIsCorrect_ShouldReturnTrue() {
        JsonObject jsonObject = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15,\"height\":120,\"frendsCount\":20}");
        QueryStringPredicate predicate = new QueryStringPredicate("height>100 || age < 15 && name=Bob");

        boolean actual = predicate.verify(jsonObject);

        assertTrue(actual);
    }

    @Test
    public void verify_WhenConditionWithLogicalOperatorsIsNotCorrect_ShouldReturnFalse() {
        JsonObject jsonObject = helper.getJsonObject("{\"name\":\"Bob\",\"age\":15,\"height\":120,\"frendsCount\":20}");
        QueryStringPredicate predicate = new QueryStringPredicate("height <100 && age >= 15 && name=Bob");

        boolean actual = predicate.verify(jsonObject);

        assertFalse(actual);
    }
}