package com.ruskaof.client.utility;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;


/**
 * Custom parser for gson for java.time.Local date into file
 */
public class DateSerializer implements JsonSerializer<java.time.LocalDate> {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("year", src.getYear());
        obj.addProperty("month", src.getMonthValue());
        obj.addProperty("day", src.getDayOfMonth());
        return obj;
    }
}
