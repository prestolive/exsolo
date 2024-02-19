package cn.exsolo.basic.security.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/9/3
 **/

@Table(name="ex_user_flat_permission")
public class UserFlatPermissionPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "userId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String userId;

    @Column(name = "userRoleId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String userRoleId;

    @Column(name = "roleId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String roleId;

    @Column(name = "orgId",length = 24,columnDefinition = "char(24)")
    private String orgId;

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
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
