package com.suniusoft.security.vo;

import com.suniusoft.security.biz.domain.generation.permission.Resource;
import lombok.Data;

/**
 *   
 *  @ProjectName: arcms 
 *  @Description: <p>
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/10/30  
 */
@Data

public class ResourceVO extends Resource {

    private long pid;
    private boolean open;

    public boolean checked ;
   // private String  isParent;

}
