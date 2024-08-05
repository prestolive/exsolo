package cn.exsolo.bpm.org.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 组织和职责分配关系表
 * @author prestolive
 * @date 2021/6/26
 **/

@Deprecated
@Table(name="ex_bpm_org_job_rela",indexes = @Index(columnList = "orgId,orgJobId",unique = true))
public class OrgJobRelaPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "orgId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String orgId;

    @Column(name = "orgJobId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String orgJobId;

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgJobId() {
        return orgJobId;
    }

    public void setOrgJobId(String orgJobId) {
        this.orgJobId = orgJobId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
