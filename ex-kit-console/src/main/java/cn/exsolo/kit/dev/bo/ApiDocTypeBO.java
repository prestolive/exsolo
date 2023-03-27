package cn.exsolo.kit.dev.bo;

import java.util.List;

/**
 * 对象结构
 * @author prestolive
 * @date 2023/3/24
 **/
public class ApiDocTypeBO {

    private Class clz;

    private String datatype;

    private String name;

    private String tsType;

    private Boolean isObject ;

    private Boolean isArray;

    private List<ApiDocTypeBO> fieldTypes;

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

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
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

    public Boolean getArray() {
        return isArray;
    }

    public void setArray(Boolean array) {
        isArray = array;
    }

    public List<ApiDocTypeBO> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(List<ApiDocTypeBO> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
}
