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
public class Resource {
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

    /** 资源id,作为@BizId */
    @BizId
    private Long resourceId;

    /** 上级资源id */
    private Long parentResourceId;

    /** 该类目是否为父节点(即：该节点是否还有子节点) */
    private Boolean isParent;

    /** 资源名称 */
    private String name;

    /** 资源地址 */
    private String url;

    /** 资源类型 */
    private String type;

    /** 描述 */
    private String description;

    /** 优先级 */
    private Integer priority;

    /** 是否可用（0不可用，1可用） */
    private Boolean enabled;

    /** 样式 */
    private String style;

    private List<Resource> childResources;

}