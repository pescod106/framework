package com.ltar.base.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

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
    public static byte[] serialize(Object object) {
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
    public static Object deserialize(byte[] bytes) {
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

    public static <T> List<T> deserialize(List<byte[]> bytes, Class<T> kclass) {
        if (null == bytes) {
            return null;
        } else {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < bytes.size(); i++) {
                list.add((T) deserialize(bytes.get(i)));
            }
            return list;
        }
    }
//
//    public static <T> T deserialize(byte[] bytes, Class<T> kclass) {
//        if (null == bytes) {
//            return null;
//        } else {
//            try {
//                return (T) (new ObjectInputStream(new ByteArrayInputStream(bytes))).readObject();
//            } catch (StreamCorruptedException e) {
//                return (T) new String(bytes, UTF_8);
//            } catch (Exception e) {
//                throw new RuntimeException("Deserialization error, errMsg:" + e.getMessage(), e);
//            }
//        }
//    }

//    /**
//     * serialize list object
//     *
//     * @param objectList
//     * @return
//     */
//    public static List<byte[]> serialize(List<? extends Object> objectList) {
//        if (CollectionUtils.isEmpty(objectList)) {
//            return Collections.emptyList();
//        }
//        List<byte[]> byteList = new ArrayList<byte[]>(objectList.size());
//        Iterator iterator = objectList.iterator();
//        while (iterator.hasNext()) {
//            byteList.add(serialize(iterator.next()));
//        }
//        return byteList;
//    }
//
//
//    public static <K, V> Map<byte[], byte[]> serialize(Map<K, V> objectMap) {
//        Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(objectMap.size());
//        if (!CollectionUtils.isEmpty(objectMap)) {
//            Iterator iterator = objectMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry entry = (Map.Entry) iterator.next();
//                map.put(serialize(entry.getKey()), serialize(entry.getValue()));
//            }
//        }
//        return map;
//    }
}
