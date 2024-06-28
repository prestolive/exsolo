package cn.exsolo.kit.dev.bo;

import java.util.List;

/**
 * @Table注解用于生成代码的参数
 * @author prestolive
 * @date 2024/6/26
 **/
public class ModelMetaBO {

    private String clzName;

    private String packageName;

    private String bizName;

    private String dbTableName;

    private List<ModelFieldBO> fields;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClzName() {
        return clzName;
    }

    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public List<ModelFieldBO> getFields() {
        return fields;
    }

    public void setFields(List<ModelFieldBO> fields) {
        this.fields = fields;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }
}
