package com.suniusoft.security.vo;

import com.suniusoft.security.biz.domain.generation.permission.Role;
import lombok.Data;

/**
 *   
 *  @ProjectName: icard 
 *  @Description: <p>
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/10/30  
 */
@Data
public class RoleVO extends Role {
    private String resourceIds;
    private  boolean checked;
}
