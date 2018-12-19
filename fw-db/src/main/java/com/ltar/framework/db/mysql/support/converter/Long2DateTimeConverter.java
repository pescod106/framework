package com.ltar.framework.db.mysql.support.converter;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public class Long2DateTimeConverter implements Converter<Long, DateTime> {
    @Override
    public DateTime convert(Long source) {
        return source == null ? null : new DateTime(source);
    }
}
