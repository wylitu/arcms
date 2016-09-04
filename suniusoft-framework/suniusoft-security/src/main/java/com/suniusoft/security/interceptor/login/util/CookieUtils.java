package com.suniusoft.security.interceptor.login.util;

import com.suniusoft.common.utils.StringUtils;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *   
 *  @ProjectName: arcms 
 *  @Description:
 *  @author dadi  litu@51xianqu.net
 *  @date 16/1/14  
 */
@Log4j
public class CookieUtils {


    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length <= 0) {

            return null;

        }

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals(name)) {

                if(StringUtils.isBlank(cookie.getValue())){
                    return null;
                }

                try {

                    return URLDecoder.decode(cookie.getValue(), "utf-8");

                } catch (UnsupportedEncodingException e) {
                    log.error("CookieUtils.getCookie error", e);
                }

            }
        }

        return null;
    }

    /**
     * 保存cookie
     * @param response
     * @param cookie
     */
    public static void saveCookie(HttpServletResponse response, Cookie cookie) {

        try {

            if(!StringUtils.isBlank(cookie.getValue())) {
                cookie.setValue(URLEncoder.encode(cookie.getValue(), "utf-8"));
            }

            response.addCookie(cookie);

        } catch (UnsupportedEncodingException e) {

            log.error("CookieUtils.saveCookie error", e);

        }

    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response,String cookieName){

        Cookie[] cookies = request.getCookies();

        if(cookies!=null && cookies.length >0){

            for(Cookie cook : cookies){

                if(cookieName.equals(cook.getName())){

                    Cookie cookie_ = new Cookie(cookieName,"");
                    cookie_.setMaxAge(0);
                    cookie_.setPath("/");
                    CookieUtils.saveCookie(response,cookie_);

                }

            }

        }

    }
}
