package com.suniusoft.security.controller.permission;

import com.google.common.collect.Maps;
import com.suniusoft.security.biz.domain.defined.permission.UserDO;
import com.suniusoft.security.biz.domain.generation.permission.Role;
import com.suniusoft.security.biz.domain.generation.permission.SecurityUser;
import com.suniusoft.security.biz.domain.generation.permission.UserRole;
import com.suniusoft.security.biz.service.permission.SecurityManageService;
import com.suniusoft.security.biz.service.permission.UserRoleService;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.controller.BaseController;
import com.suniusoft.security.vo.RoleVO;
import com.suniusoft.security.vo.UserRoleVO;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   
 *  @ProjectName: arcms 
 *  @Description: <p>
 * </p>
 *  @author yuyuchi  yuyc@suniusoft.com
 *  @date 2015/10/31  
 */
@Controller
public class UserController extends BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SecurityManageService securityManageService;



    @RequestMapping(value = "/admin/userManage")
    public ModelAndView index(ModelMap modelMap) {

        /**
         * 查出角色列表
         */
        List<Role> roleList = securityManageService.getRoleList();

        modelMap.put("roleList", roleList);

        return new ModelAndView("permission/userList", modelMap);
    }

    @RequestMapping(value = "/admin/userList")
    public Map<String, Object> showUser(UserVO userVO, Integer pageNum, Integer length) {

        Map<String, Object> dataMap = Maps.newHashMap();


        List<UserDO> userList = securityUserService.queryUser(userVO, pageNum, length);

        Map<Long, UserDO> map = new HashMap<Long, UserDO>();
        for (UserDO userDo : userList) {
            if (userDo.getMemberLevelId()==1){
                userDo.setVipLevel("普通会员");
            }else if(userDo.getMemberLevelId()==2){
                userDo.setVipLevel("VIP会员");
            }else if(userDo.getMemberLevelId()==3){
                userDo.setVipLevel("银卡会员");
            }else if(userDo.getMemberLevelId()==4){
                userDo.setVipLevel("金卡会员");
            }

            if (userDo.getRecommendUserId()!=null){
                UserDO findUser  = securityUserService.findUserByUserId(userDo.getRecommendUserId());
                if (findUser!=null){
                    userDo.setRecommendName(findUser.getUserName());
                }

            }




            if (map.get(userDo.getUserId()) == null) {
                map.put(userDo.getUserId(), userDo);
            } else {
                map.get(userDo.getUserId()).setRoleName(map.get(userDo.getUserId()).getRoleName() + " " + userDo.getRoleName());
            }

        }
        List<UserDO> userDos = new ArrayList<UserDO>();
        List<UserDO> pageUserDos = new ArrayList<UserDO>();

        for (Long id : map.keySet()) {
            userDos.add(map.get(id));
        }

        for (int i = length * (pageNum - 1); i < length * (pageNum - 1)
                + length
                && i < userDos.size(); i++) {
            pageUserDos.add(userDos.get(i));
        }
        dataMap.put("recordsFiltered", userDos.size());
        dataMap.put("recordsTotal", userDos.size());
        dataMap.put("data", pageUserDos);
        return dataMap;
    }

    @RequestMapping(value = "/admin/editUser")
    public Map<String, Object> editUser(UserVO userVO) {

        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        try {
            int code = securityUserService.insertOrUpdateUser(userVO);
            if (code == 0) {
                dataMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
                dataMap.put(ERROR_MESSAGE_KEY, "操作成功");
            } else if (code == -2) {
                dataMap.put(ERROR_MESSAGE_KEY, "对不起，该用户名已存在");
            } else {
                dataMap.put(ERROR_MESSAGE_KEY, "操作失败");
            }
        } catch (Exception e) {
            logger.error("UserController editUser occur error", e);
            dataMap.put(ERROR_MESSAGE_KEY, "操作异常");
        }

        return dataMap;
    }

    @RequestMapping(value = "/admin/getUser")
    public Map<String, Object> getUser(Long id) {

        Map<String, Object> dataMap = Maps.newHashMap();

        SecurityUser user = securityUserService.getUserById(id);

        dataMap.put("user", user);

        return dataMap;
    }

    @RequestMapping(value = "/admin/resetPassword")
    public Map<String, Object> changePass(Long id) {

        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (securityUserService.changePassById(id)) {
            dataMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            dataMap.put(ERROR_MESSAGE_KEY, "密码重置成功");
        }

        return dataMap;
    }


    @RequestMapping(value = "/admin/userRoles")
    public Map<String, Object> showRoles(UserVO userVO) {

        SecurityUser user = securityUserService.getUserById(userVO.getId());
        Map<String, Object> dataMap = Maps.newHashMap();
        UserRoleVO vo = new UserRoleVO();
        vo.setUserId(user.getUserId());
        List<UserRole> roles = userRoleService.selectUserRole(vo);

        List<Long> ids = new ArrayList<Long>();
        List<RoleVO> vos = new ArrayList<RoleVO>();
        for (UserRole ur : roles) {
            ids.add(ur.getRoleId());
        }

        List<Role> roleList = securityManageService.getRoleList();
        for (Role r : roleList) {
            RoleVO rv = new RoleVO();
            rv.setRoleId(r.getRoleId());
            rv.setName(r.getName());
            if (roleList.size() != ids.size()) {
                if (ids.size() > 0 && ids.contains(r.getRoleId())) {
                    rv.setChecked(true);
                } else rv.setChecked(false);
            }

            vos.add(rv);
        }
        dataMap.put("userId", user.getUserId());

        dataMap.put("roles", vos);
        dataMap.put("count", vos.size());
        return dataMap;
    }

    @RequestMapping(value = "/admin/authorize")
    public Map<String, Object> authorize(String roleIds, String userId) {
        Map<String, Object> dataMap = Maps.newHashMap();

        try {
            UserRoleVO v1 = new UserRoleVO();
            v1.setUserId(Long.valueOf(userId));

            List<UserRole> allRoles = userRoleService.selectUserRole(v1);


            userRoleService.deleteByUserId(Long.valueOf(userId));

            for (String roleId : roleIds.split(",")) {
                UserRoleVO vo = new UserRoleVO();
                vo.setUserId(Long.valueOf(userId));
                vo.setRoleId(Long.valueOf(roleId));
                userRoleService.saveUserRole(vo);
            }
            dataMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            dataMap.put(ERROR_MESSAGE_KEY, "操作成功");
        } catch (Exception e) {
            logger.error("UserController editUser occur error", e);
            dataMap.put(ERROR_MESSAGE_KEY, "操作异常");
        }

        return dataMap;
    }


}
