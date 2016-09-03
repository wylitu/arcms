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
public class Role {
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

    /** 后台角色id,@BizId */
    @BizId
    private Long roleId;

    /** 角色名称 */
    private String name;

    /** 描述 */
    private String description;

    /**角色对应多个资源*/
    private List<Resource> resources;
}