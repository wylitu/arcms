/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.arcms.admin.interceptor;

/**
 * @author wylitu
 * @version v 0.1 2015/1/23 6:48 Exp $$
 */
public interface LoggerNames {

    /**
     * 系统DAO层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : dal-digest.log
     */
    String DAL_DIGEST = "DAL_DIGEST";

    /**
     * 系统SERVICE层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : biz-digest.log
     */
    String BIZ_DIGEST = "BIZ_DIGEST";

    /**
     * 系统CONTROLLER层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : api-digest.log
     */
    String CONTROLLER_DIGEST = "CONTROLLER_DIGEST";

    /**
     * 系统其他层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : other-digest.log
     */
    String OTHER_DIGEST = "OTHER_DIGEST";

}
