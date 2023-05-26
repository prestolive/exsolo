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

@Table("ex_role_power")
@BaseData(name="系统角色权限表")
@Index(name = "uq_ex_role_power",unique = true,fields = "roleId,powerCode")
public class RolePowerPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "roleId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String roleId;

    @Column(name = "powerCode",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String powerCode;

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

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
