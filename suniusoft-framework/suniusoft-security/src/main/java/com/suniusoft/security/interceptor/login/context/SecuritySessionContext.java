package com.suniusoft.security.interceptor.login.context;

import com.google.common.collect.Maps;
import com.suniusoft.common.aspect.SecurityContextHolder;
import com.suniusoft.common.utils.SpringContextUtil;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.interceptor.login.util.TokenUtils;
import com.suniusoft.security.vo.UserVO;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                    权限上下文
 *                  </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/12/12  
 */
public class SecuritySessionContext {

    private static final Logger logger = Logger.getLogger(SecuritySessionContext.class);

    private static  SecurityUserService securityUserService = (SecurityUserService)SpringContextUtil.getBean("securityUserService");

    public static void init(String token){

        getUserInfo(token);
    }

    public static UserVO getUserInfo(String token){

        logger.info("token:" + token);

        Map<String,Object> loginInfo = Maps.newHashMap();

        Long userId = TokenUtils.getUserId(token);

        UserVO userVO = securityUserService.findUserByUserId(userId);

        loginInfo.put("userName", userVO.getUserName());
        loginInfo.put(SecurityConstant.SESSION_KEY, userVO);

        logger.info("user:" + userVO.getUserName());

        SecurityContextHolder.setLoginInfo(loginInfo);

        return userVO;
    }

    public static UserVO getUserInfo(){


        Map<String,Object> loginInfo = SecurityContextHolder.getLoginInfo();

        if(CollectionUtils.isEmpty(loginInfo)){
            return null;
        }

        return (UserVO)loginInfo.get(SecurityConstant.SESSION_KEY);
    }
}
