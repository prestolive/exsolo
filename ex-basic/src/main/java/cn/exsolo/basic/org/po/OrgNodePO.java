package cn.exsolo.basic.org.po;

import cn.exsolo.batis.core.AbstractPO;
import cn.exsolo.kit.item.ItemCommStatusEnum;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/9/22
 **/

@Table(name="ex_org_node")
public class OrgNodePO  extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "schemaCode",nullable = false,length = 24,columnDefinition = "varchar(64)")
    private String schemaCode;

    @Column(name = "orgName",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String orgName;

    /**
     * 编码，一般来至外部
     */
    @Column(name = "orgCode",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String orgCode;

    /**
     * 内码，每个节点2位16进制码，单个目录下最高支持256个节点，128长度支持64个层级
     */
    @Column(name = "innerCode",length = 128,columnDefinition = "varchar(128)")
    private String innerCode;

    @Column(name = "deep",length = 2,columnDefinition = "int2")
    private Integer deep;

    @Column(name = "parentId",length = 24,columnDefinition = "char(24)")
    private String parentId;

    @Column(name = "status",length = 24,columnDefinition = "varchar(24)")
    private ItemCommStatusEnum status;

    @Column(name = "sortNo",columnDefinition = "int(2)")
    private Integer sortNo;

    @Column(name = "childCounts",columnDefinition = "int(2)")
    private Integer childCounts;

    @Column(name = "modifiedBy",length = 24,columnDefinition = "char(24)")
    private String modifiedBy;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
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

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }
}
