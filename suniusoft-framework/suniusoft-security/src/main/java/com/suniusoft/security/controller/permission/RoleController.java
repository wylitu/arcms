package com.suniusoft.security.controller.permission;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.suniusoft.common.utils.JSONUtils;
import com.suniusoft.security.biz.domain.generation.permission.Resource;
import com.suniusoft.security.biz.domain.generation.permission.Role;
import com.suniusoft.security.biz.service.permission.ResourceService;
import com.suniusoft.security.biz.service.permission.SecurityManageService;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.biz.service.permission.UserRoleService;
import com.suniusoft.security.controller.BaseController;
import com.suniusoft.security.vo.ResourceRoleVO;
import com.suniusoft.security.vo.ResourceVO;
import com.suniusoft.security.vo.RoleVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
public class RoleController extends BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SecurityManageService securityManageService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/admin/roleManage")
    public ModelAndView index(ModelMap modelMap) {

        /**
         * 查出角色列表
         */


        return new ModelAndView("permission/roleList", modelMap);
    }

    @RequestMapping(value = "/admin/roleList")
    public Map<String, Object> showRole(RoleVO roleVO, Integer pageNum, Integer length) {

        Map<String, Object> dataMap = Maps.newHashMap();

        PageInfo pageInfo = securityManageService.queryRole(roleVO, pageNum, length);

        if (pageInfo != null) {
            dataMap.put("recordsFiltered", pageInfo.getTotal());
            dataMap.put("recordsTotal", pageInfo.getTotal());
            dataMap.put("data", pageInfo.getList());
        }

        return dataMap;
    }

    @RequestMapping(value = "/admin/editRole")
    public Map<String, Object> ediRole(RoleVO roleVO) {

        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        try {


            long roleId = securityManageService.insertOrUpdateRole(roleVO);
            ResourceRoleVO vo = new ResourceRoleVO();

            vo.setRoleId(roleId);

            securityManageService.deleteResourceRole(vo);
            for (String s : roleVO.getResourceIds().split(",")) {
                ResourceRoleVO v = new ResourceRoleVO();
                v.setResourceId(Long.valueOf(s));
                v.setRoleId(roleId);
                securityManageService.saveResourceRole(v);
            }
            dataMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            dataMap.put(ERROR_MESSAGE_KEY, "操作成功");


        } catch (Exception e) {
            logger.error("UserController editUser occur error", e);
            dataMap.put(ERROR_MESSAGE_KEY, "操作异常");
        }

        return dataMap;
    }

    @RequestMapping(value = "/admin/getRole")
    public Map<String, Object> getUser(Long id) {

        Map<String, Object> dataMap = Maps.newHashMap();

        Role role = securityManageService.getRoleByRoleId(id);

        dataMap.put("role", role);

        return dataMap;
    }

    @RequestMapping(value = "/admin/delRole")
    public Map<String, Object> changePass(Long id) {

        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (securityManageService.deleteRoleByRoleId(id)) {
            dataMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            dataMap.put(ERROR_MESSAGE_KEY, "删除成功");
        }

        return dataMap;
    }


    @RequestMapping(value = "/admin/roleResourceList")
    public void showTree(HttpServletRequest reqeust,
                         HttpServletResponse response) throws IOException {

        Map<String, Object> dataMap = Maps.newHashMap();

        response.setCharacterEncoding("utf-8");
        String id = reqeust.getParameter("resourceId");
        String id1 = reqeust.getParameter("roleId");

        String s = "";
        List<Resource> list = new ArrayList<Resource>();

        List<Resource> list2 = new ArrayList<Resource>();

        if (id == null) {

            list = resourceService.getChildResources(0l);


        } else {

            list = resourceService.getChildResources(Long.valueOf(id));


        }
        List<ResourceVO> list1 = new ArrayList<ResourceVO>();
        List<Long> roleIds = new ArrayList<Long>();
        if (id1 != null)
            roleIds = securityManageService.selectResourceByRole(Long.valueOf(id1));
        for (Resource r : list) {

            long isd = r.getId();
            long pid = r.getParentResourceId();
            String name = r.getName();
            ResourceVO res = new ResourceVO();
            res.setId(isd);
            res.setName(name);
            res.setPid(pid);
            res.setIsParent(r.getIsParent());
            res.setOpen(r.getIsParent());
            if (roleIds.contains(r.getResourceId()))
                res.setChecked(true);
            res.setResourceId(r.getResourceId());
            list1.add(res);
        }
        s = JSONUtils.toJSONStr(list1);
        response.getWriter().print(s);


    }

}
