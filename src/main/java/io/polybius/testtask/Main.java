package io.polybius.testtask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.polybius.testtask.predicate.QueryStringPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        String query = args[0];
        String dataString = args[1];

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(dataString).getAsJsonArray();
        List<JsonObject> jsonObjectsList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObjectsList.add(jsonArray.get(i).getAsJsonObject());
        }

        QueryStringPredicate queryPredicate = new QueryStringPredicate(query);
        List<JsonObject> filteredResult = jsonObjectsList.stream()
                .filter(queryPredicate::verify)
                .collect(Collectors.toList());

        System.out.println(new Gson().toJson(filteredResult));
    }
}
