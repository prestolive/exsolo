package cn.exsolo.basic.init;

import cn.exsolo.auth.passwd.utils.PasswordHelper;
import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.utils.TsUtil;
import cn.exsolo.basic.security.po.RolePO;
import cn.exsolo.basic.security.po.UserPO;
import cn.exsolo.basic.security.service.PermissionAnnotationService;
import cn.exsolo.basic.security.service.RoleManageService;
import cn.exsolo.basic.security.service.UserManageService;
import cn.exsolo.basic.security.vo.PermissionVO;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 系统初始化
 *
 * @author prestolive
 * @date 2022/1/20
 **/
@Service
public class ApplicationConsoleInitService {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private RoleManageService roleManageService;

    @Autowired
    private PermissionAnnotationService permissionAnnotationService;


    public void init() {
        //系统用户
        UserPO admin = baseDAO.queryOneBeanByCond(UserPO.class, new Condition().eq("loginCode", "admin"));
        if (admin == null) {
            admin = new UserPO();
            admin.setUserName("系统初始化管理员");
            admin.setLoginCode("admin");
            admin.setStatus(ItemCommStatusEnum.NORMAL);
            admin.setActiveTs(TsUtil.getTimestamp());
            String password = GenerateID.generateShortUuid().toLowerCase(Locale.ROOT);
            System.out.println(String.format("---默认用户：admin"));
            System.out.println(String.format("---默认密码：%s",password));
            password = PasswordHelper.passwordEncrypt(password,"#@=yxy=@#",5);
            userManageService.addNewUser(admin, password);
        }
        String default_role_name = "ADMIN_DEFAULT";
        RolePO role = baseDAO.queryOneBeanByCond(RolePO.class, new Condition().eq("roleName", default_role_name));
        if (role == null) {
            role = new RolePO();
            role.setRoleName(default_role_name);
            role.setRoleSchema("ADMIN");
            roleManageService.addRole(role);
            List<PermissionVO> permissionVOS = permissionAnnotationService.permissionAll();
            String[] arr = permissionVOS.stream().map(item -> item.getPermission()).collect(Collectors.toList()).toArray(new String[permissionVOS.size()]);
            roleManageService.permissionSet(role.getId(), arr);
            roleManageService.userSet(role.getId(),new String[]{admin.getId()});
        }
    }

    public static void main(String[] args) {
        String password="io19f6ne";
        String md5 = PasswordHelper.passwordEncrypt(password,"#@=yxy=@#",5);
        System.out.println(md5);
    }
}
