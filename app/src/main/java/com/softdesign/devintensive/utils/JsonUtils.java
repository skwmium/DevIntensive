package com.softdesign.devintensive.utils;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by skwmium on 28.06.16.
 */
public class JsonUtils {

    @Nullable
    public static <T> T jsonToObject(@Nullable String json, Class<T> clazz) {
        if (json == null) return null;
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            L.e(e);
            return null;
        }
    }

    @Nullable
    public static String objectToJson(@Nullable Object obj) {
        if (obj == null) return null;
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            L.e(e);
            return null;
        }
    }
}
