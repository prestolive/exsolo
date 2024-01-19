package cn.exsolo.console.org.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * TODO 将人员和部门的组合生成唯一的ID，只要搭配起来就可以做岗位
 * TODO 管组织就是管权限
 * TODO 可以探知分配权限的履行情况，酷
 * @author prestolive
 * @date 2021/9/11
 **/
@Deprecated
@Table("ex_org_schema")
@Index(name = "uq_ex_org_schema",unique = true,fields = "orgSchemaName")
@Index(name = "uq_ex_org_schema_code",unique = true,fields = "orgSchemaCode")
public class OrgSchemaPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "orgSchemaName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String orgSchemaName;

    @Column(name = "orgSchemaCode",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String orgSchemaCode;

    @Column(name = "defaultSchema",maxLength = 2,datatype = "boolean")
    private Boolean defaultSchema;

    @Column(name = "orderNo",nullable = true,maxLength = 2,datatype = "smallint")
    private Integer orderNo;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getOrgSchemaCode() {
        return orgSchemaCode;
    }

    public void setOrgSchemaCode(String orgSchemaCode) {
        this.orgSchemaCode = orgSchemaCode;
    }

    public String getOrgSchemaName() {
        return orgSchemaName;
    }

    public void setOrgSchemaName(String orgSchemaName) {
        this.orgSchemaName = orgSchemaName;
    }

    public Boolean getDefaultSchema() {
        return defaultSchema;
    }

    public void setDefaultSchema(Boolean defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
