package com.arcms.biz.dao.generation.system;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LstOfValMapper {
    int countByExample(com.arcms.biz.domain.generation.system.LstOfValExample example);

    int deleteByExample(com.arcms.biz.domain.generation.system.LstOfValExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.system.LstOfVal record);

    int insertSelective(com.arcms.biz.domain.generation.system.LstOfVal record);

    List<com.arcms.biz.domain.generation.system.LstOfVal> selectByExampleWithBLOBs(com.arcms.biz.domain.generation.system.LstOfValExample example);

    List<com.arcms.biz.domain.generation.system.LstOfVal> selectByExample(com.arcms.biz.domain.generation.system.LstOfValExample example);

    com.arcms.biz.domain.generation.system.LstOfVal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.system.LstOfVal record, @Param("example") com.arcms.biz.domain.generation.system.LstOfValExample example);

    int updateByExampleWithBLOBs(@Param("record") com.arcms.biz.domain.generation.system.LstOfVal record, @Param("example") com.arcms.biz.domain.generation.system.LstOfValExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.system.LstOfVal record, @Param("example") com.arcms.biz.domain.generation.system.LstOfValExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.system.LstOfVal record);

    int updateByPrimaryKeyWithBLOBs(com.arcms.biz.domain.generation.system.LstOfVal record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.system.LstOfVal record);
}