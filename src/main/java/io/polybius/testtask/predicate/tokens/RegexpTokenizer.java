package io.polybius.testtask.predicate.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpTokenizer implements Tokenizer {
    @Override
    public List<String> getTokenList(String originalQuery) {
        List<String> tokens = new ArrayList<>();
        String query = originalQuery.replaceAll(" ", ""); // clear whitespaces
        Pattern pattern = Pattern.compile("&&|\\|\\|"); // && or ||
        Matcher matcher = pattern.matcher(query);
        int end = 0;
        int start = 0;
        while (matcher.find()) {
            start = matcher.start();
            tokens.add(query.substring(end, start)); // expression
            end = matcher.end();
            tokens.add(query.substring(start, end)); // operator
        }
        tokens.add(query.substring(end));

        return tokens;
    }
}