package cn.exsolo.bpm.org.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 组织和用户关系表
 * @author prestolive
 * @date 2024/6/26
 **/

@Table(name="ex_bpm_org_user",indexes = @Index(columnList = "orgId,userId",unique = true))
public class OrgUserRelaPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "orgId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String orgId;

    @Column(name = "userId",nullable = false,length = 24,columnDefinition = "char(24)")
    private String userId;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
