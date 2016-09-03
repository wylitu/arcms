/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.arcms.shop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import java.util.List;

/**
 * 性能摘要日志基类
 *
 * @author wylitu
 * @version v 0.1 2015/1/23 6:40 Exp $$
 */
public abstract class BaseDigestLogInterceptor implements MethodInterceptor {
    /** logger */
    private static final Logger logger = Logger.getLogger(BaseDigestLogInterceptor.class);

    /**
     * 获取当前线程的时间戳
     *
     * @return String 时间戳
     */
    public String getTimeKey() {
        //获取当前线程的时间戳
        String timeKey = NDC.get();

        return "(TimeKey=" + (StringUtils.isBlank(timeKey) ? "0" : timeKey) + ")";
    }

    /**
     * 获取调用参数信息
     *
     * @param arguments 调用参数信息
     * @return String
     */
    public String getArgsMsg(Object[] arguments) {
        StringBuffer argsMsg = new StringBuffer();

        argsMsg.append("(");

        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];

            if (null == arg) {
                argsMsg.append("-");
            } else if (arg.getClass() == String.class) {
                //如果是String的参数，则直接打印
                argsMsg.append(arg);
            } else if (arg instanceof List<?>) {
                //如果是List的参数，将各条记录信息打印出来
                getListValue(argsMsg, arg);
            } else {
                argsMsg.append(ToStringBuilder.reflectionToString(arg,
                        ToStringStyle.SHORT_PREFIX_STYLE));
            }

            if (i != arguments.length - 1) {
                argsMsg.append(",");
            }
        }

        argsMsg.append(")]");

        return argsMsg.toString();
    }

    /**
     * 拼装ArrayList类型的参数
     *
     * @param argsMsg
     * @param arg
     */
    private void getListValue(StringBuffer argsMsg, Object arg) {
        try {
            List<?> list = (List<?>) arg;

            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClass() == String.class) {
                    argsMsg.append(list.get(i));
                } else {
                    argsMsg.append(ToStringBuilder.reflectionToString(arg,
                            ToStringStyle.SHORT_PREFIX_STYLE));
                }

                argsMsg.append(",");
            }
        } catch (Exception e) {
            logger.error("拼装调用外部系统服务参数异常", e);
        }
    }
}

