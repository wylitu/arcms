package com.suniusoft.security.biz.domain.generation.sms;

import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class SmsCode {
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

    /** 手机 */
    private String mobile;

    /** 验证码 */
    private String code;

    /**  */
    private Date gmtExpired;
}