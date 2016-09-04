package com.suniusoft.common.api.response;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 微信响应对象
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/19 17:09  
 */
@Data
public class WxResponse extends ApiResponse {

    private String returnBizId;
}
