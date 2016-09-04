package com.suniusoft.security.biz.service.permission;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.biz.dao.generation.permission.ResourceRoleMapper;
import com.suniusoft.security.biz.dao.generation.permission.RoleMapper;
import com.suniusoft.security.biz.dao.generation.permission.UserRoleMapper;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.*;
import com.suniusoft.security.vo.ResourceRoleVO;
import com.suniusoft.security.vo.RoleVO;
import com.suniusoft.security.vo.UserRoleVO;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 * 权限管理服务类
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/10/30  
 */
@Service
public class SecurityManageService {

    private final static Logger logger = Logger.getLogger(SecurityManageService.class);

    @Autowired
    private ResourceRoleMapper resourceRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * 新增或更新角色信息
     *
     * @param roleVO
     * @return
     */
    public Long insertOrUpdateRole(RoleVO roleVO) {

        AssertsUtil.notNull(roleVO, "roleVO");

        Role role;
        try {
            role = (Role) BeanCopierUtils.copyProperties(roleVO, Role.class);
        } catch (Exception e) {
            throw new ServiceException("SercurityManageService insertOrUpdateRole occur error", e);
        }

        List<Role> list = null;
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();

        if (roleVO.getId() != null || roleVO.getRoleId() != null) {
            if (roleVO.getId() != null) {
                criteria.andIdEqualTo(roleVO.getId());
            }
            if (roleVO.getRoleId() != null) {
                criteria.andRoleIdEqualTo(roleVO.getRoleId());
            }

            list = roleMapper.selectByExample(example);
        }

        if (CollectionUtils.isEmpty(list)) {

            roleMapper.insertSelective(role);
        } else {
            roleMapper.updateByExampleSelective(role, example);
        }
        return role.getRoleId();
    }

    /**
     * 根据角色id获取角色信息
     *
     * @param id
     * @return
     */
    public Role getRoleById(Long id) {

        AssertsUtil.notNull(id, "id");

        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据角色id获取角色信息
     *
     * @param id
     * @return
     */
    public Role getRoleByRoleId(Long id) {

        AssertsUtil.notNull(id, "id");
        RoleExample example = new RoleExample();
        example.createCriteria().andRoleIdEqualTo(id);
        List<Role> list = roleMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取所有的角色信息
     *
     * @return
     */
    public List<Role> getRoleList() {
        RoleExample example = new RoleExample();

        List<Role> list = roleMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list;
    }

    /**
     * 根据角色id删除角色并解绑资源
     *
     * @param roleId
     * @return
     */
    public Boolean deleteRoleByRoleId(Long roleId) {

        /**
         * 解除所有该角色与资源的绑定
         */
        ResourceRoleVO resourceRoleVO = new ResourceRoleVO();
        resourceRoleVO.setRoleId(roleId);
        deleteResourceRole(resourceRoleVO);

        /**
         * 解除所有该角色与用户的绑定
         */
        UserRoleVO userRoleVO = new UserRoleVO();
        userRoleVO.setRoleId(roleId);
        deleteUserRole(userRoleVO);

        /**
         * 删除该角色
         */
        RoleExample example = new RoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        return roleMapper.deleteByExample(example) > 0;
    }

    /**
     * 给角色绑定资源
     */
    public Boolean saveResourceRole(ResourceRoleVO resourceRoleVO) {

        AssertsUtil.notNull(resourceRoleVO.getRoleId(), "roleId");
        AssertsUtil.notNull(resourceRoleVO.getResourceId(), "resourceId");

        ResourceRole resourceRole;
        try {
            resourceRole = (ResourceRole) BeanCopierUtils.copyProperties(resourceRoleVO, ResourceRole.class);
        } catch (Exception e) {
            throw new ServiceException("SercurityManageService saveResourceRole occur error", e);
        }

        return resourceRoleMapper.insertSelective(resourceRole) > 0;
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
     * 解除角色资源绑定
     *
     * @return
     */
    public Boolean deleteResourceRole(ResourceRoleVO resourceRoleVO) {

        AssertsUtil.notNull(resourceRoleVO, "resourceRoleVO");

        ResourceRoleExample example = new ResourceRoleExample();
        ResourceRoleExample.Criteria criteria = example.createCriteria();

        if (resourceRoleVO.getResourceId() != null) {
            criteria.andResourceIdEqualTo(resourceRoleVO.getResourceId());
        }

        if (resourceRoleVO.getRoleId() != null) {
            criteria.andRoleIdEqualTo(resourceRoleVO.getRoleId());
        }

        if (resourceRoleVO.getId() != null) {
            criteria.andIdEqualTo(resourceRoleVO.getId());
        }

        return resourceRoleMapper.deleteByExample(example) > 0;
    }

    public PageInfo queryRole(RoleVO roleVO, Integer pageNum, Integer length) {

        RoleExample example = new RoleExample();
        RoleExample.Criteria c = example.createCriteria();
        if (roleVO.getName() != null && !"".equals(roleVO.getName())) {
            c.andNameLike("%" +roleVO.getName()+ "%");

        }


        PageHelper.startPage(pageNum, length);
        List<Role> roleList = roleMapper.selectByExample(example);

        return new PageInfo(roleList);
    }

    public List<Long> selectResourceByRole(long roleId) {
        ResourceRoleExample example = new ResourceRoleExample();
        ResourceRoleExample.Criteria c = example.createCriteria();
        c.andRoleIdEqualTo(roleId);
        c.andIsDeletedEqualTo(0);
        List<ResourceRole> list = resourceRoleMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            List<Long> ids = new ArrayList<Long>();
            for (ResourceRole rr : list)
                ids.add(rr.getResourceId());
            return ids;
        }
        return new ArrayList<Long>();
    }
}
