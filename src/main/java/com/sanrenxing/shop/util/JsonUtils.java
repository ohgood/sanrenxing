package com.sanrenxing.shop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * json string 互转帮助类
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static JsonNode parseString2JsonNode(String jsonStr) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    public static <T> T parseString2Object(String jsonStr, Class<T> clazz, DeserializationFeature feature, boolean state) {
        objectMapper.configure(feature, state);
        T obj = parseString2Object(jsonStr, clazz);
        objectMapper.configure(feature, feature.enabledByDefault());
        return obj;
    }

    public static <T> T parseString2Object(String jsonStr, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T parseBytes2Object(byte[] bytes, Class<T> clazz, DeserializationFeature feature, boolean state) {
        objectMapper.configure(feature, state);
        T obj = parseBytes2Object(bytes, clazz);
        objectMapper.configure(feature, feature.enabledByDefault());
        return obj;
    }

    public static <T> T parseBytes2Object(byte[] bytes, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String parseObject2String(Object obj) {
        String s = null;
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String parseObject2String(Object obj, SerializationFeature feature, boolean state) {
        String s = null;
        try {
            objectMapper.configure(feature, state);
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static byte[] parseObject2Bytes(Object obj) {
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
