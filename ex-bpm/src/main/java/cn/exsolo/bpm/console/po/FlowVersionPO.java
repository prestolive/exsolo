package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.bpm.console.ExBpmFlowStatusEnum;

/**
 * @author prestolive
 * @date 2023/10/25
 **/

@Table("ex_bpm_flow_version")
@Index(name = "uq_ex_bpm_flow_version",unique = true,fields = "code,version")
public class FlowVersionPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String code;

    @Column(name = "version",maxLength = 2,datatype = "smallint")
    private Integer version;

    @Column(name = "status",datatype = "varchar(16)")
    private ExBpmFlowStatusEnum status;

    @Column(name = "hash",maxLength = 2,datatype = "char(32)")
    private String hash;

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
