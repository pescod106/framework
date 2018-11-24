package com.ltar.framework.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/24
 * @version: 1.0.0
 */
public class UniqueThreadNameFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueThreadNameFilter.class);
    private static final String THREAD_SIGN = "-pescod-";
    private static AtomicLong threadNum = new AtomicLong(0L);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String threadName = Thread.currentThread().getName();
        try {
            if (threadName.contains(THREAD_SIGN)) {
                threadName = threadName.substring(0, threadName.indexOf(THREAD_SIGN));
            }
            threadName = threadName + THREAD_SIGN + threadNum.incrementAndGet();
            Thread.currentThread().setName(threadName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
