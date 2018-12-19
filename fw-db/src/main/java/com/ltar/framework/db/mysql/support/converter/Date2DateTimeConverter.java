package com.ltar.framework.db.mysql.support.converter;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public class Date2DateTimeConverter implements Converter<Date, DateTime> {
    @Override
    public DateTime convert(Date source) {
        return null == source ? null : new DateTime(source);
    }
}
