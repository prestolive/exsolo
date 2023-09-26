package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.stereotype.BaseData;

/**
 * @author prestolive
 * @date 2021/3/30
 **/

@Table("ex_user_role")
@BaseData(name="系统用户角色表")
@Index(name = "uq_ex_user_role",unique = true,fields = "userId,roleId,orgId")
public class UserRolePO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "userId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String userId;

    @Column(name = "roleId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String roleId;

    @Column(name = "orgId",maxLength = 24,datatype = "char(24)")
    private String orgId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
