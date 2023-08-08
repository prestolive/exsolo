package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.stereotype.BaseData;

/**
 * @author prestolive
 * @date 2023/3/30
 **/

@Table("ex_role_permission")
@BaseData(name="系统角色权限表")
@Index(name = "uq_ex_role_permission",unique = true,fields = "roleId,permission")
public class RolePermissionPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "roleId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String roleId;

    @Column(name = "permission",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String permission;

    @Column(name = "operator",maxLength = 24,datatype = "char(24)")
    private String operator;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
