package com.suniusoft.security.filter;

import com.suniusoft.common.aspect.SecurityContextHolder;
import com.suniusoft.security.interceptor.constant.LoginConstant;
import com.suniusoft.security.interceptor.util.BlackUrl;
import com.suniusoft.security.interceptor.util.WhiteUrl;
import com.suniusoft.security.vo.UserVO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description:  权限拦截器
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/16  
 */
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        /**
         * 验证请求白名单
         */
        if (WhiteUrl.isWhiteUrl(request) && !BlackUrl.isBlackUrl(request)) {

            filterChain.doFilter(request, response);

            return;

        }

//        /**
//         * 授权
//         */
//        if (WxOauthHelper.isNeedOauth(request)) {
//            WxOauthHelper.wxOauth(sellerId, request, response, filterChain);
//            return;
//        }

        UserVO user = (UserVO) session.getAttribute(LoginConstant.SESSION_KEY);

        /**
         * user为空，表示未登陆，跳转到登陆页面
         */
        if (null == user) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("userName", user.getUserName());

        SecurityContextHolder.setLoginName(map);

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }


}
