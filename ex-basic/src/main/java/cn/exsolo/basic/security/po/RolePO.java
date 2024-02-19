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

@Table(name="ex_role",indexes = @Index(columnList = "roleName",unique = true))
public class RolePO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "roleName",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String roleName;

    @Column(name = "roleSchema",nullable = false,length = 128,columnDefinition = "varchar(32)")
    private String roleSchema;

    @Column(name = "operator",length = 24,columnDefinition = "char(24)")
    private String operator;

    @Column(name = "moduleCount",length = 2,columnDefinition = "smallint")
    private Integer moduleCount;

    @Column(name = "permissionCount",length = 2,columnDefinition = "smallint")
    private Integer permissionCount;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getModuleCount() {
        return moduleCount;
    }

    public void setModuleCount(Integer moduleCount) {
        this.moduleCount = moduleCount;
    }

    public Integer getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(Integer permissionCount) {
        this.permissionCount = permissionCount;
    }

    public String getRoleSchema() {
        return roleSchema;
    }

    public void setRoleSchema(String roleSchema) {
        this.roleSchema = roleSchema;
    }
}
