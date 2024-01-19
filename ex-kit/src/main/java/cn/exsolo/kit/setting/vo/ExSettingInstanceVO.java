package cn.exsolo.kit.setting.vo;

import cn.exsolo.kit.setting.stereotype.SettingProp;

/**
 * 用于内存实例和界面显示的vo
 * @author prestolive
 * @date 2023/10/4
 **/
public class ExSettingInstanceVO {

    private String id;

    private String moduleName;

    private String groupName;

    private String propName;

    private String clzName;

    private String fieldName;

    private String propValue;

    private String lastModifyBy;

    private String lstModifyTs;

    private SettingProp.InputType inputType;

    /* 以下是界面相关的属性，并不持久化 */

    private String dataRefCode;

    private String desc;

    private Integer sortNo;

    private String prefix;

    private String suffix;

    private Boolean isProtect;

    private Boolean isRequire;

    private Boolean isRequireInInit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDataRefCode() {
        return dataRefCode;
    }

    public void setDataRefCode(String dataRefCode) {
        this.dataRefCode = dataRefCode;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public SettingProp.InputType getInputType() {
        return inputType;
    }

    public void setInputType(SettingProp.InputType inputType) {
        this.inputType = inputType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Boolean getProtect() {
        return isProtect;
    }

    public void setProtect(Boolean protect) {
        isProtect = protect;
    }

    public Boolean getRequire() {
        return isRequire;
    }

    public void setRequire(Boolean require) {
        isRequire = require;
    }

    public Boolean getRequireInInit() {
        return isRequireInInit;
    }

    public void setRequireInInit(Boolean requireInInit) {
        isRequireInInit = requireInInit;
    }

    @Override
    public String toString() {
        return String.format("%s-%s:%s",clzName,fieldName,propValue);
    }
}
