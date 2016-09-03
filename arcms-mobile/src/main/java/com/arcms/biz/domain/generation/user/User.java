package com.arcms.biz.domain.generation.user;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class User {
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

    /** 后台用户id,作为@BizId */
    @BizId
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 姓名 */
    private String name;

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

    /** 会员卡号 */
    private String memberCard;

    /** 支付密码 */
    private String payPassword;

    /** 会员级别id */
    private Long memberLevelId;

    /** 会员积分 */
    private Long integral;

    /** 账户余额 */
    private BigDecimal balance;

    /** 身份证号码 */
    private String identityCard;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 详细地址 */
    private String address;

    /** 生日 */
    private Date birthday;

    /** 密保问题 */
    private String secretQuestion;

    /** 密保答案 */
    private String secretAnswer;

    /** 推荐人用户id */
    private Long recommendUserId;

    /** 商家扩展id */
    private Long sellerId;

    /** 用户可用：1(是) 0(否) */
    private Boolean enabled;
}