package com.arcms.biz.dao.generation.activity;

import com.arcms.biz.domain.generation.activity.HongbaoObtainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HongbaoObtainMapper {
    int countByExample(HongbaoObtainExample example);

    int deleteByExample(HongbaoObtainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.activity.HongbaoObtain record);

    int insertSelective(com.arcms.biz.domain.generation.activity.HongbaoObtain record);

    List<com.arcms.biz.domain.generation.activity.HongbaoObtain> selectByExample(HongbaoObtainExample example);

    com.arcms.biz.domain.generation.activity.HongbaoObtain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoObtain record, @Param("example") HongbaoObtainExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoObtain record, @Param("example") HongbaoObtainExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.activity.HongbaoObtain record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.activity.HongbaoObtain record);
}