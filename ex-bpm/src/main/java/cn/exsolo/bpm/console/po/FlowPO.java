package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * @author prestolive
 * @date 2023/10/25
 **/

@Table("ex_bpm_flow")
@Index(name = "uq_ex_bpm_flow_a",unique = true,fields = "code")
public class FlowPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String code;

    @Column(name = "label",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String label;

    /**
     * 当前正在编辑的版本，不一定激活中、必须比已激活的版本高
     */
    @Column(name = "currVersion",maxLength = 2,datatype = "smallint")
    private Integer currVersion;

    /**
     * 当前激活的版本
     */
    @Column(name = "version",maxLength = 2,datatype = "smallint")
    private Integer version;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(Integer currVersion) {
        this.currVersion = currVersion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
