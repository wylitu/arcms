package com.arcms.biz.domain.generation.activity;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class HongbaoActivity {
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

    /** 红包池id ,作为@BizId */
    @BizId
    private Long activityId;

    /** 开始时间 */
    private Date startDate;

    /** 结束时间 */
    private Date endDate;

    /** 红包活动名称 */
    private String name;

    /** 红包个数 */
    private Long number;

    /** 每个红包爱卡数 */
    private Long icardCoin;

    /** 已领个数 */
    private Long numberObtain;

    /** 状态 unBoundd:未关联unReceived:待领取，receiving：领取中done:结束 */
    private String states;

    /** 红包总爱卡数 */
    private Long totalIcardCoin;

    /** 红包领取金额 */
    private Long obtainIcardCoin;
}