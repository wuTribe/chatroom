package com.wuyufan.bean.serializer;

/**
 * netty 序列化方式
 */
public interface Serializer {

    /**
     * JSON序列化
     */
    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT = new JSONSerializer();


    /**
     * 序列化算法
     *
     * @return 枚举值
     */
    byte getSerializerAlgorithm();

    /**
     * Java 对象序列化
     *
     * @param obj Java 对象
     * @return 二进制
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param bytes 二进制
     * @param clazz Java 类字节码
     * @param <T>   类型
     * @return Java 类对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
