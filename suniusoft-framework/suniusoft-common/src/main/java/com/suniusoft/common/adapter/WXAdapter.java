package com.suniusoft.common.adapter;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suniusoft.common.adapter.vo.*;
import com.suniusoft.common.api.client.WxApiClient;
import com.suniusoft.common.api.enums.ApiErrorEnums;
import com.suniusoft.common.api.enums.WXApiEnums;
import com.suniusoft.common.api.exception.WxApiException;
import com.suniusoft.common.api.request.WxRequest;
import com.suniusoft.common.api.response.WxResponse;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.JSONUtils;
import com.suniusoft.common.utils.PropertyReader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 微信接口服务
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/18 16:27  
 *
 * @modifier litu  litu@shufensoft.com 2015/5/22 16:05
 */

public class WXAdapter {

    private static final Logger logger = Logger.getLogger(WXAdapter.class);

    private static WxApiClient wxApiClient = new WxApiClient();

    public static String WX_API_URI_KEY = "wx.api.url";

    public static String wxApiUri = PropertyReader.getValue(WX_API_URI_KEY);


    /**
     * 获取微信access_token接口
     *
     * @param appId  参数
     * @param secret 参数
     * @return access_token
     * @modifier litu  litu@shufensoft.com 2015/5/22 14:05
     */
    public static AccessTokenVO getAccessToken(String appId, String secret) throws WxApiException {

        AssertsUtil.notBlank(appId , "appId");
        AssertsUtil.notBlank(secret , "secret");

        String result;
        AccessTokenVO accessTokenVo = null;
        WxRequest wxRequest = new WxRequest();
        wxRequest.setUrl(wxApiUri);
        wxRequest.setApi(WXApiEnums.ACCESS_TOKEN_URL.getCode());

        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", secret);

        wxRequest.setParames(params);
        WxResponse wxResponse = wxApiClient.doGet(wxRequest);

        if (ApiErrorEnums.SUCCESS.getCode().equals(wxResponse.getErrorCode())) {

            Map resultMap = (Map<String, Object>) JSONUtils.parseToObject(wxResponse.getResultJson(), Map.class);
            if (resultMap != null) {
                accessTokenVo = new AccessTokenVO();
                result = (String) resultMap.get("access_token");
                accessTokenVo.setAccessToken(result);
                accessTokenVo.setExpiresTime(Integer.parseInt(String.valueOf(resultMap.get("expires_in"))));
            }

        } else {
            logger.error(wxResponse.getErrorMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]");

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), wxResponse.getErrorMessage());
        }

        return accessTokenVo;
    }


    /**
     * 获取关注者的用户列表
     *
     * @param params 参数
     * @return 用户列表
     */
    public static WXUserListVO getUserOpenIdList(Map<String, String> params) {

        WxResponse wxResponse = null;
        WxRequest wxRequest = null;
        try {
            wxRequest = new WxRequest();
            wxRequest.setUrl(wxApiUri);
            wxRequest.setApi(WXApiEnums.USER_GET.getCode());
            wxRequest.setParames(params);
            wxResponse = wxApiClient.doGet(wxRequest);
        } catch (Exception e) {
            logger.error("获取用户列表接口失败：" + e);
        }

        WXUserListVO userListVO = null;
        if ("0".equals(wxResponse.getErrorCode())) {
            String resultJson = wxResponse.getResultJson();
            if (StringUtils.isNotBlank(resultJson)) {
                JSONObject jsonObject = (JSONObject) JSONUtils.parseToObject(resultJson, JSONObject.class);
                userListVO = new WXUserListVO();
                userListVO.setTotal(jsonObject.getLongValue("total"));
                userListVO.setCount(jsonObject.getLongValue("count"));
                userListVO.setNext_openid(jsonObject.getString("next_openid"));
                JSONObject jsonData = jsonObject.getJSONObject("data");
                if (jsonData != null) {
                    JSONArray jsonArray = jsonData.getJSONArray("openid");
                    List<String> list = new ArrayList<String>();
                    if (jsonArray == null || jsonArray.size() <= 0) {
                        return userListVO;
                    }
                    for (int i = 0; i < jsonArray.size(); i++) {
                        list.add(String.valueOf(jsonArray.get(i)));
                    }
                    userListVO.setUserOpenIdList(list);
                }

            }

            return userListVO;
        }

        if (StringUtils.isNotBlank(wxResponse.getErrorMessage())) {
            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), wxResponse.getErrorMessage());
        }

        return userListVO;
    }



    /**
     * @param code
     * @return 微信授权后返回的信息
     */
    public static WxUserOuathVO ouath(String wxAppId , String wxAppSecret , String code) {

        AssertsUtil.notBlank(code, "code");
        WxResponse wxResponse = null;
        WxRequest wxRequest = new WxRequest();
        wxRequest.setUrl(wxApiUri);
        wxRequest.setApi(WXApiEnums.WX_OUATH_API.getCode());

        Map<String, String> mapParames = new HashMap<String, String>();
        mapParames.put("appid", wxAppId);
        mapParames.put("secret", wxAppSecret);
        mapParames.put("grant_type", "authorization_code");
        mapParames.put("code", code);
        wxRequest.setParames(mapParames);
        try {
            wxResponse = wxApiClient.doGet(wxRequest);
        } catch (Exception e) {
            logger.error(e.getMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]", e);

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), e.getMessage(), e);
        }

        if ("0".equals(wxResponse.getErrorCode())) {

            String resultJson = wxResponse.getResultJson();
            Map mapResult = (Map) JSONUtils.parseToObject(resultJson, Map.class);
            WxUserOuathVO wxUserOuathVO = new WxUserOuathVO();
            wxUserOuathVO.setAccessToken((String) mapResult.get("access_token"));
            wxUserOuathVO.setOpenid((String) mapResult.get("openid"));
            wxUserOuathVO.setScope((String) mapResult.get("scope"));

            return wxUserOuathVO;
        } else {
            logger.error(wxResponse.getErrorMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]");

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), wxResponse.getErrorMessage());
        }
    }


    /**
     * @param openId   用户openId
     * @param wxAppId
     * @param wxAppSecret
     * @return 用户详细信息
     * @author litu  litu@shufensoft.com 2015/5/22 14:05
     * 获取关注用户详细信息
     */
    public WXUserInfoVO getUserInfo(String wxAppId , String wxAppSecret, String openId) throws WxApiException {

        WxResponse wxResponse = null;
        WXUserInfoVO userInfoVO;

        WxRequest wxRequest = new WxRequest();
        wxRequest.setUrl(wxApiUri);
        wxRequest.setApi(WXApiEnums.GET_USER_INFO.getCode());

        Map<String, String> userParams = new HashMap<String, String>();
        userParams.put("access_token", getAccessToken(wxAppId, wxAppSecret).getAccessToken());
        userParams.put("openid", openId);
        userParams.put("lang", "zh_CN");
        wxRequest.setParames(userParams);

        try {
            wxResponse = wxApiClient.doGet(wxRequest);
        } catch (Exception e) {
            logger.error(e.getMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]", e);

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), e.getMessage(), e);

        }

        if ("0".equals(wxResponse.getErrorCode())) {
            String resultJson = wxResponse.getResultJson();
            userInfoVO = (WXUserInfoVO) JSONUtils.parseToObject(resultJson, WXUserInfoVO.class);
        } else {
            logger.error(wxResponse.getErrorMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]");

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), wxResponse.getErrorMessage());
        }


        return userInfoVO;
    }


    /**
     * @param openId      用户openId
     * @param accessToken 授权得到的accessToken与基础支持的不同
     * @return 用户详细信息
     * @author zoujian  zujian@suniusoft.com 2015/7/14 14:05
     * 获取显性授权的用户详细信息
     */
    public static WXUserInfoVO getOuathUserInfo(String openId, String accessToken) throws WxApiException {

        WxResponse wxResponse = null;
        WXUserInfoVO userInfoVO;

        WxRequest wxRequest = new WxRequest();
        wxRequest.setUrl(wxApiUri);
        wxRequest.setApi(WXApiEnums.WX_OUATH_USER_INFO.getCode());

        Map<String, String> userParams = new HashMap<String, String>();
        userParams.put("access_token", accessToken);
        userParams.put("openid", openId);
        userParams.put("lang", "zh_CN");
        wxRequest.setParames(userParams);

        try {
            wxResponse = wxApiClient.doGet(wxRequest);
        } catch (Exception e) {
            logger.error(e.getMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]", e);

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), e.getMessage(), e);

        }

        if ("0".equals(wxResponse.getErrorCode())) {
            String resultJson = wxResponse.getResultJson();
            userInfoVO = (WXUserInfoVO) JSONUtils.parseToObject(resultJson, WXUserInfoVO.class);
        } else {
            logger.error(wxResponse.getErrorMessage() + "[api:" + wxRequest.getApi()
                    + "errorCode:" + wxResponse.getErrorCode() + "]");

            throw new WxApiException(wxRequest.getApi(), wxResponse.getErrorCode(), wxResponse.getErrorMessage());
        }


        return userInfoVO;
    }

}
