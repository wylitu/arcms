package com.suniusoft.security.biz.dao.generation.permission;

import com.suniusoft.security.biz.domain.generation.permission.ResourceRole;
import com.suniusoft.security.biz.domain.generation.permission.ResourceRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRoleMapper {
    int countByExample(ResourceRoleExample example);

    int deleteByExample(ResourceRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ResourceRole record);

    int insertSelective(ResourceRole record);

    List<ResourceRole> selectByExample(ResourceRoleExample example);

    ResourceRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ResourceRole record, @Param("example") ResourceRoleExample example);

    int updateByExample(@Param("record") ResourceRole record, @Param("example") ResourceRoleExample example);

    int updateByPrimaryKeySelective(@Param("record") ResourceRole record);

    int updateByPrimaryKey(ResourceRole record);
}