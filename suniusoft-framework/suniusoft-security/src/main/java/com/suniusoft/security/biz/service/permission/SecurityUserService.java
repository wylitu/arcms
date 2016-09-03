package com.suniusoft.security.biz.service.permission;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.exception.UserRemindException;
import com.suniusoft.common.utils.AssertsUtil;
import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.common.utils.Md5Encrypt;
import com.suniusoft.security.biz.dao.defined.permission.UserDAO;
import com.suniusoft.security.biz.dao.generation.permission.SecurityUserMapper;
import com.suniusoft.security.biz.dao.generation.permission.UserRoleMapper;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.User;
import com.suniusoft.security.biz.domain.generation.permission.UserExample;
import com.suniusoft.security.biz.domain.generation.permission.UserRoleExample;
import com.suniusoft.security.vo.UserRoleVO;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 * </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/10/31  
 */
@Service
public class SecurityUserService {

    private final static Logger logger = Logger.getLogger(SecurityUserService.class);

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserDAO userDAO;

    /**
     * 通过UserName查询用户
     *
     * @param userName
     * @return
     */
    public UserVO findUserByUserName(String userName) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        List<User> userVOs = securityUserMapper.selectByExample(userExample);

        UserVO userVO = null;

        if (!CollectionUtils.isEmpty(userVOs)) {
            userVO = (UserVO) BeanCopierUtils.copyProperties(userVOs.get(0), UserVO.class);
        }

