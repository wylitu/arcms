package com.suniusoft.security.biz.service.permission;

import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.biz.dao.generation.permission.UserRoleMapper;
import com.suniusoft.security.biz.domain.generation.permission.Resource;
import com.suniusoft.security.biz.domain.generation.permission.UserRole;
import com.suniusoft.security.biz.domain.generation.permission.UserRoleExample;
import com.suniusoft.security.vo.UserRoleVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/10/31  
 */
@Service
public class UserRoleService {

    private final static Logger logger = Logger.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 给用户分配角色
     *
     * @param userRoleVO
     * @return
     */
    public Boolean saveUserRole(UserRoleVO userRoleVO) {

        AssertsUtil.notNull(userRoleVO.getRoleId(), "roleId");
        AssertsUtil.notNull(userRoleVO.getUserId(), "userId");

        UserRole userRole;
        try {
            userRole = (UserRole) BeanCopierUtils.copyProperties(userRoleVO, UserRole.class);
        } catch (Exception e) {
            throw new ServiceException("SercurityManageService saveUserRole occur error", e);
        }

        return userRoleMapper.insertSelective(userRole) > 0;
    }

    /**
     * 解除用户和角色的绑定
     *
     * @param userRoleVO
     * @return
     */
    public Boolean deleteUserRole(UserRoleVO userRoleVO) {

        AssertsUtil.notNull(userRoleVO, "userRoleVO");

        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();

        if (userRoleVO.getId() != null) {
            criteria.andIdEqualTo(userRoleVO.getId());
        }

        if (userRoleVO.getRoleId() != null) {
            criteria.andRoleIdEqualTo(userRoleVO.getRoleId());
        }

        if (userRoleVO.getUserId() != null) {
            criteria.andUserIdEqualTo(userRoleVO.getUserId());
        }

        return userRoleMapper.deleteByExample(example) > 0;
    }

    /**
     * 删除用户和角色的绑定
     *
     * @param userId
     * @return
     */
    public Boolean deleteByUserId(Long userId) {


        return userRoleMapper.deleteByUserId(userId) > 0;
    }
    /**
     * 查询用户和角色的绑定
     *
     * @param userRoleVO
     * @return
     */
    public List<UserRole> selectUserRole(UserRoleVO userRoleVO) {

        AssertsUtil.notNull(userRoleVO, "userRoleVO");

        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();

        if (userRoleVO.getId() != null) {
            criteria.andIdEqualTo(userRoleVO.getId());
        }

        if (userRoleVO.getRoleId() != null) {
            criteria.andRoleIdEqualTo(userRoleVO.getRoleId());
        }

        if (userRoleVO.getUserId() != null) {
            criteria.andUserIdEqualTo(userRoleVO.getUserId());
        }

        return userRoleMapper.selectByExample(example);
    }
}
