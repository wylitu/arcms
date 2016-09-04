package com.suniusoft.security.biz.service.token;


import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.biz.dao.generation.system.SysTokenMapper;
import com.suniusoft.security.biz.domain.generation.system.SysToken;
import com.suniusoft.security.biz.domain.generation.system.SysTokenExample;
import com.suniusoft.security.vo.SysTokenVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                    systoken服务类
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/9/9  
 */
@Service
public class SysTokenService{

    @Autowired
    private SysTokenMapper sysTokenMapper;

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
    public boolean isRepeatSubmit(String token) {

        if (isTokenExpired(token)) {
            return false;
        }

        return deleteSysToken(token);

    }


    public boolean deleteSysToken(String token){

        if(StringUtils.isBlank(token)){
            return false;
        }

        SysTokenExample sysTokenExample = new SysTokenExample();
        sysTokenExample.createCriteria().andTokenEqualTo(token).andIsDeletedEqualTo(false);

        return sysTokenMapper.deleteByExample(sysTokenExample) == 1;

    }

    public SysTokenVO findSysToken(String token){

        SysTokenExample sysTokenExample = new SysTokenExample();
        sysTokenExample.createCriteria().andTokenEqualTo(token).andIsDeletedEqualTo(false);

        List<SysToken> sysTokens =  sysTokenMapper.selectByExample(sysTokenExample);

        if(CollectionUtils.isEmpty(sysTokens)){
            return null;
        }

        return (SysTokenVO)BeanCopierUtils.copyProperties(sysTokens.get(0), SysTokenVO.class);

    }



    /**
     *
     * 判断是否token失效
     *
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {

        if (StringUtils.isBlank(token)) {
            return true;
        }

        SysTokenExample sysTokenExample = new SysTokenExample();
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
