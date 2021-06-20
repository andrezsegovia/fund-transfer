package com.andrezsegovia.transferservice.integrationTests.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Common class to convert Map object to JSON String and vice versa
 */
public class MapToJson {

    /**
     * Convert a flat Map object into a JSON String.
     */
    public static String convertToJSON(Map<String, Object> obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static Map<String, Object> convertToMap(String obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(obj, Map.class);
    }

    public static <T> T convert(String from, Class<T> toClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(from, toClass);
    }

    public static String covertToJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
