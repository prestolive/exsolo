package cn.exsolo.console.web;

import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.item.ExUserStatusEnum;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.service.UserManageService;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2023/4/1
 **/

@Component
@RequestMapping("api/ex-basic/user/")
@RestController()
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<UserPO> page(
                                   @RequestJSON() String[] status,
                                   @RequestJSON Condition cond,
                                   @RequestJSON Pagination pagination) {
        List<ExUserStatusEnum> enumStatus = null;
        if(status!=null){
            enumStatus = Arrays.stream(status).map(row->Enum.valueOf(ExUserStatusEnum.class,row)).collect(Collectors.toList());
        }
        return userManageService.page(cond,enumStatus,pagination);
    }

    @RequestMapping(path = "info", method = RequestMethod.POST)
    public UserPO userInfo(@RequestParam() String userId){
        return userManageService.get(userId);
    }

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON UserPO userPO,@RequestParam(required = false) String password){
        userManageService.addNewUser(userPO,password);
    }

    @RequestMapping(path = "modify", method = RequestMethod.POST)
    public void update(@RequestJSON UserPO userPO){
        userManageService.modifyUser(userPO);
    }

    @RequestMapping(path = "change-password", method = RequestMethod.POST)
    public void changePassword(@RequestParam String userId,@RequestParam(required = false) String password){
        userManageService.changePassword(userId,password);
    }

    @RequestMapping(path = "locked", method = RequestMethod.POST)
    public void userLocked(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.LOCKED);
    }

    @RequestMapping(path = "deleted", method = RequestMethod.POST)
    public void userDeleted(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.DELETED);
    }

    @RequestMapping(path = "recover", method = RequestMethod.POST)
    public void userRecover(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.NORMAL);
    }
}
