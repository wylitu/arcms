package com.suniusoft.common.aspect;

import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 这里需要写一个线程安全的ThreadLocal,保存登录信息
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/15  
 */
public class SecurityContextHolder {

    private static final ThreadLocal<Map<String,Object>> contextHolder = new ThreadLocal<Map<String,Object>>();

    public static void setLoginName(Map<String,Object> map) {
        contextHolder.set(map);
    }

    public static Map<String,Object> getLoginInfo() {
        return  contextHolder.get();
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
