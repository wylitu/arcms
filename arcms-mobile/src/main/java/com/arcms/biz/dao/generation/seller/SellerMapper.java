package com.arcms.biz.dao.generation.seller;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerMapper {
    int countByExample(com.arcms.biz.domain.generation.seller.SellerExample example);

    int deleteByExample(com.arcms.biz.domain.generation.seller.SellerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.seller.Seller record);

    int insertSelective(com.arcms.biz.domain.generation.seller.Seller record);

    List<com.arcms.biz.domain.generation.seller.Seller> selectByExampleWithBLOBs(com.arcms.biz.domain.generation.seller.SellerExample example);

    List<com.arcms.biz.domain.generation.seller.Seller> selectByExample(com.arcms.biz.domain.generation.seller.SellerExample example);

    com.arcms.biz.domain.generation.seller.Seller selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.seller.Seller record, @Param("example") com.arcms.biz.domain.generation.seller.SellerExample example);

    int updateByExampleWithBLOBs(@Param("record") com.arcms.biz.domain.generation.seller.Seller record, @Param("example") com.arcms.biz.domain.generation.seller.SellerExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.seller.Seller record, @Param("example") com.arcms.biz.domain.generation.seller.SellerExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.seller.Seller record);

    int updateByPrimaryKeyWithBLOBs(com.arcms.biz.domain.generation.seller.Seller record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.seller.Seller record);
}