package com.suniusoft.security.interceptor.login.util;

import com.suniusoft.common.adapter.vo.WxUserOuathVO;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.PropertyReader;
import com.suniusoft.common.utils.SpringContextUtil;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.biz.service.wx.WXService;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 * 微信授权帮助类
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/7/17  
 */
public class WxOauthHelper {

    private static final Logger logger = Logger.getLogger(WxOauthHelper.class);

    public static WXService wXService = (WXService) SpringContextUtil.getBean("wXService");

    public static SecurityUserService securityUserService = (SecurityUserService) SpringContextUtil.getBean("securityUserService");

    public static String WX_OPEN_API_URI_KEY = "wx.open.api.url";

    public static String wxOpenApiUri = PropertyReader.getValue(WX_OPEN_API_URI_KEY);

    public static String arcms_WEB_URI_KEY = "arcms.web.url";

    public static String arcmsWebUri = PropertyReader.getValue(arcms_WEB_URI_KEY);

    public static String WX_APPID = "open.weixin.appId";

    public static String appId = PropertyReader.getValue(WX_APPID);

    public static String WX_APPSECRET = "open.weixin.appSecret";

    public static String appSecret = PropertyReader.getValue(WX_APPSECRET);





    /**
     * 拼接授权url
     * @param url
     * @param scope snsapi_base:隐性授权，
     *              snsapi_userinfo:显性授权
     */
    public static String getOauthUrl(String url, String scope) throws UnsupportedEncodingException {


        AssertsUtil.notBlank(url, "url");

        AssertsUtil.notBlank(scope, "scope");

        /**
         * 微信相关配置信息
         */
        url = URLEncoder.encode(url, "utf-8");
        return  wxOpenApiUri + "/connect/oauth2/authorize?appid=" + appId
                + "&redirect_uri=" + arcmsWebUri + url +
                "&response_type=code&scope=" + scope + "&state=" + scope + "#wechat_redirect";
    }


    /**
     * 用户授权
     * @param request
     * @param response
     * @return
     */
    public static UserVO wxOauth(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String url = request.getServletPath();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String codeFlag = request.getParameter("codeFlag");

        Map<String, String> paramMap = request.getParameterMap();

        String param = buildParamStr(paramMap,request);

        if(StringUtils.isNotBlank(param)){
            url = url + "?" +param;
        }

        /**
         * 隐性授权
         */
        if (StringUtils.isBlank(code) && !"1".equals(codeFlag)) {

            if(StringUtils.isNotBlank(param)){
                url = url + "&codeFlag=1";
            }else{
                url = url + "?" +"codeFlag=1";
            }

            response.sendRedirect(getOauthUrl(url, SecurityConstant.scope.SNSAPI_BASE));

            return null;
        }

        /**
         * 如果返回code，获取openid，并且新粉丝需要显性授权
         */
        WxUserOuathVO wxUserOuathVO = wXService.getWxUserOuath(appId,appSecret,code);

        /**
         * 隐性授权处理
         */
        if (SecurityConstant.scope.SNSAPI_BASE.equals(state)) {

            String openId = wxUserOuathVO.getOpenid();
            UserVO userVO = securityUserService.findUserByWxOpenId(openId);

            if (userVO == null) {

                if(StringUtils.isNotBlank(param)){
                    url = url + "&codeFlag=1";
                }else{
                    url = url + "?codeFlag=1";
                }

                response.sendRedirect(getOauthUrl(url, SecurityConstant.scope.SNSAPI_USERINFO));

                return null;

            }

            request.getRequestDispatcher(request.getServletPath()).forward(request,response);

            return userVO;

        }


        /**
         * 显性授权处理
         */
        if (SecurityConstant.scope.SNSAPI_USERINFO.equals(state)) {

            UserVO userVO = securityUserService.createAndReturnWXUserToUser(wxUserOuathVO.getOpenid(), wxUserOuathVO.getAccessToken());

            if(userVO!=null){
                request.getRequestDispatcher(request.getServletPath()).forward(request,response);
                return userVO;

            }

        }

        return null;
    }

    /**
     * 组装参数
     *
     * @param param
     * @return
     */
    public static String buildParamStr(Map<String, String> param,HttpServletRequest request) {
        String paramStr = "";
        Object[] keyArray = param.keySet().toArray();
        for (int i = 0; i < keyArray.length; i++) {

            String key = (String) keyArray[i];
            if("code".equals(key) || "state".equals(key)){
                continue;
            }
            if (0 == i) {
                paramStr += (key + "=" + (request.getParameter(key)==null?"":request.getParameter(key)));
            } else {
                paramStr += ("&" + key + "=" + (request.getParameter(key)==null?"":request.getParameter(key)));
            }

        }

        return paramStr;
    }

}
