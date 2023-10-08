package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * @author prestolive
 * @date 2021/3/30
 **/

@Table("ex_role")
@Index(name = "uq_ex_role",unique = true,fields = "roleName")
public class RolePO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "roleName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String roleName;

    @Column(name = "roleSchema",nullable = false,maxLength = 128,datatype = "varchar(32)")
    private String roleSchema;

    @Column(name = "operator",maxLength = 24,datatype = "char(24)")
    private String operator;

    @Column(name = "moduleCount",maxLength = 2,datatype = "smallint")
    private Integer moduleCount;

    @Column(name = "permissionCount",maxLength = 2,datatype = "smallint")
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
