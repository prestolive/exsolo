package cn.exsolo.basic.security.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/3/30
 **/

@Table(name="ex_role_permission",indexes = @Index(columnList = "roleId,permission",unique = true ))
public class RolePermissionPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "roleId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String roleId;

    @Column(name = "permission",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String permission;

    @Column(name = "operator",length = 24,columnDefinition = "char(24)")
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
