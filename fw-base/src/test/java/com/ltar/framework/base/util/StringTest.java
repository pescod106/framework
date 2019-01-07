package com.ltar.framework.base.util;

import cn.huoqiu.base.util.StringUtil;
import org.junit.Test;

/**
 * @description:
 * @author: changzhigao
 * @date: 2018-12-30
 * @version: 1.0.0
 */
public class StringTest {

    @Test
    public void testpaseFloat() {
        System.out.println(StringUtil.paseFloat("12.220000", 10));
        System.out.println(StringUtil.truncateInt("1234ac123"));
    }
}
