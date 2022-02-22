package com.ruskaof.client.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ruskaof.client.data.StudyGroup;

import java.lang.reflect.Type;
import java.util.TreeSet;

public class JsonParser {
    public String serialize(TreeSet<StudyGroup> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateSerializer()).create();
        return g.toJson(collectionData);
    }

    public TreeSet<StudyGroup> deSerialize(String strData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateDeserializer()).create();
        Type type = new TypeToken<TreeSet<StudyGroup>>() {
        }.getType();
        if (strData.equals("")) {
            return new TreeSet<>();
        }
        return g.fromJson(strData, type);
    }
}
