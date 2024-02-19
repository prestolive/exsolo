package cn.exsolo.basic.security.vo;

import cn.exsolo.basic.security.po.RolePO;
import cn.exsolo.basic.security.po.RolePermissionPO;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/5/17
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