        return userVO;
    }

    /**
     * 修改用户密码
     *
     * @param userVO
     * @return
     */
    public boolean updatePasswd(UserVO userVO) {

        UserVO user = findUserByUserName(userVO.getUserName());

        if (user == null) {
            throw new UserRemindException("用户名不存在");
        }

        String oldPasswd = Md5Encrypt.md5(userVO.getPassword());
        if (!oldPasswd.equals(userVO.getPassword())) {
            throw new UserRemindException("原密码错误");
        }

        user.setPassword(user.getNewpasswd1());
        UserVO updateUser = (UserVO) BeanCopierUtils.copyProperties(user, UserVO.class);

        return securityUserMapper.updateByPrimaryKey(updateUser) == 1;

    }

    /**
     * 验证用户名是否存在
     *
     * @param userName
     * @return
     */
    public boolean checkSameUserName(String userName) {

        UserVO user = findUserByUserName(userName);

        if (user != null) {
            return true;
        }

        return false;
    }

    /**
     * 新增或更新用户信息
     *
     * @param userVO
     * @return 0:成功，-1失败，-2新增时重名
     */
    public int insertOrUpdateUser(UserVO userVO) {

        AssertsUtil.notNull(userVO, "userVO");

        User user;
        try {
            user = (User) BeanCopierUtils.copyProperties(userVO, User.class);
        } catch (Exception e) {
            throw new ServiceException("UserService insertOrUpdateUser copyProperties occur error", e);
        }

        if (StringUtils.isNotBlank(userVO.getPassword())) {
            user.setPassword(Md5Encrypt.md5(userVO.getPassword()));
        }

        if (StringUtils.isNotBlank(userVO.getPayPassword())) {
            user.setPayPassword(Md5Encrypt.md5(userVO.getPayPassword()));
        }

        List<User> userList = null;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (userVO.getId() != null || userVO.getUserId() != null) {
            if (userVO.getId() != null) {
                criteria.andIdEqualTo(userVO.getId());
            }

            if (userVO.getUserId() != null) {
                criteria.andUserIdEqualTo(userVO.getUserId());
            }

            userList = securityUserMapper.selectByExample(example);
        }

        if (CollectionUtils.isEmpty(userList)) {
            if (StringUtils.isNotBlank(userVO.getUserName())) {
                criteria.andUserNameEqualTo(userVO.getUserName());
                userList = securityUserMapper.selectByExample(example);

                if (!CollectionUtils.isEmpty(userList)) {
                    /**
                     * 新增用户时，不允许用户名重名
                     */
                    return -2;
                }

                securityUserMapper.insertSelective(user);
                return 0;
            }
        } else {
            securityUserMapper.updateByExampleSelective(user, example);
            return 0;
        }

        //TODO 新增和更新商家时，由于要关联商家扩展表，还需其他处理

        return -1;
    }

    /**
     * 根据用户名查找该用户是否存在
     *
     * @param userVO
     * @return
     */
    public Boolean isExistWithUserName(UserVO userVO) {

        AssertsUtil.notNull(userVO, "userVO");
        AssertsUtil.notNull(userVO.getUserName(), "userName");

        UserExample example = new UserExample();
        example.createCriteria().andUserNameEqualTo(userVO.getUserName());

        List<User> userList = securityUserMapper.selectByExample(example);

        if (org.springframework.util.CollectionUtils.isEmpty(userList)) {
            return false;
        }

        return true;
    }

    /**
     * 分页查询用户
     *
     * @param userVO
     * @param pageNum
     * @param length
     * @return
     */
    public  List<UserDO> queryUser(UserVO userVO, Integer pageNum, Integer length) {

        UserDO userDO = new UserDO();

        if (StringUtils.isNotBlank(userVO.getUserName())) {
            userDO.setUserName(userVO.getUserName());
        }

        if (StringUtils.isNotBlank(userVO.getMobile())) {
            userDO.setMobile(userVO.getMobile());
        }

        if (userVO.getRecommendUserId() != null && userVO.getRecommendUserId() > 0) {
            userDO.setRecommendUserId(userVO.getRecommendUserId());
        }

        if (StringUtils.isNotBlank(userVO.getEmail())) {
            userDO.setEmail(userVO.getEmail());
        }

        if (StringUtils.isNotBlank(userVO.getProvince())) {
            userDO.setProvince(userVO.getProvince());
        }

        if (StringUtils.isNotBlank(userVO.getCity())) {
            userDO.setCity(userVO.getCity());
        }

        if (StringUtils.isNotBlank(userVO.getDistrict())) {
            userDO.setDistrict(userVO.getDistrict());
        }

        if (userVO.getRoleId() != null) {
            userDO.setRoleId(userVO.getRoleId());
        }

        List<UserDO> userList = userDAO.selectUserList(userDO);



        return userList;
    }

    /**
     * 根据userId删除该用户(逻辑删除)
     *
     * @param userId
     * @return
     */
    public Boolean deleteUser(Long userId) {

        AssertsUtil.notNull(userId, "userId");

        /**
         * 解除所有该用户与角色的绑定
         */
        UserRoleVO userRoleVO = new UserRoleVO();
        userRoleVO.setUserId(userId);
        deleteUserRole(userRoleVO);

        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return securityUserMapper.deleteByExample(example) > 0;
    }

    /**
     * 根据id或userId或username查找用户
     *
     * @param userVO
     * @return
     */
    public User querySingleUser(UserVO userVO) {

        AssertsUtil.notNull(userVO, "userVO");

        List<User> userList = null;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (userVO.getId() != null || userVO.getUserId() != null || StringUtils.isNotBlank(userVO.getUserName())) {
            if (userVO.getId() != null) {
                criteria.andIdEqualTo(userVO.getId());
            }

            if (userVO.getUserId() != null) {
                criteria.andUserIdEqualTo(userVO.getUserId());
            }

            if (StringUtils.isNotBlank(userVO.getUserName())) {
                criteria.andUserNameEqualTo(userVO.getUserName());
            }

            userList = securityUserMapper.selectByExample(example);
        }

        if (org.springframework.util.CollectionUtils.isEmpty(userList)) {
            return null;
        }

        return userList.get(0);
    }

    public User getUserById(Long id) {

        AssertsUtil.notNull(id, "id");

        return securityUserMapper.selectByPrimaryKey(id);
    }

    public Boolean changePassById(Long id) {

        AssertsUtil.notNull(id, "id");

        User user = new User();
        user.setPassword(Md5Encrypt.md5("123456"));
        user.setId(id);

        return securityUserMapper.updateByPrimaryKeySelective(user) > 0;
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
}
