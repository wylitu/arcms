package com.arcms.biz.dao.generation.content;

import com.arcms.biz.domain.generation.content.ContentManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentManageMapper {
    int countByExample(ContentManageExample example);

    int deleteByExample(ContentManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.content.ContentManage record);

    int insertSelective(com.arcms.biz.domain.generation.content.ContentManage record);

    List<com.arcms.biz.domain.generation.content.ContentManage> selectByExampleWithBLOBs(ContentManageExample example);

    List<com.arcms.biz.domain.generation.content.ContentManage> selectByExample(ContentManageExample example);

    com.arcms.biz.domain.generation.content.ContentManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.content.ContentManage record, @Param("example") ContentManageExample example);

    int updateByExampleWithBLOBs(@Param("record") com.arcms.biz.domain.generation.content.ContentManage record, @Param("example") ContentManageExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.content.ContentManage record, @Param("example") ContentManageExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.content.ContentManage record);

    int updateByPrimaryKeyWithBLOBs(com.arcms.biz.domain.generation.content.ContentManage record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.content.ContentManage record);
}