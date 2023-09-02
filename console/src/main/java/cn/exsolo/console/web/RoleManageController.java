package cn.exsolo.console.web;

import cn.exsolo.auth.shiro.ext.stereotype.*;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.service.PermissionAnnotationService;
import cn.exsolo.console.security.service.RoleManageService;
import cn.exsolo.console.security.vo.PermissionVO;
import cn.exsolo.console.security.vo.RoleInfoVO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/4/1
 **/

@Component
@RequestMapping("api/ex-basic/role/")
@RestController()
@AccessProvider(module = "sys",node = "role",label = "系统-角色管理")
public class RoleManageController {

    @Autowired
    private RoleManageService roleManageService;

    @Autowired
    private PermissionAnnotationService permissionAnnotationService;

    @AccessCommon
    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<RolePO> page(
                                   @RequestJSON Condition cond,
                                   @RequestJSON Pagination pagination) {
        return roleManageService.page(cond,pagination);
    }

    @AccessView
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public RoleInfoVO info(@RequestParam() String roleId){
        RoleInfoVO vo = new RoleInfoVO();
        vo.setRolePO(roleManageService.get(roleId));
        vo.setPermissions(roleManageService.getRolePowers(roleId));
        return vo;
    }

    @AccessView
    @RequestMapping(path = "user-page", method = RequestMethod.POST)
    public PageObject<UserPO> userPage(
            @RequestParam String roleId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return roleManageService.getUserByRole(roleId,cond,pagination);
    }

    @AccessEdit
    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON RolePO role){
        roleManageService.addRole(role);
    }

    @AccessEdit
    @RequestMapping(path = "modify", method = RequestMethod.POST)
    public void modify(String roleId,String roleName){
        roleManageService.modifyRoleName(roleId,roleName);
    }

    @AccessView
    @RequestMapping(path = "permission-all", method = RequestMethod.POST)
    public List<PermissionVO> getAllPermission(){
        return permissionAnnotationService.getPermissions();
    }

    @AccessConfig
    @RequestMapping(path = "permission-set", method = RequestMethod.POST)
    public void setPermission(@RequestParam String roleId, @RequestJSON String[] permissions){
        roleManageService.configRolePermission(roleId,permissions);
    }

    @AccessEdit
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public void deleted(@RequestParam String roleId){
        roleManageService.deleteRole(roleId);
    }

}
