package com.ltar.framework.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class DateUtils {

    /**
     * yyyyMMdd
     */
    public static final DateTimeFormatter DATE_COMPACT_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;
    /**
     * yyyy-MM-dd
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final DateTimeFormatter TIME_COMPACT_FORMAT = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATETIME_COMPACT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final int SECOND_PER_DAY = 60 * 60 * 24;


    /**
     * 将字符串按照制定格式转化成Date
     *
     * @param dateStr
     * @param formatter
     * @return
     */
    public static Date str2Date(String dateStr, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将Date转化成默认格式的字符串
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        return date2Str(date, DATETIME_FORMAT);
    }

    /**
     * 将Date转化成指定format格式的字符串
     *
     * @param date
     * @param formatter
     * @return
     */
    public static String date2Str(Date date, DateTimeFormatter formatter) {
        return date.toInstant().atZone(ZoneId.systemDefault()).format(formatter);
    }


    /**
     * 获得指定月份所包含的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获得指定时间所包含的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期的天数间隔
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long dayDiff(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (SECOND_PER_DAY * 1000);
    }

    /**
     * 判断字符串是不是日期格式
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取date日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取date对应的星期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek == 1 ? dayOfWeek += 7 : dayOfWeek;
        calendar.add(Calendar.DATE, 2 - dayOfWeek);
        return getBeginOfDay(calendar.getTime());
    }

    /**
     * 获取当周的结束时间(周日)
     *
     * @param date
     * @return
     */
    public static Date getEndOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek == 1 ? dayOfWeek -= 1 : dayOfWeek;
        calendar.add(Calendar.DATE, 8 - dayOfWeek);
        return getEndOfDay(calendar.getTime());
    }

    /**
     * 获取date对应的月份的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取date对应的月份的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return addDay(addMonth(calendar.getTime(), 1), -1);
    }

    /**
     * 获取date对应的季度的开始时间
     *
     * @param date
     * @return
     */
    public static Date getFirstOfSeason(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthOfDate = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = monthOfDate / 3 + (monthOfDate % 3 == 0 ? 0 : 1);

        int start_month = array[season - 1][0];
        calendar.clear();
        calendar.set(year, start_month - 1, 1);
        return calendar.getTime();
    }

    /**
     * 获取date对应的季度的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfSeason(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthOfDate = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = monthOfDate / 3 + (monthOfDate % 3 == 0 ? 0 : 1);

        int end_month = array[season - 1][2];
        calendar.clear();
        calendar.set(year, end_month - 1, getDaysOfMonth(date), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取date对应的年份的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取date对应的年份的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 11, 30, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 在date日期上加减amount秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSecond(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 在date日期上加减amount分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinute(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 在date日期上加减amount小时
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    /**
     * 在date日期上加减天数，
     *
     * @param date
     * @param amount 增减的数量
     *               为正数指在date基础上向后推迟amount天
     *               为负数指在date基础上向前减少|amount|天
     * @return
     */
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    /**
     * 在date日期上加减amount月
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 在date日期上加减amount年
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    private static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }


    public static class DateUtilException extends RuntimeException {
        public DateUtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
