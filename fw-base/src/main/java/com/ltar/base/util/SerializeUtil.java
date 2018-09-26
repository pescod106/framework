package com.ltar.base.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */
public final class SerializeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * serialize object
     *
     * @param object
     * @return
     */
    public static <T> byte[] serialize(T object) {
        if (object instanceof String) {
            return ((String) object).getBytes(UTF_8);
        } else {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                (new ObjectOutputStream(outputStream)).writeObject(object);
                return outputStream.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("serialize object error,errMsg:" + e.getMessage(), e);
            }
        }
    }


    /**
     * deserialize
     *
     * @param bytes
     * @return
     */
    public static <T> T deserialize(byte[] bytes) {
        if (null == bytes) {
            return null;
        } else {
            try {
                return (T) (new ObjectInputStream(new ByteArrayInputStream(bytes))).readObject();
            } catch (StreamCorruptedException e) {
                return (T) new String(bytes, UTF_8);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization error, errMsg:" + e.getMessage(), e);
            }
        }
    }

    public static Object deserialize2Object(byte[] bytes) {
        if (null == bytes) {
            return null;
        } else {
            try {
                return (new ObjectInputStream(new ByteArrayInputStream(bytes))).readObject();
            } catch (StreamCorruptedException e) {
                return new String(bytes, UTF_8);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization error, errMsg:" + e.getMessage(), e);
            }
        }
    }
}
