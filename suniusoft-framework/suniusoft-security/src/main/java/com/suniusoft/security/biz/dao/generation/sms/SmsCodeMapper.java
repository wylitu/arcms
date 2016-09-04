package com.suniusoft.security.biz.dao.generation.sms;

import com.suniusoft.security.biz.domain.generation.sms.SmsCode;
import com.suniusoft.security.biz.domain.generation.sms.SmsCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeMapper {
    int countByExample(SmsCodeExample example);

    int deleteByExample(SmsCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    List<SmsCode> selectByExample(SmsCodeExample example);

    SmsCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsCode record, @Param("example") SmsCodeExample example);

    int updateByExample(@Param("record") SmsCode record, @Param("example") SmsCodeExample example);

    int updateByPrimaryKeySelective(@Param("record") SmsCode record);

    int updateByPrimaryKey(SmsCode record);
}