package com.arcms.biz.domain.generation.goods;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Domain
@Data
@ToString
public class Goods {
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

    /** 商户号 */
    private Long sellerId;

    /** 商品id,作为@BizId */
    @BizId
    private Long goodsId;

    /** 商品标题 */
    private String title;

    /** 商品类目id */
    private Long cid;

    /** 是否虚拟商品 1是 0否 */
    private Boolean isVirtual;

    /** 上架时间 */
    private Date listTime;

    /** 下架时间 */
    private Date delistTime;

    /** 有效期开始时间 */
    private Date expiredStartTime;

    /** 有效期结束时间 */
    private Date expiredEndTime;

    /** 状态  onsale：在售中 instock：在库中 */
    private String status;

    /** 数量 */
    private Long quantity;

    /** 价格 */
    private BigDecimal price;

    /** 折扣，1代表1折 */
    private BigDecimal discount;

    /** 已购买数量 */
    private Long purchaseQuantity;

    /**  详情页 */
    private String detailUrl;

    /**  二维码图片链接（预留,一期暂时用不到） */
    private String qrUrl;

    /**  banner图片列表 */
    private String imgsUrl;

    /** 商品小图,显示在首页和列表中的图片 */
    private String thumbnail;

    /**  以逗号分隔的skuId（预留,一期暂时用不到） */
    private String skus;

    /** 	属性串pid:vid（预留,一期暂时用不到） */
    private String props;

    /**  属性名串	pid:vid:pid_name:vid_name（预留,一期暂时用不到） */
    private String propsName;

    /** 版本号 */
    private Long version;
}