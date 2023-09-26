package cn.exsolo.console.org.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.console.org.item.OrgNodeStatusEnum;
import cn.exsolo.kit.item.stereotype.BaseData;

/**
 * @author prestolive
 * @date 2021/9/22
 **/

@Table("ex_org_node")
@BaseData(name="组织节点表")
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
    @Column(name = "nodeInnerCode",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String nodeInnerCode;

    @Column(name = "parentId",maxLength = 24,datatype = "char(24)")
    private String parentId;

    @Column(name = "status",maxLength = 24,datatype = "varchar(24)")
    private OrgNodeStatusEnum status;

    @Column(name = "sortNo",datatype = "int(2)")
    private Integer sortNo;


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

    public String getNodeInnerCode() {
        return nodeInnerCode;
    }

    public void setNodeInnerCode(String nodeInnerCode) {
        this.nodeInnerCode = nodeInnerCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public OrgNodeStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrgNodeStatusEnum status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
