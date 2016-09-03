package com.arcms.biz.domain.generation.seller;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class Seller {
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
    private Long sellerId;

    /** 商家名称 */
    private String sellerName;

    /** 联系人 */
    private String contacts;

    /** 商家佣金(百分比) */
    private String commission;

    /** 营业执照复印件 */
    private String businessLicense;

    /** 商家照片 */
    private String sellerPicture;

    /** 商家折扣（8即八折） */
    private BigDecimal discount;

    /** 经度 */
    private BigDecimal lng;

    /** 纬度 */
    private BigDecimal lat;

    /** 商家简介 */
    private String sellerBrief;

    /** 商家关联用户推荐码 */
    private String businesstouserRecommendCode;

    /** 是否审核(0未审核，1已审核) */
    private Integer isChecked;

    /**  */
    private String geohash;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 详细地址 */
    private String address;

    /** 手机号码 */
    private String mobile;

    /** 座机号码 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 商家详情 */
    private String sellerDetail;
}