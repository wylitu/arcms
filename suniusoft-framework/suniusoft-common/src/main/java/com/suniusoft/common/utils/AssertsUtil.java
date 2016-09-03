package com.suniusoft.common.utils;
import org.apache.http.util.Asserts;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 *                  断言类
 *                 </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/19  
 */
public class AssertsUtil extends Asserts {

    /**
     * 判断中间不能为blank
     * @param str
     * @param name
     */
    public static void notContainsBlank(final String str, final String name) {

        notBlank(str,name);

        if(!StringUtils.isNotContainsBlank(str)){
            throw new IllegalStateException(name + " is contains blank");
        }

    }

    /**
     * 判断中间不能为blank
     * @param str
     * @param name
     */
    public static void notBothSidesBlank(final String str, final String name) {

        notBlank(str,name);

        if(!StringUtils.isNotBothSidesBlank(str)){
            throw new IllegalStateException(name + " is both sides blank");
        }

    }

}
