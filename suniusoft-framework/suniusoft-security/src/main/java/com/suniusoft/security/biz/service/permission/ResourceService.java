package com.suniusoft.security.biz.service.permission;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.biz.dao.generation.permission.ResourceMapper;
import com.suniusoft.security.biz.dao.generation.permission.ResourceRoleMapper;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.Resource;
import com.suniusoft.security.biz.domain.generation.permission.ResourceExample;
import com.suniusoft.security.biz.domain.generation.permission.ResourceRoleExample;
import com.suniusoft.security.vo.ResourceRoleVO;
import com.suniusoft.security.vo.ResourceVO;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class ResourceService {

    private final static Logger logger = Logger.getLogger(ResourceService.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceRoleMapper resourceRoleMapper;

    /**
     * 新增或更新资源记录
     *
     * @param resourceVO
     * @return
     */
    public Boolean insertOrUpdateResource(ResourceVO resourceVO) {

        AssertsUtil.notNull(resourceVO, "resourceVO");

        Resource resource;
        try {
            resource = (Resource) BeanCopierUtils.copyProperties(resourceVO, Resource.class);
        } catch (Exception e) {
            throw new ServiceException("SercurityManageService insertOrUpdateResource occur error", e);
        }

        List<Resource> list = null;
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();

        if (resourceVO.getId() != null || resourceVO.getResourceId() != null) {
            if (resourceVO.getId() != null) {
                criteria.andIdEqualTo(resourceVO.getId());
            }

            if (resourceVO.getResourceId() != null) {
                criteria.andResourceIdEqualTo(resourceVO.getResourceId());
            }

            list = resourceMapper.selectByExample(example);
        }

        if (CollectionUtils.isEmpty(list)) {
            resourceMapper.insertSelective(resource);
        } else {
            resourceMapper.updateByExampleSelective(resource, example);
        }

        return true;
    }

    /**
     * 根据条件查找该资源(该资源可能包括多级子资源)
     *
     * @param resource
     * @return
     */
    public Resource getResourceBySourceId(Resource resource) {

        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();

        if (resource.getResourceId() != null) {
            criteria.andResourceIdEqualTo(resource.getResourceId());
        }

        if (resource.getParentResourceId() != null) {
            criteria.andParentResourceIdEqualTo(resource.getParentResourceId());
        }

        List<Resource> list = resourceMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Resource source = list.get(0);

        List<Resource> childResources = getChildResources(source.getResourceId());

        if (!CollectionUtils.isEmpty(childResources)) {
            resource.setChildResources(childResources);
        }

        return source;
    }

    /**
     * 根据资源id寻找子类资源
     *
     * @return
     */
    public List<Resource> getChildResources(Long resourceId) {

        ResourceExample example = new ResourceExample();
        example.createCriteria().andParentResourceIdEqualTo(resourceId);

        List<Resource> childResources = resourceMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(childResources)) {
            return null;
        }

        for (Resource resource : childResources) {
            /**
             * 递归查找所有子资源
             */
            List<Resource> list = getChildResources(resource.getResourceId());
            if (!CollectionUtils.isEmpty(list)) {
                resource.setChildResources(list);
            }
        }

        return childResources;
    }

    /**
     * 删除资源
     *
     * @param resourceId
     * @return
     */
    public Boolean deleteResource(Long resourceId) {

        AssertsUtil.notNull(resourceId, "resourceId");

        /**
         * 解除所有该资源与角色的绑定
         */
        ResourceRoleVO resourceRoleVO = new ResourceRoleVO();
        resourceRoleVO.setResourceId(resourceId);
        deleteResourceRole(resourceRoleVO);

        ResourceExample example = new ResourceExample();
        example.createCriteria().andResourceIdEqualTo(resourceId);

        /**
         * 删除所有子类资源
         */
        List<Resource> childResources = getChildResources(resourceId);

        if (!CollectionUtils.isEmpty(childResources)) {
            for (Resource resource : childResources) {
                deleteResource(resource.getResourceId());
            }
        }
        resourceMapper.deleteByExample(example);
        return true;
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

    /**
     * 分页查询模块
     *
     * @param resourceVO
     * @param pageNum
     * @param length
     * @return
     */
    public PageInfo queryResource(ResourceVO resourceVO, Integer pageNum, Integer length) {
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria c=   example.createCriteria();
        c.andParentResourceIdEqualTo(resourceVO.getResourceId());
        if (resourceVO.getName() != null&&!"".equals(resourceVO.getName())) {
            c.andNameLike("%" +resourceVO.getName()+ "%");
        }

        PageHelper.startPage(pageNum, length);
        List<Resource> userList = resourceMapper.selectByExample(example);

        return new PageInfo(userList);
    }

    public Resource getResourceById(ResourceVO resourceVO) {

        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();

        if (resourceVO.getResourceId() != null) {
            criteria.andResourceIdEqualTo(resourceVO.getResourceId());
        }



        if (resourceVO.getId() != null) {
            criteria.andIdEqualTo(resourceVO.getId());
        }

        List<Resource> list = resourceMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Resource source = list.get(0);


        return source;
    }
}
