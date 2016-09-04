package com.suniusoft.common.adapter.vo;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm 
 *  @Description: <p>
 *     微信通过code换取网页授权access_token等信息
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/6/29  
 */
@Data
public class WxUserOuathVO {

    /**
     * 网页授权接口调用凭证
     */
    private String accessToken ;

    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private String expiresIn;

    /**
     * 用户刷新access_token
     */
    private String refreshToken;

    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openid;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息
     */
    private String unionid;
}
