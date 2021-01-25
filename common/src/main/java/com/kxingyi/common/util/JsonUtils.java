package com.kxingyi.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengpan
 * @Date: 2019/9/23
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper STRICT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            logger.error("", e);
        }
        return null;
    }

    public static <T> T toObject(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }


    /***
     * 使用严格字段校验反序列化JSON，如果序列化失败则返回空
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T toObjectStrictly(String jsonData, Class<T> beanType) {
        try {
            return STRICT_MAPPER.readValue(jsonData, beanType);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static <T> T toObject(JsonNode jsonNode, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(MAPPER.writeValueAsString(jsonNode), beanType);
            return t;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 转换为泛型类
     *
     * @param objectType   目标类型
     * @param genericsType 泛型实参类型
     */
    public static <O, G> O toGenericsObject(String jsonData, Class<O> objectType, Class<G> genericsType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(objectType, genericsType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static <T> List<T> toList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static <K, V> Map<K, V> toMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(HashMap.class, keyType, valueType);
        try {
            Map<K, V> map = MAPPER.readValue(jsonData, javaType);
            return map;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static Map toMap(String jsonData) {
        try {
            Map result = MAPPER.readValue(jsonData, Map.class);
            return result;
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }


}
