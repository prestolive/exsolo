package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * @author prestolive
 * @date 2021/9/3
 **/

@Table("ex_user_flat_permission")
public class UserFlatPermissionPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "userId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String userId;

    @Column(name = "userRoleId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String userRoleId;

    @Column(name = "roleId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String roleId;

    @Column(name = "orgId",maxLength = 24,datatype = "char(24)")
    private String orgId;

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
