package com.suniusoft.security.interceptor.login;

import com.google.common.collect.Maps;
import com.suniusoft.common.aspect.SecurityContextHolder;
import com.suniusoft.common.utils.MapUtils;
import com.suniusoft.common.utils.SpringContextUtil;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.interceptor.login.context.SecuritySessionContext;
import com.suniusoft.security.interceptor.login.util.*;
import com.suniusoft.security.vo.UserVO;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *  @ProjectName: sf-crm  
 *  @Description: 登陆Interceptor
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/15  
 */
@Log4j
public class SecurityInterceptor implements HandlerInterceptor {

    private SecurityUserService securityUserService = (SecurityUserService)SpringContextUtil.getBean("securityUserService");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String token = request.getHeader("token");

        if(!StringUtils.isBlank(token)){
            log.info("request token from header");
        }

        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
            if(!StringUtils.isBlank(token)){
                log.info("request token from request.getParameter");
            }
        }

        log.info("request token="+(token==null?"":token)+",request url:"+request.getServletPath());

        log.info("request param : "+ MapUtils.toString(request.getParameterMap()));

        /**
         * 验证请求白名单
         */
        if (WhiteUrl.isWhiteUrl(request) && !BlackUrl.isBlackUrl(request)) {
            return true;
        }

        UserVO userVO=null;
        if(request.getServletPath().indexOf("/mobile/seller/")!=-1){

            userVO = (UserVO) session.getAttribute(SecurityConstant.SELLER_SESSION_KEY);
            if(userVO == null){
                String userNo = CookieUtils.getCookie(request,"_sun");
                if(!StringUtils.isBlank(userNo)){
                    userVO = securityUserService.findUserByUserNo(userNo);
                }
            }
            session.setAttribute(SecurityConstant.SELLER_SESSION_KEY,userVO);
        }else{

            if (!StringUtils.isBlank(token) && TokenUtils.validateToken(token)){
                SecuritySessionContext.init(token);
                userVO = SecuritySessionContext.getUserInfo();
                String userNo = CookieUtils.getCookie(request,"_u");
                if(userVO!=null && StringUtils.isBlank(userNo)) {
                    Cookie userCookie = new Cookie("_u", userVO.getUserNo());
                    userCookie.setPath("/");
                    userCookie.setMaxAge(365 * 24 * 60 * 60);
                    CookieUtils.saveCookie(response, userCookie);
                    session.setAttribute(SecurityConstant.SESSION_KEY, userVO);
                }

            }

            if(userVO == null) {
                userVO = (UserVO) session.getAttribute(SecurityConstant.SESSION_KEY);
                if (userVO == null) {
                    String userNo = CookieUtils.getCookie(request, "_u");
                    if (!StringUtils.isBlank(userNo)) {
                        userVO = securityUserService.findUserByUserNo(userNo);
                    }
                    session.setAttribute(SecurityConstant.SESSION_KEY, userVO);

                    if (userVO != null) {
                        log.info("loginInfo from cookie [userNo=" + (userVO.getUserNo() == null ? "" : userVO.getUserNo()) + "]");
                    }

                }
            }

        }

        /**
         * token 验证失败，表示未登陆，跳转到登陆页面
         */
        if (userVO == null) {

            /**
             * 商家登录
             */
            if (BlackUrl.isBlackUrl(request) && request.getServletPath().indexOf("/mobile/seller/")!=-1) {

                response.sendRedirect(contextPath + "/mobile/sellerLogin");

                return false;

            }

            /**
             * 手机端用户登录
             */
            if (BlackUrl.isBlackUrl(request) && request.getServletPath().indexOf("/mobile/")!=-1) {

                userVO = WxOauthHelper.wxOauth(request,response);

                if(userVO!=null){
                    session.setAttribute(SecurityConstant.SESSION_KEY, userVO);
                }

                return true;

            }

            /**
             * 后台登录
             */
            response.sendRedirect(contextPath + "/admin/login");

            return false;
        }

        Map<String,Object> loginInfo = Maps.newHashMap();
        loginInfo.put("userNo", userVO.getUserNo());
        loginInfo.put(SecurityConstant.SESSION_KEY, userVO);
        SecurityContextHolder.setLoginInfo(loginInfo);
        log.info("loginInfo[userNo="+(userVO.getUserNo()==null?"":userVO.getUserNo())+"]");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Map<String,Object> resultMap = SecurityContextHolder.getLoginInfo();

        if(!CollectionUtils.isEmpty(resultMap)){

            UserVO userVO = (UserVO) resultMap.get(SecurityConstant.SESSION_KEY);

            if(userVO!=null){

                log.info("clear loginInfo[userNo="+userVO.getUserNo()==null?"":userVO.getUserNo()+"]");
            }

        }

        SecurityContextHolder.clearLoginInfo();


    }




}
