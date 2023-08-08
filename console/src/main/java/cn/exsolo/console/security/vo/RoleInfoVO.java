package cn.exsolo.console.security.vo;

import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.console.security.po.RolePermissionPO;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/5/17
 **/
public class RoleInfoVO {

    private RolePO rolePO;

    private List<RolePermissionPO> permissions;

    public RolePO getRolePO() {
        return rolePO;
    }

    public void setRolePO(RolePO rolePO) {
        this.rolePO = rolePO;
    }

    public List<RolePermissionPO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermissionPO> permissions) {
        this.permissions = permissions;
    }
}
