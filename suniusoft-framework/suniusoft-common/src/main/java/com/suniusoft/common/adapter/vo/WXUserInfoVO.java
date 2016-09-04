package com.suniusoft.common.adapter.vo;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 公众号关注用户信息
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/15 17:15  
 */
@Data
public class WXUserInfoVO {
    /**
     * 用户是滞关注公众号标识，0：没有关注
     */
    private String subscribe;

    private String remark;
    /**
     * 用户对当前公众号的唯一标识
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户所在的城市
     */
    private String city;

    /**
     * 用户所在的国家
     */
    private String country;

    /**
     * 用的所在的省份
     */
    private String province;

    /**
     * 用户的语言
     */
    private String language;

    /**
     * 用户头像地址
     */
    private String headimgurl;

    /**
     * 用户关注的时间
     */
    private String subscribe_time;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    private String unionid;
}
