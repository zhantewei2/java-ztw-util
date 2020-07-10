package org.cm.pro.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author hht
 * @description 对象序列化和反序列化utils
 * @date 2019/12/5
 */
@Slf4j
public class SerializeUtils {

    /**
     * objMapper
     */
    private static ObjectMapper objMapper = new ObjectMapper();

    static {
        objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    /**
     * 对象序列化
     * @param data data
     * @return data.toString
     */
    public static String serialize(Object data) {

        try {
            return objMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 针对list的反序列化
     * @param data json
     * @param t1 集合类型
     * @param elementClass 元素类型
     * @param <T> 类
     * @return result
     */
    public static <T> T deserializeCollection(String data, @SuppressWarnings("rawtypes") Class<? extends Collection> t1,
                                              Class<?> elementClass) {
        try {
            return objMapper.readValue(data, TypeFactory.defaultInstance().constructCollectionType(t1, elementClass));
        } catch (Exception e) {
            log.error("异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 针对list的反序列化
     * @param fis 输入流
     * @param t1 集合类型
     * @param elementClass 元素类型
     * @param <T> T
     * @return result
     */
    public static <T> T deserializeCollection(InputStream fis,
                                              @SuppressWarnings("rawtypes") Class<? extends Collection> t1,
                                              Class<?> elementClass) {
        try {
            return objMapper.readValue(fis, TypeFactory.defaultInstance().constructCollectionType(t1, elementClass));
        } catch (Exception e) {
            log.error("异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 针对Map的反序列化
     * @param data 数据
     * @param mapClass map类型
     * @param keyClass key类型
     * @param valueClass value类型
     * @param <T> T
     * @return result
     */
    public static <T> T deserializeHash(String data, @SuppressWarnings("rawtypes") Class<? extends Map> mapClass,
                                        Class<?> keyClass, Class<?> valueClass) {
        try {
            return objMapper.readValue(data,
                    TypeFactory.defaultInstance().constructMapType(mapClass, keyClass, valueClass));
        } catch (Exception e) {
            log.error("异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据串反序列化(针对继承类)
     * @param data string data
     * @param ref 类型参数
     * @param <T> T
     * @return result
     */
    public static <T> T deserializeRef(String data, TypeReference ref) {

        try {
            return objMapper.readValue(data, ref);
        } catch (Exception e) {
            log.error("异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据反序列化
     * @param data string data
     * @param clazz 元素类型
     * @param <T> T
     * @return result
     */
    public static <T> T deserialize(String data, Class<T> clazz) {
        try {
            return objMapper.readValue(data, clazz);
        } catch (Exception e) {
            log.error("异常",e);
            throw new RuntimeException(e);
        }
    }

}