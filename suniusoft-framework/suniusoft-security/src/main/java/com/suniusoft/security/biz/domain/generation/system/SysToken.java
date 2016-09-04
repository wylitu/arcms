package com.suniusoft.security.biz.domain.generation.system;

import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class SysToken {
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

    /** 用户id */
    private Long userId;

    /** 用户名 */
    private String userNo;

    /** 类型，login:作为登陆session、repeat_submit：防止表单重复提交 */
    private String type;

    /** token 值 */
    private String token;

    /** 过期时间 */
    private Date gmtExpired;
}