package cn.exsolo.kit.setting.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 用于系统设置的持久化po
 *
 * @author prestolive
 **/
@Table(name = "s_setting_prop", uniqueConstraints = @UniqueConstraint(columnNames = "clzName,fieldName"))
public class ExSettingPropPO extends AbstractPO {

    @Id
    @Column(name = "id", nullable = false, length = 24, columnDefinition = "char(24)")
    private String id;

    @Column(name = "moduleName", nullable = false, length = 64, columnDefinition = "varchar(64)")
    private String moduleName;

    @Column(name = "propName", nullable = false, length = 64, columnDefinition = "varchar(64)")
    private String propName;

    @Column(name = "clzName", nullable = false, length = 128, columnDefinition = "varchar(128)")
    private String clzName;

    @Column(name = "fieldName", nullable = false, length = 128, columnDefinition = "varchar(128)")
    private String fieldName;

    @Column(name = "inputType", length = 64, columnDefinition = "varchar(64)")
    private String inputType;

    @Column(name = "propValue", length = 128, columnDefinition = "varchar(256)")
    private String propValue;

    @Column(name = "lastModifyBy", length = 24, columnDefinition = "char(24)")
    private String lastModifyBy;

    @Column(name = "lstModifyTs", length = 24, columnDefinition = "char(19)")
    private String lstModifyTs;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getClzName() {
        return clzName;
    }

    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public String getLstModifyTs() {
        return lstModifyTs;
    }

    public void setLstModifyTs(String lstModifyTs) {
        this.lstModifyTs = lstModifyTs;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
