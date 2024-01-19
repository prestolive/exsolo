package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * @author prestolive
 * @date 2023/10/25
 **/

@Table("ex_bpm_flow_case")
//@Index(name = "uq_ex_bpm_flow_case",unique = true,fields = "bizId,code")
public class FlowCasePO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String code;

    @Column(name = "version",maxLength = 2,datatype = "smallint")
    private Integer version;

    @Column(name = "hash",maxLength = 2,datatype = "char(32)")
    private String hash;

    @Column(name = "bizId",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String bizId;

    @Column(name = "status",maxLength = 32,datatype = "varchar(32)")
    private FlowCaseStatusEnum status;

    @Column(name = "pass",datatype = "smallint")
    private Boolean pass;

    @Column(name = "modifiedBy",maxLength = 24,datatype = "char(24)")
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
