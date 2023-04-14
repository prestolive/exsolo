package cn.exsolo.console.web;

import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.item.ExUserStatusEnum;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.service.UserManageService;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import cn.hutool.core.util.EnumUtil;
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
@RequestMapping("api/ex-basic/security/")
@RestController()
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(path = "users", method = RequestMethod.POST)
    public PageObject<UserPO> page(@RequestParam(required = false) String userCode,
                                   @RequestParam(required = false) String userName,
                                   @RequestJSON() String[] status,
                                   @RequestJSON Pagination pagination) {
        List<ExUserStatusEnum> enumStatus = null;
        if(status!=null){
            enumStatus = Arrays.stream(status).map(row->Enum.valueOf(ExUserStatusEnum.class,row)).collect(Collectors.toList());
        }
        return userManageService.page(userCode,userName,enumStatus,pagination);
    }

    @RequestMapping(path = "user-info", method = RequestMethod.POST)
    public void userInfo(@RequestParam() String userId){
        userManageService.get(userId);
    }

    @RequestMapping(path = "user-add", method = RequestMethod.POST)
    public void add(@RequestJSON UserPO userPO,@RequestParam(required = false) String password){
        userManageService.addNewUser(userPO,password);
    }

    @RequestMapping(path = "user-update", method = RequestMethod.POST)
    public void update(@RequestJSON UserPO userPO,@RequestParam(required = false) String password){
        userManageService.updateUser(userPO,password);
    }

    @RequestMapping(path = "user-locked", method = RequestMethod.POST)
    public void userLocked(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.LOCKED);
    }

    @RequestMapping(path = "user-deleted", method = RequestMethod.POST)
    public void userDeleted(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.DELETED);
    }

    @RequestMapping(path = "user-recover", method = RequestMethod.POST)
    public void userRecover(@RequestParam String userId){
        userManageService.updateUserStatus(userId,ExUserStatusEnum.NORMAL);
    }
}
