package cn.exsolo.console.web;

import cn.exsolo.auth.shiro.service.RdbcViewService;
import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.user.UserConsoleService;
import cn.exsolo.console.user.vo.UserInfoVO;
import cn.exsolo.kit.picker.IPicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2024/2/18
 **/
@Component
@RequestMapping("api/console/")
@RestController()
public class UserConsoleController {

    @Autowired
    private UserConsoleService userConsoleService;

    @RequestMapping(path = "user/info", method = RequestMethod.POST)
    public UserInfoVO userInfo() {
        String id = SecurityUserContext.getUserID();
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return userConsoleService.getUserInfo(id);
    }

    @RequestMapping(path = "user/change_password", method = RequestMethod.POST)
    public void changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        String id = SecurityUserContext.getUserID();
        userConsoleService.changePassword(id, oldPassword, newPassword);
    }

    @RequestMapping(path = "user/logout", method = RequestMethod.POST)
    public void logout() {
        //FIXME refreshToken的处理
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
    }

    /**
     * 什么事都不用干的心跳检测，前端调用这个请求，如果检测到accesstoken过期前端自动发起重启获取新accesstoken 的请求
     */
    @RequestMapping(path = "echo", method = RequestMethod.POST)
    public void echo() {
       //do nothing
    }
}
