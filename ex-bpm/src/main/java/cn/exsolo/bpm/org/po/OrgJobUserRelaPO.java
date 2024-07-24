package cn.exsolo.bpm.org.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 组织用户和组织职责关系表
 * @author prestolive
 * @date 2021/6/26
 **/
@Table(name="ex_bpm_org_job_user_rela",indexes = @Index(columnList = "orgUserId,orgJobId",unique = true))
public class OrgJobUserRelaPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "orgUserId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String orgUserId;

    /**
     * 冗余存，根据orgUserId 带出
     */
    @Column(name = "orgId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String orgId;

    /**
     * 冗余存，根据orgUserId 带出
     */
    @Column(name = "userId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String userId;

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

    public String getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(String orgUserId) {
        this.orgUserId = orgUserId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

