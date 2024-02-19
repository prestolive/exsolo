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

@Table(name="ex_user_role",indexes = @Index(columnList = "userId,roleId,orgId",unique = true))
public class UserRolePO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "userId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String userId;

    @Column(name = "roleId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String roleId;

    @Column(name = "orgId",length = 24,columnDefinition = "char(24)")
    private String orgId;

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
