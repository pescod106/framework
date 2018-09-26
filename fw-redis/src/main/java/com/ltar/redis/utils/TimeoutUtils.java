package com.ltar.redis.utils;

import com.google.common.base.Objects;

import java.util.concurrent.TimeUnit;

/**
 * @desc: Helper class featuring methods for calculating Redis timeouts
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
abstract public class TimeoutUtils {
    public static final Long ONE_SECOND = 1000L;
    public static final Long ONE_MINUTE = 60 * ONE_SECOND;
    public static final Long ONE_HOUR = 60 * ONE_MINUTE;
    public static final Long ONE_DAY = 24 * ONE_HOUR;

    /**
     * Converts the given timeout to seconds.
     *
     * @param timeout
     * @param unit
     * @return
     */
    public static int toSeconds(long timeout, TimeUnit unit) {
        return Long.valueOf(roundUpIfNecessary(timeout, unit.toSeconds(timeout))).intValue();
    }

    /**
     * Converts the given timeout to milliseconds.
     *
     * @param timeout
     * @param unit
     * @return
     */
    public static long toMillis(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout));
    }

    public static long getTimeUnitNum(long millisTimeout, TimeUnit unit) {
        if (Objects.equal(TimeUnit.SECONDS, unit)) {
            return millisTimeout / ONE_SECOND;
        } else if (Objects.equal(TimeUnit.MINUTES, unit)) {
            return millisTimeout / ONE_MINUTE;
        } else if (Objects.equal(TimeUnit.HOURS, unit)) {
            return millisTimeout / ONE_HOUR;
        } else if (Objects.equal(TimeUnit.DAYS, unit)) {
            return millisTimeout / ONE_DAY;
        }
        return millisTimeout;
    }

    private static long roundUpIfNecessary(long timeout, long convertedTimeout) {
        if (timeout > 0 && convertedTimeout == 0) {
            return 1;
        }
        return convertedTimeout;
    }


}
