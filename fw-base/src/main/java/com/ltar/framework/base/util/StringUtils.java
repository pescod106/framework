package com.ltar.framework.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @description:
 * @author: changzhigao
 * @date: 2018-12-30
 * @version: 1.0.0
 */
public class StringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);
    private static final Pattern email_pattern = Pattern.compile("^\\w+[\\w\\.\\-_]*@[\\w\\.]+\\.[a-zA-Z]{2,4}$");

    public static boolean isBlack(String string) {
        if (null == string || string.length() == 0) {
            return true;
        } else {
            for (int i = 0; i < string.length(); i++) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
