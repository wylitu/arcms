package com.arcms.biz.dao.generation.user;

import com.arcms.biz.domain.generation.user.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(com.arcms.biz.domain.generation.user.User record);

    int insertSelective(com.arcms.biz.domain.generation.user.User record);

    List<com.arcms.biz.domain.generation.user.User> selectByExample(UserExample example);

    com.arcms.biz.domain.generation.user.User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") com.arcms.biz.domain.generation.user.User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") com.arcms.biz.domain.generation.user.User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(@Param("record") com.arcms.biz.domain.generation.user.User record);

    int updateByPrimaryKey(com.arcms.biz.domain.generation.user.User record);
}