package com.ltar.framework.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @desc: Java数据类型工具类
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public class DataTypeUtils {

    /**
     * 判断是否为原始数据类型
     *
     * @param kclass
     * @return
     */
    public static boolean isPrimitive(Class<?> kclass) {
        if (kclass.isPrimitive()) {
            return true;
        } else {
            return kclass == String.class
                    || kclass == Byte.class
                    || kclass == Short.class
                    || kclass == Integer.class
                    || kclass == Long.class
                    || kclass == Float.class
                    || kclass == Double.class
                    || kclass == Boolean.class
                    || kclass == BigInteger.class
                    || kclass == BigDecimal.class;
        }
    }

}
