package com.suniusoft.security.interceptor.login.util;

import com.suniusoft.common.utils.SpringContextUtil;
import com.suniusoft.security.biz.service.token.SysTokenService;
import com.suniusoft.security.vo.SysTokenVO;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/12/12  
 */
public class TokenUtils {

    private static SysTokenService sysTokenService = (SysTokenService)SpringContextUtil.getBean("sysTokenService");

    /**
     *  验证token
     * @param token
     * @return
     */
    public static boolean validateToken(String token){

        if(sysTokenService.isTokenExpired(token)){

            sysTokenService.deleteSysToken(token);

            return false;
        }

        return true;

    }

    /**
     *  获取token
     * @param token
     * @return
     */
    public static Long getUserId(String token){

        SysTokenVO sysTokenVO = sysTokenService.findSysToken(token);

        return sysTokenVO==null ? null : sysTokenVO.getUserId();

    }

}
