package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractPO;
import cn.exsolo.bpm.console.ExBpmFlowStatusEnum;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/10/25
 **/

@Table(name="ex_bpm_flow_version",indexes = @Index(columnList = "code,version",unique = true))
public class FlowVersionPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String code;

    @Column(name = "version",length = 2,columnDefinition = "smallint")
    private Integer version;

    @Column(name = "status",columnDefinition = "varchar(16)")
    private ExBpmFlowStatusEnum status;

    @Column(name = "hash",length = 2,columnDefinition = "char(32)")
    private String hash;

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

    public ExBpmFlowStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExBpmFlowStatusEnum status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
