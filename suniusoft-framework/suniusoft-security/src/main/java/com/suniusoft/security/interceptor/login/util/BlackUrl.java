package com.suniusoft.security.interceptor.login.util;

import com.suniusoft.common.utils.PropertyReader;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *  @ProjectName: suniusoft  
 *  @Description:  黑名单过滤
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/29  
 */
public class BlackUrl {


    private static List<String> blackUrl = new ArrayList<String>();

    private static String BLACK_URL_KEY = "black.url";

    static {

        String urlStr = PropertyReader.getValue(BLACK_URL_KEY);

        String[] urls = StringUtils.split(urlStr,",");

        if(urls != null && urls.length > 0){

            for(String str : urls){

                blackUrl.add(str);

            }

        }

    }


    /**
     * 验证是否白名单url
     *
     * @param request
     * @return
     */
    public static boolean isBlackUrl(HttpServletRequest request) {

        final String url = request.getServletPath();

        /**
         * 黑名单url
         */
        if (filterBlackUrl(url)) {
            return true;
        }

        return false;

    }

    /**
     * 过滤黑名单url
     * @param url
     * @return
     */
    private static boolean filterBlackUrl(String url) {

        for (String s : blackUrl) {

            if (null == s) {
                return false;
            }

            if("/*".equals(s)){

                return true;

            }

            if (s.endsWith("*") && s.startsWith("*")) {
                String fixStr = s.substring(1, s.length() - 1);
                if (url.indexOf(fixStr) != -1) {
                    return true;
                }

            }

            if (s.endsWith("*")) {
                String fixStr = s.substring(0, s.length() - 1);
                if (url.indexOf(fixStr) != -1) {
                    return true;
                }

            }

            if (url.equals(s)) {
                return true;
            }
        }

        return false;
    }



}
