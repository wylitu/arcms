package com.suniusoft.common.api.client;
import com.suniusoft.common.api.enums.ApiErrorEnums;
import com.suniusoft.common.api.exception.ApiException;
import com.suniusoft.common.api.request.ApiRequest;
import com.suniusoft.common.api.response.ApiResponse;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.HttpUtils;
import com.suniusoft.common.utils.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: api客户端基本类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/25 15:19  
 */
public class BaseApiClient {


    private static final Logger logger = Logger.getLogger(BaseApiClient.class);

    /**
     * 返回错误信息key
     */
    protected static final String ERROR_MESSAGE_KEY = "errorMessage";

    /**
     * 返回错误码key
     */
    protected static final String ERROR_CODE_KEY = "errorCode";

    /**
     * 返回内容key
     */
    protected static final String INFO_KEY = "info";

    public String getParamStr(Map<String, String> parames) {
        String str = "";
        try {
            str = URLEncoder.encode(buildParamStr(parames), "UTF-8")
                    .replace("%3A", ":")
                    .replace("%2F", "/")
                    .replace("%26", "&")
                    .replace("%3D", "=")
                    .replace("%3F", "?");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    private String buildParamStr(Map<String, String> param) {
        String paramStr = "";
        Object[] keyArray = param.keySet().toArray();
        for (int i = 0; i < keyArray.length; i++) {
            String key = (String) keyArray[i];

            if (0 == i) {
                paramStr += (key + "=" + param.get(key));
            } else {
                paramStr += ("&" + key + "=" + param.get(key));
            }
        }

        return paramStr;
    }

    /**
     * @param apiRequest
     * @return 接口get请求
     * @throws Exception
     */
    public ApiResponse doGet(ApiRequest apiRequest) throws ApiException {

        AssertsUtil.notNull(apiRequest, "apiRequest is null");

        AssertsUtil.notContainsBlank(apiRequest.getApi(), "apiRequest api");

        AssertsUtil.notContainsBlank(apiRequest.getUrl(), "apiRequest bese url");

        String url = apiRequest.getUrl() + apiRequest.getApi();

        if(!CollectionUtils.isEmpty(apiRequest.getParames())){
            url = url + "?" + getParamStr(apiRequest.getParames());
        }


        HttpGet request = new HttpGet(url);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode(ApiErrorEnums.ERROR.getCode());

        Map mapResult;
        String response;
        try {

            Map<String,String> headerMap = apiRequest.getHeaderMap();
            if(!CollectionUtils.isEmpty(headerMap)){
                for(String key : headerMap.keySet()){
                    request.setHeader(key,headerMap.get(key));
                }
            }

            response = HttpUtils.doGet(request);

            logger.info("response:" + response);

            if (StringUtils.isBlank(response)) {
                throw new ApiException("",apiRequest.getApi(), "response is null");
            }
            mapResult = (Map) JSONUtils.parseToObject(response, Map.class);

        } catch (Exception e) {
            apiResponse.setErrorMessage(e.getMessage());
            return apiResponse;
        }

        apiResponse.setErrorCode(String.valueOf(mapResult.get(ERROR_CODE_KEY)));
        apiResponse.setErrorMessage(String.valueOf(mapResult.get(ERROR_MESSAGE_KEY)));
        Object info = mapResult.get(INFO_KEY);

        if(!"0".equals(apiResponse.getErrorCode())){

            apiResponse.setResultJson(response);
            return apiResponse;

        }

        try {

            if(info!=null){
                apiResponse.setResultJson(JSONUtils.toJSONStr(info));
            }

        } catch (Exception e) {
            throw new ApiException("",apiRequest.getApi(), "response parse to json error");
        }

        apiResponse.setErrorCode(ApiErrorEnums.SUCCESS.getCode());

        return apiResponse;

    }


    /**
     * @param apiRequest
     * @return 接口post请求
     * @throws Exception
     */
    public ApiResponse doPost(ApiRequest apiRequest) throws ApiException {

        AssertsUtil.notNull(apiRequest, "apiRequest is null");

        AssertsUtil.notContainsBlank(apiRequest.getApi(), "apiRequest api");

        AssertsUtil.notContainsBlank(apiRequest.getUrl(), "apiRequest api bese url");


        String url = apiRequest.getUrl() + apiRequest.getApi();

        ApiResponse aiResponse = new ApiResponse();
        aiResponse.setErrorCode(ApiErrorEnums.ERROR.getCode());

        String response = null;
        try {
            response = HttpUtils.doPost(url,apiRequest.getParames());
        } catch (Exception e) {
            aiResponse.setErrorMessage(e.getMessage());
            return aiResponse;
        }

        aiResponse.setResultJson(response);
        aiResponse.setErrorCode(ApiErrorEnums.SUCCESS.getCode());

        return aiResponse;

    }

    /**
     * @param apiRequest
     * @return 接口 json post请求
     * @throws Exception
     */
    public ApiResponse doJsonPost(ApiRequest apiRequest) throws ApiException {

        AssertsUtil.notNull(apiRequest, "apiRequest is null");

        AssertsUtil.notContainsBlank(apiRequest.getApi(), "apiRequest api");

        AssertsUtil.notContainsBlank(apiRequest.getUrl(), "apiRequest api bese url");


        String url = apiRequest.getUrl() + apiRequest.getApi();

        ApiResponse aiResponse = new ApiResponse();
        aiResponse.setErrorCode(ApiErrorEnums.ERROR.getCode());

        String response = null;

        try {
            response = HttpUtils.doJsonPost(url,apiRequest.getJsonStrParam());
        } catch (Exception e) {
            aiResponse.setErrorMessage(e.getMessage());
            return aiResponse;
        }

        aiResponse.setResultJson(response);
        aiResponse.setErrorCode(ApiErrorEnums.SUCCESS.getCode());

        return aiResponse;

    }

}
