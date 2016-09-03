package com.suniusoft.security.biz.service.repeatSubmit;


import com.suniusoft.security.vo.SysTokenVO;
import org.springframework.stereotype.Service;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                  systoken服务类
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/9/9  
 */

public interface RepeatSubmitTokenService {

    public boolean createToken(SysTokenVO sysTokenVO);

    public boolean validateToken(String token);
}
