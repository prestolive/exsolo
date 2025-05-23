package cn.exsolo.console.user;

import cn.exsolo.auth.passwd.PasswdService;
import cn.exsolo.auth.shiro.service.RdbcViewService;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.console.user.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/2/18
 **/
@Service
public class UserConsoleService {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private PasswdService passwdService;

    @Autowired
    private RdbcViewService rdbcViewService;


    public UserInfoVO getUserInfo(String id){
        String sql = "select id as userId,loginCode,userName,email,phone from ex_user where id=#{id}";
        Map<String,Object> param = new HashMap<>();
        param.put("id",id);
        UserInfoVO info = baseDAO.queryForOneObject(sql,param,UserInfoVO.class);
        if(info!=null){
            info.setPermissions(rdbcViewService.getPermission(id));
        }
        return info;
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    public void changePassword(String userId,String oldPwd,String newPwd){
        passwdService.changePassword(userId,oldPwd,newPwd);
    }
}
