
package cn.exsolo.kit.setting.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
* 用于系统设置的持久化po
 * @author prestolive
**/
@Table("s_setting_prop")
@Index(name = "idx_s_setting_prop",unique = true,fields = "clzName,fieldName")
public class ExSettingPropPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "moduleName",nullable = false,maxLength = 64,datatype = "varchar(64)")
    private String moduleName;

    @Column(name = "propName",nullable = false,maxLength = 64,datatype = "varchar(64)")
    private String propName;

    @Column(name = "clzName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String clzName;

    @Column(name = "fieldName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String fieldName;

    @Column(name = "inputType",maxLength = 64,datatype = "varchar(64)")
    private String inputType;

    @Column(name = "propValue",maxLength = 128,datatype = "varchar(256)")
    private String propValue;

    @Column(name = "lastModifyBy",maxLength = 24,datatype = "char(24)")
    private String lastModifyBy;

    @Column(name = "lstModifyTs",maxLength = 24,datatype = "char(19)")
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
