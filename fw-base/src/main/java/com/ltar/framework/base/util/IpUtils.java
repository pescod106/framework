package com.ltar.framework.base.util;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class IpUtils {
    private static final int IP_SEG_NUM = 4;

    public static long ip2Long(String ipStr) {
        long result = 0L;
        String[] strs = ipStr.split("\\.");
        if (strs.length != IP_SEG_NUM) {
            throw new IllegalArgumentException("invalid ip format :" + ipStr);
        }

        for (int i = 0; i < IP_SEG_NUM; i++) {
            int ipSeg = Integer.parseInt(strs[i]);
            if (ipSeg < 0 || ipSeg > 255) {
                throw new IllegalArgumentException("invalid ip format: " + ipStr);
            }
            result = (long) (result + ipSeg * Math.pow((1 << 8), 3 - i));
        }
        return result;
    }

    public static String long2Ip(long ipValue) {
        String result = "";
        long interval = 1 << 8;
        for (int i = 0; i < IP_SEG_NUM; i++) {
            result = ipValue % interval + (i == 0 ? "" : ".") + result;
            ipValue /= interval;
        }
        return result;
    }
}
