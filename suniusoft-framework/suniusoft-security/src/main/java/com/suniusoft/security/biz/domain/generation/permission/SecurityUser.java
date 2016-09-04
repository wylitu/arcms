package com.suniusoft.security.biz.domain.generation.permission;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class SecurityUser {
    /** 主键ID */
    private Long id;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;

    /** 创建人 */
    private String createdBy;

    /** 修改人 */
    private String modifiedBy;

    /** 是否删除：1(删除) 0(未删除) */
    private Boolean isDeleted;

    /** 用户id,作为@BizId */
    @BizId
    private Long userId;

    /** 昵称 */
    private String userNick;

    /** 姓名 */
    private String name;

    /** 用户编号 */
    private String userNo;

    /** 密码 */
    private String password;

    /** 性别1:男；2:女 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 手机号码 */
    private String mobile;

    /** 座机号码 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 身份证号码 */
    private String identityCard;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 微信openid */
    private String wxOpenid;

    /** 公众号关注标识 */
    private String wxSubscribe;

    /** 微信头像 */
    private String wxHeadimgurl;

    /** 关注时间 */
    private String wxSubscribeTime;

    /** 寝室编号 */
    private String bedroomNo;

    /** 详细地址 */
    private String address;

    /** 空调开通时间 */
    private Date acOpenDate;

    /** 用户是可用：1(是) 0(否) */
    private Boolean enabled;

    /** 角色*/
    private List<Role> roles;

}