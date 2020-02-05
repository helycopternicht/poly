package io.polybius.testtask.predicate.tokens;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegexpTokenizerTest {

    private final RegexpTokenizer tokenizer = new RegexpTokenizer();

    @Test
    public void getTokenList_WhenThereAreNotOperators_ShouldReturnListOfOneToken() {
        String query = "foo>bar";
        List<String> expected = Collections.singletonList(query);

        List<String> actual = tokenizer.getTokenList(query);

        assertEquals(expected, actual);
    }

    @Test
    public void getTokenList_WhenQueryWithWhitespacesOrWithout_ShouldProduceSameResult() {
        String with = "foo>12 && bar<12 || baz=12";
        String without = "foo>12&&bar<12||baz=12";

        List<String> resultWith = tokenizer.getTokenList(with);
        List<String> resultWithout = tokenizer.getTokenList(without);

        assertEquals(resultWith, resultWithout);
    }

    @Test
    public void getTokenList_WhenQueryContainMultipleOperators_ShouldReturnCorrectTokenList() {
        String query = "foo >12 && bar <12 ||baz= 12";
        List<String> expectedTokens = Arrays.asList("foo>12", "&&", "bar<12", "||", "baz=12");

        List<String> actualTokens = tokenizer.getTokenList(query);

        assertEquals(expectedTokens, actualTokens);
    }
}