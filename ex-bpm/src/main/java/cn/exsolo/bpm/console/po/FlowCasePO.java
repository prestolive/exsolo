package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/10/25
 **/

@Table(name="ex_bpm_flow_case",indexes = @Index(columnList = "bizId,code",unique = true ))
public class FlowCasePO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String code;

    @Column(name = "version",length = 2,columnDefinition = "smallint")
    private Integer version;

    @Column(name = "hash",length = 2,columnDefinition = "char(32)")
    private String hash;

    @Column(name = "bizId",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String bizId;

    @Column(name = "status",length = 32,columnDefinition = "varchar(32)")
    private FlowCaseStatusEnum status;

    @Column(name = "pass",columnDefinition = "smallint")
    private Boolean pass;

    @Column(name = "modifiedBy",length = 24,columnDefinition = "char(24)")
    private String modifiedBy;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public FlowCaseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(FlowCaseStatusEnum status) {
        this.status = status;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
