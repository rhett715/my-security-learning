package org.rhett.mysecurity.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 封装了Jackson的json工具类，用于代替JSONObject
 */
public class JacksonUtil {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    public static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Java对象转JSON字符串
     * @param object 目标对象
     * @return 字符串
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonString is error : \n", e);
            throw new RuntimeException();
        }
    }

    /**
     * Java对象转JSON字符串 - 美化输出
     *
     * @param object 目标对象
     * @return 美化后的字符串
     */
    public static String toJsonStringWithPretty(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonString is error : \n", e);
            throw new RuntimeException();
        }
    }


    /**
     * Java对象转byte数组
     *
     * @param object 目标对象
     * @return 字节数组
     */
    public static byte[] toJsonBytes(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonBytes is error : \n", e);
            throw new RuntimeException();
        }
    }


    /**
     * JSON字符串转对象
     *
     * @param json 字符串
     * @param clazz 类
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("The JacksonUtil parseObject is error, json str is {}, class name is {} \n", json, clazz.getName(), e);
            throw new RuntimeException();
        }
    }

    /**
     * JSON字符串转List集合
     *
     * @param json JSON字符串
     * @param elementClasses 元素类
     * @param <T> 类型
     * @return List集合
     */
    @SafeVarargs
    public static <T> List<T> parseList(String json, Class<T>... elementClasses) {
        try {
            return objectMapper.readValue(json, getCollectionType(objectMapper, List.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseList is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
        }
    }


    /**
     * JSON字符串转Set集合
     *
     * @param json JSON字符串
     * @param elementClasses 元素类
     * @param <T> 类型
     * @return List集合
     */
    @SafeVarargs
    public static <T> Set<T> parseSet(String json, Class<T>... elementClasses) {
        try {
            return objectMapper.readValue(json, getCollectionType(objectMapper, Set.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseSet is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
        }
    }

    /**
     * JSON字符串转Collection集合
     *
     * @param json JSON字符串
     * @param elementClasses 元素类
     * @param <T> 类型
     * @return Collection集合
     */
    @SafeVarargs
    public static <T> Collection<T> parseCollection(String json, Class<T>... elementClasses) {
        try {
            return objectMapper.readValue(json, getCollectionType(objectMapper, Collection.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseCollection is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
