package com.ruskaof.server.data.remote.repository.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;


/**
 * Custom parser for gson for java.time.Local date from file
 */
public class DateDeserializer implements JsonDeserializer<java.time.LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson g = new Gson();
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> map = g.fromJson(json.toString(), type);
        return java.time.LocalDate.of(map.get("year"), map.get("month"), map.get("day"));
    }
}
