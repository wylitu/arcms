package com.arcms.biz.domain.generation.activity;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class HongbaoObtain {
    /** 主键ID */
    private Long id;

    /** 创建时间 */
    private Date gmtCreated;

    /** 创建人 */
    private String createdBy;

    /** 修改时间 */
    private Date gmtModified;

    /** 修改人 */
    private String modifiedBy;

    /** 是否删除：1(删除) 0(未删除) */
    private Boolean isDeleted;

    /** 领取ID,作为@BizId */
    @BizId
    private Long hongbaoObtainId;

    /** 用户ID */
    private Long userId;

    /** 爱卡币金额 */
    private Long arcmsIcon;

    /** 红包活动ID */
    private Long hongbaoActivityId;

    /** 红包活动名称冗余字段 */
    private String hongbaoActivityName;

    /** 领取时间 */
    private Date obtainDatatime;

    /** toReceive 待领取  received 已领取 */
    private String status;
}