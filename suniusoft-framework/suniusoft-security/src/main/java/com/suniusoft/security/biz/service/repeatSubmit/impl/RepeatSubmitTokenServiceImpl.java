package com.suniusoft.security.biz.service.repeatSubmit.impl;


import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.biz.dao.generation.system.SysTokenMapper;
import com.suniusoft.security.biz.domain.generation.system.SysToken;
import com.suniusoft.security.biz.domain.generation.system.SysTokenExample;
import com.suniusoft.security.biz.service.repeatSubmit.RepeatSubmitTokenService;
import com.suniusoft.security.vo.SysTokenVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/9/9  
 */
@Service(value = "repeatSubmitTokenService")
public class RepeatSubmitTokenServiceImpl implements RepeatSubmitTokenService {

    @Autowired
    private SysTokenMapper sysTokenMapper;

    @Override
    public boolean createToken(SysTokenVO sysTokenVO) {

        SysToken sysToken = new SysToken();

        BeanCopierUtils.copyProperties(sysTokenVO, sysToken);
        sysTokenMapper.insert(sysToken);

        return true;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @Override
    @Transactional
    public boolean validateToken(String token) {

        if (isRepeatSubmit(token)) {
            return false;
        }

        /**
         * 验证成功删除token
         */
        SysTokenExample sysTokenExample = new SysTokenExample();
        sysTokenExample.createCriteria().andTokenEqualTo(token);
        sysTokenMapper.deleteByExample(sysTokenExample);

        return true;

    }

    /**
     * 判断表单是否重复提交
     *
     * @param token
     * @return
     */
    private boolean isRepeatSubmit(String token) {

        if (StringUtils.isBlank(token)) {
            return true;
        }

        SysTokenExample sysTokenExample = new SysTokenExample();
        sysTokenExample.setForUpdate(true);
        sysTokenExample.createCriteria().andTokenEqualTo(token);
        List<SysToken> sysTokens = sysTokenMapper.selectByExample(sysTokenExample);

        if(CollectionUtils.isEmpty(sysTokens)){
            return true;
        }

        if (sysTokens.get(0).getGmtExpired().before(new Date())) {
            return true;
        }

        return false;
    }
}
