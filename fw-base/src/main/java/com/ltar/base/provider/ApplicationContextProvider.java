package com.ltar.base.provider;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/22
 * @version: 1.0.0
 */
@Component
public class ApplicationContextProvider extends ApplicationObjectSupport {

    <T> T getBean(String serviceName, Class<T> requiredType) {
        if (getApplicationContext().containsBean(serviceName)) {
            return getApplicationContext().getBean(serviceName, requiredType);
        } else {
            return null;
        }
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

}
