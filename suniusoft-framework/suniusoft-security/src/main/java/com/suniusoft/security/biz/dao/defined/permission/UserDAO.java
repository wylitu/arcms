package com.suniusoft.security.biz.dao.defined.permission;

import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *   
 *  @ProjectName: icard 
 *  @Description: <p>
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/11/2  
 */

@Repository
public interface UserDAO {

    List<UserDO> selectUserList(UserDO userDO);

}
