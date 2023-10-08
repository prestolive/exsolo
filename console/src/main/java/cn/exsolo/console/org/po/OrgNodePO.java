package cn.exsolo.console.org.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.ItemCommStatusEnum;

/**
 * @author prestolive
 * @date 2021/9/22
 **/

@Table("ex_org_node")
public class OrgNodePO  extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "schemaId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String schemaId;

    @Column(name = "orgName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String orgName;

    /**
     * 编码，一般来至外部
     */
    @Column(name = "orgCode",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String orgCode;

    /**
     * 内码，每个节点2位16进制码，单个目录下最高支持256个节点，128长度支持64个层级
     */
    @Column(name = "innerCode",maxLength = 128,datatype = "varchar(128)")
    private String innerCode;

    @Column(name = "parentId",maxLength = 24,datatype = "char(24)")
    private String parentId;

    @Column(name = "status",maxLength = 24,datatype = "varchar(24)")
    private ItemCommStatusEnum status;

    @Column(name = "sortNo",datatype = "int(2)")
    private Integer sortNo;

    @Column(name = "childCounts",datatype = "int(2)")
    private Integer childCounts;

    @Column(name = "modifiedBy",maxLength = 24,datatype = "char(24)")
    private String modifiedBy;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ItemCommStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemCommStatusEnum status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getChildCounts() {
        return childCounts;
    }

    public void setChildCounts(Integer childCounts) {
        this.childCounts = childCounts;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
