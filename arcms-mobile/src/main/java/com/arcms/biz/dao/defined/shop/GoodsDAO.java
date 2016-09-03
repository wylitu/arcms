package com.arcms.biz.dao.defined.shop;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 *  @ProjectName: icard
 *  @Description:
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/11/26 下午10:26  
 */
@Repository
public interface GoodsDAO {

    /**
     * 根据位置的GEOHASH值查询附近商品
     * @param geohash
     * @return
     */
    List<com.arcms.biz.domain.defined.shop.GoodsDO> selectHotGoodsByGeoHash(@Param("geohash") String geohash);
}