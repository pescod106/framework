package com.ltar.base.util;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ltar.base.util.DateUtils.getDaysOfMonth;

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
}