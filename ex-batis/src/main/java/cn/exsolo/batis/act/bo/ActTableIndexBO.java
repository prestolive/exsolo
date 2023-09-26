package cn.exsolo.batis.act.bo;

/**
 * @author prestolive
 * @date 2021/3/8
 **/
public class ActTableIndexBO {

    private String tableName;

    private String name;

    private boolean unique;

    private String fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
