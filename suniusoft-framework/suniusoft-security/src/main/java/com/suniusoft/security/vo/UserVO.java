package com.suniusoft.security.vo;

import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.User;
import lombok.Data;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/10/27  
 */
@Data
public class UserVO extends UserDO {

    private String newpasswd1;

    private String newpasswd2;
}
