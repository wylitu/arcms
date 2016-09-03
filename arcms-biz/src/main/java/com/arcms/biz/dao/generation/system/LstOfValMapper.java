package com.arcms.biz.dao.generation.system;

import com.arcms.biz.domain.generation.system.LstOfVal;
import com.arcms.biz.domain.generation.system.LstOfValExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LstOfValMapper {
    int countByExample(LstOfValExample example);

    int deleteByExample(LstOfValExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LstOfVal record);

    int insertSelective(LstOfVal record);

    List<LstOfVal> selectByExampleWithBLOBs(LstOfValExample example);

    List<LstOfVal> selectByExample(LstOfValExample example);

    LstOfVal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LstOfVal record, @Param("example") LstOfValExample example);

    int updateByExampleWithBLOBs(@Param("record") LstOfVal record, @Param("example") LstOfValExample example);

    int updateByExample(@Param("record") LstOfVal record, @Param("example") LstOfValExample example);

    int updateByPrimaryKeySelective(@Param("record") LstOfVal record);

    int updateByPrimaryKeyWithBLOBs(LstOfVal record);

    int updateByPrimaryKey(LstOfVal record);
}