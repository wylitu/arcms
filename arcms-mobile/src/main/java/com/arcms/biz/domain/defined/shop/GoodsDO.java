package com.arcms.biz.domain.defined.shop;

import com.arcms.biz.domain.generation.goods.Goods;
import lombok.Data;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: 
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/11/26 下午10:31  
 */
@Data
public class GoodsDO extends Goods {

    private Double lng;

    private Double lat;

    private String geohash;


}
