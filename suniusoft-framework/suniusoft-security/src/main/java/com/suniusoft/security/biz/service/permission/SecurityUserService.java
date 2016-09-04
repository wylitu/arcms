package com.suniusoft.security.biz.service.permission;

import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.exception.UserRemindException;
import com.suniusoft.common.utils.*;
import com.suniusoft.security.biz.dao.defined.permission.UserDAO;
import com.suniusoft.security.biz.dao.generation.permission.SecurityUserMapper;
import com.suniusoft.security.biz.dao.generation.permission.UserRoleMapper;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.SecurityUser;
import com.suniusoft.security.biz.domain.generation.permission.SecurityUserExample;
import com.suniusoft.security.biz.domain.generation.permission.UserRoleExample;
import com.suniusoft.security.biz.service.wx.WXService;
import com.suniusoft.security.vo.UserRoleVO;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public static WXService wXService = (WXService) SpringContextUtil.getBean("wXService");

    /**
     * 通过UserNo查询用户
     *
     * @param userNo
     * @return
     */
    public UserVO findUserByUserNo(String userNo) {

        SecurityUserExample securityUserExample = new SecurityUserExample();
        securityUserExample.createCriteria().andUserNoEqualTo(userNo).andEnabledEqualTo(true);
        List<SecurityUser> userVOs = securityUserMapper.selectByExample(securityUserExample);

        UserVO userVO = null;

        if (!CollectionUtils.isEmpty(userVOs)) {
            userVO = (UserVO) BeanCopierUtils.copyProperties(userVOs.get(0), UserVO.class);
        }

        return userVO;
    }



    /**
     * 通过UserNo查询用户
     *
     * @param wxOpenId
     * @return
     */
    public UserVO findUserByWxOpenId(String wxOpenId) {

        SecurityUserExample securityUserExample = new SecurityUserExample();
        securityUserExample.createCriteria().andWxOpenidEqualTo(wxOpenId).andEnabledEqualTo(true);
        List<SecurityUser> userVOs = securityUserMapper.selectByExample(securityUserExample);

        UserVO userVO = null;

        if (!CollectionUtils.isEmpty(userVOs)) {
            userVO = (UserVO) BeanCopierUtils.copyProperties(userVOs.get(0), UserVO.class);
        }

        return userVO;
    }


    /**
     * 根据微信授权创建用户信息
     *
     * @param openId
     * @param accessToken
     */
    public UserVO createAndReturnWXUserToUser(String openId, String accessToken) {


        UserVO userVO;
        try {

            userVO = wXService.getOuathUserInfo(openId, accessToken);

            if (userVO != null) {
                saveUserInfo(userVO);
            }

        } catch (Exception e) {

            logger.error("createAndReturnWXUserToUser error ", e);
            throw new ServiceException("createAndReturnWXUserToUser error", e);

        }

        return userVO;
    }

    /**
     * 保存用户信息
     * @param userVO
     * @return
     */
    public boolean saveUserInfo(UserVO userVO) {

        AssertsUtil.notNull(userVO, "useVO is null");

        SecurityUser user = (SecurityUser)BeanCopierUtils.copyProperties(userVO , SecurityUser.class);

        SecurityUserExample securityUserExample = new SecurityUserExample();
        SecurityUserExample.Criteria criteria = securityUserExample.createCriteria();

        if(StringUtils.isNotBlank(userVO.getWxOpenid())){
            criteria.andWxOpenidEqualTo(userVO.getWxOpenid());
        }

        if(StringUtils.isNotBlank(userVO.getMobile())){
            criteria.andMobileEqualTo(userVO.getMobile());
        }

        List<SecurityUser> users = securityUserMapper.selectByExample(securityUserExample);

        if(CollectionUtils.isEmpty(users)){
            securityUserMapper.insertSelective(user);
            userVO.setUserId(user.getUserId());
        }else{
            userVO.setUserId(users.get(0).getUserId());
            user.setId(users.get(0).getId());
            securityUserMapper.updateByExampleSelective(user, securityUserExample);
        }

        return true;

    }

    /**
     * 通过UserId查询用户
     *
     * @param userId
     * @return
     */
    public UserVO findUserByUserId(Long userId) {

        SecurityUserExample securityUserExample = new SecurityUserExample();
        securityUserExample.createCriteria().andUserIdEqualTo(userId);
        List<SecurityUser> userVOs = securityUserMapper.selectByExample(securityUserExample);

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

        UserVO user = findUserByUserNo(userVO.getUserNo());

        if (user == null) {
            throw new UserRemindException("用户名不存在");
        }

        String oldPasswd = userVO.getPassword();
        if (!oldPasswd.equals(user.getPassword())) {
            throw new UserRemindException("原密码错误");
        }

        user.setPassword(userVO.getNewpasswd1());
        UserVO updateUser = (UserVO) BeanCopierUtils.copyProperties(user, UserVO.class);

        return securityUserMapper.updateByPrimaryKey(updateUser) == 1;

    }


    /**
     * 重置用户密码
     *
     * @param userVO
     * @return
     */
    public boolean updateUserByMobile(UserVO userVO) {

        if (userVO == null) {
            throw new UserRemindException("用户不能为空");
        }

        if (!StringUtils.isMobile(userVO.getMobile())) {
            throw new UserRemindException("手机号码不能正确!");
        }

        UserVO user = findUserByMobile(userVO.getMobile());

        if(user==null){
            throw new UserRemindException("该手机号码对应的用户不存在!");
        }

        userVO.setId(user.getId());

        return securityUserMapper.updateByPrimaryKeySelective(userVO) == 1;

    }


    /**
     * 通过mobile查询用户
     *
     * @param mobile
     * @return
     */
    public UserVO findUserByMobile(String mobile) {

        SecurityUserExample securityUserExample = new SecurityUserExample();
        securityUserExample.createCriteria().andMobileEqualTo(mobile).andEnabledEqualTo(true);
        List<SecurityUser> userVOs = securityUserMapper.selectByExample(securityUserExample);

        UserVO userVO = null;

        if (!CollectionUtils.isEmpty(userVOs)) {
            userVO = (UserVO) BeanCopierUtils.copyProperties(userVOs.get(0), UserVO.class);
        }

        return userVO;
    }
    /**
     * 验证用户名是否存在
     *
     * @param userNo
     * @return
     */
    public boolean checkSameUserNo(String userNo) {

        UserVO user = findUserByUserNo(userNo);

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

        SecurityUser user;
        try {
            user = (SecurityUser) BeanCopierUtils.copyProperties(userVO, SecurityUser.class);
        } catch (Exception e) {
            throw new ServiceException("UserService insertOrUpdateUser copyProperties occur error", e);
        }

        if (StringUtils.isNotBlank(userVO.getPassword())) {
            user.setPassword(Md5Encrypt.md5(userVO.getPassword()));
        }

        List<SecurityUser> userList = null;
        SecurityUserExample example = new SecurityUserExample();
        SecurityUserExample.Criteria criteria = example.createCriteria();

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
            if (StringUtils.isNotBlank(userVO.getUserNo())) {
                criteria.andUserNoEqualTo(userVO.getUserNo());
                userList = securityUserMapper.selectByExample(example);

                if (!CollectionUtils.isEmpty(userList)) {
                    /**
                     * 新增用户时，不允许用户名重名
                     */
                    return -2;
                }

                securityUserMapper.insertSelective(user);
                userVO.setUserId(user.getUserId());
                return 0;
            }
        } else {
            securityUserMapper.updateByExampleSelective(user, example);
            userVO.setUserId(user.getUserId());
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
    public Boolean isExistWithUserNo(UserVO userVO) {

        AssertsUtil.notNull(userVO, "userVO");
        AssertsUtil.notNull(userVO.getUserNo(), "userNo");

        SecurityUserExample example = new SecurityUserExample();
        example.createCriteria().andUserNoEqualTo(userVO.getUserNo());

        List<SecurityUser> userList = securityUserMapper.selectByExample(example);

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

        if (StringUtils.isNotBlank(userVO.getUserNo())) {
            userDO.setUserNo(userVO.getUserNo());
        }

        if (StringUtils.isNotBlank(userVO.getMobile())) {
            userDO.setMobile(userVO.getMobile());
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

        SecurityUserExample example = new SecurityUserExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return securityUserMapper.deleteByExample(example) > 0;
    }

    /**
     * 根据id或userId或userNo查找用户
     *
     * @param userVO
     * @return
     */
    public SecurityUser querySingleUser(UserVO userVO) {

        AssertsUtil.notNull(userVO, "userVO");

        List<SecurityUser> userList = null;
        SecurityUserExample example = new SecurityUserExample();
        SecurityUserExample.Criteria criteria = example.createCriteria();

        if (userVO.getId() != null || userVO.getUserId() != null || StringUtils.isNotBlank(userVO.getUserNo())) {
            if (userVO.getId() != null) {
                criteria.andIdEqualTo(userVO.getId());
            }

            if (userVO.getUserId() != null) {
                criteria.andUserIdEqualTo(userVO.getUserId());
            }

            if (StringUtils.isNotBlank(userVO.getUserNo())) {
                criteria.andUserNoEqualTo(userVO.getUserNo());
            }

            userList = securityUserMapper.selectByExample(example);
        }

        if (org.springframework.util.CollectionUtils.isEmpty(userList)) {
            return null;
        }

        return userList.get(0);
    }

    public SecurityUser getUserById(Long id) {

        AssertsUtil.notNull(id, "id");

        return securityUserMapper.selectByPrimaryKey(id);
    }

    public Boolean changePassById(Long id) {

        AssertsUtil.notNull(id, "id");

        SecurityUser user = new SecurityUser();
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
