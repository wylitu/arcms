package com.suniusoft.security.biz.dao.generation.permission;

import com.suniusoft.security.biz.domain.generation.permission.SecurityUser;
import com.suniusoft.security.biz.domain.generation.permission.SecurityUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserMapper {
    int countByExample(SecurityUserExample example);

    int deleteByExample(SecurityUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SecurityUser record);

    int insertSelective(SecurityUser record);

    List<SecurityUser> selectByExample(SecurityUserExample example);

    SecurityUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SecurityUser record, @Param("example") SecurityUserExample example);

    int updateByExample(@Param("record") SecurityUser record, @Param("example") SecurityUserExample example);

    int updateByPrimaryKeySelective(@Param("record") SecurityUser record);

    int updateByPrimaryKey(SecurityUser record);
}