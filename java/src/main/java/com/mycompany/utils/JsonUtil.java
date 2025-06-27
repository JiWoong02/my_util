package org.mdback.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();


    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Json 읽는 중 오류 발생", e);
        }
    }
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Json 변환 중 오류 발생", e);
        }
    }

}
