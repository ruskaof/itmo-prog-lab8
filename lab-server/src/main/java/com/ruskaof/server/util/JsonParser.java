package com.ruskaof.server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ruskaof.server.data.remote.repository.json.DateDeserializer;
import com.ruskaof.server.data.remote.repository.json.DateSerializer;

import java.lang.reflect.Type;
import java.util.TreeSet;

public class JsonParser {
    public <T> String serialize(TreeSet<T> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateSerializer()).create();
        return g.toJson(collectionData);
    }

    public <T> TreeSet<T> deSerialize(String strData) throws JsonSyntaxException, IllegalArgumentException {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateDeserializer()).create();
        Type type = new TypeToken<TreeSet<T>>() {
        }.getType();
        if ("".equals(strData)) {
            return new TreeSet<T>();
        }
        return g.fromJson(strData, type);
    }
}
