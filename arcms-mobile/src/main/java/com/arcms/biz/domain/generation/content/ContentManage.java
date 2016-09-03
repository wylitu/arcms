package com.arcms.biz.domain.generation.content;

import com.suniusoft.common.annotation.Domain;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class ContentManage {
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

    /** 文章发布位置 user:用户端 seller:商家端 */
    private String articlePosition;

    /** 内容类型 article:文章 advertisement:广告 arcms_service:爱卡服务 recommen_rules:推荐规则         payment_help:支付帮助  about_arcms:关于我的卡    */
    private String type;

    /** 标题 */
    private String title;

    /** 发布状态 published发布状态 unPublish未发布 */
    private String status;

    /** 内容缩略图 */
    private String contentUrl;

    /** 链接地址 */
    private String linkUrl;

    /** 排序 */
    private Integer sort;

    /** 内容详情 */
    private String contentDetail;
}