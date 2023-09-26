package cn.exsolo.batis.act.dto;

/**
 * @author prestolive
 * @date 2021/3/9
 **/
public class ActDdTableIndexDTO {

    private String tableName;

    private String indexName;

    private Boolean isUnique;

    private String indexFields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Boolean getUnique() {
        return isUnique;
    }

    public void setUnique(Boolean unique) {
        isUnique = unique;
    }

    public String getIndexFields() {
        return indexFields;
    }

    public void setIndexFields(String indexFields) {
        this.indexFields = indexFields;
    }

}
