package com.suniusoft.common.adapter.vo;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm 
 *  @Description: <p>
 *     调用各平台接口获取到的 AccessToken 结果, 包括access_token, refresh_token, expires_time等
 * </p>
 *  @author: lin
 *  @date: 2015/7/22  
 */
@Data
public class AccessTokenVO {

    private Long sellerId;

    //淘宝tb, 京东jd, 微信wx
    private String type;

    private String accessToken;

    private String refreshToken;

    private int expiresTime;

}
