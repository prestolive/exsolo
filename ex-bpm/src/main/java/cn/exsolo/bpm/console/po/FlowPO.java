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

@Table(name="ex_bpm_flow",indexes = @Index(columnList = "code",unique = true))
public class FlowPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "code",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String code;

    @Column(name = "label",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String label;

    /**
     * 当前正在编辑的版本，不一定激活中、必须比已激活的版本高
     */
    @Column(name = "currVersion",length = 2,columnDefinition = "smallint")
    private Integer currVersion;

    /**
     * 当前激活的版本
     */
    @Column(name = "version",length = 2,columnDefinition = "smallint")
    private Integer version;

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
