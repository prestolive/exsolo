package cn.exsolo.console.web;

import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.item.ExUserStatusEnum;
import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.service.RoleManageService;
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
public class RoleManageController {

    @Autowired
    private RoleManageService roleManageService;

    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<RolePO> page(
                                   @RequestJSON Condition cond,
                                   @RequestJSON Pagination pagination) {
        return roleManageService.page(cond,pagination);
    }

    @RequestMapping(path = "info", method = RequestMethod.POST)
    public RoleInfoVO info(@RequestParam() String roleId){
        RoleInfoVO vo = new RoleInfoVO();
        vo.setRolePO(roleManageService.get(roleId));
        vo.setPowers(roleManageService.getRolePowers(roleId));
        return vo;
    }

    @RequestMapping(path = "user-page", method = RequestMethod.POST)
    public PageObject<UserPO> userPage(
            @RequestParam String roleId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return roleManageService.getUserByRole(roleId,cond,pagination);
    }

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON RolePO role){
        roleManageService.addRole(role);
    }

    @RequestMapping(path = "modify", method = RequestMethod.POST)
    public void modify(String roleId,String roleName){
        roleManageService.modifyRoleName(roleId,roleName);
    }

    @RequestMapping(path = "config-power", method = RequestMethod.POST)
    public void updateRoleName(String roleId, List<String> powers){
        roleManageService.configRolePower(roleId,powers);
    }

    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public void deleted(@RequestParam String roleId){
        roleManageService.deleteRole(roleId);
    }

}
