package com.suniusoft.security.biz.service.sms;

import com.suniusoft.common.utils.StringUtils;
import com.suniusoft.security.biz.dao.generation.sms.SmsCodeMapper;
import com.suniusoft.security.biz.domain.generation.sms.SmsCode;
import com.suniusoft.security.biz.domain.generation.sms.SmsCodeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description:
 *  @author litu  litu@suniusoft.com
 *  @date 15/12/17  
 */
@Service
public class SmsService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    /**
     * 验证码保存
     * @param smsCode
     */
    public boolean saveSmsCode(SmsCode smsCode){

        smsCodeMapper.insert(smsCode);

        return true;
    }

    /**
     * 验证短信验证码
     * @param mobile
     * @param smsCode
     * @return
     */
    public boolean validateCode(String mobile, String smsCode){

        if(!StringUtils.isMobile(mobile) || StringUtils.isBlank(smsCode)){

            return false;
        }

        SmsCodeExample smsCodeExample = new SmsCodeExample();
        smsCodeExample.createCriteria().andCodeEqualTo(smsCode)
                .andMobileEqualTo(mobile).andGmtExpiredGreaterThan(new Date());

        List<SmsCode> smsCodeList = smsCodeMapper.selectByExample(smsCodeExample);

        if(CollectionUtils.isEmpty(smsCodeList)){
            return false;
        }

        return true;

    }
}
