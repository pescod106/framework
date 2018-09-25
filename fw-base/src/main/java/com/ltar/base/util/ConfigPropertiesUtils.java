package com.ltar.base.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @desc:读取配置文件工具类
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */
public class ConfigPropertiesUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigPropertiesUtils.class);
    private static ResourceBundle resourceBundle = null;

    public static String getValue(String key) {
        return getValue(key, null);
    }

    public static String getValue(String key, String defaultValue) {
        return getValue(key, defaultValue, Charset.forName("UTF-8"));
    }

    public static String getValue(String key, String defaultValue, Charset convertCharset) {
        return getValue(key, defaultValue, Charset.forName("ISO-8859-1"), convertCharset);
    }

    public static String getValue(String key, String defaultValue, Charset originCharaset, Charset convertCharset) {
        String value = null;
        value = resourceBundle.getString(key);
        try {
            if (!StringUtils.isEmpty(value)) {
                value = new String(value.getBytes(originCharaset), convertCharset);
            }
        } catch (Exception e) {
            LOGGER.debug("获取[" + key + "]时出现问题，errorMsg:" + e.getMessage(), e);
        }
        value = null == value ? defaultValue : value;
        return value;
    }


    static {
        resourceBundle = ResourceBundle.getBundle("config", Locale.CHINESE);
    }
}
