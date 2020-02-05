package io.polybius.testtask.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

    private final JsonParser jsonParser;

    public JsonHelper() {
        this.jsonParser = new JsonParser();
    }

    public JsonObject getJsonObject(String dataString) {
        return jsonParser.parse(dataString).getAsJsonObject();
    }
}
