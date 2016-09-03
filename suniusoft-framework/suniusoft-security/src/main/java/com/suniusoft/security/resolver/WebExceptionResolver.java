package com.suniusoft.security.resolver;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wylitu
 * @version 1.0
 * @desc 记录页面异常
 * @company shufensoft
 * @date 2015/3/26.
 */
public class WebExceptionResolver implements HandlerExceptionResolver {

    /** logger */
    private static final Logger logger = Logger.getLogger(WebExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        logger.error("请求页面异常", ex);
        return null;
    }
}
