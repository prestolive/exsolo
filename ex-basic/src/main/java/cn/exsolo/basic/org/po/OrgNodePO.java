package cn.exsolo.basic.org.po;

import cn.exsolo.basic.tree.CommonTreeNodePO;
import cn.exsolo.kit.item.ItemCommStatusEnum;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/9/22
 **/

@Table(name="ex_org_node")
public class OrgNodePO  extends CommonTreeNodePO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "orgName",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String orgName;

    /**
     * 编码，一般来至外部
     */
    @Column(name = "orgCode",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String orgCode;

    @Column(name = "status",length = 24,columnDefinition = "varchar(24)")
    private ItemCommStatusEnum status;

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

    public ItemCommStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemCommStatusEnum status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
