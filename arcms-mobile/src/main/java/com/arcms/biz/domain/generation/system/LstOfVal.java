package com.arcms.biz.domain.generation.system;

import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class LstOfVal {
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

    /** 类型 */
    private String type;

    /** Key */
    private String name;

    /** 值 */
    private String value;

    /** 描述 */
    private String descText;

    /** 一个key对应多个值时需要 */
    private String value01;

    /** 一个key对应多个值时需要 */
    private String value02;

    /** 一个key对应多个值时需要 */
    private String value03;

    /** 排序字段 */
    private Integer ordeBy;

    /** 大值 */
    private String bigValue;
}