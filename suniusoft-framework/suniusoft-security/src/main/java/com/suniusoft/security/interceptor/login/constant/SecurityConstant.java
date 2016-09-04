package com.suniusoft.security.interceptor.login.constant;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description:  登录常量
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/29  
 */
public class SecurityConstant {

    public static final String SESSION_KEY = "USER_SESSION_KEY";
    public static final String SELLER_SESSION_KEY = "SELLER_SESSION_KEY";

    /**
     * 关键字匹配类型
     */
    public interface scope {

        /**
         * 隐性授权
         */
        public static final String SNSAPI_BASE = "snsapi_base";

        /**
         * 显性授权
         */
        public static final String SNSAPI_USERINFO = "snsapi_userinfo";

    }

}
