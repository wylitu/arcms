package com.arcms.biz.domain.generation.user;

import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class MemberLevel {
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
    private Integer isDeleted;

    /** 会员级别名称 */
    private String name;

    /** 人民币返现比例(百分比) */
    private String rmbReturnRatio;

    /** 爱卡返现比例(百分比) */
    private String icardReturnRatio;

    /** 该级别所需积分 */
    private Long requiredIntegral;

    /** 会员级别描述 */
    private String description;
}