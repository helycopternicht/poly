package io.polybius.testtask.predicate.tokens;

import java.util.List;

public interface Tokenizer {
    List<String> getTokenList(String query);
}
