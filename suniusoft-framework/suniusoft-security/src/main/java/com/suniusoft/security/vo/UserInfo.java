package com.suniusoft.security.vo;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/12/16  
 */
@Data
public class UserInfo {

    /** 用户名或昵称 */
    private String userNo;

    /**  */
    private String qqOpenId;

    /** 微信openid */
    private String wxOpenId;

    /**手机号码*/
    private String mobile;

    /** 用户头像 */
    private String headImg;

    /** 性别1:男；2:女 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 密码 */
    private String password;

    /** 登录类型 */
    private String loginType;

}
