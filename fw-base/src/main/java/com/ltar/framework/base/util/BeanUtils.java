package com.ltar.framework.base.util;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class BeanUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);

    private static CaseFormat[] toFormat;

    static {
        toFormat = new CaseFormat[]{
                CaseFormat.LOWER_CAMEL,
                CaseFormat.UPPER_CAMEL,
                CaseFormat.LOWER_HYPHEN,
                CaseFormat.LOWER_UNDERSCORE,
                CaseFormat.UPPER_UNDERSCORE
        };
    }

    /**
     * Set the specified value as current property value.
     *
     * @param bean
     * @param property
     * @param value
     */
    public static void set(Object bean, String property, Object value) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        wrapper.setPropertyValue(property, value);
    }

    public static Object get(Object bean, String property) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        return wrapper.getPropertyValue(property);
    }

}
