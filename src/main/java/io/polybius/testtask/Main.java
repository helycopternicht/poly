package io.polybius.testtask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

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

    List<JsonObject> filteredResult = jsonObjectsList; //replace with own implementation

    System.out.println(new Gson().toJson(filteredResult));
  }
}
