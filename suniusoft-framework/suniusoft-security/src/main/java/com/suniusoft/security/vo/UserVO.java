package com.suniusoft.security.vo;

import com.suniusoft.common.annotation.Domain;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import lombok.Data;
import lombok.ToString;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/10/27  
 */
@Data
@ToString
@Domain
public class UserVO extends UserDO {

    private String newpasswd1;

    private String newpasswd2;
}
