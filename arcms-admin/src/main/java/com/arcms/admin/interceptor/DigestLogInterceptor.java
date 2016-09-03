/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.arcms.admin.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * 系统性能摘要日志拦截器
 * <p/>
 * <p>格式：[(类名称.方法名，是否调用成功，耗时)]</p>
 * <p>示例：[(DbInfoDAO.queryById，Y，1ms)]</p>
 *
 * @author wylitu
 * @version v 0.1 2015/1/23 6:46 Exp $$
 */
public class DigestLogInterceptor extends BaseDigestLogInterceptor {
    /**
     * logger
     */
    private static final Logger logger = Logger
            .getLogger(DigestLogInterceptor.class);

    /**
     * DAL层摘要日志
     */
    private static final Logger dalLogger = Logger
            .getLogger(LoggerNames.DAL_DIGEST);

    /**
     * BIZ层摘要日志
     */
    private static final Logger bizLogger = Logger
            .getLogger(LoggerNames.BIZ_DIGEST);


    /**
     * CONTROLLER层摘要日志
     */
    private static final Logger controllerLogger = Logger
            .getLogger(LoggerNames.CONTROLLER_DIGEST);

    /**
     * 其他层摘要日志
     */
    private static final Logger otherLogger = Logger
            .getLogger(LoggerNames.OTHER_DIGEST);

    /**
     * 拦截包的名字
     */
    private static final String DAO_PACKAGE_PREX = "com.arcms.biz.dao";

    /**
     * 拦截包的名字
     */
    private static final String BIZ_PACKAGE_PREX = "com.arcms.biz";

    /**
     * 拦截包的名字
     */
    private static final String CONTROLLER_PACKAGE_PREX = "com.arcms.admin.web.controller";


    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //开始时间
        long startTime = System.currentTimeMillis();
        //类全名称
        String fullClassName = invocation.getMethod().getDeclaringClass().getName();
        //接口名称
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        //方法名称
        String methodName = invocation.getMethod().getName();
        //调用是否成功
        boolean isSuccess = true;

        try {
            return invocation.proceed();
        } catch (Throwable t) {
            isSuccess = false;
            logger.error("系统异常",t);
            throw t;
        } finally {
            //确保任何情况下业务都能正常进行
            try {
                long elapseTime = System.currentTimeMillis() - startTime;
                if (fullClassName.startsWith(DAO_PACKAGE_PREX)) {
                    dalLogger.info(constructLogString(className, methodName,
                            isSuccess, elapseTime));
                }
                else if (fullClassName.startsWith(BIZ_PACKAGE_PREX)) {
                    bizLogger.info(constructLogString(className, methodName,
                            isSuccess, elapseTime));
                }
                else if (fullClassName.startsWith(CONTROLLER_PACKAGE_PREX)) {
                    controllerLogger.info(constructLogString(className, methodName,
                            isSuccess, elapseTime));
                }else{
                    otherLogger.info(constructLogString(className, methodName,
                            isSuccess, elapseTime));
                }
            } catch (Exception e) {
                logger.error("记录系统性能摘要日志出错!", e);
            }

        }
    }


    /**
     * 构造记录日志的字符串
     * <p>格式：[(类名称.方法名，是否调用成功，耗时)]</p>
     * <p>示例：[(DbInfoDAO.queryById，Y，1ms)]</p>
     * <p/>
     * <p>暂不考虑记录DAO层业务参数信息<p/>
     *
     * @param className  DAO名称
     * @param methodName 方法名称
     * @param isSuccess  调用是否成功
     * @param elapseTime 耗时
     * @return
     */
    protected String constructLogString(String className, String methodName,
                                        boolean isSuccess, long elapseTime) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[(");
        buffer.append(className);
        buffer.append(".");
        buffer.append(methodName);
        buffer.append(",");
        buffer.append(isSuccess ? "Y" : "N");
        buffer.append(",");
        buffer.append(elapseTime + "ms)");
        //添加当前线程时间戳
        buffer.append(getTimeKey());
        buffer.append("]");

        return buffer.toString();
    }
}
