package com.suniusoft.common.api.client;

import com.suniusoft.common.api.exception.WxApiException;
import com.suniusoft.common.api.request.WxRequest;
import com.suniusoft.common.api.response.ApiResponse;
import com.suniusoft.common.api.response.WxResponse;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.HttpUtils;
import com.suniusoft.common.utils.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;


/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/19 14:05  
 *  @modifier litu  litu@shufensoft.com 2015/5/22 14:05
 *  
 */

public class WxApiClient extends BaseApiClient {


    private static final Logger logger = Logger.getLogger(WxApiClient.class);

    /**
     * 微信接口客户端GET调用
     *
     * @param wxRequest 接口get请求
     * @return 接口返回结果
     * @throws Exception
     */
    public  WxResponse doGet(WxRequest wxRequest) throws WxApiException {

        WxResponse wxResponse = new WxResponse();
        ApiResponse apiResponse = super.doGet(wxRequest);

        logger.info("response:" + apiResponse);

        AssertsUtil.notNull(apiResponse,"apiResponse");
        AssertsUtil.notNull(apiResponse.getResultJson(),"apiResponse ResultJson");


        Map mapResult = (Map) JSONUtils.parseToObject(apiResponse.getResultJson(), Map.class);

        if(mapResult == null){
            throw  new WxApiException("result is null");
        }

        String errcode = String.valueOf(mapResult.get("errcode"));
        if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)&&"null"!=errcode) {
            String errmsg = String.valueOf(mapResult.get("errmsg"));
            wxResponse.setErrorCode(errcode);
            wxResponse.setErrorMessage(errmsg);
        } else {
            wxResponse.setErrorCode("0");
            wxResponse.setResultJson(apiResponse.getResultJson());
        }

        return wxResponse;
    }


    /**
     * 微信接口客户端POST调用
     *
     * @param wxRequest 请求参数
     * @return 接口返回结果
     * @throws Exception
     */
    public  WxResponse doPost(WxRequest wxRequest) throws WxApiException {

        WxResponse wxResponse = new WxResponse();

        String result = HttpUtils.doPost(wxRequest.getApi(), wxRequest.getStringParames(), "UTF-8", wxRequest.getSecrtePath(),wxRequest.getMchId());

        Map mapResult = (Map) JSONUtils.parseToObject(result, Map.class);

        if(mapResult == null){
            return wxResponse;
        }

        String errcode = String.valueOf(mapResult.get("errcode"));
        wxResponse.setErrorCode(errcode);

        if ("0".equals(errcode)) {
            String bizId = String.valueOf(mapResult.get("msgid"));
            if (StringUtils.isBlank(bizId)) {
                wxResponse.setReturnBizId(bizId);
            }
        } else {
            String errmsg = String.valueOf(mapResult.get("errmsg"));
            wxResponse.setErrorMessage(errmsg);
        }

        return wxResponse;
    }


    /**
     * 微信接口客户端POST调用
     *
     * @param wxRequest 请求参数
     * @return 接口返回结果
     * @throws Exception
     */
    public  WxResponse doJsonPost(WxRequest wxRequest) throws WxApiException {

        WxResponse wxResponse = new WxResponse();
        ApiResponse apiResponse = super.doJsonPost(wxRequest);
        AssertsUtil.notNull(apiResponse,"apiResponse");
        AssertsUtil.notNull(apiResponse.getResultJson(),"apiResponse ResultJson");

        Map mapResult = (Map) JSONUtils.parseToObject(apiResponse.getResultJson(), Map.class);

        if(mapResult == null){
            return wxResponse;
        }

        String errcode = String.valueOf(mapResult.get("errcode"));
        wxResponse.setErrorCode(errcode);

        if ("0".equals(errcode)) {
            String bizId = String.valueOf(mapResult.get("msgid"));
            if (StringUtils.isBlank(bizId)) {
                wxResponse.setReturnBizId(bizId);
            }
        } else {
            String errmsg = String.valueOf(mapResult.get("errmsg"));
            wxResponse.setErrorMessage(errmsg);
        }

        return wxResponse;
    }


    /**
     * 微信企业转账接口客户端POST调用
     *
     * @param wxRequest 请求参数
     * @return 接口返回结果
     * @throws Exception
     */
    public  String doEnterpriseTransfersPost(WxRequest wxRequest) throws WxApiException {

        WxResponse wxResponse = new WxResponse();

        String result = HttpUtils.doPost(wxRequest.getApi(), wxRequest.getStringParames(), "UTF-8", wxRequest.getSecrtePath(),wxRequest.getMchId());

        if(!result.contains("SUCCESS")){
            throw new  WxApiException("企业转账失败");
        }

        return result;
    }

}
