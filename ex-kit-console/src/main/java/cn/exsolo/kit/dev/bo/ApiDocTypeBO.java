package cn.exsolo.kit.dev.bo;

import java.util.List;

/**
 * 对象结构
 * @author prestolive
 * @date 2023/3/24
 **/
public class ApiDocTypeBO {

    private String clz;

    private String datatype;

    private String name;

    private String tsType;

    private Boolean isObject ;

    private Boolean isListType;

    private Boolean isNullAble;

    private List<ApiDocTypeBO> fieldTypes;

    public Boolean getNullAble() {
        return isNullAble;
    }

    public void setNullAble(Boolean nullAble) {
        isNullAble = nullAble;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTsType() {
        return tsType;
    }

    public void setTsType(String tsType) {
        this.tsType = tsType;
    }

    public Boolean getObject() {
        return isObject;
    }

    public void setObject(Boolean object) {
        isObject = object;
    }

    public Boolean getListType() {
        return isListType;
    }

    public void setListType(Boolean listType) {
        isListType = listType;
    }

    public List<ApiDocTypeBO> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(List<ApiDocTypeBO> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
}
