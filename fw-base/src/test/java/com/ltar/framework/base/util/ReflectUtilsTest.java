package com.ltar.framework.base.util;

import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ltar.framework.base.util.DateUtils.getDaysOfMonth;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class ReflectUtilsTest {

    @Test
    public void getFields() {
        List<Field> list = ReflectUtils.getFields(Object.class, true);
        System.out.println("---------------------------------");
        for (Field field : list) {
            System.out.println(field.toString());
        }
        System.out.println("---------------------------------");
    }

    @Test
    public void isAnnotationPresent() {

    }

    @Test
    public void get() {
        System.out.println(IpUtils.ip2Long("127.0.0.1"));
        System.out.println(IpUtils.long2Ip(2130706433));
    }


    public static String[][] getPreTwelveList(int year, int month) {
        String[][] list = new String[12][3];
        String start = "";
        String end = "";

        for (int i = 0; i < 12; ++i) {
            int days = getDaysOfMonth(year, month);
            if (month >= 1) {
                start = year + "-" + month + "-01 00:00:00";
                end = year + "-" + month + "-" + days + " 00:00:00";
            } else {
                month = 12;
                start = year - 1 + "-" + month + "-01 00:00:00";
                end = year - 1 + "-" + month + "-" + days + " 00:00:00";
                --year;
            }

            list[12 - i - 1][0] = start;
            list[12 - i - 1][1] = end;
            list[12 - i - 1][2] = year + "/" + month;
            --month;
        }

        return list;
    }

    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
//        c.setFirstDayOfWeek(1);
        c.set(year, month - 1, 1);
        return c.get(7);
    }

    public static String getCurrent() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        int hour = cal.get(11);
        int minute = cal.get(12);
        int second = cal.get(13);
        StringBuffer sb = new StringBuffer();
        sb.append(year).append("_").append(addzero(month, 2)).append("_").append(addzero(day, 2)).append("_").append(addzero(hour, 2)).append("_").append(addzero(minute, 2)).append("_").append(addzero(second, 2));
        return sb.toString();
    }

    public static String addzero(int num, int length) {
        String str = "";
        if ((double) num < Math.pow(10.0D, (double) (length - 1))) {
            for (int i = 0; i < length - (num + "").length(); ++i) {
                str = str + "0";
            }
        }

        str = str + num;
        return str;
    }

    @Test
    public void testDateTime() {
        String dateStr = "2018-11-28";
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(localDateTime);
    }

    @Test
    public void testDateTimeFormat() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr0 = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("ISO_LOCAL_DATE:"+dateStr0);
//        String dateStr1 = localDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE);
//        System.out.println("ISO_OFFSET_DATE:"+dateStr1);
        String dateStr2 = localDateTime.format(DateTimeFormatter.ISO_DATE);
        System.out.println("ISO_DATE:"+dateStr2);
        String dateStr3 = localDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        System.out.println("ISO_LOCAL_TIME:"+dateStr3);
//        String dateStr4 = localDateTime.format(DateTimeFormatter.ISO_OFFSET_TIME);
//        System.out.println("ISO_OFFSET_TIME:"+dateStr4);
        String dateStr5 = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("ISO_LOCAL_DATE_TIME:"+dateStr5);
//        String dateStr6 = localDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//        System.out.println("ISO_OFFSET_DATE_TIME:"+dateStr6);
//        String dateStr7 = localDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
//        System.out.println("ISO_ZONED_DATE_TIME:"+dateStr7);
        String dateStr8 = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("ISO_DATE_TIME:"+dateStr8);
        String dateStr9 = localDateTime.format(DateTimeFormatter.ISO_ORDINAL_DATE);
        System.out.println("ISO_ORDINAL_DATE:"+dateStr9);
        String dateStr10 = localDateTime.format(DateTimeFormatter.ISO_WEEK_DATE);
        System.out.println("ISO_WEEK_DATE:"+dateStr10);
//        String dateStr11 = localDateTime.format(DateTimeFormatter.ISO_INSTANT);
//        System.out.println("ISO_INSTANT:"+dateStr11);
        String dateStr12 = localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("BASIC_ISO_DATE:"+dateStr12);
//        String dateStr13 = localDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
//        System.out.println("RFC_1123_DATE_TIME:"+dateStr13);
        String dateStr14 = localDateTime.format(DateTimeFormatter.ISO_TIME);
        System.out.println("ISO_TIME:"+dateStr14);

    }
}