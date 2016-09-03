package com.arcms.biz.dao.generation.activity;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HongbaoActivityMapper {
    int countByExample(com.arcms.biz.domain.generation.activity.HongbaoActivityExample example);

    int deleteByExample(com.arcms.biz.domain.generation.activity.HongbaoActivityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.activity.HongbaoActivity record);

    int insertSelective(com.arcms.biz.domain.generation.activity.HongbaoActivity record);

    List<com.arcms.biz.domain.generation.activity.HongbaoActivity> selectByExample(com.arcms.biz.domain.generation.activity.HongbaoActivityExample example);

    com.arcms.biz.domain.generation.activity.HongbaoActivity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoActivity record, @Param("example") com.arcms.biz.domain.generation.activity.HongbaoActivityExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoActivity record, @Param("example") com.arcms.biz.domain.generation.activity.HongbaoActivityExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoActivity record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.activity.HongbaoActivity record);
}